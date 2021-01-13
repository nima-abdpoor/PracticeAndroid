package com.nima.practice

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.template.other.JsonArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //createJsonString()
    }

//    private fun createFolderInInternalStorage() {
//        Log.d("TAG", "createFolderInInternalStorage: dddd")
//        val filename = "myFile"
//        val fileContents = "Hello world!"
//        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
//            it.write(fileContents.toByteArray())
//        }
//        var files: Array<String> = this.fileList()
//        for (f in files){
//            Log.d("TAG", "createFolderInInternalStorage: $f")
//        }
//    }

//    private fun permission() {
//        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode ==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//           createFolderInInternalStorage()
//            Log.d("TAG", "createFolderInInternalStorage: dddddddd")
//        }
//    }

    private fun createJsonString() {
        val jsonConverter = JsonConverter()
        val list = ArrayList<JsonObject>()
        val list1 = listOf("answer1", "answer2", "answer3")
        val list2 = listOf("answer4", "answer5", "answer6")
        val list3 = listOf("answer7", "answer8", "answer9")
        val jsonObject1 = JsonObject("Q1", "question1", "ans1", list1, 3)
        val jsonObject2 = JsonObject("Q2", "question2", "ans2", list2, 3)
        val jsonObject3 = JsonObject("Q3", "question3", "ans3", list1, 3)
        list.add(jsonObject1)
        list.add(jsonObject2)
        list.add(jsonObject3)
        val jsonArray = JsonArray(list, 1)
        val s = jsonConverter.fromJson(jsonArray)
        Log.d("TAG", "\nonCreatezzzzzzzzzz:\n $s")
    }
}