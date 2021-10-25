package com.carta.myapplication.ui.allsecurities

import androidx.lifecycle.*
import com.carta.myapplication.R
import com.carta.myapplication.common.Event
import com.carta.myapplication.common.Result
import com.carta.myapplication.data.SecuritySummaryRepository
import com.carta.myapplication.ui.SummaryViewModel
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllSecuritySummaryViewModel @Inject constructor(
    private val repository: SecuritySummaryRepository
) : ViewModel(), SummaryViewModel {

    val securitySummary: LiveData<List<SecuritySummary>> =
        repository.securitySummariesFlow.asLiveData()

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    private val _showFavConfirmDialog = MutableLiveData<Event<SecuritySummary>>()
    val showFavConfirmDialog: LiveData<Event<SecuritySummary>> =
        _showFavConfirmDialog

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        loadSecuritySummary()
    }

    override fun onClickSecuritySummary(securitySummary: SecuritySummary) {}

    override fun onClickSecuritySummaryFav(securitySummary: SecuritySummary, confirmed: Boolean) {
        viewModelScope.launch {
            if (!securitySummary.isFavorite && !confirmed) {
                _showFavConfirmDialog.value = Event(securitySummary)
            } else {
                val result = if (!securitySummary.isFavorite) {
                    repository.setFavoriteSecurity(securitySummary)
                } else {
                    repository.setUnFavoriteSecurity(securitySummary)
                }
                if (result is Result.Error) {
                    showSnackbarMessage(R.string.set_favorite_error)
                }
            }
        }
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