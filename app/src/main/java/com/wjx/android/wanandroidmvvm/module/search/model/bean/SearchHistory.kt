package com.wjx.android.wanandroidmvvm.module.search.model.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/17 13:02
 */
@Entity
data class SearchHistory(
    var name : String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var primaryKeyId: Int = 0
}