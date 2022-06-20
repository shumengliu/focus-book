package com.honestboook.focusbook.model;

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Sites")
data class Site(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: Int,
    val name: String,
    val url: String
): Parcelable {
}

