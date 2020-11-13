package com.example.temperaturelog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection

class LoginActivity: AppCompatActivity() {
    private var etLogin: EditText? = null;
    private var etPassword: EditText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password)
        findViewById<View>(R.id.progressBar).visibility = View.GONE

    }


    fun onLoginClick(view: View) {
        println("clicked!")
        if (etLogin != null && etPassword != null) {
            val username: String = etLogin!!.text.toString().trim()
            val password: String = etPassword!!.text.toString().trim()
            val obj = JSONObject()
            try {
                obj.put("login", username)
                obj.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val handler: RequestHandler = RequestHandler()
            handler.httpMethod = "POST"
            handler.urlResource = "auth"
            findViewById<View>(R.id.progressBar).visibility = View.VISIBLE
            println(obj.toString())
            handler.execute(object : CallBack() {
                override fun onSuccess(result: String) {
                    startMainActivityAndClose(result)
                    runOnUiThread { findViewById<View>(R.id.progressBar).visibility = View.GONE }
                }

                override fun onFail(message: String?, requestCode: Int) {
                    ifFailure(requestCode)
                }
            }, obj.toString())
        }
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
            findViewById<View>(R.id.progressBar).visibility = View.GONE
        }
    }

    private fun startMainActivityAndClose(result: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("token", result)
        intent.putExtra("username", etLogin!!.text.toString().trim())
        startActivity(intent)
        finish()
    }


}