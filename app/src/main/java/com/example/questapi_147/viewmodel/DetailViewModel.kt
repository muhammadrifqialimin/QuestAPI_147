package com.example.questapi_147.viewmodel

sealed interface StatusUiDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUiDetail
    object Error : StatusUiDetail
    object Loading : StatusUiDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])
    var statusUiDetail: StatusUiDetail by mutableStateOf(StatusUiDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUiDetail = StatusUiDetail.Loading
            statusUiDetail = try {
                StatusUiDetail.Success(satusiswa = repositoryDataSiswa.getSatuSiswa(idSiswa))
            } catch (e: IOException) {
                StatusUiDetail.Error
            } catch (e: HttpException) {
                StatusUiDetail.Error
            }
        }
    }
