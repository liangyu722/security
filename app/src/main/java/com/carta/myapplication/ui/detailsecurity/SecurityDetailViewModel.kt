package com.carta.myapplication.ui.detailsecurity

import androidx.lifecycle.*
import com.carta.myapplication.R
import com.carta.myapplication.common.Event
import com.carta.myapplication.common.Result
import com.carta.myapplication.data.SecurityDetailRepository
import com.carta.myapplication.data.SecuritySummaryRepository
import com.carta.myapplication.ui.SummaryViewModel
import com.carta.myapplication.ui.model.SecurityDetail
import com.carta.myapplication.ui.model.SecuritySummary
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecurityDetailViewModel @Inject constructor(
    private val repository: SecurityDetailRepository
) : ViewModel() {

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarText

    private val _securityDetail = MutableLiveData<SecurityDetail>()
    val securityDetail: LiveData<SecurityDetail> = _securityDetail

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun getSecurityDetail(securityId: Int) {
        viewModelScope.launch {
            _dataLoading.value = true
            val result = repository.getSecurityDetail(securityId)
            if (result is Result.Success) {
                _securityDetail.value = result.data
            } else {
                showSnackbarMessage(R.string.loading_security_detail_error)
            }
            _dataLoading.value = false
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbarText.value = Event(message)
    }

}
