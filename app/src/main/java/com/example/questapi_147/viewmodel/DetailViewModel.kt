package com.example.questapi_147.viewmodel

sealed interface StatusUiDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUiDetail
    object Error : StatusUiDetail
    object Loading : StatusUiDetail
}