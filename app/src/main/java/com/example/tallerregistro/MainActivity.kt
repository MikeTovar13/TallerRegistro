package com.example.tallerregistro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {

    // Names type documents
    private val optionsTypeDocument: Array<String>
        get() = arrayOf(getString(R.string.document_CC),getString(R.string.document_CE),
                getString(R.string.document_PA),getString(R.string.document_RC),
                getString(R.string.document_TI))

    // Name acronym type documents
    private val acronymTypeDocument
        get() = arrayOf(getString(R.string.document_acronymCC),getString(R.string.document_acronymCE),
                getString(R.string.document_acronymPA),getString(R.string.document_acronymRC),
                getString(R.string.document_acronymTI))

    // Hobbies checked true
    private var hobbiesChecked = booleanArrayOf(false, false, false, false, false, false, false, false, false, false)

    // Name hobbies existent
    private val hobbiesNames
        get() = arrayOf(getString(R.string.hobbie_1), getString(R.string.hobbie_2),
                getString(R.string.hobbie_3),getString(R.string.hobbie_4), getString(R.string.hobbie_5),
                getString(R.string.hobbie_6), getString(R.string.hobbie_7), getString(R.string.hobbie_8),
                getString(R.string.hobbie_9), getString(R.string.hobbie_10))

    // Name months of year
    private val months
        get() = arrayOf(getString(R.string.january), getString(R.string.february), getString(R.string.march),
                getString(R.string.april), getString(R.string.may), getString(R.string.june),
                getString(R.string.july), getString(R.string.august), getString(R.string.september),
                getString(R.string.october), getString(R.string.november), getString(R.string.december))


    @SuppressLint("NewApi")
    @RequiresApi(VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button Clean Form
        val resetForm = findViewById<Button>(R.id.reset_button)
        resetForm.setOnClickListener {
            this.resetForm()
        }

        // Button show type document user
        val typeDocumentObj = findViewById<Button>(R.id.type_document_button)
        typeDocumentObj.setOnClickListener{
            this.getTypeDocument()
        }

        // Button show list hobbies user
        val getHobbiesObj = findViewById<Button>(R.id.hobbies_button)
        getHobbiesObj.setOnClickListener{
            this.getHobbies()
        }

        // Button show Date picker dialog
        val getBirthDateObj = findViewById<Button>(R.id.birth_date_button)
        getBirthDateObj.setOnClickListener {
            this.getBirthday()
        }

        // Function get data user
        val getDataObj = findViewById<Button>(R.id.register_button)
        getDataObj.setOnClickListener {
            this.register()
        }
    }

    // Function button reset
    private fun resetForm() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Limpiar formulario")
        builder.setMessage("Estás seguro que quieres limpiar los datos del formulario")
        builder.setPositiveButton(R.string.agree) { _, _ ->
             this.cleanForm()
        }
        builder.setNegativeButton(R.string.cancel, null)
        builder.setCancelable(false)
        builder.show()
    }

    // Function clean form
    private fun cleanForm() {
        findViewById<EditText>(R.id.name_input).setText("") // Name
        findViewById<EditText>(R.id.last_name_input).setText("") // Last name
        findViewById<Button>(R.id.type_document_button).text = getString(R.string.type_document_button) // Type document
        findViewById<EditText>(R.id.document_input).setText("") // Number document
        findViewById<Button>(R.id.birth_date_button).text = getString(R.string.birth_date_button) // Birth date
        findViewById<TextView>(R.id.hobbies_text_choice).text = "" // Hobbies text empty
        hobbiesChecked = booleanArrayOf(false, false, false, false, false, false, false, false, false, false) // Values hobbies init false
        findViewById<EditText>(R.id.password_input).setText("") // Password
        findViewById<EditText>(R.id.confirm_password_input).setText("") // Confirm password
        Toast.makeText(this, getText(R.string.clean_form), Toast.LENGTH_LONG).show()
    }

    // Function show type document user
    private fun getTypeDocument() {
        val typeDocumentObj = findViewById<Button>(R.id.type_document_button)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.type_document_textView)
        builder.setItems(this.optionsTypeDocument) { _, position ->
            typeDocumentObj.text = acronymTypeDocument[position];
        }
        builder.show()
    }

    // Function get list hobbies user
    @SuppressLint("SetTextI18n")
    private fun getHobbies() {

        val hobbiesTextChoice = findViewById<TextView>(R.id.hobbies_text_choice)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.type_document_textView)
        builder.setPositiveButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, hobbiesChecked) { _, position, isChecked ->
            hobbiesChecked[position] = isChecked
            if (isChecked){
                hobbiesTextChoice.setText(hobbiesTextChoice.text.toString() + hobbiesNames[position] + " | ")
            }else{
                hobbiesTextChoice.setText(hobbiesTextChoice.text.toString().replace(hobbiesNames[position]+" | ", ""))
            }

        }
        builder.show()
    }

    // Function get date user
    @RequiresApi(VERSION_CODES.N)
    private fun getBirthday() {
        val dateButton = findViewById<Button>(R.id.birth_date_button)
        val dialog = DatePickerDialog(this, { _,year,month,day ->
            val numMonth = month+1;
            dateButton.text = ("$year-$numMonth-$day")
        }, 1990, 0, 1) // Meses se toman de 0 a 11 para enero a diciembre
        dialog.show()
    }

    // Function get data user
    @RequiresApi(O)
    private fun register() {

        // Get data for inputs
        var fieldsEmpty = " "

        val nameObj = findViewById<EditText>(R.id.name_input)
        if (nameObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.name_input) + " | "
        }

        val lastNameObj = findViewById<EditText>(R.id.last_name_input)
        if (lastNameObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.last_name_input) + " | "
        }

        val typeDocumentObj = findViewById<Button>(R.id.type_document_button)
        if (typeDocumentObj.text.toString() == "" || typeDocumentObj.text.toString() == "N.N.") {
            fieldsEmpty += getString(R.string.type_document_textView) + " | "
        }

        val numberDocumentObj = findViewById<EditText>(R.id.document_input)
        if (numberDocumentObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.document_input) + " | "
        }


        // https://grokonez.com/kotlin/kotlin-get-current-datetime

        val birthDateObj = findViewById<Button>(R.id.birth_date_button)
        val currentDateTime = LocalDateTime.now()

        println(currentDateTime.format(DateTimeFormatter.ISO_DATE))
        println(birthDateObj.text.toString())
        if (birthDateObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.birth_date_button) + " | "
        }

        /*
        //val hobbiesObj = findViewById<Button>(R.id.hobbies_button)
        if (type_document.text.toString() == "") {
            fieldsEmpty += getString(R.string.last_name_input) + " | "
        }
        */

        val passwordObj = findViewById<EditText>(R.id.password_input)
        if (passwordObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.password_input) + " | "
        }

        val confirmPasswordObj = findViewById<EditText>(R.id.confirm_password_input)
        if (confirmPasswordObj.text.toString() == "") {
            fieldsEmpty += getString(R.string.confirm_password_input) + " | "
        }

        // Init verifications
        if (fieldsEmpty != " ") {
            // Verify data empty
            this.verifiedData(getString(R.string.fields_required), fieldsEmpty, 1)
        } else if (!passwordSame(passwordObj.text.toString(), confirmPasswordObj.text.toString())){
            // Verify different passwords
            this.verifiedData(getString(R.string.different_passwords), getString(R.string.different_passwords_description), 2)
        } else {
            // Set data to Class User
            val user = User(
                    name = nameObj.text.toString(),
                    last_name = lastNameObj.text.toString(),
                    type_document = typeDocumentObj.text.toString(),
                    number_document = numberDocumentObj.text.toString(),
                    birth_date = birthDateObj.text.toString(),
                    //hobbies = "1",
                    password = passwordObj.text.toString())

            println(user)
            this.registerSuccess()
        }
    }

    // Function verified data
    private fun verifiedData(titleShow: String, textShow: String, type: Int) {
        val builder = AlertDialog.Builder(this)

        if (type == 1) {
            // type 1 for fields empty
            builder.setTitle(titleShow)
            builder.setMessage(getString(R.string.fields_required_description) + textShow)
        } else if (type == 2) {
            // type 2 for different passwords
            builder.setTitle(titleShow)
            builder.setMessage(textShow)
        }
        builder.setPositiveButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.show()
    }

    // Function verify password same
    private fun passwordSame(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword;
    }

    // Function register user success
    private fun registerSuccess() {
        Toast.makeText(this, "Resgistro Exitoso", Toast.LENGTH_SHORT).show()
    }

}