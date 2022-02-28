package uz.behadllc.mytaxi.model

import uz.behadllc.mytaxi.utils.TaxiType

data class MiniTrip(

    val startPoint: String,
    val endPoint: String,
    val time: String,
    val price: String,
    val taxiType: TaxiType,


    )