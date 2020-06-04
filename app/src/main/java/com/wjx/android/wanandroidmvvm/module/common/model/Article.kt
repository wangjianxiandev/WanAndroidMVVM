package com.wjx.android.wanandroidmvvm.module.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 21:10
 */
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    var primaryKeyId: Int = 0,
    var id: Int = 0,
    var author: String = "",
    var shareUser: String = "",
    var chapterName: String? = "",
    var desc: String = "",
    var link: String = "",
    var originId: Int = 0,
    var title: String = "",
    var collect: Boolean = false,
    var superChapterName: String? = "",
    var niceDate: String = "",
    var fresh: Boolean = false,
    var top: Boolean = false,
    var envelopePic: String = ""
)