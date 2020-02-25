package ru.sarmatin.mobble.mv.common

import ru.sarmatin.mobble.mv.R

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
//TODO Update Layout
class DefaultSpinnerLoadingFragment :
    AbstractSpinnerLoadingFragment(R.layout.fragment_spinner_loading_default) {

    companion object {

        fun newInstance() = DefaultSpinnerLoadingFragment()

    }

}