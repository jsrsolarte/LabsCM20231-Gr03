package co.edu.udea.compumovil.gr03_20231.lab1.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import co.edu.udea.compumovil.gr03_20231.lab1.R
import co.edu.udea.compumovil.gr03_20231.lab1.databinding.ActivityPersonalDataBinding
import co.edu.udea.compumovil.gr03_20231.lab1.domain.PersonalData
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class PersonalDataActivity : AppCompatActivity() {

    private val myCalendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private lateinit var editTextDate: EditText
    private lateinit var bindingPersonal: ActivityPersonalDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingPersonal = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(bindingPersonal.root)
        //setContentView(R.layout.activity_personal_data)
        editTextDate = findViewById(R.id.labelbirthday)

        setSpinnerEducational()

        setDatePicker()

        setNextButton()

    }


    private fun setNextButton() {
        val nextButton = findViewById<Button>(R.id.button_next)
        nextButton.setOnClickListener {


            var name: String = bindingPersonal.lblName.text.toString()
            var lastName: String = bindingPersonal.lblLastname.text.toString()
            var sex: String = if (bindingPersonal.rdFemale.isChecked) {
                "Femenino"
            } else {
                "Masculino"
            }
            var birthday: String = bindingPersonal.labelbirthday.text.toString()
            var educationLvl: String = bindingPersonal.spinner.selectedItem.toString()
            var model = PersonalData(name, lastName, sex, birthday, educationLvl)


            if (validateFields(model)) {
                var gson = Gson()
                var jsonString = gson.toJson(model)
                val intent = Intent(this, ContactDataActivity::class.java)
                intent.putExtra("mykey", jsonString)
                startActivity(intent)
            }
        }
    }

    private fun validateFields(model: PersonalData): Boolean {
        if (model.name == "" || model.lastName == "") {
            Toast.makeText(this, "Verifique los campos obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun setSpinnerEducational() {
        val spinner = findViewById<Spinner>(R.id.spinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    (parent?.getChildAt(0) as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@PersonalDataActivity,
                            R.color.gray
                        )
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.educational_options_array,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
    }

    private fun setDatePicker() {
        var buttonDatePicker = findViewById<Button>(R.id.button_date_picker)
        buttonDatePicker.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                dateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            editTextDate.setText(dateFormat.format(myCalendar.time))
        }
}