package ru.sarmatin.mobble.mv.common.loading

import java.io.Serializable

/**
 * Created by antonsarmatin
 * Date: 2020-04-04
 * Project: Mobble
 */

sealed class Loading {

    object NoLoading : Loading(), Serializable

    abstract class Fullscreen : Loading(), Serializable

}