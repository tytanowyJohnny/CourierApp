package com.example.courierapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.status_dialog.view.*

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

    var parcelID: Int = -1

    lateinit var DBHelper: SQLiteDbHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel_view)

        ButterKnife.bind(this)

        DBHelper = SQLiteDbHandler(this)

        parcelID = intent.getIntExtra("parcelID", -1)

        textView_parcelName.text = CourierApplication.Parcels[parcelID].name
        textView_parcelOrigin_L1.text = CourierApplication.Parcels[parcelID].origin_L1
        textView_parcelOrigin_L2.text = CourierApplication.Parcels[parcelID].origin_L2
        textView_parcelOrigin_L3.text = CourierApplication.Parcels[parcelID].origin_L3
        textView_parcelDest_L1.text = CourierApplication.Parcels[parcelID].destination_L1
        textView_parcelDest_L2.text = CourierApplication.Parcels[parcelID].destination_L2
        textView_parcelDest_L3.text = CourierApplication.Parcels[parcelID].destination_L3
        textView_parcelWeight.text = CourierApplication.Parcels[parcelID].weight.toString()
        textView_parcelStatus.text = CourierApplication.Parcels[parcelID].status

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        if(CourierApplication.currentUser.acc_type == 0) { // Kurier
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {

                val mDialogView = LayoutInflater.from(this).inflate(R.layout.status_dialog, null)

                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Zmień status")

                val mAlertDialog = mBuilder.show()

                mDialogView.button_statusCancel.setOnClickListener{
                    mAlertDialog.dismiss()
                }

                mDialogView.button_statusSave.setOnClickListener {

                    mAlertDialog.dismiss()

                    val tempStatus = mDialogView.editText_newStatus.text.toString()

                    CourierApplication.Parcels[parcelID].status = tempStatus

                    DBHelper.updateParcelStatus(parcelID, tempStatus)

                    startActivity(Intent(this, ParcelActivity::class.java))

                }

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
