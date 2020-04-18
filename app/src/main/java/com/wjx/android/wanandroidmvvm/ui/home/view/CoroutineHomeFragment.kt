package com.wjx.android.wanandroidmvvm.ui.home.view

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.view.BaseCoroutineLifeCycleFragment
import com.wjx.android.wanandroidmvvm.base.view.BaseFragment
import com.wjx.android.wanandroidmvvm.base.view.BaseLifeCycleFragment
import com.wjx.android.wanandroidmvvm.common.utils.GlideImageLoader
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import com.wjx.android.wanandroidmvvm.ui.common.data.Article
import com.wjx.android.wanandroidmvvm.ui.common.view.ArticleListFragment
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.viewmodel.CoroutinHomeViewModel
import com.wjx.android.wanandroidmvvm.ui.home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_home_headview.view.*
import java.util.ArrayList
import kotlin.math.log

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/18 12:18
 */
class CoroutineHomeFragment : BaseCoroutineLifeCycleFragment<CoroutinHomeViewModel>() {

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
        fun getInstance(): CoroutineHomeFragment? {
            return CoroutineHomeFragment()
        }
    }

    override fun initView() {
        super.initView()
        val headView = View.inflate(activity, R.layout.layout_home_headview, null)
        mBanner = headView.mBanner
        mBanner.setOnBannerListener { position ->
            val intent: Intent = Intent(activity, ArticleDetailActivity::class.java)
            intent.putExtra("url", urls[position])
            intent.putExtra("title", titles[position])
            startActivity(intent)
        }
        mBanner.setImageLoader(GlideImageLoader())
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        mBanner.setDelayTime(2000)
        mBanner.setBannerAnimation(Transformer.DepthPage)
    }

    override fun initData() {
        mCurrentPage = 0
        mTopArticlesLoadTimes = 0
        mViewModel.loadBannerData()
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initDataObserver() {
        mViewModel.mBannerData.observe(this, Observer { response ->
            response?.let {
                Log.e("WJXBANNER", it.data.toString())
            }
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
}