package co.edu.udea.compumovil.gr03_20231.lab1.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import co.edu.udea.compumovil.gr03_20231.lab1.R
import co.edu.udea.compumovil.gr03_20231.lab1.data.service.LocationService
import co.edu.udea.compumovil.gr03_20231.lab1.databinding.ActivityContactDataBinding
import co.edu.udea.compumovil.gr03_20231.lab1.domain.ContactData
import co.edu.udea.compumovil.gr03_20231.lab1.domain.DataBind
import co.edu.udea.compumovil.gr03_20231.lab1.domain.PersonalData
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ContactDataActivity : AppCompatActivity() {

    private lateinit var locationService: LocationService
    private lateinit var countryAutoComplete: AutoCompleteTextView
    private lateinit var stateAutoComplete: AutoCompleteTextView
    private lateinit var cityAutoComplete: AutoCompleteTextView
    private lateinit var bindingContact: ActivityContactDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingContact = ActivityContactDataBinding.inflate(layoutInflater)
        setContentView(bindingContact.root)
        val nextButton = findViewById<Button>(R.id.btn_next_2)
        nextButton.setOnClickListener {
            val model = ContactData(
                bindingContact.lblPhone.text.toString(),
                bindingContact.lblAddress.text.toString(),
                bindingContact.lblMail.text.toString(),
                bindingContact.autocompleteCountry.text.toString(),
                bindingContact.autocompleteState.text.toString(),
                bindingContact.autocompleteCity.text.toString()
            )
            if (validateFields(model)) {
                val bundle = intent.extras;
                val dato = bundle?.getString("mykey")
                val gson = Gson()
                var personalModel = gson.fromJson(dato, PersonalData::class.java)
                val data = DataBind(personalModel, model)
                printInformation(data)
            }
        }

        locationService = LocationService()
        countryAutoComplete = findViewById(R.id.autocomplete_country)
        stateAutoComplete = findViewById(R.id.autocomplete_state)
        cityAutoComplete = findViewById(R.id.autocomplete_city)


        lifecycleScope.launch {
            fillCountryField()
        }
    }

    private fun validateFields(model: ContactData): Boolean {
        if (model.phone == "" || model.email == "" || model.country == "") {
            Toast.makeText(this, "Verifique los campos obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun printInformation(data: DataBind) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(data.toString())

        val dialog = builder.create()
        dialog.show()
        Log.d(TAG, data.toString())
    }

    private suspend fun fillCountryField() {
        val countries = locationService.getAllCountries()
        val adapter = ArrayAdapter(
            this@ContactDataActivity,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        countryAutoComplete.setAdapter(adapter)
        countryAutoComplete.isEnabled = true
        countryAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            val countryItem = parent.getItemAtPosition(position).toString()
            lifecycleScope.launch {
                fillStateField(countryItem)
            }
        }
    }

    private suspend fun fillStateField(country: String) {
        val states = locationService.getAllStates(country)
        val adapter = ArrayAdapter(
            this@ContactDataActivity,
            android.R.layout.simple_dropdown_item_1line,
            states
        )
        stateAutoComplete.setAdapter(adapter)
        stateAutoComplete.isEnabled = true
        stateAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            var stateItem = parent.getItemAtPosition(position).toString()
            lifecycleScope.launch {
                fillCityField(stateItem)
            }
        }
    }

    private suspend fun fillCityField(city: String) {
        val states = locationService.getAllCity(city)
        val adapter = ArrayAdapter(
            this@ContactDataActivity,
            android.R.layout.simple_dropdown_item_1line,
            states
        )
        cityAutoComplete.setAdapter(adapter)
        cityAutoComplete.isEnabled = true
    }
}