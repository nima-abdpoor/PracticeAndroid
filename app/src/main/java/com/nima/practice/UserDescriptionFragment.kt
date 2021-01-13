package com.nima.practice

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_user_description.*


//class UserDescriptionFragment : Fragment(R.layout.fragment_user_description) {
//
//
//    private var description: String = ""
//    private val DESCRIPTION: String = "description"
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        subscribeOnDescriptionEditor()
//        subscribeOnSubmitButton()
//        subscribeOnOtherPages()
//    }
//
//    private fun subscribeOnOtherPages() {
//        txt_previous_page_description.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        txt_next_page_description.setOnClickListener {
//            subscribeOnSubmitButton()
//        }
//    }
//
//    private fun subscribeOnSubmitButton() {
//        btn_submit_description.setOnClickListener {
//            description = etDescription.text.toString().trim()
//            if (description.isNotEmpty()) {
//                handleAlert()
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "برای ارسال درخواست به مشاور باید تمامی سوالات پاسخ داده شود",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    private fun handleAlert() {
//        val prefs = requireContext()?.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
//        val loadedString = prefs.getString(QUESTIONNAIR, "null")
//        Log.d("TAG", "handleAlert: $loadedString")
////        createAlert(
////            requireContext(),
////            "are you sure?",
////            "ok",
////            "No",
////            3,
////            object : DialogListener {
////                override fun OnAccept(dialog: Dialog?) {
////                    //navigate
////                    TODO()
////                    pref.edit().putString(DESCRIPTION, description)
////                }
////
////                override fun OnDecline(dialog: Dialog?) {
////                    dialog?.cancel()
////                }
////            })
//    }
//
//    private fun subscribeOnDescriptionEditor() {
//
//        etDescription.setOnFocusChangeListener { _, _ ->
//            requireActivity().window
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        }
//
//        etDescription.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
//                Unit
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
//
//            override fun afterTextChanged(s: Editable?) {
//                textCounter.text =
//                    (500 - etDescription.text.toString().length).toString() + " " + "کاراکتر"
//            }
//        })
//    }
//
//}