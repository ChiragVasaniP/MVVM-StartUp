package com.dr.rajkul.utils

import com.dr.rajkul.enum.AppLanguages
import com.dr.rajkul.model.CountryData
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object AppHelper {

    var isDevelopment = true

    fun getPercentage(amount: Int, percentage: Float) = (amount * percentage) / 100

    fun getPercentage(intBig: Int, intSmall: Int) = (intSmall * 100) / intBig

    fun getPercentage(longBig: Long, longSmall: Long) = (longSmall * 100) / longBig


    var isVerificationOn: Boolean
        get() = PreferenceManager.getBoolean(Const.PHONE_VERIFICATION_ON)
        set(value) {
            PreferenceManager.putBoolean(Const.PHONE_VERIFICATION_ON, value)
        }

    fun getCountries(): ArrayList<CountryData> {
        val gson = Gson()
        val result: Array<CountryData> = gson.fromJson(
            Const.countriesListStr,
            Array<CountryData>::class.java
        )
        return ArrayList<CountryData>().apply {
            addAll(result)
        }
    }

    fun getAppLanguage() = PreferenceManager.getString(Const.APP_LANGUAGE, AppLanguages.HINDI.value)

    fun setAppLanguage(appLanguage: AppLanguages) {
        PreferenceManager.putString(Const.APP_LANGUAGE, appLanguage.value)
    }


    fun getDate(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
    }

    fun fullTimeStamp(): String {
        return SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault()).format(getDate().time)
    }

    fun getDateAsKey(): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(getDate().time)
    }

    fun weekAsKey() = SimpleDateFormat("w-yyyy", Locale.getDefault()).format(getDate().time)

    fun yearAsKey() = SimpleDateFormat("yyyy", Locale.getDefault()).format(getDate().time)

    fun monthAsKey() = SimpleDateFormat("MM-yyyy", Locale.getDefault()).format(getDate().time)



}