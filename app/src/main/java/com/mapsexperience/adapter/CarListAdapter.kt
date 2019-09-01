package com.mapsexperience.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapsexperience.R
import com.mapsexperience.adapter.viewholder.CarListItemViewHolder
import com.mapsexperience.domain.CarInList

class CarListAdapter : RecyclerView.Adapter<CarListItemViewHolder>() {

    val items = mutableListOf<CarInList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CarListItemViewHolder(inflater.inflate(R.layout.car_list_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CarListItemViewHolder, position: Int) {
        val carInList = items[position]
        with(holder) {
            val resources = carEngineTextView.resources
            carNameTextView.text = carInList.name
            carEngineTextView.text = resources.getString(R.string.engine, carInList.engine)
            carExteriorTextView.text = resources.getString(R.string.car_ext, carInList.exterior)
            carInteriorTextView.text = resources.getString(R.string.car_int, carInList.interior)
        }
    }

    fun setItems(carList: List<CarInList>) {
        items.clear()
        items.addAll(carList)
        notifyDataSetChanged()
    }
}