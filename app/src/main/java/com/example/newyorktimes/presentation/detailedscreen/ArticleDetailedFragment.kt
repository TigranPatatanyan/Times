package com.example.newyorktimes.presentation.detailedscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newyorktimes.R
import com.example.newyorktimes.core.presenter.BaseFragment
import com.example.newyorktimes.databinding.FragmentArticleDetailedBinding
import com.example.newyorktimes.domain.entity.Article
import com.example.newyorktimes.util.Size
import com.example.newyorktimes.util.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailedFragment : BaseFragment() {

    override val viewModel: ArticleDetailedViewModel by viewModel()
    private var binding: FragmentArticleDetailedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =
            FragmentArticleDetailedBinding.bind(view.findViewById(R.id.articleDetailedFragment))
        if (savedInstanceState == null) {
            viewModel.articleData = arguments?.get(DETAILED_FRAGMENT_ARGS) as? Article
        }
        initView()
        initListeners()
    }

    private fun initListeners() {
        binding?.btnBack?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun initView() {
        binding?.run {
            contentLabel.text = viewModel.articleData?.title
            contentImage.loadImage(viewModel.articleData, Size.LARGE)
            contentText.text = viewModel.articleData?.text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(data: Article): ArticleDetailedFragment {
            val args = Bundle()
            args.putSerializable(DETAILED_FRAGMENT_ARGS, data)
            val fragment = ArticleDetailedFragment()
            fragment.arguments = args
            return fragment
        }

        const val DETAILED_FRAGMENT_ARGS = "d_f_args"
    }
}
