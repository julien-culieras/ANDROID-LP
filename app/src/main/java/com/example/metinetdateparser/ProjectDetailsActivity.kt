package com.example.metinetdateparser

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ProjectDetailsActivity : AppCompatActivity() {

    val client = OkHttpClient()
    lateinit var project:Project
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView
    lateinit var tvTutor: TextView
    lateinit var tvStudents: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)
        tvTitle = findViewById(R.id.tv_title)
        tvDescription = findViewById(R.id.tv_description)
        tvTutor = findViewById(R.id.tv_tutor)

        val project_id = intent.getStringExtra("project_id")
        Log.d("DEBUG", "After intent : " + project_id)
        run("http://project-manager.ptut.web-capsule.fr/graphql/?query={project(id:\"$project_id\"){id, name, description, maxStudents, tutor{login, mail}}}")

        Handler().postDelayed({
            tvTitle.text = project.name
            tvDescription.text = project.description
            tvTutor.text = "Tuteur : ${project.tutor.login}"
        }, 1500)
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

                val test = json

                val tutor = Tutor(
                    json.getJSONObject("tutor").getString("mail"),
                    json.getJSONObject("tutor").getString("login")
                )

                project = Project(
                    json.getString("id"),
                    json.getString("name"),
                    json.getString("description"),
                    json.getInt("maxStudents"),
                    tutor
                )
            }
        })
    }
}
