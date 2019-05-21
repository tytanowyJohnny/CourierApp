package com.example.courierapp

import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.courierapp.CourierApplication.Companion.userType

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    @BindView(R.id.inputLayout_username)
    lateinit var inputLayout_username: TextInputLayout

    @BindView(R.id.inputLayout_password)
    lateinit var inputLayout_password: TextInputLayout

    @BindView(R.id.textView_signup)
    lateinit var textView_signup: TextView

    @BindView(R.id.button_login)
    lateinit var button_login: Button

    lateinit var DBHelper : SQLiteDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)

        DBHelper = SQLiteDbHandler(this)

        button_login.setOnClickListener{

            login(inputLayout_username.editText?.text.toString(), inputLayout_password.editText?.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun login(username: String, password: String) {

        val db = DBHelper.readableDatabase

        val cursor : Cursor?

        try {

            cursor = db.rawQuery("SELECT * FROM " + DbReader.UserEntry.TABLE_NAME + " WHERE (username = '" + username + "' AND password = '" + password + "')", null)


            if(cursor != null && cursor.moveToFirst()) {

                val DbUsername: String = cursor.getString(cursor.getColumnIndex(DbReader.UserEntry.COLUMN_NAME_USERNAME))
                val DbUserType: Int = cursor.getInt(cursor.getColumnIndex(DbReader.UserEntry.COLUMN_NAME_TYPE))

                cursor.moveToFirst()

                Log.i("DB RESULT: ", DbUsername)

                CourierApplication.logged = true
                CourierApplication.userID = DbUsername
                CourierApplication.userType = DbUserType

                Toast.makeText(this, "Logowanie zakończone sukcesem!", Toast.LENGTH_SHORT).show()

                //TODO: New activity depending on account type

            } else {

                Log.i("DB RESULT: ", "Account not found")

                Toast.makeText(this, "Logowanie nie powiodło się, spróbuj ponownie", Toast.LENGTH_SHORT).show()
            }

        } catch(e: SQLiteException) {

            Log.d("DB Exception", "FUNCTION LOGIN")
        }

    }
}
