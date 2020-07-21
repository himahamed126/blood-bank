package com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings

import com.example.bloodbank.data.model.general.GeneralData

interface NotificationSettingsContract {

    interface Model {

        interface OnFailure {
            fun onFailure(message: String)
        }

        interface OnFinishedListenerBlood : OnFailure {
            fun onFinishedBlood(list: MutableList<GeneralData>)
        }

        interface OnFinishedListenerGovernorate : OnFailure {
            fun onFinishedGovernorate(list: MutableList<GeneralData>)
        }

        interface OnFinishedListenerGetNotiSett : OnFailure {
            fun onFinishedGetNotiSett(bloodList: MutableList<String>, governorteList: MutableList<String>)
        }

        interface OnFinishedListenerChangeNotiSett : OnFailure {
            fun onFinishedChangeNotiSett(message: String)
        }

        fun getBloodTypes(onFinishedListenerBlood: OnFinishedListenerBlood)
        fun getGovernorate(onFinishedListenerGovernorate: OnFinishedListenerGovernorate)
        fun getNotificationSettings(onFinishedListenerGetNotiSett: OnFinishedListenerGetNotiSett, apiToken: String)

        fun changeNotificationSetting(onFinishedListenerChangeNotiSett: OnFinishedListenerChangeNotiSett,
                                      apiToken: String, governorateList: MutableList<Int>, bloodList: MutableList<Int>)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun getBloodTypeList(bloodList: MutableList<GeneralData>)
        fun getGovernoratesList(governoratesList: MutableList<GeneralData>)
        fun getNotificationSettings(bloodList: MutableList<String>, governorteList: MutableList<String>)
        fun onResponseFailure(message: String)
    }

    interface Presenter {
        fun destroy()
        fun requestFillAdapterFromServer(apiToken: String)
        fun requestChangeNotificationSetting(apiToken: String,
                                             governorateList: MutableList<Int>,
                                             bloodList: MutableList<Int>)
    }
}