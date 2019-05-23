package com.example.courierapp

import android.content.Context

class Parcel(val ID: Int, val name: String, val weight: Double, val origin_L1: String, val origin_L2: String, val origin_L3: String,
             val destination_L1: String, val destination_L2: String, val destination_L3: String, var status: String, val parcelUser: Int) {

    lateinit var DBHelper : SQLiteDbHandler

    fun updateStatus(newStatus: String) {

        this.status = newStatus

        //TODO: Write to database

    }

}