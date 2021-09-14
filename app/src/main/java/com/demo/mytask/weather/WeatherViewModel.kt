package com.demo.mytask.weather

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.mytask.database.SampleData
import com.demo.mytask.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class WeatherViewModel(val application: Application) : ViewModel() {

    //this is the data that we will fetch asynchronously
    private var dataList: MutableLiveData<SampleData>? = null

    //we will call this method to get the data
    fun getData(): LiveData<SampleData>? {
        //if the list is null
        if (dataList == null) {
            dataList = MutableLiveData<SampleData>()
            //we will load it asynchronously from server in this method
            loadData()
        }

        //finally we will return the list
        return dataList
    }

    fun getCityName(latitude: Double, longitude: Double): String {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(application, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        val city: String = addresses[0].locality
        val country = addresses[0].countryName
        return "$city, $country"
    }

    private fun loadData() {
        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object : Callback<SampleData> {
            override fun onFailure(call: Call<SampleData>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
                dataList?.postValue(null)
            }

            override fun onResponse(
                call: Call<SampleData>,
                response: Response<SampleData>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                //val msg = data!!.message

                dataList?.postValue(data)
            }
        })
    }
}