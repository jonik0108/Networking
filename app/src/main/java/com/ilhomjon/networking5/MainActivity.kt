package com.ilhomjon.networking5

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.ilhomjon.networking5.Utils.NetworkHelper
import com.ilhomjon.networking5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var networkHelper: NetworkHelper
    lateinit var handler: Handler

    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*
//        networkHelper = NetworkHelper(this)
////        if (networkHelper.isNetworkConnected()){
////            binding.txtInternet.text = "Connected"
////        }else{
////            binding.txtInternet.text = "Disconnected"
////        }
//
//        handler = Handler()
//        handler.postDelayed(runnable,1000)
//
////        if (isNetworkConnected()){
////            binding.txtInternet.text = "Connected"
////        }else{
////            binding.txtInternet.text = "Disconnected"
////        }
 */
        requestQueue = Volley.newRequestQueue(this)

        getImageUri()
    }
/*    val runnable = object : Runnable{
        override fun run() {
            if (networkHelper.isNetworkConnected()) {
                binding.txtInternet.text = "Connected"
                Log.d("NETWORK","Connected")
            } else {
                binding.txtInternet.text = "Disconnected"
                Log.d("NETWORK","Disconnected")
            }
            handler.postDelayed(this, 1000)
        }
    }*/

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }


    fun getImageUri() {
        val imageRequest = ImageRequest("https://i.imgur.com/Nwk25LA.jpg",
            object : Response.Listener<Bitmap> {
                override fun onResponse(response: Bitmap?) {
                    binding.image.setImageBitmap(response)
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            }
        )
        requestQueue.add(imageRequest)
    }
}