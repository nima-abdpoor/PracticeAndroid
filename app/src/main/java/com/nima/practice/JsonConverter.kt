package com.nima.practice

import com.example.template.other.JsonArray

class JsonConverter {
    fun fromJson(jsonArray: JsonArray): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("[")
        for (jsonObject in jsonArray.jsonObjects) {
            stringBuilder.append("[{${jsonObject.questionKey}:\"${jsonObject.question}\"},{${jsonObject.answerKey}:\"")
            for (i in jsonObject.answers ){
                stringBuilder.append("$i&&")
            }
            stringBuilder.replace(stringBuilder.length - 2, stringBuilder.length, "\"},")
            stringBuilder.replace(stringBuilder.length - 1, stringBuilder.length, "],")
        }

        stringBuilder.replace(stringBuilder.length - 1, stringBuilder.length, "]")
        return stringBuilder.toString()
    }
}