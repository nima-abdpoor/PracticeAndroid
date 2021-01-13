package com.example.template.other

import com.nima.practice.JsonObject


data class JsonArray(
    val jsonObjects: List<JsonObject>,
    val count: Int
)