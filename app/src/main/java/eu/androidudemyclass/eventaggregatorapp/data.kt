package eu.androidudemyclass.eventaggregatorapp

data class FestItem(
    val id:Int,
    val title:String,
    val location:String,
    val date:String,
    val imageUrl:String
)
val sampleFests = listOf(
            FestItem(1, "Music Mania", "New York", "2023-12-20", "https://example.com/music_mania.jpg"),
            FestItem(2, "Food Fiesta", "Los Angeles", "2023-11-15", "https://example.com/food_fiesta.jpg"),
            FestItem(3, "Tech Expo", "San Francisco", "2023-12-05", "https://example.com/tech_expo.jpg"),
            FestItem(4, "Art Festival", "Chicago", "2023-10-28", "https://example.com/art_festival.jpg"),
            FestItem(5, "Film Fest", "Seattle", "2023-11-02", "https://example.com/film_fest.jpg")
        )


