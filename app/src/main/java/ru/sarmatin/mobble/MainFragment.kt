package ru.sarmatin.mobble

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * Created by antonsarmatin
 * Date: 2020-02-20
 * Project: Mobble
 */
class MainFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.btnConsumable.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_consumableParentFragment)
        }

        view.btnNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_navigationActivity)
        }

    }

}