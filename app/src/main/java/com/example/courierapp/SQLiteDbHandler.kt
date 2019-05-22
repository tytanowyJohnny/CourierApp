package com.example.courierapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

object CourierContract {

    object UserEntry : BaseColumns {

        const val TABLE_NAME = "users"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_TYPE = "acc_type"

    }

    object ParcelEntry : BaseColumns {

        const val TABLE_NAME = "parcels"
        const val COLUMN_NAME_PARCEL = "parcel_name"
        const val COLUMN_NAME_WEIGHT = "parcel_weight"
        const val COLUMN_NAME_ORIGIN = "parcel_origin"
        const val COLUMN_NAME_DEST = "parcel_destination"
        const val COLUMN_NAME_STATUS = "parcel_status"
        const val COLUMN_NAME_PARCEL_USER = "parcel_user_id"
    }

}


private const val SQL_CREATE_USER_ENTRIES =
    "CREATE TABLE ${CourierContract.UserEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${CourierContract.UserEntry.COLUMN_NAME_USERNAME} TEXT," +
            "${CourierContract.UserEntry.COLUMN_NAME_PASSWORD} TEXT," +
            "${CourierContract.UserEntry.COLUMN_NAME_TYPE} INTEGER)"

private const val SQL_CREATE_PARCEL_ENTRIES =
    "CREATE TABLE ${CourierContract.ParcelEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${CourierContract.ParcelEntry.COLUMN_NAME_PARCEL} TEXT," +
            "${CourierContract.ParcelEntry.COLUMN_NAME_WEIGHT} DOUBLE," +
            "${CourierContract.ParcelEntry.COLUMN_NAME_ORIGIN} TEXT," +
            "${CourierContract.ParcelEntry.COLUMN_NAME_DEST} TEXT," +
            "${CourierContract.ParcelEntry.COLUMN_NAME_STATUS} TEXT," +
            "${CourierContract.ParcelEntry.COLUMN_NAME_PARCEL_USER} INTEGER)"

private const val SQL_DELETE_USER_ENTRIES =
        "DROP TABLE IF EXISTS ${CourierContract.UserEntry.TABLE_NAME}"

private const val SQL_DELETE_PARCEL_ENTRIES =
        "DROP TABLE IF EXISTS ${CourierContract.ParcelEntry.TABLE_NAME}"

class SQLiteDbHandler(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(SQL_CREATE_USER_ENTRIES)
        db?.execSQL(SQL_CREATE_PARCEL_ENTRIES)

        insertCourierUser("Kurier", "admin", db!!)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL(SQL_DELETE_USER_ENTRIES)
        db?.execSQL(SQL_DELETE_PARCEL_ENTRIES)

        onCreate(db)
    }

    companion object {

        const val DB_NAME = "app_db"
        const val DB_VERSION = 2

    }

    fun loadParcels() : ArrayList<Parcel> {

        val tempArray : ArrayList<Parcel> = ArrayList()

        val db = this.readableDatabase

        val cursor: Cursor?

        try {

            cursor = db.rawQuery("SELECT * FROM " + CourierContract.ParcelEntry.TABLE_NAME, null)

            if(cursor != null && cursor.moveToFirst()) {

                cursor.moveToFirst()

                do {

                    val tempID: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                    val tempParcel: String = cursor.getString(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_PARCEL))
                    val tempWeight: Double = cursor.getDouble(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_WEIGHT))
                    val tempOrigin: String = cursor.getString(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_ORIGIN))
                    val tempDest: String = cursor.getString(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_DEST))
                    val tempStatus: String = cursor.getString(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_STATUS))
                    val tempUserID: Int = cursor.getInt(cursor.getColumnIndex(CourierContract.ParcelEntry.COLUMN_NAME_PARCEL_USER))

                    tempArray.add(Parcel(tempID,tempParcel,tempWeight,tempOrigin,tempDest,tempStatus,tempUserID))

                    Log.i("DB", "Załadowano przesyłke: $tempParcel")
                } while (cursor.moveToNext())
            } else {

                Log.i("DB", "Nie znaleziono żadnych przesyłek")
            }

            cursor.close()
        } catch (e: SQLiteException) {

            Log.e("DB Esception", "Parcel load failed")
        }

        return tempArray
    }

    fun loadUsers() : ArrayList<User> {

        val tempArray : ArrayList<User> = ArrayList()

        val db = this.readableDatabase
        val cursor: Cursor?

        try {

            cursor = db.rawQuery("SELECT * FROM " + CourierContract.UserEntry.TABLE_NAME, null)

            if(cursor != null && cursor.moveToFirst()) {

                cursor.moveToFirst()

                do {

                    val tempID: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
                    val tempUsername: String = cursor.getString(cursor.getColumnIndex(CourierContract.UserEntry.COLUMN_NAME_USERNAME))
                    val tempPassword: String = cursor.getString(cursor.getColumnIndex(CourierContract.UserEntry.COLUMN_NAME_PASSWORD))
                    val tempAccType: Int = cursor.getInt(cursor.getColumnIndex(CourierContract.UserEntry.COLUMN_NAME_TYPE))

                    tempArray.add(User(tempID, tempUsername, tempPassword, tempAccType))

                    Log.i("DB", "Załadowano usera: " + tempUsername)
                }while (cursor.moveToNext())

            } else {

                Log.i("DB", "Nie znaleziono userów.")

            }

            cursor.close()
        } catch (e: SQLiteException) {

            Log.e("DB Exception", "User load failed")
        }

        return tempArray

    }

    fun insertUser(username: String, password: String) : Int {

        val values = ContentValues()

        values.put(CourierContract.UserEntry.COLUMN_NAME_USERNAME, username)
        values.put(CourierContract.UserEntry.COLUMN_NAME_PASSWORD, password)
        values.put(CourierContract.UserEntry.COLUMN_NAME_TYPE, 1)

        return this.writableDatabase.insert(CourierContract.UserEntry.TABLE_NAME, null, values).toInt()

    }

    fun insertParcel(name: String, weight: Double, origin: String, dest: String, status: String, parcelUser: Int) : Int {

        val values = ContentValues()

        values.put(CourierContract.ParcelEntry.COLUMN_NAME_PARCEL, name)
        values.put(CourierContract.ParcelEntry.COLUMN_NAME_WEIGHT, weight)
        values.put(CourierContract.ParcelEntry.COLUMN_NAME_ORIGIN, origin)
        values.put(CourierContract.ParcelEntry.COLUMN_NAME_DEST, dest)
        values.put(CourierContract.ParcelEntry.COLUMN_NAME_STATUS, status)
        values.put(CourierContract.ParcelEntry.COLUMN_NAME_PARCEL_USER, parcelUser)

        return this.writableDatabase.insert(CourierContract.ParcelEntry.TABLE_NAME, null, values).toInt()

    }

    private fun insertCourierUser(username: String, password: String, db: SQLiteDatabase) : Boolean {

        val values = ContentValues()

        values.put(CourierContract.UserEntry.COLUMN_NAME_USERNAME, username)
        values.put(CourierContract.UserEntry.COLUMN_NAME_PASSWORD, password)
        values.put(CourierContract.UserEntry.COLUMN_NAME_TYPE, 0) // Konto kuriera


        val rowID = db.insert(CourierContract.UserEntry.TABLE_NAME, null, values)

        return true
    }



}