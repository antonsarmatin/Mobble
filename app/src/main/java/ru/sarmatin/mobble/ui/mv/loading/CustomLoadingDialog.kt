package ru.sarmatin.mobble.ui.mv.loading

import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.common.loading.dialog.FullscreenDialog

/**
 * Created by antonsarmatin
 * Date: 2020-04-06
 * Project: Mobble
 */
class CustomLoadingDialog : FullscreenDialog(R.layout.dialog_custom_loading) {

    companion object {

        fun newInstance() = CustomLoadingDialog()

    }


}