package com.wjx.android.wanandroidmvvm.ui.wechat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.wjx.android.wanandroidmvvm.ui.common.view.ArticleListFragment
import com.wjx.android.wanandroidmvvm.ui.wechat.viewmodel.WeChatViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 15:33
 */
class WeChatArticleFragment : ArticleListFragment<WeChatViewModel>() {
    private var mCurrentPage = 1
    private val mCid: Int by lazy { arguments?.getInt("id") ?: -1 }

    companion object {
        fun newInstance(id : Int) : Fragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val fragment = WeChatArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.loadWeChatArticle(mCid, mCurrentPage)
    }

    override fun onRefreshData() {
        mCurrentPage = 0
        mViewModel.loadWeChatArticle(mCid, mCurrentPage)
    }

    override fun onLoadMoreData() {
        mViewModel.loadWeChatArticle(mCid, ++mCurrentPage)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mWeChatArticleData.observe(this, Observer { response ->
            response?.let {
                addData(it.datas)
            }
        })
    }
}