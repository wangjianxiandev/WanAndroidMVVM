package com.wjx.android.wanandroidmvvm.ui.search.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.repository.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.search.data.HotKeyResponse
import com.wjx.android.wanandroidmvvm.ui.search.data.SearchResultResponse
import com.wjx.android.wanandroidmvvm.ui.search.data.dao.SearchHistoryDao
import com.wjx.android.wanandroidmvvm.ui.search.data.db.SearchHistory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:14
 */
class SearchRepostiory (loadState : MutableLiveData<State>) : BaseArticleRepository(loadState) {
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

    fun loadSearchResult(pageNum : Int, key : String, liveData: MutableLiveData<BaseResponse<SearchResultResponse>>) {
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

    fun loadSearchHistory() : List<SearchHistory> {
        return SearchHistoryDao.loadSearchHistory()
    }

    fun clearSearchHistory() : Int {
        return SearchHistoryDao.clearSearchHistory()
    }

    fun addSearchHistory(name : String) : Boolean {
        return SearchHistoryDao.addSearchHistory(name)
    }

    fun deleteSearchHistory(name : String) : Int {
        return SearchHistoryDao.deleteHistory(name)
    }
 }