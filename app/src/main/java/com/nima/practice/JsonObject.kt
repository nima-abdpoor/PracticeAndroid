package com.nima.practice

data class JsonObject(
    val questionKey: String,
    val question: String,
    val answerKey : String,
    val answers: List<String>,
    val count: Int
)