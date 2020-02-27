package com.wjx.android.wanandroidmvvm.ui.system.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemArticleResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemTabResponse
import com.wjx.android.wanandroidmvvm.ui.system.repository.SystemRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:09
 */
class SystemViewModel (application: Application) : BaseArticleViewModel<SystemRepository>(application) {
    val mSystemTabData : MutableLiveData<BaseResponse<List<SystemTabResponse>>> = MutableLiveData()
    val mSystemArticleData : MutableLiveData<BaseResponse<SystemArticleResponse>> = MutableLiveData()

    fun loadSystemTab() {
        mRepository.loadSystemTab(mSystemTabData)
    }

    fun loadSystemArticle(pageNun : Int, cid : Int) {
        mRepository.loadSystemArticle(pageNun, cid, mSystemArticleData)
    }
}