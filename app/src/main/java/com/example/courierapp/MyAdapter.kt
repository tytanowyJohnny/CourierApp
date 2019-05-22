package layout


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courierapp.Parcel
import com.example.courierapp.R
import kotlinx.android.synthetic.main.parcel_row_v2.view.*

class MyAdapter(private val myDataset: ArrayList<Parcel>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.parcel_row_v2, parent, false)

        return MyViewHolder(linearLayout as LinearLayout)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        holder.linearLayout.parcelName.text = myDataset[position].name

        holder.linearLayout.parcelStatus.text = myDataset[position].status
    }

    override fun getItemCount(): Int {

        return myDataset.count()
    }

}
