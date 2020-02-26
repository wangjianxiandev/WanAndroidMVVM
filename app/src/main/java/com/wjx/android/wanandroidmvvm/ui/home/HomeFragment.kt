package com.wjx.android.wanandroidmvvm.ui.home

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListFragment
import com.wjx.android.wanandroidmvvm.base.utils.GlideImageLoader
import com.wjx.android.wanandroidmvvm.ui.home.data.bean.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.home_headview.view.*
import java.util.ArrayList

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class HomeFragment : BaseArticleListFragment<HomeViewModel>(){

    private lateinit var mBanner : Banner

    private val urls by lazy {
        arrayListOf<String>()
    }

    private val titles by lazy {
        arrayListOf<String>()
    }

    private var page = 0

    override fun initView() {
        super.initView()
        val headView = View.inflate(activity, R.layout.home_headview, null)
        mBanner = headView.mBanner
        mBanner.setOnBannerListener { position ->
           Toast.makeText(activity, urls[position] + "/" + titles[position], Toast.LENGTH_SHORT)
        }
        mAdapter.addHeaderView(headView)
        mBanner.setImageLoader(GlideImageLoader())
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        mBanner.setDelayTime(2000)
        mBanner.setBannerAnimation(Transformer.DepthPage)
    }

    override fun initData() {
        page = 0

        mViewModel.loadBanner()

        mViewModel.loadHomeArticleData(page)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mBannerData.observe(this, Observer { response ->
            response?.let {
                setBannerData(it.data) }
        })
        mViewModel.mHomeArticleData.observe(this, Observer { response ->
            response?.let { addData(it.data.datas) }
        })
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
        page = 0
        mViewModel.loadHomeArticleData(page)
        mViewModel.loadBanner()
    }

    override fun onLoadMoreData() {
        mViewModel.loadHomeArticleData(++page)
    }
}