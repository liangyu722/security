package com.example.myapplication.ui.home

import androidx.lifecycle.*
import com.example.myapplication.R
import com.example.myapplication.common.Event
import com.example.myapplication.common.Result
import com.example.myapplication.data.SecuritySummaryRepository
import com.example.myapplication.ui.SummaryViewModel
import com.example.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PREVIEW_NUM = 5

class SecuritySummaryViewModel @Inject constructor(
    private val repository: SecuritySummaryRepository
) : ViewModel(), SummaryViewModel {

    val favSecuritySummary: LiveData<List<SecuritySummary>> = liveData {
        repository.securitySummariesFlow.collect {
            emit(it.filter { securitySummary ->
                securitySummary.isFavorite
            })
        }
    }

    val securitySummary: LiveData<List<SecuritySummary>> = liveData {
        repository.securitySummariesFlow.collect {
            emit(it.take(PREVIEW_NUM))
        }
    }

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    private val _clickSecuritySummaryEvent = MutableLiveData<Event<SecuritySummary>>()
    val clickSecuritySummaryEvent: LiveData<Event<SecuritySummary>> =
        _clickSecuritySummaryEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        loadSecuritySummary()
    }

    override fun onClickSecuritySummary(securitySummary: SecuritySummary) {
        _clickSecuritySummaryEvent.value = Event(securitySummary)
    }

    override fun onClickSecuritySummaryFav(securitySummary: SecuritySummary, confirmed: Boolean) {
    }

    private fun loadSecuritySummary() {
        viewModelScope.launch {
            _dataLoading.value = true
            val result = repository.getSecuritySummaries()
            if (result is Result.Error) {
                showSnackbarMessage(R.string.loading_security_summaries_error)
            }
            _dataLoading.value = false
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}