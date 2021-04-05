package com.example.tallerregistro

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val resetForm = findViewById<Button>(R.id.reset_button)
        resetForm.setOnClickListener {
            this.resetForm()
        }

        val getTypeDocument = findViewById<Button>(R.id.type_document_button)
        getTypeDocument.setOnClickListener{
            this.getTypeDocument()
        }

        val getHobbies = findViewById<Button>(R.id.hobbies_button)
        getHobbies.setOnClickListener{
            this.getHobbies()
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
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_LONG).show()
    }

    private val optionsTypeDocument: Array<String>
        get() = arrayOf(getString(R.string.document_CC),getString(R.string.document_CE),getString(R.string.document_PA),getString(R.string.document_RC),getString(R.string.document_TI))
    private val acronymTypeDocument
        get() = arrayOf(getString(R.string.document_acronymCC),getString(R.string.document_acronymCE),getString(R.string.document_acronymPA),getString(R.string.document_acronymRC),getString(R.string.document_acronymTI))

    // Function get type document
    private fun getTypeDocument() {

        var typeDocumentButton = findViewById<Button>(R.id.type_document_button)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.type_document_input)
        builder.setItems(this.optionsTypeDocument) { _, position ->
            typeDocumentButton.text = acronymTypeDocument[position];
        }
        builder.show()
    }

    private val hobbiesChecked = booleanArrayOf(false, false, false, false, false, false, false, false, false, false)
    private val hobbiesNames = arrayOf("Leer",  "Dormir", "Jugar video juegos", "Hacer ejercicio", "Bailar", "Viajar", "Ver series de TV", "Caminar", "Cocinar", "Escuchar música")

    // Function get hobbies
    private fun getHobbies() {

        var hobbiesInput = findViewById<EditText>(R.id.hobbies_input)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.type_document_input)
        builder.setNeutralButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, hobbiesChecked) { _, position, isChecked ->
            hobbiesChecked[position] = isChecked
            if (isChecked){
                hobbiesInput.setText(hobbiesInput.text.toString() + hobbiesNames[position] + "| ")
            }else{
                hobbiesInput.setText(hobbiesInput.text.toString().replace(hobbiesNames[position]+"| ", ""))
            }

        }
        builder.show()
    }

}