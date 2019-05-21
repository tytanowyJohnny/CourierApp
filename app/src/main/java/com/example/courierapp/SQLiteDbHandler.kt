package com.example.courierapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import com.example.courierapp.CourierApplication.Companion.logged
import com.example.courierapp.CourierApplication.Companion.userID

object DbReader {

    object UserEntry : BaseColumns {

        const val TABLE_NAME = "users"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_TYPE = "acc_type"

    }

}


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DbReader.UserEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DbReader.UserEntry.COLUMN_NAME_USERNAME} TEXT," +
            "${DbReader.UserEntry.COLUMN_NAME_PASSWORD} TEXT," +
            "${DbReader.UserEntry.COLUMN_NAME_TYPE} INTEGER)"

private const val SQL_DELTE_ENTRIES =
        "DROP TABLE IF EXISTS ${DbReader.UserEntry.TABLE_NAME}"

class SQLiteDbHandler(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_ENTRIES)

        insertAdminUser("admin", "admin", db!!)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL(SQL_DELTE_ENTRIES)
        onCreate(db)
    }

    companion object {

        const val DB_NAME = "app_db"
        const val DB_VERSION = 1

    }

    private fun insertAdminUser(username: String, password: String, database: SQLiteDatabase) : Boolean {

        val values = ContentValues()

        values.put(DbReader.UserEntry.COLUMN_NAME_USERNAME, username)
        values.put(DbReader.UserEntry.COLUMN_NAME_PASSWORD, password)
        values.put(DbReader.UserEntry.COLUMN_NAME_TYPE, 0) // Konto kuriera

        val rowID = database.insert(DbReader.UserEntry.TABLE_NAME, null, values)

        return true
    }



}