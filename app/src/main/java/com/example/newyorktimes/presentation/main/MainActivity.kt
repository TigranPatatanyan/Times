package com.example.newyorktimes.presentation.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.newyorktimes.R
import com.example.newyorktimes.presentation.detailedscreen.ArticleDetailedFragment
import com.example.newyorktimes.presentation.listscreen.ArticlesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : FragmentActivity() {

    private val viewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val mFragmentTransaction = supportFragmentManager.beginTransaction()
            val mFragment = ArticlesFragment()
            mFragmentTransaction.add(R.id.fragment_container_view, mFragment)
                .commit()
        }

        lifecycleScope.launchWhenResumed {
            viewModel.onItemClickStateFlow.collect {
                it?.let {
                    val fragment = ArticleDetailedFragment.newInstance(it)
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container_view, fragment)
                        .addToBackStack(DETAILED_FRAGMENT_TAG)
                        .commit()
                }
            }
        }
    }


    companion object {
        const val ARTICLES_FRAGMENT_TAG = "1234"
        const val DETAILED_FRAGMENT_TAG = "4321"
    }
}