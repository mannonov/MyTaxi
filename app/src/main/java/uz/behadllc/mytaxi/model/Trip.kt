package uz.behadllc.mytaxi.model

data class Trip(
    val date: String,
    val miniTrips: List<MiniTrip>,
)