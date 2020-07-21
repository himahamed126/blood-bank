package com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings

import com.example.bloodbank.data.model.general.GeneralData

class NotificationSettingsPresenter(var notificationSettingsView: NotificationSettingsContract.View?) :
        NotificationSettingsContract.Presenter,
        NotificationSettingsContract.Model.OnFinishedListenerBlood,
        NotificationSettingsContract.Model.OnFinishedListenerGovernorate,
        NotificationSettingsContract.Model.OnFinishedListenerGetNotiSett,
        NotificationSettingsContract.Model.OnFinishedListenerChangeNotiSett {

    var notificationSettingsModel: NotificationSettingsModel = NotificationSettingsModel()

    override fun onFinishedBlood(list: MutableList<GeneralData>) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.hideProgress()
        }
        notificationSettingsView!!.getBloodTypeList(list)
    }


    override fun onFinishedGovernorate(list: MutableList<GeneralData>) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.hideProgress()
        }
        notificationSettingsView!!.getGovernoratesList(list)
    }


    override fun onFinishedGetNotiSett(bloodList: MutableList<String>, governorteList: MutableList<String>) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.hideProgress()
        }
        notificationSettingsView!!.getNotificationSettings(bloodList, governorteList)
    }

    override fun onFinishedChangeNotiSett(message: String) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.hideProgress()
        }
        notificationSettingsView!!.onResponseFailure(message)
    }

    override fun onFailure(message: String) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.hideProgress()
        }
        notificationSettingsView!!.onResponseFailure(message)
    }

    override fun destroy() {
        notificationSettingsView = null
    }

    override fun requestFillAdapterFromServer(apiToken: String) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.showProgress()
        }
        notificationSettingsModel.getBloodTypes(this)
        notificationSettingsModel.getGovernorate(this)
        notificationSettingsModel.getNotificationSettings(this, apiToken)
    }

    override fun requestChangeNotificationSetting(apiToken: String,
                                                  governorateList: MutableList<Int>,
                                                  bloodList: MutableList<Int>) {
        if (notificationSettingsView != null) {
            notificationSettingsView!!.showProgress()
        }
        notificationSettingsModel.changeNotificationSetting(this, apiToken, governorateList, bloodList)
    }
}