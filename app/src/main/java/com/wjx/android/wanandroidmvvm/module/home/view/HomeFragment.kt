package com.wjx.android.wanandroidmvvm.module.home.view

import android.view.View
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.CommonUtil
import com.wjx.android.wanandroidmvvm.module.common.view.ArticleListFragment
import com.wjx.android.wanandroidmvvm.module.common.model.Article
import com.wjx.android.wanandroidmvvm.common.utils.GlideImageLoader
import com.wjx.android.wanandroidmvvm.module.home.model.BannerResponse
import com.wjx.android.wanandroidmvvm.module.home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_home_headview.view.*
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class HomeFragment : ArticleListFragment<HomeViewModel>() {

    private lateinit var mBanner: Banner

    private val urls by lazy {
        arrayListOf<String>()
    }

    private val titles by lazy {
        arrayListOf<String>()
    }

    private var mCurrentPage = 0

    private var mTopArticlesLoadTimes: Int = 0

    companion object {
        fun getInstance(): HomeFragment? {
            return HomeFragment()
        }
    }

    override fun initView() {
        super.initView()
        val headView = View.inflate(activity, R.layout.layout_home_headview, null)
        mBanner = headView.mBanner
        mBanner.apply {
            setOnBannerListener { position ->
                CommonUtil.startWebView(requireContext(), urls[position], titles[position])
            }
            setImageLoader(GlideImageLoader())
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setDelayTime(5000)
            setBannerAnimation(Transformer.DepthPage)
        }
        mAdapter.addHeaderView(headView)
    }

    override fun initData() {
        mCurrentPage = 0
        mTopArticlesLoadTimes = 0
        mViewModel.loadBannerCo()
        mViewModel.loadHomeArticleDataCo(mCurrentPage)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mBannerData.observe(this, Observer { response ->
            response?.let {
                setBannerData(it)
            }
        })

        mViewModel.mHomeArticleData.observe(this, Observer { responseArticle ->
            responseArticle?.let {
                mViewModel.mTopArticleData.observe(this, Observer { responseTop ->
                    responseTop?.let {
                        // 仅让置顶文章add一次，避免不必要的开销
                        if (mCurrentPage == 0 && mTopArticlesLoadTimes == 0) {
                            handleTopArticle(it)
                            addData(responseTop + responseArticle.datas)
                            mTopArticlesLoadTimes++
                        }
                    }
                })
                if (mCurrentPage != 0) {
                    // 非第0页直接加载首页数据
                    addData(responseArticle.datas)
                }
            }
        })
    }

    private fun handleTopArticle(articleList: List<Article>) {
        for (article in articleList) {
            article.top = true
        }
    }

    private fun setBannerData(bannerList: List<BannerResponse>) {
        val images = ArrayList<String>()
        urls.clear()
        titles.clear()
        for (bannerItem in bannerList) {
            images.add(bannerItem.imagePath)
            titles.add(bannerItem.title)
            urls.add(bannerItem.url)
        }
        mBanner.setImages(images)
        mBanner.setBannerTitles(titles)
        mBanner.start()
    }

    override fun onRefreshData() {
        mCurrentPage = 0
        mTopArticlesLoadTimes = 0
        mViewModel.loadBannerCo()
        mViewModel.loadHomeArticleDataCo(mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadHomeArticleDataCo(++mCurrentPage)
    }
}