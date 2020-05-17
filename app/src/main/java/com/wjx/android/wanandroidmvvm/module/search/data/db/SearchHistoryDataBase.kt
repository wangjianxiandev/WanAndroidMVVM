package com.wjx.android.wanandroidmvvm.module.search.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wjx.android.wanandroidmvvm.module.search.data.bean.SearchHistory
import com.wjx.android.wanandroidmvvm.module.search.data.dao.SearchHistoryDao

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/17 12:57
 */
@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class SearchHistoryDataBase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var INSTANCE: SearchHistoryDataBase? = null
        fun getInstance(context: Context): SearchHistoryDataBase? {
            if (INSTANCE == null) {
                synchronized(SearchHistoryDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            SearchHistoryDataBase::class.java,
                            "database_search_history"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}