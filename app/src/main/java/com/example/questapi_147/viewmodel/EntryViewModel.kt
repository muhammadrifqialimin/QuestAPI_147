package com.example.questapi_147.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.questapi_147.modeldata.DetailSiswa
import com.example.questapi_147.modeldata.UiStateSiswa
import com.example.questapi_147.modeldata.toDataSiswa
import com.example.questapi_147.modeldata.toDetailSiswa
import com.example.questapi_147.repositori.RepositoryDataSiswa
import retrofit2.Response

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) :
    ViewModel() {

    var uiStateSiswa by mutableStateOf(UiStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(
        uiState: DetailSiswa = uiStateSiswa.detailsiswa
    ): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    //Fungsi untuk menangani saat ada perubahan pada text input
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UiStateSiswa(
                detailsiswa = detailSiswa,
                isEntryValid = validasiInput(detailSiswa)
            )
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (!validasiInput()) return

        try {
            val response = repositoryDataSiswa.postDataSiswa(
                uiStateSiswa.detailsiswa.toDataSiswa()
            )

            if (response.isSuccessful) {
                println("✅ Sukses Tambah Data")
            } else {
                println("❌ Gagal tambah data: ${response.code()}")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            println("🔥 ERROR addSiswa: ${e.message}")
        }
    }

}
