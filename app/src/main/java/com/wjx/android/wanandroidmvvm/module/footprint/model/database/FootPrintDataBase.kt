package com.wjx.android.wanandroidmvvm.module.footprint.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wjx.android.wanandroidmvvm.module.common.model.Article
import com.wjx.android.wanandroidmvvm.module.footprint.model.dao.FootPrintDao


/**
 * Created with Android Studio.
 * Description:
 * @author: WangjianxianA
 * @CreateDate: 2020/5/9 15:22
 */
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class FootPrintDataBase : RoomDatabase() {
    abstract fun footPrintDao(): FootPrintDao

    companion object {
        private var INSTANCE: FootPrintDataBase? = null
        fun getInstance(context: Context): FootPrintDataBase? {
            if (INSTANCE == null) {
                synchronized(FootPrintDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            FootPrintDataBase::class.java,
                            "database_wanandroid"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
