package ru.sarmatin.mobble.ui.mv.state

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_some_state.*
import kotlinx.android.synthetic.main.fragment_some_state.view.*
import ru.sarmatin.mobble.R

/**
 * Created by antonsarmatin
 * Date: 2020-05-01
 * Project: Mobble
 */
typealias ColorType = SomeStateViewModel.ViewState.ColorType

class SomeStateFragment : BaseFragment<SomeStateViewModel.ViewState>(){

    override val viewModel: SomeStateViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_some_state

    private lateinit var text: TextView

    private val textObserver = Observer<String> {
        text.text = it
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text = view.text

        btnColor1.setOnClickListener {
            viewModel.setColorType(ColorType.COLOR_RED)
        }
        btnColor2.setOnClickListener {
            viewModel.setColorType(ColorType.COLOR_GREEN)
        }
        btnColor3.setOnClickListener {
            viewModel.setColorType(ColorType.COLOR_BLUE)
        }
    }

    override fun handleFeatureState(featureState: SomeStateViewModel.ViewState?) {

        viewModel.text1.removeObserver(textObserver)
        viewModel.text2.removeObserver(textObserver)
        viewModel.text3.removeObserver(textObserver)
        when(featureState){
            SomeStateViewModel.ViewState.Red -> {
                setCardColor(R.color.card1)
                viewModel.text1.observe(viewLifecycleOwner, textObserver)
            }
            ColorType.COLOR_GREEN -> {
                setCardColor(R.color.card2)
                viewModel.text2.observe(viewLifecycleOwner, textObserver)
            }
            ColorType.COLOR_BLUE -> {
                setCardColor(R.color.card3)
                viewModel.text3.observe(viewLifecycleOwner, textObserver)
            }
        }
    }


    private fun setCardColor(@ColorRes color: Int){
        cardText.setCardBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }
}