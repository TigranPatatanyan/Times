package com.example.newyorktimes.core.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

open class BaseFragment : Fragment() {
    open val viewModel: BaseViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel?.noInternetConnection?.collect {
                it?.let {
                    if (it) {
                        Toast.makeText(requireContext(), "no internet", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel?.somethingWentWrong?.collect {
                it?.let {
                    if (it) {
                        Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}