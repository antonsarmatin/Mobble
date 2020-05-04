package ru.sarmatin.mobble

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

/**
 * Created by antonsarmatin
 * Date: 2020-05-03
 * Project: Mobble
 */

fun Fragment.alert(msg: String?) {
    try {
        val dialog = AlertDialog.Builder(context!!)
            .setMessage(msg)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    } catch (e: Exception) {

    }

}