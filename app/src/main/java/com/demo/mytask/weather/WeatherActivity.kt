package com.demo.mytask.weather

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.mytask.R
import com.demo.mytask.databinding.ActivityMainBinding
import com.demo.mytask.databinding.ActivityWheatherBinding
import com.example.mydatabaseapp.userDetails.UserDetalisFactory

class WeatherActivity : AppCompatActivity() {

    lateinit var context: Context

    lateinit var weatherActivityViewModel: WeatherViewModel
    private val TAG = "WeatherActivity"

    private lateinit var binding: ActivityWheatherBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_wheather)
        binding = DataBindingUtil.setContentView<ActivityWheatherBinding>(
            this,
            R.layout.activity_wheather
        )
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding.loader.visibility = View.VISIBLE

        context = this@WeatherActivity

        val factory = WeatherFactory(application)

        weatherActivityViewModel =
            ViewModelProvider(this, factory).get(WeatherViewModel::class.java)

        weatherActivityViewModel.getData()?.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            binding.loader.visibility = View.GONE
            val address = weatherActivityViewModel.getCityName(it.lat, it.lon)

            binding.address.text = address
            binding.temp.text = "${it.current.temp} °C"
            binding.humidity.text = "${it.current.humidity} g.kg-1"
            binding.wind.text = "${it.current.wind_speed} km/h"
            //binding.weather.text = it.current.weather[0].description.toString()
            binding.status.text = it.current.weather[0].description.toString()
            binding.tempMin.text = "Min temp: ${it.daily[0].temp.min} °C"
            binding.tempMax.text = "Max temp: ${it.daily[0].temp.max} °C"

        })
    }
}