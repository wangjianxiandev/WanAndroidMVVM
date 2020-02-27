package com.wjx.android.wanandroidmvvm.ui.wechat.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatArticleResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.repository.WeChatRepository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:39
 */
class WeChatViewModel(application: Application) :
        BaseArticleViewModel<WeChatRepository>(application) {
        val mWChatTabData : MutableLiveData<BaseResponse<List<WeChatTabNameResponse>>> = MutableLiveData()
        var mWeChatArticleData : MutableLiveData<BaseResponse<WeChatArticleResponse>> = MutableLiveData()

        fun loadWeChatTabName() {
                mRepository.loadWeChatTabName(mWChatTabData)
        }

        fun loadWeChatArticle(cid : Int, pageNum : Int) {
                mRepository.loadWeChatArticle(cid, pageNum, mWeChatArticleData)
        }
}