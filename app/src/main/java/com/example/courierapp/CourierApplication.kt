package com.example.courierapp

import android.app.Application

class CourierApplication : Application() {


    // global var to store users
    companion object {

        var Users: ArrayList<User> = ArrayList()
        var Parcels: ArrayList<Parcel> = ArrayList()
        lateinit var currentUser: User
    }

}