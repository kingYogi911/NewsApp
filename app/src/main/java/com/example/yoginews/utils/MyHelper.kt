package com.example.yoginews.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

class MyHelper(private val context: Context): LiveData<Boolean>() {
    private val networkReceiver=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            postValue(isOnline())
        }
    }
    override fun onActive() {
        super.onActive()
        context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }
    private fun isOnline(): Boolean {
        val connMan =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connMan.activeNetworkInfo.let {
            it?.isConnectedOrConnecting == true
        }
    }
}