package com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings

import com.example.bloodbank.data.api.ApiClient.client
import com.example.bloodbank.data.model.general.GeneralData
import com.example.bloodbank.extensions.addEnqueue
import com.example.bloodbank.ui.fragment.homeCycle.more.notification_settings.NotificationSettingsContract.Model

class NotificationSettingsModel : Model {

    private var bloodList: MutableList<GeneralData> = mutableListOf()
    private var governoatesList: MutableList<GeneralData> = mutableListOf()
    private var oldBloodTypeList: MutableList<String> = mutableListOf()
    private var oldGovernorateList: MutableList<String> = mutableListOf()

    override fun getBloodTypes(onFinishedListenerBlood: Model.OnFinishedListenerBlood) {
        client().getbloodTypes().addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        bloodList.addAll(it.body()!!.data!!)
                        onFinishedListenerBlood.onFinishedBlood(bloodList)
                    } else {
                        onFinishedListenerBlood.onFailure(it.body()!!.msg!!)
                    }
                },
                {
                    onFinishedListenerBlood.onFailure(it.message!!)
                }
        )
    }

    override fun getGovernorate(onFinishedListenerGovernorate: Model.OnFinishedListenerGovernorate) {
        client().governorates.addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        governoatesList.addAll(it.body()!!.data!!)
                        onFinishedListenerGovernorate.onFinishedGovernorate(governoatesList)
                    } else {
                        onFinishedListenerGovernorate.onFailure(it.body()!!.msg!!)
                    }
                },
                {
                    onFinishedListenerGovernorate.onFailure(it.message!!)
                }
        )
    }

    override fun getNotificationSettings(onFinishedListenerGetNotiSett: Model.OnFinishedListenerGetNotiSett,
                                         apiToken: String) {
        client().getNotificationSettings(apiToken).addEnqueue(
                {
                    if (it.body()!!.status == 1) {
                        oldBloodTypeList.addAll(it.body()!!.data!!.bloodTypes!!)
                        oldGovernorateList.addAll(it.body()!!.data!!.governorates!!)
                    }
                    onFinishedListenerGetNotiSett.onFinishedGetNotiSett(oldBloodTypeList, oldGovernorateList)
                },
                {
                    onFinishedListenerGetNotiSett.onFailure(it.message!!)
                }
        )
    }

    override fun changeNotificationSetting(
            onFinishedListenerChangeNotiSett: Model.OnFinishedListenerChangeNotiSett,
            apiToken: String, governorateList: MutableList<Int>, bloodList: MutableList<Int>) {
        client().changeNotificationSettings(apiToken, governorateList, bloodList).addEnqueue(
                {
                    onFinishedListenerChangeNotiSett.onFinishedChangeNotiSett(it.body()!!.msg!!)
                },
                {
                    onFinishedListenerChangeNotiSett.onFailure(it.message!!)
                }
        )
    }
}