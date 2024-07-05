package com.movies.streamy.viewmodel

import android.content.ClipData
import androidx.lifecycle.ViewModel
// Adjust based on your actual data model

class DetailViewModel : ViewModel() {
    private var selectedItem: ClipData.Item? = null // Replace with your actual data model class

    fun setSelectedItem(item: ClipData.Item) {
        selectedItem = item
    }

    fun getSelectedItem(): ClipData.Item? {
        return selectedItem
    }
}
