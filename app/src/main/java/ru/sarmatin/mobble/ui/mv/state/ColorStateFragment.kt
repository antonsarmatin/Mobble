package ru.sarmatin.mobble.ui.mv.state

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_color.*
import kotlinx.android.synthetic.main.fragment_color.view.*
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.navigation.NavAction

/**
 * Created by antonsarmatin
 * Date: 2020-05-01
 * Project: Mobble
 */

class ColorStateFragment : BaseFragment<ColorStateViewModel.ViewState>() {

    override val viewModel: ColorStateViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_color

    private lateinit var text: TextView

    private val textObserver = Observer<String> {
        text.text = it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text = view.text

        btnColor1.setOnClickListener {
            viewModel.setColorType(ColorStateViewModel.ViewState.Red)
        }
        btnColor2.setOnClickListener {
            viewModel.setColorType(ColorStateViewModel.ViewState.Green)
        }
        btnColor3.setOnClickListener {
            viewModel.setColorType(ColorStateViewModel.ViewState.Blue)
        }

        btnNavigateToDetailt.setOnClickListener {
            viewModel.navigate()
        }

    }

    override fun handleFeatureState(featureState: ColorStateViewModel.ViewState?) {

        viewModel.text1.removeObserver(textObserver)
        viewModel.text2.removeObserver(textObserver)
        viewModel.text3.removeObserver(textObserver)
        when (featureState) {
            ColorStateViewModel.ViewState.Red -> {
                setCardColor(R.color.card1)
                viewModel.text1.observe(viewLifecycleOwner, textObserver)
            }
            ColorStateViewModel.ViewState.Green -> {
                setCardColor(R.color.card2)
                viewModel.text2.observe(viewLifecycleOwner, textObserver)
            }
            ColorStateViewModel.ViewState.Blue -> {
                setCardColor(R.color.card3)
                viewModel.text3.observe(viewLifecycleOwner, textObserver)
            }
        }
    }

    override fun handleNavigationEvent(action: NavAction?) {
        when(action){
            is ColorStateViewModel.ViewState.Navigate -> findNavController().navigate(R.id.action_colorStateFragment_to_colorDetailFragment)
        }
    }


    private fun setCardColor(@ColorRes color: Int) {
        cardText.setCardBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }
}