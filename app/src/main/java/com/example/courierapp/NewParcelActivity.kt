package com.example.courierapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.parcel_row_v2.*

const val STATUS_NEW = "Oczekuje na kuriera"

class NewParcelActivity : AppCompatActivity() {

    @BindView(R.id.editText_parcelName)
    lateinit var editText_parcelName: EditText

    @BindView(R.id.editText_parcelOrigin_L1)
    lateinit var editText_parcelOrigin_L1: EditText

    @BindView(R.id.editText_parcelOrigin_L2)
    lateinit var editText_parcelOrigin_L2: EditText

    @BindView(R.id.editText_parcelOrigin_L3)
    lateinit var editText_parcelOrigin_L3: EditText

    @BindView(R.id.editText_parcelDest_L1)
    lateinit var editText_parcelDest_L1: EditText

    @BindView(R.id.editText_parcelDest_L2)
    lateinit var editText_parcelDest_L2: EditText

    @BindView(R.id.editText_parcelDest_L3)
    lateinit var editText_parcelDest_L3: EditText

    @BindView(R.id.editText_parcelWeight)
    lateinit var editText_parcelWeight: EditText

    @BindView(R.id.button_newParcel)
    lateinit var button_newParcel: Button

    lateinit var DBHelper: SQLiteDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel_new)

        ButterKnife.bind(this)

        DBHelper = SQLiteDbHandler(this)

        button_newParcel.setOnClickListener{

            // Load inputs
            val tempName= editText_parcelName.text.toString()
            val tempOrigin_L1 = editText_parcelOrigin_L1.text.toString()
            val tempOrigin_L2 = editText_parcelOrigin_L2.text.toString()
            val tempOrigin_L3 = editText_parcelOrigin_L3.text.toString()
            val tempDest_L1 = editText_parcelDest_L1.text.toString()
            val tempDest_L2 = editText_parcelDest_L2.text.toString()
            val tempDest_L3 = editText_parcelDest_L3.text.toString()
            val tempWeight = editText_parcelWeight.text.toString()

            // Check if mandatory populated
            if(tempName == "" || tempOrigin_L1 == "" || tempDest_L1 == "" || tempWeight == "") {

                Toast.makeText(this, "Wypełnij pola: Nazwa przesyłki, Nadawca, Adresat, Waga", Toast.LENGTH_LONG).show()

            } else {

                val rowID = DBHelper.insertParcel(tempName, tempWeight.toDouble(), tempOrigin_L1, tempOrigin_L2, tempOrigin_L3,
                    tempDest_L1, tempDest_L2, tempDest_L3, STATUS_NEW, CourierApplication.currentUser.ID)

                CourierApplication.Parcels.add(rowID, Parcel(rowID, tempName, tempWeight.toDouble(), tempOrigin_L1, tempOrigin_L2, tempOrigin_L3,
                    tempDest_L1, tempDest_L2, tempDest_L3, STATUS_NEW, CourierApplication.currentUser.ID))

                startActivity(Intent(this, ParcelActivity::class.java))

                //userParcels.add(Parcel(rowID, "test parcel", 2.0, "origin_L1", "origin_L2", "origin_L3",
                //    "dest_L1", "dest_L2", "dest_L3", "test_status", CourierApplication.currentUser.ID))

                //viewAdapter.notifyDataSetChanged()

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}

/*
val rowID = DBHelper.insertParcel("test parcel", 2.0, "origin_L1", "origin_L2", "origin_L3",
    "dest_L1", "dest_L2", "dest_L3", "test_status", CourierApplication.currentUser.ID)

CourierApplication.Parcels.add(rowID, Parcel(rowID, "test parcel", 2.0, "origin_L1", "origin_L2", "origin_L3",
"dest_L1", "dest_L2", "dest_L3", "test_status", CourierApplication.currentUser.ID))

userParcels.add(Parcel(rowID, "test parcel", 2.0, "origin_L1", "origin_L2", "origin_L3",
"dest_L1", "dest_L2", "dest_L3", "test_status", CourierApplication.currentUser.ID))

viewAdapter.notifyDataSetChanged() */
