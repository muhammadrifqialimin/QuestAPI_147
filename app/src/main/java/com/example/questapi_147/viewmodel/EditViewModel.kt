package com.example.questapi_147.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questapi_147.modeldata.DetailSiswa
import com.example.questapi_147.modeldata.UiStateSiswa
import com.example.questapi_147.modeldata.toDataSiswa
import com.example.questapi_147.modeldata.toUiStateSiswa
import com.example.questapi_147.repositori.RepositoryDataSiswa
import com.example.questapi_147.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.Response

class EditViewModel( savedStateHandle: SavedStateHandle,private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UiStateSiswa())
        private set

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    init {
        viewModelScope.launch {
            uiStateSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa).toUiStateSiswa(true)
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UiStateSiswa(
            detailsiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailsiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    suspend fun editSatuSiswa() {
        if (validasiInput(uiStateSiswa.detailsiswa)) {
            val call: Response<Void> = repositoryDataSiswa.editSatuSiswa(idSiswa, uiStateSiswa.detailsiswa.toDataSiswa())
            if (call.isSuccessful) {
                println("Update Sukses ${call.message()}")
            } else {
                println("Update Error: ${call.errorBody()}")
            }
        }
    }

}