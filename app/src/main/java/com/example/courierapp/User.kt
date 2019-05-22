package com.example.courierapp

class User(val ID: Int, val username: String, val password: String, val acc_type: Int = 1){

    fun isCourier() : Boolean {

        if(acc_type == 0) // 0 means Courier
            return true

        return false
    }

}