package com.demo.mytask.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.mytask.database.SampleData
import com.demo.mytask.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class WeatherRepository {

    val serviceSetterGetter = MutableLiveData<SampleData>()

    fun getInstance(): MutableLiveData<SampleData> {
        return serviceSetterGetter
    }

   public fun getServicesApiCalls(): MutableLiveData<SampleData> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object : Callback<SampleData> {
            override fun onFailure(call: Call<SampleData>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<SampleData>,
                response: Response<SampleData>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                //val msg = data!!.message

            }
        })

        return serviceSetterGetter
    }
}