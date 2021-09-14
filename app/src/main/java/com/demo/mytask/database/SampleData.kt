package com.demo.mytask.database

data class SampleData(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)