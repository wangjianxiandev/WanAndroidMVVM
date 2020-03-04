package com.wjx.android.wanandroidmvvm.ui.home.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleListFragment
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.utils.GlideImageLoader
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
import com.wjx.android.wanandroidmvvm.ui.home.data.BannerResponse
import com.wjx.android.wanandroidmvvm.ui.home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_home_headview.view.*
import java.util.ArrayList

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 19:59
 */
class HomeFragment : BaseArticleListFragment<HomeViewModel>() {

    private lateinit var mBanner: Banner

    private val urls by lazy {
        arrayListOf<String>()
    }

    private val titles by lazy {
        arrayListOf<String>()
    }

    private var page = 0

    override fun initView() {
        super.initView()
        initStatusColor()
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
        mAdapter.addHeaderView(headView)
    }

    private fun initStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity!!.window.statusBarColor =
                ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) { // 设置状态栏中字体的颜色为黑色
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else { // 跟随系统
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initStatusColor()
    }

    override fun initData() {
        page = 0
        mViewModel.loadTopArticle()
        mViewModel.loadBanner()
        mViewModel.loadHomeArticleData(page)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mBannerData.observe(this, Observer { response ->
            response?.let {
                setBannerData(it.data)
            }
        })

        mViewModel.mTopArticleData.observe(this, Observer { response ->
            response?.let {
                handleTopArticle(it.data)
                addData(it.data)
            }
        })

        mViewModel.mHomeArticleData.observe(this, Observer { response ->
            response?.let {
                addData(it.data.datas)
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
        page = 0
        mViewModel.loadHomeArticleData(page)
        mViewModel.loadTopArticle()
        mViewModel.loadBanner()
    }

    override fun onLoadMoreData() {
        mViewModel.loadHomeArticleData(++page)
    }
}