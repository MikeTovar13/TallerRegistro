package com.example.tallerregistro

data class User (

    // Data user
    var name: String,
    var last_name: String = "",
    var type_document: String  = "",
    var number_document: String = "",
    var birth_date: String = "",
    //var hobbies: MutableList<String> = mutableListOf<String>(),
    var password: String = ""

)