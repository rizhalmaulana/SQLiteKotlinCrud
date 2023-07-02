package com.rizal.sqlitekotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rizal.sqlitekotlin.helper.SQLiteHelper
import com.rizal.sqlitekotlin.model.EmployeeModel

class UpdateEmployeeActivity : AppCompatActivity() {

    private lateinit var username: TextInputEditText
    private lateinit var fullname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var salary: TextInputEditText
    private lateinit var gender: TextInputEditText
    private lateinit var region: TextInputEditText
    private lateinit var address: TextInputEditText
    private lateinit var btnUpdate: MaterialButton

    private lateinit var sqliteHelper: SQLiteHelper

    var idIntent: Int = 0
    lateinit var usernameIntent: String
    lateinit var fullnameIntent: String
    lateinit var emailIntent: String
    lateinit var salaryIntent: String
    lateinit var genderIntent: String
    lateinit var regionIntent: String
    lateinit var addressIntent: String

    private var std: EmployeeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_employee)

        username = findViewById(R.id.update_text_username)
        fullname = findViewById(R.id.update_text_fullname)
        email = findViewById(R.id.update_text_email)
        salary = findViewById(R.id.update_text_salary)
        gender = findViewById(R.id.update_text_gender)
        region = findViewById(R.id.update_text_region)
        address = findViewById(R.id.update_text_address)
        btnUpdate = findViewById(R.id.btn_update)

        idIntent = intent.extras?.getInt("id")!!
        usernameIntent = intent.extras?.getString("username").toString()
        fullnameIntent = intent.extras?.getString("fullname").toString()
        emailIntent = intent.extras?.getString("email").toString()
        salaryIntent = intent.extras?.getString("salary").toString()
        genderIntent = intent.extras?.getString("gender").toString()
        regionIntent = intent.extras?.getString("region").toString()
        addressIntent = intent.extras?.getString("address").toString()

        username.setText(usernameIntent)
        fullname.setText(fullnameIntent)
        email.setText(emailIntent)
        salary.setText(salaryIntent)
        gender.setText(genderIntent)
        region.setText(regionIntent)
        address.setText(addressIntent)

        sqliteHelper = SQLiteHelper(this)

        btnUpdate.setOnClickListener { updateEmployee() }
    }

    private fun updateEmployee() {
        val userUpdate = username.text.toString()
        val nameUpdate = fullname.text.toString()
        val emailUpdate = email.text.toString()
        val salaryUpdate = salary.text.toString()
        val genderUpdate = gender.text.toString()
        val regionUpdate = region.text.toString()
        val addressUpdate = address.text.toString()

        if (userUpdate == usernameIntent && nameUpdate == fullnameIntent && emailUpdate == emailIntent && salaryUpdate == salaryIntent &&
                genderUpdate == genderIntent && regionUpdate == regionIntent && addressUpdate == addressIntent) {
            Toast.makeText(this, "Employee gagal ditambahkan", Toast.LENGTH_SHORT).show()
            return
        }

        val std = EmployeeModel(id = idIntent, username = userUpdate, fullname = nameUpdate, email = emailUpdate, salary = salaryUpdate, gender = genderUpdate, region = regionUpdate, address = addressUpdate)
        Log.e("UpdateEmployee", "employee: $std")

        val status = sqliteHelper.updateEmployee(std)

        if (status > -1) {
            // Balik Ke Awal
            startActivity(Intent(this@UpdateEmployeeActivity, LihatEmployeeActivity::class.java))
        } else {
            Toast.makeText(this, "Employee gagal diupdate", Toast.LENGTH_SHORT).show()
        }
    }
}