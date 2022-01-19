package com.example.informationpage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.view.View
import android.widget.*
import android.widget.RadioButton
import androidx.constraintlayout.widget.Group
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edFullName = findViewById<EditText>(R.id.edFullName)
        val edUserName = findViewById<EditText>(R.id.edUserName)
        val edEmail = findViewById<EditText>(R.id.edEmali)
        val edPassword = findViewById<EditText>(R.id.edPassword)
        val edRePassword = findViewById<EditText>(R.id.edRePassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnShowInfo = findViewById<Button>(R.id.btnShowInfo)
        val btnHideInfo = findViewById<Button>(R.id.btnHideInfo)
        val radioMale = findViewById<RadioButton>(R.id.Male)
        val tvFullName = findViewById<TextView>(R.id.tvFullName)
        val tvUserName = findViewById<TextView>(R.id.tvUserName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvPassword = findViewById<TextView>(R.id.tvPassword)
        val tvGender = findViewById<TextView>(R.id.tvGender)
        val tvGroup = findViewById<Group>(R.id.tvInfoGroup)

        var shPref: SharedPreferences =
            this.getSharedPreferences("kotlinsharedpreference", Context.MODE_PRIVATE)

        register(btnRegister,edFullName,edUserName,edEmail,edPassword,edRePassword,radioMale,shPref)

        showInfo(btnShowInfo,tvFullName,shPref,tvUserName,tvPassword,tvEmail,tvGender,tvGroup)

        hideInfo(edFullName,edUserName,edEmail,edPassword,edRePassword,btnHideInfo,shPref,tvGroup)
    }

    private fun hideInfo(
        edFullName: EditText,
        edUserName: EditText,
        edEmail: EditText,
        edPassword: EditText,
        edRePassword: EditText,
        btnHideInfo: Button,
        shPref: SharedPreferences,
        tvGroup: Group
    ) {
        btnHideInfo.setOnClickListener {
            val editor = shPref.edit()
            editor.clear()
            editor.apply()
            edFullName.text.clear()
            edUserName.text.clear()
            edEmail.text.clear()
            edPassword.text.clear()
            edRePassword.text.clear()

            tvGroup.visibility = View.GONE
        }
    }

    private fun showInfo(
        btnShowInfo: Button,
        tvFullName: TextView,
        shPref: SharedPreferences,
        tvUserName: TextView,
        tvPassword: TextView,
        tvEmail: TextView,
        tvGender: TextView,
        tvGroup: Group
    ) {
        btnShowInfo.setOnClickListener {

            tvFullName.text = shPref.getString("fullName_Key", "default Fullname")
            tvUserName.text = shPref.getString("userName_Key", "default Username")
            tvPassword.text = shPref.getString("email_Key", "default Email")
            tvEmail.text = shPref.getString("password_Key", "default Password")
            tvGender.text = shPref.getString("gender_Key", "default Gender")

            tvGroup.visibility = View.VISIBLE
        }
    }

    private fun register(
        btnRegister: Button,
        edFullName: EditText,
        edUserName: EditText,
        edEmail: EditText,
        edPassword: EditText,
        edRePassword: EditText,
        radioGender: RadioButton,
        shPref: SharedPreferences
    ) {
        btnRegister.setOnClickListener {
            val fullName: String = edFullName.text.toString()
            val userName: String = edUserName.text.toString()
            val email: String = edEmail.text.toString()
            val password: String = edPassword.text.toString()
            val editor: SharedPreferences.Editor = shPref.edit()
            val txtGender: String = if (radioGender.isChecked) {
                "Male"
            } else "Female"

            var rePasswordLayout =
                findViewById<TextInputLayout>(R.id.textInputLayout5)
            if (edPassword.text.toString() == edRePassword.text.toString()) {
                editor.putString("password_Key", password)
            } else {
                rePasswordLayout.error = "Confirm password is not correct"
            }
            editor.putString("fullName_Key", fullName)
            editor.putString("userName_Key", userName)
            editor.putString("email_Key", email)
            editor.putString("gender_Key", txtGender)
            editor.apply()
            editor.commit()
            Snackbar.make(it, "$userName successfully registered", Snackbar.LENGTH_LONG).show()
        }
    }
}

