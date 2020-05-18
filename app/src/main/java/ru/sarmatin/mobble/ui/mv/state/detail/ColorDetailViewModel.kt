package ru.sarmatin.mobble.ui.mv.state.detail

import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.mv.platform.state.FeatureState

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
class ColorDetailViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<ColorDetailViewModel.ViewState>(handle) {

    override val defaultFeatureState: ViewState
        get() = ViewState()

    data class ViewState(val color: String? = null) : FeatureState()

}