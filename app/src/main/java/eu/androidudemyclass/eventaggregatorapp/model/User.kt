package eu.androidudemyclass.eventaggregatorapp.model

data class User(
    var email: String="",
    var firstName:String="",
    var lastName:String="",
    var password:String="",
    var collegeName:String ="",
)
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

