package com.amooose.Calorie_Center

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.Preference
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    fun setNeedsRefresh(){
        val sharedPref = this.getSharedPreferences("com.amooose.Calorie_Center", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("refresh", true)
        editor.commit()
    }

    fun showDialog(){
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val sharedPref = this.getSharedPreferences("com.amooose.Calorie_Center", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("graphData", "[]")
                    editor.commit()
                    Toast.makeText(this, "Cleared all tracking/graph data", Toast.LENGTH_SHORT).show()

                }

                DialogInterface.BUTTON_NEGATIVE -> {
                }
            }
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val setupPref  = findPreference("setup") as Preference?
            setupPref!!.setOnPreferenceClickListener {
                startActivity(Intent(context, SetupMain::class.java))
                false
            }

            val graphClearPref  = findPreference("clearGraph") as Preference?
            graphClearPref!!.setOnPreferenceClickListener {
                val activity = activity as SettingsActivity?
                activity!!.showDialog()
                activity!!.setNeedsRefresh()
                false
            }



        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> finish()
        }
        return true
    }
}