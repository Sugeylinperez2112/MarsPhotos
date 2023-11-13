
package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/*
Abre el archivo ui/screens/MarsViewModel.kt. Desplázate hacia abajo hasta el método getMarsPhotos().
 Borra la línea que establece la respuesta de estado en "Set the Mars API Response here!" para que el método
  getMarsPhotos() esté vacío.
 */
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set


    init {
        getMarsPhotos()
    }


    /*
    Dentro de getMarsPhotos(), inicia la corrutina mediante viewModelScope.launch.
     */
    fun getMarsPhotos() {

        /*
        Dentro de viewModelScope, usa el objeto singleton MarsApi para llamar al método getPhotos() desde la interfaz
         retrofitService. Guarda la respuesta que se muestra en un val llamado listResult.
         */
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                /*
                Asigna el resultado que acabamos de recibir del servidor backend a marsUiState marsUiState es un objeto de estado mutable
                 que representa
                 el estado de la solicitud web más reciente.
                 */
                val listResult = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}
