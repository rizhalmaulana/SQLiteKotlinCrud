package com.rizal.sqlitekotlin.model

import kotlin.random.Random

class EmployeeModel (
    var id: Int = getAutoId(),
    var username: String = "",
    var fullname: String = "",
    var email: String = "",
    var salary: String = "",
    var gender: String = "",
    var region: String = "",
    var address: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random
            return random.nextInt(1,100)
        }
    }
}