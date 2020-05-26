package com.fcmpusher.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcmpusher.model.App

class AppListViewModel: ViewModel(), LifecycleObserver {

    public var hasData: MutableLiveData<Boolean> = MutableLiveData()
    public var appList: MutableLiveData<List<App>> = MutableLiveData()

    init {
        hasData.value = false
    }

    fun getAppList() {
        var tempAppList: MutableList<App> = ArrayList()
        val firstApp = App()
        firstApp.appName = "Mega Bank"
        firstApp.fcmKey = "d8mjYgxg7Ez1vqNLuotZZE:APA91bFDjGpV5bvo3m1xdICefDvlSHY_AeZr52aI_fdCKKptbojd1mCm8qEHDPx55LXSyWq5naceUisbWoBJKbtDFRcJt4WEyMc9TN90tKDevOPiFNru54nsivcmQ8Kbntr9z_OCJVbO"
        tempAppList.add(firstApp)

        val secondApp = App()
        secondApp.appName = "Test App 2"
        secondApp.fcmKey = "asdfsaljkdfklsadjfkljsadf"
        tempAppList.add(secondApp)

        hasData.value = true
        appList.value = tempAppList
    }

}