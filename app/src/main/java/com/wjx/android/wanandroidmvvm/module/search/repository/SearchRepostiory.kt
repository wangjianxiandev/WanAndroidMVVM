package com.wjx.android.wanandroidmvvm.module.search.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.common.utils.RoomHelper
import com.wjx.android.wanandroidmvvm.module.search.data.HotKeyResponse
import com.wjx.android.wanandroidmvvm.module.search.data.SearchResultResponse
import com.wjx.android.wanandroidmvvm.module.search.data.bean.SearchHistory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:14
 */
class SearchRepostiory(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadHotKey(liveData: MutableLiveData<BaseResponse<List<HotKeyResponse>>>) {
        apiService.loadHotKey()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    fun loadSearchResult(
        pageNum: Int,
        key: String,
        liveData: MutableLiveData<BaseResponse<SearchResultResponse>>
    ) {
        apiService.loadSearchResult(pageNum, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }

    suspend fun loadSearchHistory() = RoomHelper.queryAllSearchHistory()

    suspend fun insertSearchHistory(searchHistory: SearchHistory) =
        RoomHelper.insertSearchHistory(searchHistory)

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) =
        RoomHelper.deleteSearchHistory(searchHistory)

    suspend fun deleteAllSearchHistory() = RoomHelper.deleteAllSearchHistory()
}