package com.example.tallerregistro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.solver.widgets.WidgetContainer
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    // Names type documents
    private val optionsTypeDocument: Array<String>
        get() = resources.getStringArray(R.array.document_name)

    // Name acronym type documents
    private val acronymTypeDocument
        get() = resources.getStringArray(R.array.acronym)

    // Name hobbies existent
    private val hobbiesNames
        get() = resources.getStringArray(R.array.hobbies)

    // Hobbies checked true
    private var hobbiesChecked:BooleanArray = booleanArrayOf()

    @SuppressLint("NewApi")
    @RequiresApi(VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hobbiesChecked = BooleanArray(hobbiesNames.size)

        // Call functions
        this.setupButtons()
    }

    // Function button reset
    private fun resetForm() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_button_reset))
        builder.setMessage(getString(R.string.message_button_reset))
        builder.setPositiveButton(R.string.agree) { _, _ ->
             this.cleanForm(true)
        }
        builder.setNegativeButton(R.string.cancel, null)
        builder.setCancelable(false)
        builder.show()
    }

    // Function clean form
    private fun cleanForm(bool: Boolean) {
        name_input.setText("") // Name
        last_name_input.setText("") // Last name
        type_document_button.text = getString(R.string.type_document_button) // Type document
        document_input.setText("") // Number document
        birth_date_button.text = getString(R.string.date) // Birth date
        hobbies_text_choice.text = "" // Hobbies text empty
        hobbiesChecked = BooleanArray(hobbiesNames.size) // Values hobbies init false
        password_input.setText("") // Password5
        confirm_password_input.setText("") // Confirm password
        if (bool) {
            Toast.makeText(this, getText(R.string.clean_form), Toast.LENGTH_LONG).show()
        }
    }

    // Function show type document user
    private fun getTypeDocument() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.type_document_textView)
        builder.setItems(this.optionsTypeDocument) { _, position ->
            type_document_button.text = acronymTypeDocument[position];
        }
        builder.show()
    }

    // Function get list hobbies user
    @SuppressLint("SetTextI18n")
    private fun getHobbies() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.hobbies_textView)
        builder.setPositiveButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, hobbiesChecked) { _, position, isChecked ->
            hobbiesChecked[position] = isChecked
            if (isChecked){
                hobbies_text_choice.setText(hobbies_text_choice.text.toString() + hobbiesNames[position] + " | ")
            }else{
                hobbies_text_choice.setText(hobbies_text_choice.text.toString().replace(hobbiesNames[position]+" | ", ""))
            }
        }
        builder.show()
    }

    // Function get date user
    @RequiresApi(VERSION_CODES.N)
    private fun getBirthday() {
        var year =1990
        var month =0
        var day =1

        if (birth_date_button.text.toString()!= getString(R.string.date)){
            val date = birth_date_button.text.toString().split("-")
            year = date[0].toInt()
            month = date[1].toInt()-1
            day = date[2].toInt()
        }

        val dialog = DatePickerDialog(this, { _,yearDate,monthDate,dayDate ->
            val numMonth = monthDate+1;
            birth_date_button.text = ("$yearDate-$numMonth-$dayDate")
        }, year, month, day) // Meses se toman de 0 a 11 para enero a diciembre
        dialog.show()
    }

    // Function get data user
    @RequiresApi(O)
    private fun register() {

        // Get data for inputs
        var fieldsEmpty = " "

        // Validate name
        fieldsEmpty+= this.validateFields(name_input, "",  getString(R.string.name_input))

        // Validate last name
        fieldsEmpty+= this.validateFields(last_name_input, "",  getString(R.string.last_name_input))

        // Validate type document
        fieldsEmpty += this.validateFields(type_document_button, getString(R.string.type_document_button), getString(R.string.type_document_textView))

        // Validate number document
        fieldsEmpty+= this.validateFields(document_input, "",  getString(R.string.document_input))

        // Validate birth date
        val isDateValidate = this.validateFields(birth_date_button, getString(R.string.date),  getString(R.string.birth_date_textView))

        var dateFuture: Boolean = false
        if (isDateValidate!="") {
            fieldsEmpty += isDateValidate
        } else {
            val currentDateTime = LocalDateTime.now().toLocalDate()
            val date = birth_date_button.text.toString().split("-")
            val dateUser = LocalDate.of(date[0].toInt(), date[1].toInt(),date[2].toInt())
            dateFuture = dateUser > currentDateTime  // Validation birth date
        }

        // Validate hobbies
        fieldsEmpty+= this.validateFields(hobbies_text_choice, "",  getString(R.string.hobbies_textView))

        // Validate password
        fieldsEmpty+= this.validateFields(password_input, "",  getString(R.string.password_input))

        // Validate Confirm password
        fieldsEmpty+= this.validateFields(confirm_password_input, "",  getString(R.string.confirm_password_input))

        // Init verifications
        if (fieldsEmpty != " ") {
            // Verify data empty
            this.verifiedData(getString(R.string.fields_required), fieldsEmpty, 1)
        } else if (dateFuture) {
            // Verify date in the future
            this.verifiedData(getString(R.string.future_date), getString(R.string.future_date_description), 2)
        } else if (!isPasswordSame(password_input.text.toString(), confirm_password_input.text.toString())){
            // Verify different passwords
            this.verifiedData(getString(R.string.different_passwords), getString(R.string.different_passwords_description), 3)
        } else {
            // Set data to Class User
            val user = User(
                    name = name_input.text.toString(),
                    last_name = last_name_input.text.toString(),
                    type_document = optionsTypeDocument[acronymTypeDocument.indexOf(type_document_button.text.toString())],
                    number_document = document_input.text.toString(),
                    birth_date = birth_date_button.text.toString(),
                    hobbies_v = hobbies_text_choice.text.toString(),
                    hobbies_c = this.hobbiesChecked,
                    password = password_input.text.toString()
            )

            this.registerSuccess(user)
        }
    }

    // Function verified data
    private fun verifiedData(titleShow: String, textShow: String, type: Int) {
        val builder = AlertDialog.Builder(this)
        var textMessage = textShow
        if (type == 1) {
            // type 1 for fields empty
            textMessage = getString(R.string.fields_required_description) + textShow
        }
        builder.setTitle(titleShow)
        builder.setMessage(textMessage)
        builder.setPositiveButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.show()
    }

    // Function verify password same
    private fun isPasswordSame(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword;
    }

    // Function register user success
    private fun registerSuccess(data: User) {

        val view = layoutInflater.inflate(R.layout.terms_and_conditions, null)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_tyc))
        builder.setView(view)
        builder.setPositiveButton(getString(R.string.agree)) { _, _ ->
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("User", data)
            startActivity(intent)
            this.cleanForm(false)
        }
        builder.setNegativeButton(getString(R.string.cancel), null)
        builder.setCancelable(false)
        builder.show()
    }

    private fun validateFields(component: TextView, compare: String, textOut: String) :String{
        if (component.text.toString() == compare) {
            return "$textOut | "
        }
        return ""
    }

    @RequiresApi(VERSION_CODES.N)
    private fun setupButtons() {
        // Button Clean Form
        reset_button.setOnClickListener {
            this.resetForm()
        }

        // Button show type document user
        type_document_button.setOnClickListener{
            this.getTypeDocument()
        }

        // Button show list hobbies user
        hobbies_button.setOnClickListener{
            this.getHobbies()
        }

        // Button show Date picker dialog
        birth_date_button.setOnClickListener {
            this.getBirthday()
        }

        // Function get data user
        register_button.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                this.register()
            }
        }
    }

}