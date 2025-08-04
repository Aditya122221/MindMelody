package com.example.mindmelody

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnboardingItem(
        val title: String,
        val description: String,
        val iconRes: Int,
        val gradientStart: Int,
        val gradientEnd: Int
) : Parcelable {
        override fun describeContents(): Int {
                return 0
        }

        override fun writeToParcel(p0: Parcel, p1: Int) {
        }
}
