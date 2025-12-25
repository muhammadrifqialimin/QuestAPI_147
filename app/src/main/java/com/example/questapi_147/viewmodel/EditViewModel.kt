package com.example.questapi_147.viewmodel

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
