package com.example.itr003

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class MyAdapter(val myDataSet : ArrayList<ItemData>, val onClickListener: View.OnClickListener) : RecyclerView.Adapter<MyAdapter.MyVieWHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyVieWHolder {
        val view : View = LayoutInflater.from(p0.context).inflate(R.layout.list_item, p0, false)
        return MyVieWHolder(view)
    }

    override fun onBindViewHolder(p0: MyVieWHolder, p1: Int) {
        val itemData = myDataSet.get(p1)
        p0.textViewName.text = itemData.name
        p0.textViewPhoneNum.text = itemData.phoneNum
        p0.iconStatus.setImageResource(if (itemData.status) {
            R.drawable.ic_check_circle_black_24dp
        } else {
            R.drawable.ic_error_black_24dp
        })

        p0.itemView.setOnClickListener(onClickListener)
        p0.itemView.tag = itemData

    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

    class MyVieWHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName : TextView
        var textViewPhoneNum : TextView
        var iconStatus : ImageView

        init {
            textViewName = view.findViewById(R.id.textViewTemplate)
            textViewPhoneNum = view.findViewById(R.id.textViewTemplate2)
            iconStatus = view.findViewById(R.id.imageView)

        }
    }

}