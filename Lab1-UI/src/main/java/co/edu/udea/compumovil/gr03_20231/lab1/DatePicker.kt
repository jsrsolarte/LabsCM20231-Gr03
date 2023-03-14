package co.edu.udea.compumovil.gr03_20231.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class DatePicker : AppCompatActivity() {

    lateinit var lblbirthday:EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        lblbirthday = findViewById<EditText>(R.id.labelbirthday);
        lblbirthday.setOnClickListener{
            showPickerDialog()
        }

    }

    private fun showPickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        lblbirthday.setText("$day/$month/$year")
    }

}