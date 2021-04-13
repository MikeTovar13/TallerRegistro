package com.example.tallerregistro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_hobbies.*
import kotlinx.android.synthetic.main.fragment_user.*

/**
 * A simple [Fragment] subclass.
 * Use the [HobbiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HobbiesFragment : Fragment() {

    // Name hobbies existent
    private val hobbiesNames
        get() = resources.getStringArray(R.array.hobbies)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobbies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as HomeActivity // Get data from activity

        // Set data to TextView
       hobbies_hobies_value.text = activity.userData!!.hobbies_text

        hobbies_hobbies_button.setOnClickListener {
            this.getHobbies()
        }

    }
    // Function get list hobbies user
    @SuppressLint("SetTextI18n")
    private fun getHobbies() {

        val activity = requireActivity() as HomeActivity
        val checked = activity.userData!!.hobbies_check
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.hobbies_textView)
        builder.setPositiveButton(R.string.agree, null)
        builder.setCancelable(false)
        builder.setMultiChoiceItems(hobbiesNames, checked) { _, position, isChecked ->
            checked[position] = isChecked
            if(!checked.all { value-> value==false }) {
                if (isChecked) {
                    hobbies_hobies_value.setText(hobbies_hobies_value.text.toString() + hobbiesNames[position] + " | ")
                } else {
                    hobbies_hobies_value.setText(hobbies_hobies_value.text.toString().replace(hobbiesNames[position] + " | ", ""))
                }
                activity.userData!!.hobbies_text = hobbies_hobies_value.text.toString()
            }else{
                checked[position] = !isChecked
                Toast.makeText(requireContext(), getText(R.string.hobbies_required), Toast.LENGTH_LONG).show()
            }
        }
        builder.show()
    }

}