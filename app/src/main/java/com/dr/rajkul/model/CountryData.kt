package com.dr.rajkul.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("dial_code")
    var dial_code: String? = null,
    @SerializedName("flag")
    var flag: String? = null,
    @SerializedName("name")
    var name: String? = null,
    var selected: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dial_code)
        parcel.writeString(flag)
        parcel.writeString(name)
        parcel.writeByte(if (selected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryData> {
        override fun createFromParcel(parcel: Parcel): CountryData {
            return CountryData(parcel)
        }

        override fun newArray(size: Int): Array<CountryData?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$flag $name"
    }

}