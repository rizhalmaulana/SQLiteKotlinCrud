package com.rizal.sqlitekotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.rizal.sqlitekotlin.adapter.EmployeeAdapter
import com.rizal.sqlitekotlin.helper.SQLiteHelper
import com.rizal.sqlitekotlin.model.EmployeeModel

class LihatEmployeeActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var recycleView: RecyclerView
    private lateinit var sqliteHelper: SQLiteHelper

    private var employeeAdapter: EmployeeAdapter? = null
    private var std: EmployeeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_employee)

        searchView = findViewById(R.id.search_employee)
        recycleView = findViewById(R.id.rv_listEmployee)

        initRecycleView()

        sqliteHelper = SQLiteHelper(this)

        employeeAdapter?.setOnClickItem {
            val intent = Intent(this@LihatEmployeeActivity, UpdateEmployeeActivity::class.java)
            intent.apply {
                this.putExtra("id", it.id)
                this.putExtra("username", it.username)
                this.putExtra("fullname", it.fullname)
                this.putExtra( "email", it.email)
                this.putExtra( "salary", it.salary)
                this.putExtra( "gender", it.gender)
                this.putExtra("region", it.region)
                this.putExtra("address", it.address)
            }
            startActivity(intent)

            std = it
        }

        lihatEmployee()

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                employeeAdapter?.filter?.filter(newText)
                return false
            }

        })

        employeeAdapter?.setOnClickDeleteItem {
            hapusEmployee(it.id)
        }
    }

    private fun lihatEmployee() {
        val stdlist = sqliteHelper.getAllEmployee()
        Log.e(TAG, "lihatEmployee: ${stdlist.size}")

        employeeAdapter?.addItems(stdlist)
    }

    private fun hapusEmployee(id: Int) {
        AlertDialog.Builder(this).apply {
            setMessage("Apakah kamu yakin ingin menghapus item ini?")
            setCancelable(true)
            setPositiveButton("Ya") { dialog, _ ->
                sqliteHelper.hapusEmployee(id)
                lihatEmployee()

                dialog.dismiss()
            }
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    private fun initRecycleView() {
        recycleView.apply {
            layoutManager = LinearLayoutManager(this@LihatEmployeeActivity)
            employeeAdapter = EmployeeAdapter()
            adapter = employeeAdapter
        }
    }

    companion object {
        private const val TAG = "LihatEmployeeActivity"
    }
}