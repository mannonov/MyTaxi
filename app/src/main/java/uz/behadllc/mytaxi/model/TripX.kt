package uz.behadllc.mytaxi.model

data class TripX(
    val carName: String,
    val carNumber: String,
    val carType: String,
    val date: String,
    val dateAndTimeTrip: String,
    val driverImage: String,
    val driverName: String,
    val driverRating: Int,
    val driverTrips: Int,
    val endPoint: String,
    val increasedDemand: String,
    val latlng: List<Latlng>,
    val minPrice: String,
    val order: Int,
    val paymentMethod: String,
    val price: String,
    val pricePending: String,
    val rate: String,
    val startPoint: String,
    val time: String,
    val total: String,
    val tripDuration: String,
    val tripPrice: String
)