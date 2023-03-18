package co.edu.udea.compumovil.gr03_20231.lab1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.lifecycleScope
import co.edu.udea.compumovil.gr03_20231.lab1.R
import co.edu.udea.compumovil.gr03_20231.lab1.data.service.LocationService
import kotlinx.coroutines.launch

class ContactDataActivity : AppCompatActivity() {

    private lateinit var locationService: LocationService
    private lateinit var countryAutoComplete: AutoCompleteTextView
    private lateinit var stateAutoComplete: AutoCompleteTextView
    private lateinit var cityAutoComplete: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_data)
        locationService = LocationService()
        countryAutoComplete = findViewById(R.id.autocomplete_country)
        stateAutoComplete = findViewById(R.id.autocomplete_state)
        cityAutoComplete = findViewById(R.id.autocomplete_city)

        lifecycleScope.launch {
            fillCountryField()
        }
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