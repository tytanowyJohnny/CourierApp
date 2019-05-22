package com.example.courierapp

import android.content.Context

class Parcel(val ID: Int, val name: String, val weight: Double, val origin: String, val destination: String, var status: String, val parcelUser: Int) {

    lateinit var DBHelper : SQLiteDbHandler

    fun updateStatus(newStatus: String) {

        this.status = newStatus

        //TODO: Write to database

    }

}