package eu.androidudemyclass.eventaggregatorapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.androidudemyclass.eventaggregatorapp.Accesing.FestRepo
import eu.androidudemyclass.eventaggregatorapp.Items.FestItem

import eu.androidudemyclass.eventaggregatorapp.model.Result

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FestViewModel(private val festRepository: FestRepo) : ViewModel() {
    private val _fests = MutableStateFlow<Result<List<FestItem>>>(Result.Success(emptyList()))
    val fests = _fests.asStateFlow()

    init {
        viewModelScope.launch {
            festRepository.getFestsList().collect { result ->
                _fests.value = result.data?.let { Result.Success(it) } ?: Result.Error(exception = Exception())
            }
        }
    }
}