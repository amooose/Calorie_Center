package com.amooose.Calorie_Center.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.SharedPreferences
import android.widget.*
import com.amooose.Calorie_Center.R
import com.amooose.Calorie_Center.SetupMain
import com.amooose.Calorie_Center.helpers.spruceHelper


/**
 * A simple [Fragment] subclass.
 */
class setupFragment2 : Fragment() {
    lateinit var comboBox : Spinner
    lateinit var info : TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(
            R.layout.fragment_setup_fragment2,
            container, false
        )

        val animview : ViewGroup = view.findViewById(R.id.innerSetup2Layout)
        spruceHelper().animateSlideInRight(animview)

        info = view.findViewById(R.id.actInfo)
        comboBox = view.findViewById(R.id.activityLvlsCombo)
        val nextABtn: Button = view.findViewById(R.id.nextABtn)
        nextABtn.setOnClickListener{
            val activity = activity as SetupMain?
            activity!!.activityLevelNext()
            var sharedPref : SharedPreferences = activity!!.getSharedPreferences("com.amooose.Calorie_Center", Context.MODE_PRIVATE)
            //now get Editor
            val editor = sharedPref.edit()
            editor.putInt("activity", comboBox.selectedItemPosition)
            editor.commit()
        }
        val arrayList = ArrayList<String>()
        arrayList.add("Inactive/Sedentary")
        arrayList.add("Light")
        arrayList.add("Moderate")
        arrayList.add("High")
        arrayList.add("Extreme")
        val arrayAdapter =
            ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        comboBox.setAdapter(arrayAdapter)
        initComboBox()

        return view
    }


    private fun initComboBox(){
        comboBox?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val pos = parent.getItemAtPosition(position).toString()
                when (position) {
                    0 -> info.setText(pos + ":  Almost no activity throughout the day")
                    1 -> info.setText(pos + ": 1–3 workouts per week, or job with light activity")
                    2 -> info.setText(pos + ": 3–5 workouts per week ")
                    3 -> info.setText(pos + ": 6 workouts per week")
                    4 -> info.setText(pos + ": 7 workouts per week")
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
    }

}
