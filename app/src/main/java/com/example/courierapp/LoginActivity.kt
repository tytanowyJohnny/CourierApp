package com.example.courierapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.courierapp.CourierApplication.Companion.currentUser

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

        // Load all users to ArrayList
        CourierApplication.Users = DBHelper.loadUsers()
        CourierApplication.Parcels = DBHelper.loadParcels()

        button_login.setOnClickListener{

            login(inputLayout_username.editText?.text.toString(), inputLayout_password.editText?.text.toString())
        }

        textView_signup.setOnClickListener {

            val intent: Intent = Intent(this, SignupActivity::class.java)

            startActivity(intent)

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

    private fun login(mUsername: String, mPassword: String) {

        var failed = true

        for(tempUser: User in CourierApplication.Users) {

            if(tempUser.username == mUsername && tempUser.password == mPassword) {

                CourierApplication.currentUser = tempUser

                Toast.makeText(this, "Zalogowano użytkownika: " + CourierApplication.currentUser.username, Toast.LENGTH_SHORT).show()

                failed = false

                startActivity(Intent(this, ParcelActivity::class.java))

                break
            }
        }

        if(failed) {

            Toast.makeText(this, "Logowanie nie powiodło się, spróbuj ponownie", Toast.LENGTH_SHORT).show()
        }

    }

}
