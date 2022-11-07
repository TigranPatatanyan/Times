package com.example.newyorktimes.presentation.listscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newyorktimes.R
import com.example.newyorktimes.core.presenter.BaseFragment
import com.example.newyorktimes.presentation.main.SharedViewModel
import com.example.newyorktimes.data.Period
import com.example.newyorktimes.databinding.FragmentArticlesBinding
import com.example.newyorktimes.domain.entity.Article
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticlesFragment : BaseFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModel()
    override val viewModel: ArticlesViewModel by viewModel()
    private var binding: FragmentArticlesBinding? = null
    private var rv: RecyclerView? = null
    private var articlesAdapter: ArticlesViewAdapter? = null
    private var selected: Period? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticlesBinding.bind(view.findViewById(R.id.articlesFragment))
        initViews()
        initObservers()
        initTool(savedInstanceState)
    }

    private fun initTool(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            selected = Period.DAY
            binding?.dayTV?.run {
                select(this)
                viewModel.tryToDownloadArticleList(period = selected!!)
            }

        } else {
            viewModel.onRestoreState(savedInstanceState)
            select(savedInstanceState.getInt(PERIOD_KEY, 1), false)
        }
    }

    private fun initViews() {
        binding?.let {
            rv = it.listView
            articlesAdapter =
                ArticlesViewAdapter().apply {
                    onItemSelected = { item ->
                        sharedViewModel.performItemClick(item)
                    }
                }
            rv?.run {
                adapter = articlesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
        initPeriodSelectionView()
    }

    @SuppressLint("ResourceAsColor")
    private fun initPeriodSelectionView() {
        binding?.run {
            dayTV.setOnClickListener {
                select(1)
            }
            weekTV.setOnClickListener {
                select(7)
            }
            monthTV.setOnClickListener {
                select(30)
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.articlesStateFlow.collect {
                it?.takeIf { it.isNotEmpty() }?.let {
                    updateView(it)
                }
            }
        }
    }

    private fun select(period: Int, fromUser: Boolean = true) {
        binding?.run {
            when (period) {
                Period.DAY.period -> {
                    select(dayTV)
                    selected = Period.DAY
                }
                Period.WEEK.period -> {
                    select(weekTV)
                    selected = Period.WEEK
                }
                Period.MONTH.period -> {
                    select(monthTV)
                    selected = Period.MONTH
                }
            }
            selected?.takeIf { fromUser }?.let { viewModel.tryToDownloadArticleList(it) }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun select(selected: View) {
        binding?.run {
            dayTV.background = null
            weekTV.background = null
            monthTV.background = null
        }
        selected.background = resources.getDrawable(R.color.white, null)
    }

    private fun updateView(articles: List<Article>?) {
        articles?.let { articlesAdapter?.setData(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.onSaveState(outState)
        selected?.let { outState.putInt(PERIOD_KEY, it.period) }
    }

    companion object {
        const val PERIOD_KEY = "periodKey"
    }
}