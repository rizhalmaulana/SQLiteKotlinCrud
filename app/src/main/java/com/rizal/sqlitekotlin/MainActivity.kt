package com.rizal.sqlitekotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rizal.sqlitekotlin.adapter.StudentAdapter
import com.rizal.sqlitekotlin.helper.SQLiteHelper
import com.rizal.sqlitekotlin.model.StudentModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnTambah: MaterialButton
    private lateinit var btnLihat: MaterialButton
    private lateinit var btnUpdate: MaterialButton

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recycleView: RecyclerView
    private var studentAdapter: StudentAdapter? = null
    private var std: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLihat = findViewById(R.id.btn_lihat)
        btnTambah = findViewById(R.id.btn_tambah)
        btnUpdate = findViewById(R.id.btn_update)

        etEmail = findViewById(R.id.et_emailUser)
        etName = findViewById(R.id.et_namaUser)
        recycleView = findViewById(R.id.rv_listmurid)

        initRecycleView()

        sqliteHelper = SQLiteHelper(this)

        btnTambah.setOnClickListener { tambahMurid() }
        btnLihat.setOnClickListener { lihatMurid() }
        btnUpdate.setOnClickListener { updateMurid() }

        studentAdapter?.setOnClickItem {
            etEmail.setText(it.email)
            etName.setText(it.name)

            std = it
        }

        studentAdapter?.setOnClickDeleteItem {
            hapusMurid(it.id)
        }

    }

    private fun updateMurid() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()

        if (name == std?.name && email == std?.email) {
            Toast.makeText(this, "Murid gagal ditambahkan", Toast.LENGTH_SHORT).show()
            return
        }

        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email)
        val status = sqliteHelper.updateMurid(std)

        if (status > -1) {
            clearEditText()
            lihatMurid()
        } else {
            Toast.makeText(this, "Murid gagal diupdate", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecycleView() {
        recycleView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            studentAdapter = StudentAdapter()
            adapter = studentAdapter
        }

    }

    private fun lihatMurid() {
        val stdlist = sqliteHelper.getAllStudent()
        Log.e(TAG, "lihatMurid: ${stdlist.size}")

        studentAdapter?.addItems(stdlist)
    }

    private fun hapusMurid(id: Int) {
        AlertDialog.Builder(this).apply {
            setMessage("Apakah kamu yakin ingin menghapus item ini?")
            setCancelable(true)
            setPositiveButton("Ya") { dialog, _ ->
                sqliteHelper.hapusMurid(id)
                lihatMurid()

                dialog.dismiss()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
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

                lihatMurid()
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