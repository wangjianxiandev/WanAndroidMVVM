package com.wjx.android.wanandroidmvvm.module.square.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.module.square.data.SquareResponse
import com.wjx.android.wanandroidmvvm.module.square.repository.SquareRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:07
 */
class SquareViewModel(application: Application) :
    ArticleViewModel<SquareRepository>(application) {
    var mSquareData: MutableLiveData<BaseResponse<SquareResponse>> = MutableLiveData()
    var mAddShareData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadSquareArticle(pageNum: Int) {
        mRepository.loadSquareArticle(pageNum, mSquareData)
    }

    fun addShareData(title: String, link: String) {
        mRepository.addShareArticle(title, link, mAddShareData)
    }
}