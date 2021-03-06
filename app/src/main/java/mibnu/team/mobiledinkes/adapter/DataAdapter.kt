package mibnu.team.mobiledinkes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_data.view.*
import mibnu.team.mobiledinkes.view.DetailDataActivity
import mibnu.team.mobiledinkes.R
import mibnu.team.mobiledinkes.models.Data
class DataAdapter (private  var datas:MutableList<Data>, private var context: Context):RecyclerView.Adapter<DataAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false))

    override fun getItemCount()= datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(datas[position],context)
    fun updateList(ls : List<Data>){
        datas.clear()
        datas.addAll(ls)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(d : Data, context: Context){
            with(itemView){
                tv_nik.text = d.waris?.nik.toString()
                tv_name.text = d.waris!!.nama
                if (d.confirmedII==true){
                    val dataReceived : TextView = findViewById(R.id.icon)
                    val hasil ="Diterima"
                    dataReceived.text=hasil
                }else if(d.confirmedII==null){
                    val dataReceived : TextView = findViewById(R.id.icon2)
                    val hasil ="belum divalidasi"
                    dataReceived.text = hasil
                }else{
                    val dataReceived : TextView = findViewById(R.id.icon1)
                    val hasil ="Ditolak"
                    dataReceived.text=hasil
                }
                setOnClickListener {
                    context.startActivity(Intent(context, DetailDataActivity::class.java).apply {
                        putExtra("DATA", d)
                    })
                }
            }
        }
    }

}