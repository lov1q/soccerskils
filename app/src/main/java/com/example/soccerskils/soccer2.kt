package com.example.soccerskils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.soccerskils.databinding.ActivitySoccer2Binding
import kotlinx.coroutines.launch

class soccer2 : AppCompatActivity() {
    lateinit var nameEd: EditText
    lateinit var emailEd: EditText
    lateinit var passEd: EditText
    var binding: ActivitySoccer2Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_soccer2)
        nameEd = findViewById(R.id.editTextText3)
        emailEd = findViewById(R.id.editTextText4)
        passEd = findViewById(R.id.editTextText5)*/
        binding = ActivitySoccer2Binding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)

    }

    fun goBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun signUp(v: View){
        /*val email = findViewById<TextView>(R.id.editTextText4)
        val login = findViewById<TextView>(R.id.editTextText3)
        val password = findViewById<TextView>(R.id.editTextText5)
        val passwordcon = findViewById<TextView>(R.id.editTextText6)*/
        var namebinding = binding!!.editTextText3.text
        var emailbinding = binding!!.editTextText4.text
        var passwordbinding = binding!!.editTextText5.text
        var passwordconbinding = binding!!.editTextText6.text
        var count = 0



        if (namebinding.toString().isEmpty()
            || emailbinding.toString().isEmpty()
            || passwordbinding.toString().isEmpty()
            || passwordconbinding.toString().isEmpty()){
            val warn = Toast.makeText(applicationContext, "Вы заполнили не все поля", Toast.LENGTH_LONG)
            warn.show()
            return
        }

        if (!passwordbinding.toString().contentEquals(passwordconbinding.toString())) {
            val warn = Toast.makeText(applicationContext, "Пароли не совпадают", Toast.LENGTH_LONG)
            warn.show()
            return
        }
        val warn = Toast.makeText(applicationContext, "Данные введены верно!", Toast.LENGTH_LONG)
        warn.show()
        addSock(v)
    }


    fun addSock(v: View) {
        var namebinding = binding!!.editTextText3.text
        var emailbinding = binding!!.editTextText4.text
        var passwordbinding = binding!!.editTextText5.text
        var passwordconbinding = binding!!.editTextText6.text
//        val LOG_TAG: String = "myLogs"
//        var cv = ContentValues()
//        var name = nameEd.text.toString()
//        var email = emailEd.text.toString()
//        var password = passEd.text.toString()
//        val dbHelper = DBHelper(this)
//        var db: SQLiteDatabase = dbHelper.writableDatabase
//        when (v.id) {
//            R.id.button3 -> {
//                Log.d(LOG_TAG, "--- Insert in mytable: ---")
//                cv.put("name", name)
//                cv.put("email", email)
//                cv.put("password", password)
//                cv.put(DBHelper.COLUMN_COUNT,0)
//                var rowId: Long = db.insertOrThrow("mySoccers", null, cv)
//                Log.d(LOG_TAG, "row inserted, ID = $rowId")
//            }
//        }

        val user = User(0,namebinding.toString(), emailbinding.toString(), passwordbinding.toString(), count = 1)
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "soccers").build().userDao()
        lifecycleScope.launch { db.insert(user) }

    }
}



class DBHelper(context: Context?): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val LOG_TAG: String = "myLogs"

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "myDatabase.db"
        const val TABLE_NAME = "mySoccers"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_COUNT = "count"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(LOG_TAG, "--- onCreate database ---")
        db?.execSQL(
            "create table $TABLE_NAME ($COLUMN_ID integer primary key autoincrement, "
                    + "$COLUMN_NAME text, $COLUMN_EMAIL text, $COLUMN_PASSWORD text, $COLUMN_COUNT integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists $TABLE_NAME")
        onCreate(db)
    }

    fun getUsers() {
        val users = mutableListOf<User>()

        val db = this.writableDatabase
        var cur = db.query("myTable", null, null, null, null, null, null)
        if (cur.moveToFirst()) {
            val emailIndex = cur.getColumnIndex("email")
            val nameIndex = cur.getColumnIndex("name")
            val passwordIndex = cur.getColumnIndex("password")
        }
    }
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
@Entity("mySoccers")
data class User(
    @PrimaryKey (autoGenerate = true)
    var id: Long,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var count: Long = 0
)