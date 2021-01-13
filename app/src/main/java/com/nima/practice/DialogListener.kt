package com.nima.practice

import android.app.Dialog

interface DialogListener {
    fun OnAccept(dialog: Dialog?)
    fun OnDecline(dialog: Dialog?)
}
