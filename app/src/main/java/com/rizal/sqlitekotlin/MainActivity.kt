package com.rizal.sqlitekotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rizal.sqlitekotlin.adapter.EmployeeAdapter
import com.rizal.sqlitekotlin.helper.SQLiteHelper
import com.rizal.sqlitekotlin.model.EmployeeModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnTambah: MaterialButton
    private lateinit var btnLihat: MaterialButton

    private lateinit var etUsername: TextInputEditText
    private lateinit var etFullname: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etSalary: TextInputEditText
    private lateinit var etGender: TextInputEditText
    private lateinit var etRegion: TextInputEditText
    private lateinit var etAddress: TextInputEditText

    private lateinit var sqliteHelper: SQLiteHelper

    private var employeeAdapter: EmployeeAdapter? = null
    private var std: EmployeeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLihat = findViewById(R.id.btn_lihat)
        btnTambah = findViewById(R.id.btn_tambah)

        etUsername = findViewById(R.id.et_username)
        etFullname = findViewById(R.id.et_fullname)
        etEmail = findViewById(R.id.et_email)
        etSalary = findViewById(R.id.et_salary)
        etGender = findViewById(R.id.et_gender)
        etRegion = findViewById(R.id.et_region)
        etAddress = findViewById(R.id.et_address)

        sqliteHelper = SQLiteHelper(this)

        btnTambah.setOnClickListener { tambahEmployee() }
        btnLihat.setOnClickListener { lihatEmployee() }
    }

    private fun lihatEmployee() {
        val stdlist = sqliteHelper.getAllEmployee()
        Log.e(TAG, "lihatEmployee: ${stdlist.size}")

        employeeAdapter?.addItems(stdlist)

        val intent = Intent(this@MainActivity, LihatEmployeeActivity::class.java)
        startActivity(intent)
    }

    private fun tambahEmployee() {
        val username = etUsername.text.toString()
        val fullname = etFullname.text.toString()
        val email = etEmail.text.toString()
        val salary = etSalary.text.toString()
        val gender = etGender.text.toString()
        val region = etRegion.text.toString()
        val address = etAddress.text.toString()

        if (username.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Silahkan isi terlebih dahulu!", Toast.LENGTH_SHORT).show()
        } else {
            val std = EmployeeModel(
                username = username,
                fullname = fullname,
                email = email,
                salary = salary,
                gender = gender,
                region = region,
                address = address
            )
            val status = sqliteHelper.insertEmployee(std)

            if (status > -1) {
                Toast.makeText(this, "Employee berhasil ditambah", Toast.LENGTH_SHORT).show()
                clearEditText()

                lihatEmployee()
            } else {
                Toast.makeText(this, "Employee gagal ditambah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etUsername.setText("")
        etFullname.setText("")
        etEmail.setText("")
        etSalary.setText("")
        etGender.setText("")
        etRegion.setText("")
        etAddress.setText("")

        etUsername.requestFocus()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}