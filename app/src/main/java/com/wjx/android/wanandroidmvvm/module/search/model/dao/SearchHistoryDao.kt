package com.wjx.android.wanandroidmvvm.module.search.model.dao

import androidx.room.*
import com.wjx.android.wanandroidmvvm.module.search.model.bean.SearchHistory


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:36
 */
@Dao
interface SearchHistoryDao {
    @Transaction
    @Query("SELECT * FROM searchhistory")
    suspend fun queryAllSearchHistory(): List<SearchHistory>

    @Transaction
    @Insert(entity = SearchHistory::class)
    suspend fun insertSearchHistory(searchHistory: SearchHistory): Long

    @Transaction
    @Query("SELECT * FROM searchhistory WHERE name = (:name)")
    suspend fun querySearchHistoryByName(name: String): SearchHistory?

    @Transaction
    @Delete(entity = SearchHistory::class)
    suspend fun deleteSearchHistory(searchHistory: SearchHistory) : Int

    @Transaction
    @Query("DELETE FROM searchhistory")
    suspend fun deleteAllSearchHistory() : Int
}