package co.edu.udea.compumovil.gr03_20231.lab1

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

class PersonalDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        val btnSelectDate = findViewById<Button>(R.id.datepickerbutton)
        btnSelectDate.setOnClickListener {
            // Obtiene la fecha actual
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Crea un DatePickerDialog que muestre el datepicker
            val datePickerDialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
                // Aquí puedes hacer lo que quieras con la fecha seleccionada
            }, year, month, day)

            // Muestra el dialogo del datepicker
            datePickerDialog.show()
        }

    }
}