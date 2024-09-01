package eu.androidudemyclass.eventaggregatorapp.Accesing

import eu.androidudemyclass.eventaggregatorapp.Items.FestItem
import eu.androidudemyclass.eventaggregatorapp.Retrofit.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.truncate

class FestImpl(
private val api: Api
) : FestRepo {
    override suspend fun getFestsList(): Flow<Result<List<FestItem>>> {
        return flow {


            val result = try {
                val festFromApi = api.getFestsList()
                Result.Success(festFromApi.map { it })
            } catch (e: IOException) {
                e.printStackTrace()
                Result.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                e.printStackTrace()
                Result.Error("API error: ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error("Unknown error: ${e.message}")
            }

            emit(result)
        }
    }
}

