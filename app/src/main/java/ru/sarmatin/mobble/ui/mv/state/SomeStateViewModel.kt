package ru.sarmatin.mobble.ui.mv.state

import androidx.lifecycle.SavedStateHandle
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel

/**
 * Created by antonsarmatin
 * Date: 2020-04-17
 * Project: Mobble
 */
class SomeStateViewModel(handle: SavedStateHandle): MobbleStateViewModel<SomeStateViewModel.ViewState>(handle){



    class ViewState : MobbleState()

    class NotState()

}