package com.wjx.android.wanandroidmvvm.module.search.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.module.search.data.HotKeyResponse
import com.wjx.android.wanandroidmvvm.module.search.data.SearchResultResponse
import com.wjx.android.wanandroidmvvm.module.search.data.db.SearchHistory
import com.wjx.android.wanandroidmvvm.module.search.repository.SearchRepostiory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:49
 */
class SearchViewModel (application: Application) : ArticleViewModel<SearchRepostiory>(application) {
    val mHotKeyData = MutableLiveData<BaseResponse<List<HotKeyResponse>>>()
    val mSearResultData = MutableLiveData<BaseResponse<SearchResultResponse>>()
    val mDeleteHistory = MutableLiveData<Int>()
    val mSearchHistory = MutableLiveData<List<SearchHistory>>()
    val mAddSearchHistory = MutableLiveData<Boolean>()
    val mClearHistory = MutableLiveData<Int>()

    fun loadHotkey() {
        mRepository.loadHotKey(mHotKeyData)
    }

    fun loadSearchResult(pageNum : Int, key : String) {
        mRepository.loadSearchResult(pageNum, key, mSearResultData)
    }

    fun loadSearchHistory() {
        mSearchHistory.value = mRepository.loadSearchHistory()
    }

    fun clearSearchHistory() {
        mClearHistory.value = mRepository.clearSearchHistory()
    }

    fun addSearchHistory(name : String) {
        var history = mRepository.loadSearchHistory()
        history.filter { history ->
            return@filter history.name == name
        }.getOrElse(0) {
            return@getOrElse if (history.size >= SearchHistory.MAX_HISTORY) history[9] else SearchHistory()
        }.delete()
        mAddSearchHistory.value = mRepository.addSearchHistory(name)
    }

    fun deleteSearchHistory(name : String) {
        mDeleteHistory.value = mRepository.deleteSearchHistory(name)
    }
}
