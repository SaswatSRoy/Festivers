package eu.androidudemyclass.eventaggregatorapp.Retrofit

import eu.androidudemyclass.eventaggregatorapp.Items.Fest
import eu.androidudemyclass.eventaggregatorapp.Items.FestItem
import retrofit2.http.GET

    interface Api {

        @GET("fetch")
        suspend fun getFestsList():List<FestItem>

        companion object{
            const val BASE_URL="https://1192.168.112.65:27017/"
        }

    }
