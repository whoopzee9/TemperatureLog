package com.example.temperaturelog

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    private lateinit var rvTemperatures: RecyclerView
    var values = ArrayList<Temperature>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTemperatures = findViewById(R.id.rv_temperatures)

        val adapter = RecyclerAdapter(values)
        rvTemperatures.adapter = adapter
        rvTemperatures.layoutManager = LinearLayoutManager(this)

        val user = intent.getStringExtra("username")
        val token = intent.getStringExtra("token")

        auth(user, token, adapter)

        mHandler = Handler()

        swipeRefreshLayout.setOnRefreshListener {
            // Initialize a new Runnable
            mRunnable = Runnable {
                auth(user, token, adapter)

                swipeRefreshLayout.isRefreshing = false
            }
            // Execute the task after specified time
            mHandler.postDelayed(
                mRunnable, 500.toLong()
            )
        }

        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_orange_dark,
            android.R.color.holo_orange_light,
            android.R.color.holo_green_light,
            android.R.color.holo_blue_bright,
            android.R.color.holo_blue_dark,
            android.R.color.holo_purple
        )
    }

    private fun auth(user: String, token:String, adapter:RecyclerAdapter){
        val handler: RequestHandler = RequestHandler()
        handler.httpMethod = "GET"
        handler.urlResource = "$user/measurements"
        handler.token = token
        handler.execute(object : CallBack() {
            override fun onSuccess(result: String) {
                println(result)
                val converter = TemperatureConverter()
                val builder = GsonBuilder()
                builder.registerTypeAdapter(Temperature::class.java, converter)
                val gson = builder.create()
                val listType = object : TypeToken<List<Temperature>>(){}.type
                val type = object : TypeToken<Temperature>(){}.type
                var list = ArrayList<Temperature>()
                try {
                    list = gson.fromJson(result, listType)
                } catch (ex: JsonIOException) {
                    val obj:Temperature = gson.fromJson(result, type)
                    list.add(obj)
                } catch (ex: JsonSyntaxException) {
                    val obj: Temperature = gson.fromJson(result, type)
                    list.add(obj)
                }
                println(list.size)
                adapter.setValues(list)
                runOnUiThread { adapter.notifyDataSetChanged() }
            }

            override fun onFail(message: String?, requestCode: Int) {
                ifFailure(requestCode)
            }
        }, "")
    }

    private fun ifFailure(code: Int) {
        val msg: String = when (code) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                "Bad request!"
            }
            else -> {
                "Invalid username or password!"
            }
        }
        runOnUiThread {
            val toast = Toast.makeText(
                applicationContext,
                msg, Toast.LENGTH_LONG
            )

            toast.show()
        }
    }
}