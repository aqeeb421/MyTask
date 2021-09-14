package com.demo.mytask.retrofit

import com.demo.mytask.database.SampleData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("data/2.5/onecall?lat=12.9082847623315&lon=77.65197822993314&units=imperial&appid=b143bb707b2ee117e62649b358207d3e")
    fun getServices() : Call<SampleData>

}