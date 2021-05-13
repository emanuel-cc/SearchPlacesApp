package com.example.searchplacesapp.Model


data class DetailsResponse (
    val htmlAttributions: List<Any?>,
    val result: Result,
    val status: String
)

data class Result (
    val address_components: List<AddressComponent>,
    val adr_address: String,
    val formatted_address: String,
    val geometry: Geometry,
    val icon: String,
    val name: String,
    val place_id: String,
    val reference: String,
    val types: List<String>,
    val url: String,
    val utc_offset: Long,
    val vicinity: String
)

data class AddressComponent (
    val long_name: String,
    val short_name: String,
    val types: List<String>
)

data class Geometry (
    val location: Location,
    val viewport: Viewport
)

data class Location (
    val lat: Double,
    val lng: Double
)

data class Viewport (
    val northeast: Location,
    val southwest: Location
)
