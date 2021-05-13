package com.example.searchplacesapp

import com.example.searchplacesapp.Model.DetailsResponse
import com.example.searchplacesapp.Model.MainPojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    // https://maps.googleapis.com/maps/api/place/queryautocomplete/json
    @GET("place/queryautocomplete/json")
    fun getPlace(@Query("input") text:String, @Query("key") key:String): Call<MainPojo>
    @GET("place/details/json")
    fun getLatLng(@Query("placeid") place_id:String, @Query("key") key:String):Call<DetailsResponse>
}