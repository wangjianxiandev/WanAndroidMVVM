package com.wjx.android.wanandroidmvvm.ui.wechat.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.ui.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatArticleResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.repository.WeChatRepository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:39
 */
class WeChatViewModel(application: Application) :
    ArticleViewModel<WeChatRepository>(application) {
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
        viewModelScope.launch {
            try {
                mWChatTabData.value = mRepository.loadWeChatTabNameCo()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadWeChatArticle(cid: Int, pageNum: Int) {
        viewModelScope.launch {
            try {
                mWeChatArticleData.value = mRepository.loadWeChatArticleCo(cid, pageNum)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}