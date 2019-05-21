package com.example.courierapp

import android.app.Application

class CourierApplication : Application() {

    companion object {
        var logged = false
        var userID : String = ""
        var userType : Int = -1
    }
}