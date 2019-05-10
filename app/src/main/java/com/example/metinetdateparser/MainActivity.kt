package com.example.metinetdateparser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.jsonResponse)
        val btnProjects = findViewById<Button>(R.id.btnListProjects)

        btnProjects.setOnClickListener {view ->
            val intent = Intent(applicationContext,ProjectsActivity::class.java)
            startActivity(intent)
        }


        val queue = Volley.newRequestQueue(this)
        val url = "https://script.googleusercontent.com/macros/echo?user_content_key=qU9oQrcNJv4QHBRENSTLMt-ZmrwQTnr8y7ZTB6JN39KAWTwY2jVjZNIW3cO8TWTCGJUoPCj-6BAYU_DRnRNkATDO6h2lacHZm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnJ9GRkcRevgjTvo8Dc32iw_BLJPcPfRdVKhJT5HNzQuXEeN3QFwl2n0M6ZmO-h7C6bwVq0tbM60-YSRgvERRRx_Glx38N8iKHQ&lib=MwxUjRcLr2qLlnVOLh12wSNkqcO1Ikdrk"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                println(response)
                textView.text = response.getString("fulldate")
            },
            Response.ErrorListener { error ->
                textView.text = "Erreur : $error"
            }
        )
        queue.add(jsonObjectRequest)
    }


}
