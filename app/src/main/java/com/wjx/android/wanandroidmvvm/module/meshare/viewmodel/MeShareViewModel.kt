package com.wjx.android.wanandroidmvvm.module.meshare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.network.response.EmptyResponse
import com.wjx.android.wanandroidmvvm.module.meshare.model.MeShareArticleResponse
import com.wjx.android.wanandroidmvvm.module.meshare.model.MeShareResponse
import com.wjx.android.wanandroidmvvm.module.meshare.repository.MeShareRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 17:36
 */
class MeShareViewModel : ArticleViewModel<MeShareRepository>() {
    var mMeShareData: MutableLiveData<BaseResponse<MeShareResponse<MeShareArticleResponse>>> =
        MutableLiveData()
    var mDeleteMeShareData: MutableLiveData<BaseResponse<EmptyResponse>> = MutableLiveData()

    fun loadMeShareArticle(pageNum: Int) {
        mRepository.loadShareArticle(pageNum, mMeShareData)
    }

    fun deleteMeShareArticle(id: Int) {
        mRepository.deleteShareArticle(id, mDeleteMeShareData)
    }
}