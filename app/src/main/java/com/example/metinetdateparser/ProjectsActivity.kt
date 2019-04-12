package com.example.metinetdateparser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList

class ProjectsActivity : AppCompatActivity() {

    lateinit var listView_details: ListView
    var arrayList_details:ArrayList<Project> = ArrayList()
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)
        listView_details = findViewById(R.id.list_projects)
        run("http://project-manager.ptut.web-capsule.fr/graphql/?query={projects{elements{id, name, description, maxStudents} totalCount}}")
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("DEBUG", e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val str_response = response.body()!!.string()
                Log.d("DEBUG", str_response)
                //creating json object
                val json_contact:JSONObject = JSONObject(str_response).getJSONObject("data").getJSONObject("projects");
                //creating json array
                val jsonarray_info:JSONArray= json_contact.getJSONArray("elements")
                var i:Int = 0
                val size:Int = jsonarray_info.length()
                arrayList_details= ArrayList()
                for (i in 0.. size-1) {
                    val json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
                    val project:Project= Project();
                    project.id=json_objectdetail.getString("id")
                    project.name=json_objectdetail.getString("name")
                    project.description=json_objectdetail.getString("description")
                    project.countStudents=json_objectdetail.getInt("maxStudents")
                    arrayList_details.add(project)
                }

                runOnUiThread {
                    //stuff that updates ui
                    val obj_adapter : ProjectAdapter
                    obj_adapter = ProjectAdapter(applicationContext,arrayList_details)
                    listView_details.adapter=obj_adapter
                }
            }
        })
    }
}