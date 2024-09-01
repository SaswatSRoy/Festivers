package eu.androidudemyclass.eventaggregatorapp.Accesing

import eu.androidudemyclass.eventaggregatorapp.Items.FestItem
import eu.androidudemyclass.eventaggregatorapp.Accesing.Result

import kotlinx.coroutines.flow.Flow

interface FestRepo {
    suspend fun getFestsList(): Flow<Result<List<FestItem>>>

}