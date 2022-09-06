package com.example.monsterhunter

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
const val BASE_URL = "https://mhw-db.com/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ArmorsApiService {
    /**
     * Returns a Retrofit callback that delivers a list of armors
     * The @GET annotation indicates that the "armor" endpoint will be requested with the GET
     * HTTP method
     */

    @GET("armor")
    fun getArmorAsync():
            Deferred<List<Armor>>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ArmorsApi {
    val retrofitService: ArmorsApiService by lazy { retrofit.create(ArmorsApiService::class.java) }
}
