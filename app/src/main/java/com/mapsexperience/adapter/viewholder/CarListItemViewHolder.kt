package com.mapsexperience.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_list_item.view.*

class CarListItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val carNameTextView = view.carName
    val carEngineTextView = view.carEngine
    val carInteriorTextView = view.carInt
    val carExteriorTextView = view.carExt
}