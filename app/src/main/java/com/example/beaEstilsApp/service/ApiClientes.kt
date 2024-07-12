package com.example.beaEstilsApp.service

import com.example.beaEstilsApp.models.ClienteResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiClientes {
    @GET
    suspend fun getClientes(@Url url: String): ClienteResponse

}

object RetrofitServiceFactory {
    //Funcion que crea un objeto Retrofit(aliasclientes) a partir de una URl + una factoría de conversión + la Interfaz de la petición
    fun getClientesRetrofit(): ApiClientes {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")

            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClientes::class.java)
    }


}