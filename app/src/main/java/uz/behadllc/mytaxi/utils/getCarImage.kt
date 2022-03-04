package uz.behadllc.mytaxi.utils

import uz.behadllc.mytaxi.R

fun getCarImage(string: String): Int {
    return when (string) {
        "delivery" -> R.drawable.ic_car_delivery
        "start" -> R.drawable.ic_car_start
        "comfort" -> R.drawable.ic_car_comfort
        else -> R.drawable.ic_car_comfort
    }
}