package com.wjx.android.wanandroidmvvm.module.search.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.common.utils.RoomHelper
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.module.search.data.HotKeyResponse
import com.wjx.android.wanandroidmvvm.module.search.data.SearchResultResponse
import com.wjx.android.wanandroidmvvm.module.search.data.bean.SearchHistory
import com.wjx.android.wanandroidmvvm.module.search.repository.SearchRepostiory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:49
 */
class SearchViewModel(application: Application) : ArticleViewModel<SearchRepostiory>(application) {
    val mHotKeyData = MutableLiveData<BaseResponse<List<HotKeyResponse>>>()
    val mSearResultData = MutableLiveData<BaseResponse<SearchResultResponse>>()
    val mDeleteHistory = MutableLiveData<Int>()
    val mSearchHistory = MutableLiveData<List<SearchHistory>>()
    val mAddSearchHistory = MutableLiveData<Long>()
    val mClearHistory = MutableLiveData<Int>()

    fun loadHotkey() {
        mRepository.loadHotKey(mHotKeyData)
    }

    fun loadSearchResult(pageNum: Int, key: String) {
        mRepository.loadSearchResult(pageNum, key, mSearResultData)
    }

    fun loadSearchHistory() {
        viewModelScope.launch {
            mSearchHistory.value = withContext(Dispatchers.IO) {
                mRepository.loadSearchHistory()
            }
        }
    }

    fun insertSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            mAddSearchHistory.value = withContext(Dispatchers.IO) {
                mRepository.insertSearchHistory(searchHistory)
            }
        }
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            mDeleteHistory.value = withContext(Dispatchers.IO) {
                mRepository.deleteSearchHistory(searchHistory)
            }
        }
    }

    fun deleteAllSearchHistory() {
        viewModelScope.launch {
            mClearHistory.value = withContext(Dispatchers.IO) {
                mRepository.deleteAllSearchHistory()
            }
        }
    }
}
