package ru.sarmatin.mobble.mv.common.loading.dialog

import ru.sarmatin.mobble.mv.R

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
//TODO Update Layout
class DefaultSpinnerLoadingDialog :
    FullscreenDialog(R.layout.fragment_spinner_loading_default) {

    companion object {

        fun newInstance() =
            DefaultSpinnerLoadingDialog()

    }

}