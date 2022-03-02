package uz.behadllc.mytaxi.model

import android.os.Parcel
import android.os.Parcelable

data class Latlng(
    val lat: Double,
    val lng: Double
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Latlng> {
        override fun createFromParcel(parcel: Parcel): Latlng {
            return Latlng(parcel)
        }

        override fun newArray(size: Int): Array<Latlng?> {
            return arrayOfNulls(size)
        }
    }
}
