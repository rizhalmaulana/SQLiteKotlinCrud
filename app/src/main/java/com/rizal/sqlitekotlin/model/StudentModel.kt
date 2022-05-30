package com.rizal.sqlitekotlin.model

import kotlin.random.Random

class StudentModel (
    var id: Int = getAutoId(),
    var name: String = "",
    var email: String = ""
) {
    companion object {
        fun getAutoId(): Int {
            val random = Random
            return random.nextInt(1,100)
        }
    }
}