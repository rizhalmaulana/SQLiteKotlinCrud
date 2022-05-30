package com.rizal.sqlitekotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rizal.sqlitekotlin.helper.SQLiteHelper
import com.rizal.sqlitekotlin.model.StudentModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnTambah: MaterialButton
    private lateinit var btnLihat: MaterialButton

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText

    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLihat = findViewById(R.id.btn_lihat)
        btnTambah = findViewById(R.id.btn_tambah)
        etEmail = findViewById(R.id.et_emailUser)
        etName = findViewById(R.id.et_namaUser)

        sqliteHelper = SQLiteHelper(this)

        btnTambah.setOnClickListener { tambahMurid() }
        btnLihat.setOnClickListener { lihatMurid() }

    }

    private fun lihatMurid() {
        val stdlist = sqliteHelper.getAllStudent()
        Log.e(TAG, "lihatMurid: ${stdlist.size}")
    }

    private fun tambahMurid() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Silahkan isi terlebih dahulu!", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqliteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Murid berhasil ditambah", Toast.LENGTH_SHORT).show()
                clearEditText()
            } else {
                Toast.makeText(this, "Murid gagal ditambah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        etName.setText("")
        etEmail.setText("")

        etName.requestFocus()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}