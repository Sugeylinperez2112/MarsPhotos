
package com.example.marsphotos.network

import com.example.marsphotos.model.MarsPhoto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

/*
3.Crea un nuevo archivo de Kotlin debajo del nuevo paquete. Asígnale el nombre MarsApiService.
4.Abre network/MarsApiService.kt.
5.Agrega la siguiente constante para la URL de base del servicio web.
 */

    private const val BASE_URL =
        "https://android-kotlin-fun-mars-server.appspot.com"

    /**
     * Agrega un compilador de Retrofit justo debajo de esa constante para compilar y crear un objeto Retrofit.
     */
    private val retrofit = Retrofit.Builder()
        /*
        Llama a addConverterFactory() en el compilador con una instancia de ScalarsConverterFactory.
         */
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))

        /*
        Agrega la URL base para el servicio web con el método baseUrl().
        Por último, llama a build() para crear el objeto Retrofit.
         */
        .baseUrl(BASE_URL)
        .build()

/*Debajo de la llamada al compilador de Retrofit, define una interfaz llamada MarsApiService, que define cómo
 Retrofit se comunica con el servidor web mediante solicitudes HTTP.
 */
    interface MarsApiService {

    /*
    Usa la anotación @GET para indicar a Retrofit que es una solicitud GET y especifica el extremo para ese método
     de servicio web. En este caso, el extremo es photos. Como se mencionó en la tarea anterior, usarás el extremo /photos
      en este codelab.
     */
        @GET("photos")

        /*
        Cuando se invoca el método getPhotos(), Retrofit agrega el extremo photos a la URL de base (que definiste en
        el compilador de Retrofit) que se usó para iniciar la solicitud. Agrega un tipo de datos que se muestra de la función
        a String.
         */
        suspend fun getPhotos(): List<MarsPhoto>
    }

    /*
    Agrega una función llamada getPhotos() a la interfaz MarsApiService para obtener la string de respuesta del servicio web.
     */


/*
Fuera de la declaración de la interfaz de MarsApiService, define un objeto público llamado MarsApi para inicializar
el servicio de Retrofit. Este es el objeto singleton público al que puede acceder el resto de la app.
 */
    object MarsApi {

    /*
    Dentro de la declaración del objeto MarsApi, agrega una propiedad de objeto de Retrofit inicializada de forma diferida
     y con el nombre retrofitService del tipo MarsApiService. Realizarás esta inicialización diferida para asegurarte de que
     se inicialice en su primer uso. Ignora el error, ya que lo corregirás en los próximos pasos.
     */
        val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)

        /*
        Inicializa la variable retrofitService usando el método retrofit.create() con la interfaz MarsApiService.
         */
        }
}
