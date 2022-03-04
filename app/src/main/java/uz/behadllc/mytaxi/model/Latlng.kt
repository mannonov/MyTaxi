package uz.behadllc.mytaxi.model

data class Latlng(
    val lat: Double,
    val lng: Double,
) {

    override fun toString(): String {
        return String.format("$lat,$lng")
    }

}
