package com.example.tallerregistro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var userData: User? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userData = intent.getParcelableExtra<User>("User")!! // Get user data from arguments
        this.showFragment(UserFragment())  // Load fragment default "user data"

        // Welcome to User register
        home_title.text = getString(R.string.home_title, userData!!.name)


        // Buttons home
        home_button_user.setOnClickListener {
            this.showFragment(name = UserFragment())  // Fragment "user data"
        }

        home_button_hobbies.setOnClickListener {
            this.showFragment(name = HobbiesFragment()) // Fragment "user edit hobbies"
        }

        val buttonLogOut = findViewById<Button>(R.id.home_button_logout)
        buttonLogOut.setOnClickListener {
            finish() // Close activity
        }

    }

    private fun showFragment(name: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_container, name, null)
        transaction.commit()
    }
}