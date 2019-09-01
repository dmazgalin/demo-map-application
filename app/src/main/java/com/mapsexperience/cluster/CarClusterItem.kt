package com.mapsexperience.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class CarClusterItem(val clusterLatLng: LatLng, val clusterTitle: String) : ClusterItem {
    override fun getSnippet(): String = ""

    override fun getTitle(): String = clusterTitle

    override fun getPosition(): LatLng = clusterLatLng
}