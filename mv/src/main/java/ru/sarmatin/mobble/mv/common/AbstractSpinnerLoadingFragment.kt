package ru.sarmatin.mobble.mv.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment


/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
abstract class AbstractSpinnerLoadingFragment(@LayoutRes private val layoutId: Int) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false).apply {
            setStyle(
                STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen
            )
        }

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        return view
    }

    companion object {

        const val TAG_LOADING_FRAGMENT = "customProgressTag"

    }

}