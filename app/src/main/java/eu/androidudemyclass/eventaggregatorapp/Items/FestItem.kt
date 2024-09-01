package eu.androidudemyclass.eventaggregatorapp.Items

data class FestItem(
    val _id: Id,
    val city: String,
    val dateOfFest: String,
    val name: String,
    val noOfMaxPeople: Int,
    val pricePerDay: Int,
    val state: String,
    val theme: String
)