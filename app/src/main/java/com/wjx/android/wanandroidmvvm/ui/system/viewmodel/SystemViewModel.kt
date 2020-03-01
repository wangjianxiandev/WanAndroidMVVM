package com.wjx.android.wanandroidmvvm.ui.system.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemArticleResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.system.repository.SystemRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:09
 */
class SystemViewModel (application: Application) : BaseArticleViewModel<SystemRepository>(application) {
    val mSystemTabNameData : MutableLiveData<BaseResponse<List<SystemTabNameResponse>>> = MutableLiveData()
    val mSystemArticleData : MutableLiveData<BaseResponse<SystemArticleResponse>> = MutableLiveData()

    fun loadSystemTab() {
        mRepository.loadSystemTab(mSystemTabNameData)
    }

    fun loadSystemArticle(pageNum : Int, cid : Int?) {
        mRepository.loadSystemArticle(pageNum, cid, mSystemArticleData)
    }
}