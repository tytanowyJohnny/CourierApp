package com.example.courierapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

class ParcelViewActivity : AppCompatActivity() {

    @BindView(R.id.textView_parcelName)
    lateinit var textView_parcelName: TextView

    @BindView(R.id.textView_parcelOrigin_L1)
    lateinit var textView_parcelOrigin_L1: TextView

    @BindView(R.id.textView_parcelOrigin_L2)
    lateinit var textView_parcelOrigin_L2: TextView

    @BindView(R.id.textView_parcelOrigin_L3)
    lateinit var textView_parcelOrigin_L3: TextView

    @BindView(R.id.textView_parcelDest_L1)
    lateinit var textView_parcelDest_L1: TextView

    @BindView(R.id.textView_parcelDest_L2)
    lateinit var textView_parcelDest_L2: TextView

    @BindView(R.id.textView_parcelDest_L3)
    lateinit var textView_parcelDest_L3: TextView

    @BindView(R.id.textView_parcelWeight)
    lateinit var textView_parcelWeight: TextView

    @BindView(R.id.textView_parcelStatus)
    lateinit var textView_parcelStatus: TextView

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel_view)

        ButterKnife.bind(this)

        textView_parcelName.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].name
        textView_parcelOrigin_L1.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].origin_L1
        textView_parcelOrigin_L2.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].origin_L2
        textView_parcelOrigin_L3.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].origin_L3
        textView_parcelDest_L1.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].destination_L1
        textView_parcelDest_L2.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].destination_L2
        textView_parcelDest_L3.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].destination_L3
        textView_parcelWeight.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].weight.toString()
        textView_parcelStatus.text = CourierApplication.Parcels[intent.getIntExtra("parcelID", -1)].status

    }
}
