package com.alphacircle.vroadway.viewmodels

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alphacircle.vroadway.data.category.Depth1Category
import com.alphacircle.vroadway.util.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel(private val category: Depth1Category) : ViewModel() {
    var depth1CategoryNameList : MutableLiveData<List<String>> = MutableLiveData(listOf())

    fun setDepth1CategoryNameList(nameList: List<String>) {
        depth1CategoryNameList.value = nameList
    }
}