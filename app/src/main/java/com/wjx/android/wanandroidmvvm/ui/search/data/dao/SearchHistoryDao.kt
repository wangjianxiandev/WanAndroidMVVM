package com.wjx.android.wanandroidmvvm.ui.search.data.dao

import com.wjx.android.wanandroidmvvm.ui.search.data.db.SearchHistory
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:36
 */
object SearchHistoryDao {
    fun loadSearchHistory(): List<SearchHistory> {
        return LitePal.select("name").order("id desc").find()
    }

    fun addSearchHistory(name: String): Boolean {
        return SearchHistory().also { it.name = name }.save()
    }

    fun deleteHistory(name: String): Int {
        return LitePal.deleteAll(SearchHistory::class.java, "name=?", name)
    }

    fun clearSearchHistory(): Int {
        return LitePal.deleteAll<SearchHistory>()
    }
}