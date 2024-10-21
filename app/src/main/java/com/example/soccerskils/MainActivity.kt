package com.example.soccerskils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.soccerskils.databinding.ActivityMainBinding
import com.example.soccerskils.databinding.ActivitySoccer2Binding
import com.google.ai.client.generativeai.common.shared.Content
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    val MY_SETTINGS: String = "my_settings"
    val MY_SETTINGSS: String = "my_soccers"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)
        /*setContentView(R.layout.activity_main)
        val name = findViewById<TextView>(R.id.editTextText)
        val password = findViewById<TextView>(R.id.editTextText2)*/
        var sp: SharedPreferences=getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE)
        var hasVizited:Boolean=sp.getBoolean("hasVizited", false)
        if(!hasVizited){
            val clickActivity2 = Intent(this@MainActivity, author::class.java)
            startActivity(clickActivity2)
            var editor:SharedPreferences.Editor=sp.edit()
            editor.putBoolean("hasVizited", true)
            editor.apply()
        }
    }



    //var dbHelper = DBHelper(this)
    fun signf(v: View){
        var email = binding!!.editTextText.text
        var password = binding!!.editTextText2.text
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "soccers").build().userDao()
        lifecycleScope.launch { val user = db?.getByEmail(email.toString())
            if (user != null && user.password == password.toString()){
                Log.i("tag", user.toString())
                user.count++
                db.update(user)
            }
            else {
                Toast.makeText(this@MainActivity, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch{
            val maxCountUser = db?.getMaxCount()
            if (maxCountUser != null){
                val sp: SharedPreferences = getSharedPreferences(MY_SETTINGSS, MODE_PRIVATE)
                val editor = sp.edit()

                editor.putString("userMaxCount", maxCountUser)
                editor.apply()
            }
        }






//        val name = findViewById<TextView>(R.id.editTextText)
//        val password = findViewById<TextView>(R.id.editTextText2)

//        var db = dbHelper.writableDatabase
//        val cursor = db.query(
//            DBHelper.TABLE_NAME,
//            null,
//            "${DBHelper.COLUMN_NAME} = ? AND ${DBHelper.COLUMN_PASSWORD} = ?",
//            arrayOf(name.toString(), password.toString()),
//            null,
//            null,
//            null
//        )

//        cursor.use {c ->
//            if(c.moveToFirst()){
//                val nameColIndex: Int = c.getColumnIndex(DBHelper.COLUMN_NAME)
//                val LOG_TAG: String = "myLogs"
//                val nameValue = c.getString(nameColIndex)
//
//
//                db.execSQL("UPDATE ${DBHelper.TABLE_NAME} " +
//                        "SET ${DBHelper.COLUMN_COUNT} = ${DBHelper.COLUMN_COUNT} + 1 " +
//                        "WHERE ${DBHelper.COLUMN_NAME} = '$nameValue'")
//
//                val intent = Intent(this@MainActivity, soccer3::class.java)
//                intent.putExtra("name",name.toString())
//                val toast = Toast.makeText(applicationContext, getString(R.string.Transition_to_a_new_activity), Toast.LENGTH_LONG)
//                toast.show()
//                startActivity(intent)
//
//
//            }
//            else{
//                val warning = Toast.makeText(applicationContext, "User with this password and name not exist", Toast.LENGTH_SHORT)
//                warning.show()
//            }


        }
    fun goNewView(v: View){
        val intent = Intent(this@MainActivity, soccer2::class.java)
        val toast = Toast.makeText(applicationContext, getString(R.string.registration_window), Toast.LENGTH_LONG)
        toast.show()
        startActivity(intent)
    }


    }
