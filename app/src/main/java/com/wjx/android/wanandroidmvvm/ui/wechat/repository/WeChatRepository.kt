package com.wjx.android.wanandroidmvvm.ui.wechat.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatArticleResponse
import com.wjx.android.wanandroidmvvm.ui.wechat.data.WeChatTabNameResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:40
 */
class WeChatRepository (loadState : MutableLiveData<State>) : BaseArticleRepository(loadState) {
    fun loadWeChatTabName(liveData: MutableLiveData<BaseResponse<List<WeChatTabNameResponse>>>) {
        apiService.loadWeChatName()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }

    fun loadWeChatArticle(cid : Int, pageNum : Int, liveData: MutableLiveData<BaseResponse<WeChatArticleResponse>>) {
        apiService.loadWeChatArticle(cid, pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }
}