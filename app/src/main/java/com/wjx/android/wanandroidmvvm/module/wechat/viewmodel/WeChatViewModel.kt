package com.wjx.android.wanandroidmvvm.module.wechat.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.module.wechat.model.WeChatArticleResponse
import com.wjx.android.wanandroidmvvm.module.wechat.model.WeChatTabNameResponse
import com.wjx.android.wanandroidmvvm.module.wechat.repository.WeChatRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:39
 */
class WeChatViewModel :
    ArticleViewModel<WeChatRepository>() {
    // RxJava2
//    val mWChatTabData: MutableLiveData<BaseResponse<List<WeChatTabNameResponse>>> =
//        MutableLiveData()
//    var mWeChatArticleData: MutableLiveData<BaseResponse<WeChatArticleResponse>> = MutableLiveData()
//
//    fun loadWeChatTabName() {
//        mRepository.loadWeChatTabName(mWChatTabData)
//    }
//
//    fun loadWeChatArticle(cid: Int, pageNum: Int) {
//        mRepository.loadWeChatArticle(cid, pageNum, mWeChatArticleData)
//    }
    val mWChatTabData: MutableLiveData<List<WeChatTabNameResponse>> =
        MutableLiveData()
    var mWeChatArticleData: MutableLiveData<WeChatArticleResponse> = MutableLiveData()
    fun loadWeChatTabName() {
        initiateRequest({ mWChatTabData.value = mRepository.loadWeChatTabNameCo() }, loadState)
    }

    fun loadWeChatArticle(cid: Int, pageNum: Int) {
        initiateRequest({
            mWeChatArticleData.value = mRepository.loadWeChatArticleCo(cid, pageNum)
        }, loadState)
    }
}