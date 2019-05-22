package com.example.courierapp

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textfield.TextInputLayout

class SignupActivity : AppCompatActivity() {

    @BindView(R.id.inputLayout_signup_username)
    lateinit var inputLayout_signup_username: TextInputLayout

    @BindView(R.id.inputLayout_signup_password)
    lateinit var inputLayout_signup_password: TextInputLayout

    @BindView(R.id.button_signup)
    lateinit var button_signup: Button

    lateinit var DBHelper: SQLiteDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ButterKnife.bind(this)

        DBHelper = SQLiteDbHandler(this)

        button_signup.setOnClickListener{

            val tempUsername = inputLayout_signup_username.editText?.text.toString()
            val tempPassword = inputLayout_signup_password.editText?.text.toString()

            try{

                val rowID = DBHelper.insertUser(tempUsername, tempPassword)

                CourierApplication.Users.add(User(rowID,tempUsername, tempPassword, 1))
                Toast.makeText(this, "Dodano nowego użytkownika: $tempUsername", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, LoginActivity::class.java))

            } catch (e: SQLiteException) {

                Toast.makeText(this, "Rejestracja nieudana, spróbuj ponownie", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
