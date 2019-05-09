package com.example.metinetdateparser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ProjectDetailsActivity : AppCompatActivity() {

    val client = OkHttpClient()
    val project:Project= Project()
    lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)
        tvTitle = findViewById(R.id.tv_title)

        val project_id = intent.getStringExtra("project_id")
        Log.d("DEBUG", "After intent : " + project_id)
        run("http://project-manager.ptut.web-capsule.fr/graphql/?query={project(id:\"$project_id\"){id, name, description, maxStudents}}")

    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .header("X-AUTH-TOKEN", "admin_key")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("DEBUG", e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val str_response = response.body()!!.string()
                val json: JSONObject = JSONObject(str_response).getJSONObject("data").getJSONObject("project")
                project.id=json.getString("id")
                project.name=json.getString("name")
                project.description=json.getString("description")
                project.countStudents=json.getInt("maxStudents")

                print(project.name)
                tvTitle.text = project.name
            }
        })
    }
}
