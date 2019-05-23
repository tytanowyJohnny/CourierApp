package com.example.courierapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife

import kotlinx.android.synthetic.main.activity_parcel.*
import layout.MyAdapter

class ParcelActivity : AppCompatActivity() {

    @BindView(R.id.recyclerView_parcel)
    lateinit var recyclerView_parcel: RecyclerView

    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var DBHelper: SQLiteDbHandler

    lateinit var userParcels: ArrayList<Parcel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel)
        setSupportActionBar(toolbar)

        ButterKnife.bind(this)

        DBHelper = SQLiteDbHandler(this)
        userParcels = ArrayList()

        for(parcel in CourierApplication.Parcels) {

            if(parcel.parcelUser == CourierApplication.currentUser.ID)
                userParcels.add(parcel)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(userParcels)

        val itemDecorator = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(resources.getDrawable(R.drawable.divider))

        recyclerView_parcel.apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

            addItemDecoration(itemDecorator)

        }

        fab.setOnClickListener { view ->

            this.startActivity(Intent(this, NewParcelActivity::class.java))
        }
    }

}
