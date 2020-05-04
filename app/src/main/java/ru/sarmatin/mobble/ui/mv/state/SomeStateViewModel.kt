package ru.sarmatin.mobble.ui.mv.state

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel

/**
 * Created by antonsarmatin
 * Date: 2020-04-17
 * Project: Mobble
 */
class SomeStateViewModel(private val handle: SavedStateHandle) :
    MobbleStateViewModel<SomeStateViewModel.ViewState>(handle) {

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

    override val defaultState: ViewState
        get() = ViewState(ViewState.ColorType.COLOR_RED)

    data class ViewState(
        val color: ColorType
    ) : MobbleState() {

        enum class ColorType {
            COLOR_RED,
            COLOR_GREEN,
            COLOR_BLUE
        }

    }


    fun setColorType(type: ViewState.ColorType) {
        updateState(_viewState.value?.copy(color = type))
    }


}