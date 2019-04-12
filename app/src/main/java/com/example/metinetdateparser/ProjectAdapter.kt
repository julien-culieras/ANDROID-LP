package com.example.metinetdateparser

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

class ProjectAdapter(context: Context,arrayListDetails:ArrayList<Project>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Project>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.projects_listview_item, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.itemName.text = arrayListDetails.get(position).name
        listRowHolder.itemDescription.text = arrayListDetails.get(position).description
        listRowHolder.itemCountStudents.text = "Nombre maximum d'étudiants : " + arrayListDetails.get(position).countStudents.toString()
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val itemName: TextView
    public val itemDescription: TextView
    public val itemCountStudents: TextView
    public val linearLayout: LinearLayout

    init {
        this.itemName = row?.findViewById<TextView>(R.id.item_title) as TextView
        this.itemDescription = row?.findViewById<TextView>(R.id.item_description) as TextView
        this.itemCountStudents = row?.findViewById<TextView>(R.id.item_countStudents) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
}