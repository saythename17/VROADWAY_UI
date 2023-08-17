package com.alphacircle.vroadway.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alphacircle.vroadway.data.category.Depth1Category
import javax.inject.Inject

class VroadwayViewModel @Inject constructor(private val category: Depth1Category) : ViewModel() {
    var depth1CategoryNameList : MutableLiveData<List<String>> = MutableLiveData(listOf())

    fun setDepth1CategoryNameList(nameList: List<String>) {
        depth1CategoryNameList.value = nameList
    }
}