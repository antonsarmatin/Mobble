package ru.sarmatin.mobble.ui.consumable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_consumable_child.view.*
import ru.sarmatin.mobble.R

/**
 * Created by antonsarmatin
 * Date: 2020-02-20
 * Project: Mobble
 */
class ConsumableChildFragment : Fragment() {

    private val sharedViewModel: ConsumableSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumable_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.button.setOnClickListener {
            val textData = view.editText.text.toString()
            sharedViewModel.setData(textData)
            findNavController().popBackStack()
        }

    }

}