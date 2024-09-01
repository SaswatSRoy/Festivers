package eu.androidudemyclass.eventaggregatorapp.Retrofit


import eu.androidudemyclass.eventaggregatorapp.Accesing.FestImpl
import eu.androidudemyclass.eventaggregatorapp.Accesing.FestRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val _interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(_interceptor)
        .build()

    private val api: Api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Api.BASE_URL)
        .client(client)
        .build()
        .create(Api::class.java)
    val festRepo:FestRepo = FestImpl(api)
}