package com.ilazar.myservices.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ilazar.myservices.util.ConnectivityManagerNetworkMonitor
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
class MyNetworkStatusViewModel(application: Application) : AndroidViewModel(application) {

    init {
        //ConnectivityManagerNetworkMonitor(getApplication()).isOnline? true: false
    }

//    companion object {
//        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                MyNetworkStatusViewModel(application)
//            }
//        }
//    }
}

//@Composable
//fun MyNetworkStatus() {
//    val myNewtworkStatusViewModel = viewModel<MyNetworkStatusViewModel>(
//        factory = MyNetworkStatusViewModel.Factory(
//            LocalContext.current.applicationContext as Application
//        )
//    )
//
//    Text(
//        "Is online: ${myNewtworkStatusViewModel.uiState}",
//        style = MaterialTheme.typography.h3,
//    )
//}
