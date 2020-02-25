package ru.sarmatin.mobble.ui.mv

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_some_screen.view.*
import ru.sarmatin.mobble.R
import ru.sarmatin.mobble.mv.platform.MobbleFragment
import ru.sarmatin.mobble.utils.failure.Failure

/**
 * Created by antonsarmatin
 * Date: 2020-02-25
 * Project: Mobble
 */
class SomeScreenFragment : MobbleFragment() {

    override fun layoutId() = R.layout.fragment_some_screen

    override val viewModel: SomeScreenViewModel by viewModels()

    override val failureObserver = Observer<Failure> {
        when (it) {
            Failure.ServerError -> {

                AlertDialog.Builder(context!!)
                    .setMessage(R.string.dialog_error_sample_message)
                    .setPositiveButton(android.R.string.ok) { dialog, which ->

                    }
                    .show()

            }
            //Remaining branches
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.btnProduceError.setOnClickListener {
            viewModel.produceError()
        }

        view.btnLoadData.setOnClickListener {
            viewModel.fetchData()
        }

        viewModel.text.observe(viewLifecycleOwner, Observer {
            view.textView.text = it
        })

    }
}