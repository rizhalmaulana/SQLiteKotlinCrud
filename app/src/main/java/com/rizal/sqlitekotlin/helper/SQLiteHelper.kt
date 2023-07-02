package com.rizal.sqlitekotlin.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rizal.sqlitekotlin.model.EmployeeModel

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "employee.db"
        private const val DATABASE_VERSION = 2
        private const val TBL_EMPLOYEE = "tbl_employee"

        private const val ID = "id"
        private const val USERNAME = "username"
        private const val FULLNAME = "fullname"
        private const val EMAIL = "email"
        private const val SALARY = "salary"
        private const val GENDER = "gender"
        private const val REGION = "region"
        private const val ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblEmployee: String =
            ("CREATE TABLE $TBL_EMPLOYEE ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $USERNAME TEXT, $FULLNAME TEXT, $EMAIL TEXT, $SALARY TEXT, $GENDER TEXT, $REGION TEXT, $ADDRESS TEXT)")
        db?.execSQL(createTblEmployee)
    }

    override fun onUpgrade(db: SQLiteDatabase?, olderVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_EMPLOYEE")
        onCreate(db)
    }

    fun insertEmployee(std: EmployeeModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(ID, std.id)
            put(USERNAME, std.username)
            put(FULLNAME, std.fullname)
            put(EMAIL, std.email)
            put(SALARY, std.salary)
            put(GENDER, std.gender)
            put(REGION, std.region)
            put(ADDRESS, std.address)
        }

        val success = db.insert(TBL_EMPLOYEE, null, contentValues)
        db.close()

        return success
    }

    @SuppressLint("Range", "Recycle")
    fun getAllEmployee(): ArrayList<EmployeeModel> {
        val stdList: ArrayList<EmployeeModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_EMPLOYEE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)

            return ArrayList()
        }

        var id: Int
        var username: String
        var fullname: String
        var email: String
        var salary: String
        var gender: String
        var region: String
        var address: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                username = cursor.getString(cursor.getColumnIndex("username"))
                fullname = cursor.getString(cursor.getColumnIndex("fullname"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                salary = cursor.getString(cursor.getColumnIndex("salary"))
                gender = cursor.getString(cursor.getColumnIndex("gender"))
                region = cursor.getString(cursor.getColumnIndex("region"))
                address = cursor.getString(cursor.getColumnIndex("address"))

                val std = EmployeeModel(id = id, username = username, fullname = fullname, email = email, salary = salary, gender = gender, region = region, address = address)
                stdList.add(std)
            } while (cursor.moveToNext())
        }

        return stdList
    }

    fun updateEmployee(std: EmployeeModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(ID, std.id)
            put(USERNAME, std.username)
            put(FULLNAME, std.fullname)
            put(EMAIL, std.email)
            put(SALARY, std.salary)
            put(GENDER, std.gender)
            put(REGION, std.region)
            put(ADDRESS, std.address)
        }

        val success = db.update(TBL_EMPLOYEE, contentValues, "id=" + std.id, null)
        db.close()

        return success
    }

    fun hapusEmployee(id: Int): Int {
        val db = this.writableDatabase

        ContentValues().apply {
            put(ID, id)
        }

        val success = db.delete(TBL_EMPLOYEE, "id=$id", null)
        db.close()

        return success
    }

}