package com.nima.practice
//
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.Toast
//import androidx.core.view.iterator
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.example.template.other.JsonArray
//import com.nima.practice.JsonConverter
//import com.nima.practice.JsonObject
//import kotlinx.android.synthetic.main.fragment_user_questioner.*
//
//
//const val SHARED_PREFERENCES_NAME = "My_Pref"
//const val QUESTIONNAIR: String = "questionnaire"
//
//class UserQuestionerFragment : Fragment(R.layout.fragment_user_questioner) {
//
////    @Inject
////    lateinit var pref: SharedPreferences
//
//    private val questionCount = 4
//
//    private val answersOneCount = 7
//    private val answersThreeCount = 15
//    private val answersFourCount = 6
//
//    private var answerOneSize = 0
//    private var answerTwoSize = 1
//    private var answerThreeSize = 0
//    private var answerFourSize = 0
//
//    private val questions = Array(questionCount) { "" }
//
//    private val keys = arrayOf("Q1", "Q2", "Q3", "Q4")
//
//    private var answersOne = Array(answersOneCount) { "" }
//    private var answersTwo = ""
//    private var answersThree = Array(answersThreeCount) { "" }
//    private var answersFour = Array(answersFourCount) { "" }
//
//
//    private lateinit var jsonConverter: JsonConverter
//    private lateinit var jsonArray: JsonArray
//    private lateinit var jsonObjectOne: JsonObject
//    private lateinit var jsonObjectTwo: JsonObject
//    private lateinit var jsonObjectThree: JsonObject
//    private lateinit var jsonObjectFour: JsonObject
//
//    private lateinit var jsonObjects: ArrayList<JsonObject>
//
//    private var isComplaining = false
//    private var hasDisease = false
//    private var checkState = false
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        freeUpValues()
//        clickListeners()
//        subscribeOnCheckBoxes()
//        subscribeOnRadioButtons()
//    }
//
//    private fun freeUpValues() {
//        answerOneSize = 0
//        answerTwoSize = 1
//        answerThreeSize = 0
//        answerFourSize = 0
//        answersOne = Array(answersOneCount) { "" }
//        answersTwo = ""
//        answersThree = Array(answersThreeCount) { "" }
//        answersFour = Array(answersFourCount) { "" }
//    }
//
//    private fun subscribeOnCheckBoxes() {
//        questions[0] = txt_question02.text.toString()
//        //checkBox 1
//        checkbox1_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerOneSize++
//                answersOne[0] = txt1_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[0] = ""
//            }
//        }
//        //checkBox 2
//        checkbox2_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerOneSize++
//                answersOne[1] = txt2_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[1] = ""
//            }
//        }
//        //checkBox 3
//        checkbox3_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                hasDisease = true
//                answerOneSize++
//                answersOne[2] = txt3_q2.text.toString()
//            } else {
//                showDiseases(false)
//                answerOneSize--
//                answersOne[2] = ""
//                hasDisease = false
//            }
//            showDiseases(isChecked)
//        }
//        //checkBox 4
//        checkbox4_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerOneSize++
//                answersOne[3] = txt4_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[3] = ""
//            }
//        }
//        //checkBox 5
//        checkbox5_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                isComplaining = true
//                answerOneSize++
//                answersOne[4] = txt5_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[4] = ""
//                isComplaining = false
//            }
//            showComplaining(isChecked)
//        }
//        //checkBox 6
//        checkbox6_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerOneSize++
//                answersOne[5] = txt6_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[5] = ""
//            }
//        }
//        //checkBox 7
//        checkbox7_q2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerOneSize++
//                answersOne[6] = txt7_q2.text.toString()
//            } else {
//                answerOneSize--
//                answersOne[6] = ""
//            }
//        }
//    }
//
//    private fun subscribeOnRadioButtons() {
//        questions[1] = txt_question03.text.toString()
//        emergency_radio?.setOnCheckedChangeListener { _, checkedId ->
//            answerTwoSize = 1
//            answersTwo = if (checkedId == R.id.not_emergency) {
//                txt_not_emergency.text.toString()
//            } else {
//                txt_emergency.text.toString()
//            }
//        }
//    }
//
//    private fun subscribeOnDiseaseCheckBoxes() {
//        questions[2] = txt_question04.text.toString()
//        //checkBox 1
//        checkbox1_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[0] = txt1_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[0] = ""
//            }
//        }
//        //checkBox 2
//        checkbox2_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[1] = txt2_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[1] = ""
//            }
//        }
//        //checkBox 3
//        checkbox3_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[2] = txt3_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[2] = ""
//            }
//        }
//        //checkBox 4
//        checkbox4_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[3] = txt4_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[3] = ""
//            }
//        }
//        //checkBox 5
//        checkbox5_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[4] = txt5_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[4] = ""
//            }
//        }
//        //checkBox 6
//        checkbox6_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[5] = txt6_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[5] = ""
//            }
//        }
//        //checkBox 7
//        checkbox7_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[6] = txt7_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[6] = ""
//            }
//        }
//        //checkBox 8
//        checkbox8_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[7] = txt8_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[7] = ""
//            }
//        }
//        //checkBox 9
//        checkbox9_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[8] = txt9_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[8] = ""
//            }
//        }
//        //checkBox 10
//        checkbox10_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[9] = txt10_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[9] = ""
//            }
//        }
//        //checkBox 11
//        checkbox11_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[10] = txt11_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[10] = ""
//            }
//        }
//        //checkBox 12
//        checkbox12_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[11] = txt12_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[11] = ""
//            }
//        }
//        //checkBox 13
//        checkbox13_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[12] = txt13_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[12] = ""
//            }
//        }
//        //checkBox 14
//        checkbox14_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[13] = txt14_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[13] = ""
//            }
//        }
//        //checkBox 15
//        checkbox15_q3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerThreeSize++
//                answersThree[14] = txt15_q3.text.toString()
//            } else {
//                answerThreeSize--
//                answersThree[14] = ""
//            }
//        }
//    }
//
//    private fun subscribeOnComplaining() {
//        questions[3] = txt_question05.text.toString()
//        //checkBox 1
//        checkbox1_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[0] = txt1_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersThree[0] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//        //checkBox 2
//        checkbox2_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[1] = txt2_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersThree[1] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//        //checkBox 3
//        checkbox3_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[2] = txt3_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersThree[2] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//        //checkBox 4
//        checkbox4_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[3] = txt4_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersFour[3] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//        //checkBox 5
//        checkbox5_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[4] = txt5_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersFour[4] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//        //checkBox 6
//        checkbox6_q4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                answerFourSize++
//                answersFour[5] = txt6_q4.text.toString()
//            } else {
//                answerFourSize--
//                answersFour[5] = ""
//            }
//            Log.d("TAG", "subscribeOnComplaining: $answerFourSize")
//        }
//    }
//
//
//    private fun showComplaining(checked: Boolean) {
//        if (checked) {
//            Complaining_layout.visibility = View.VISIBLE
//            subscribeOnComplaining()
//        } else {
//            removeCheckBoxState(Complaining_layout)
//            Complaining_layout.visibility = View.GONE
//        }
//    }
//
//    private fun removeCheckBoxState(viewGroup: ViewGroup) {
//        for (v in viewGroup.iterator()) {
//            if (v is CheckBox) {
//                v.isChecked = false
//            }
//        }
//    }
//
//
//    private fun showDiseases(checked: Boolean) {
//        if (checked) {
//            disease_signs_layout.visibility = View.VISIBLE
//            subscribeOnDiseaseCheckBoxes()
//        } else {
//            removeCheckBoxState(disease_signs_layout)
//            disease_signs_layout.visibility = View.GONE
//        }
//    }
//
//
//    private fun clickListeners() {
//        txt_fragmentUserQuestioner_nextPage.setOnClickListener {
//            checkSubmitBtn()
//        }
//        txt_fragmentUserQuestioner_previousPage.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        btn_fragmentUserQuestioner_submit.setOnClickListener {
//            checkSubmitBtn()
//        }
//    }
//
//    private fun checkSubmitBtn() {
//        if (minNumberAnswers()) {
//            putIntoSharedPref()
//            findNavController().navigate(R.id.action_userQuestionnaireFragment_to_userDescriptionFragment)
//        } else {
//            Toast.makeText(
//                requireContext(),
//                "برای ارسال درخواست به مشاور باید تمامی سوالات پاسخ داده شود",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
////    private fun putIntoSharedPref() {
////        jsonConverter = JsonConverter()
////        jsonObjects = ArrayList()
////        jsonObjectOne =
////            JsonObject(keys[0], questions[0], free(answersOne.toList()), answersOne.size)
////        jsonObjects.add(jsonObjectOne)
////        jsonObjectTwo = JsonObject(keys[1], questions[1], listOf(answersTwo), 1)
////        jsonObjects.add(jsonObjectTwo)
////        if (hasDisease) {
////            jsonObjectThree =
////                JsonObject(keys[2], questions[2], free(answersThree.toList()), answersThree.size)
////            jsonObjects.add(jsonObjectThree)
////        }
////        if (isComplaining) {
////            jsonObjectFour =
////                JsonObject(keys[3], questions[3], free(answersFour.toList()), answersFour.size)
////            jsonObjects.add(jsonObjectFour)
////        }
////        jsonArray = JsonArray(jsonObjects, jsonObjects.size)
////
////        val json = jsonConverter.fromJson(jsonArray)
////        pref.edit().putString(QUESTIONNAIR, json).apply()
////    }
//
//    private fun free(toList: List<String>): List<String> {
//        return toList.filter {
//            it.isNotEmpty()
//        }
//    }
//
//    private fun minNumberAnswers(): Boolean {
//        return if (answerOneSize > 0 && answerTwoSize > 0) {
//            if (isComplaining) {
//                return if (hasDisease) {
//                    if (answerFourSize <= 0) {
//                        false
//                    } else {
//                        answerThreeSize > 0
//                    }
//                } else {
//                    answerFourSize > 0
//                }
//            }
//            if (hasDisease) {
//                return answerThreeSize > 0
//            }
//            if (!hasDisease && !isComplaining) {
//                checkState = true
//            }
//            checkState
//        } else false
//    }
//}