package com.example.tallerregistro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as HomeActivity // Get data from activity

        // Set data to TextView
        user_name_value.text = activity.userData!!.name
        user_last_name_value.text = activity.userData!!.last_name
        user_type_document_value.text = activity.userData!!.type_document
        user_document_value.text = activity.userData!!.number_document
        user_birthdate_value.text = activity.userData!!.birth_date
        user_hobies_value.text = activity.userData!!.hobbies_text
        user_password_value.text = activity.userData!!.password

    }

}