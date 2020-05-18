package ru.sarmatin.mobble.ui.mv.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.navigation.NavAction
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.mv.platform.state.FeatureState

/**
 * Created by antonsarmatin
 * Date: 2020-04-17
 * Project: Mobble
 */
class ColorStateViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<ColorStateViewModel.ViewState>(handle) {

    private val _text1 = handle.getLiveData<String>("text1")
    val text1: LiveData<String>
    get()  = _text1

    private val _text2 = handle.getLiveData<String>("text2")
    val text2: LiveData<String>
    get()  = _text2

    private val _text3 = handle.getLiveData<String>("text3")
    val text3: LiveData<String>
    get()  = _text3

    init {
        _text1.value = "RED"
        _text2.value = "GREEN!"
        _text3.value = "Blue..."
    }

    override val defaultFeatureState: ColorStateViewModel.ViewState
        get() = ViewState.Red

    sealed class ViewState(): FeatureState(){
        object Red: ViewState()
        object Green: ViewState()
        object Blue: ViewState()
        data class Navigate(val color: ViewState?) : ViewState(), NavAction
    }

    fun navigate(){

       handleNavigation(ViewState.Navigate(getFeatureState()))

    }

    fun setColorType(type: ViewState) {
        updateFeatureState(type)
    }


}