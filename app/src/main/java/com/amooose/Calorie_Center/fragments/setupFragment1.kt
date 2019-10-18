package com.amooose.Calorie_Center.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.text.InputFilter
import android.widget.EditText
import android.widget.RadioButton
import android.content.Context
import android.content.SharedPreferences
import com.amooose.Calorie_Center.helpers.InputFilterMinMax
import com.amooose.Calorie_Center.R
import com.amooose.Calorie_Center.SetupMain
import com.amooose.Calorie_Center.helpers.spruceHelper
import kotlinx.android.synthetic.main.fragment_setup_fragment1.*





/**
 * A simple [Fragment] subclass.
 */
class setupFragment1 : Fragment() {
    lateinit var age : EditText
    lateinit var weight : EditText
    lateinit var ft : EditText
    lateinit var inches : EditText
    lateinit var bodyfat : EditText
    lateinit var male : RadioButton
    lateinit var female : RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(
            R.layout.fragment_setup_fragment1,
            container, false
        )

        val animview : ViewGroup = view.findViewById(R.id.innerSetup1Layout)
        spruceHelper().animateSlideInLeft(animview)

        initViewVars(view)

        return view
    }

    fun initViewVars(view: View){
        male = view.findViewById(R.id.maleRadio)
        female = view.findViewById(R.id.femaleRadio)
        val nextBtn: Button = view.findViewById(R.id.nextBtn1)
        nextBtn.setOnClickListener{nextClick()}
        age = view.findViewById(R.id.ageInput)
        age.setFilters(arrayOf<InputFilter>(
            InputFilterMinMax("1", "99")
        ))
        weight = view.findViewById(R.id.weightInput)
        weight.setFilters(arrayOf<InputFilter>(
            InputFilterMinMax("1", "500")
        ))
        ft = view.findViewById(R.id.ftInput)
        ft.setFilters(arrayOf<InputFilter>(
            InputFilterMinMax("1", "8")
        ))
        inches = view.findViewById(R.id.inchInput)
        inches.setFilters(arrayOf<InputFilter>(
            InputFilterMinMax("1", "11")
        ))
        bodyfat = view.findViewById(R.id.bodyfatInput)
        bodyfat.setFilters(arrayOf<InputFilter>(
            InputFilterMinMax("1", "99")
        ))

    }

    fun saveInfo(){
        var sharedPref : SharedPreferences = activity!!.getSharedPreferences("com.amooose.Calorie_Center", Context.MODE_PRIVATE)
        //now get Editor
        val editor = sharedPref.edit()
        //put your value
        editor.putBoolean("sex", maleRadio.isChecked())
        editor.putInt("age",  Integer.parseInt(age.getText().toString()))
        editor.putInt("weight",  Integer.parseInt(weight.getText().toString()))
        editor.putInt("ft",  Integer.parseInt(ft.getText().toString()))
        if(inches.length()==0){
            editor.putInt("inches", 0)
        } else {
            editor.putInt("inches", Integer.parseInt(inches.getText().toString()))
        }
        if(bodyfat.length()==0){
            editor.putInt("bodyfat", -1)
        } else {
            editor.putInt("bodyfat", Integer.parseInt(bodyfat.getText().toString()))
        }
        //commits your edits
        editor.commit()
    }

    fun filledOut() : Boolean{
        if(age.length()>0 && weight.length()>0 && ft.length()> 0
             && (male.isChecked || female.isChecked)){
            return true
        }
        return false
    }

    fun nextClick(){
        if(!filledOut()) {
            Toast.makeText(getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        } else{
            saveInfo()
            val activity = activity as SetupMain?
            activity!!.activityLevelOpen()
        }
    }




}
