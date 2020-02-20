package ru.sarmatin.mobble.ui.consumable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_consumable_parent.view.*
import ru.sarmatin.consumablelivedata.ConsumableObserver
import ru.sarmatin.mobble.R

/**
 * Created by antonsarmatin
 * Date: 2020-02-20
 * Project: Mobble
 */
class ConsumableParentFragment : Fragment() {

    private val sharedViewModel: ConsumableSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumable_parent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.button.setOnClickListener {
            findNavController().navigate(R.id.action_consumableParentFragment_to_consumableChildFragment)
        }

        sharedViewModel.data.observe(viewLifecycleOwner, ConsumableObserver {
            view.textView.text = it
        })

    }

}