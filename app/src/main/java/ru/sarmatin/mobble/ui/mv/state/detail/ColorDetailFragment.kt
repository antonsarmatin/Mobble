package ru.sarmatin.mobble.ui.mv.state.detail

import androidx.fragment.app.viewModels
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.platform.MobbleStateViewModel
import ru.sarmatin.mobble.ui.mv.state.BaseFragment

/**
 * Created by antonsarmatin
 * Date: 17/05/2020
 * Project: Mobble
 */
class ColorDetailFragment : BaseFragment<ColorDetailViewModel.ViewState>(){

    override val viewModel: ColorDetailViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_color_detailed

    override fun handleFeatureState(featureState: ColorDetailViewModel.ViewState?) {

    }
}