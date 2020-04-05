package com.wjx.android.wanandroidmvvm.ui.square.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.https.EmptyResponse
import com.wjx.android.wanandroidmvvm.ui.square.data.SquareResponse
import com.wjx.android.wanandroidmvvm.ui.square.repository.SquareRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:07
 */
class SquareViewModel(application: Application) :
    BaseArticleViewModel<SquareRepository>(application) {
    var mSquareData: MutableLiveData<BaseResponse<SquareResponse>> = MutableLiveData()
    var mAddShareData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadSquareArticle(pageNum: Int) {
        mRepository.loadSquareArticle(pageNum, mSquareData)
    }

    fun addShareData(title: String, link: String) {
        mRepository.addShareArticle(title, link, mAddShareData)
    }
}