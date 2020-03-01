package com.wjx.android.wanandroidmvvm.base.BaseArticle.data

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 21:10
 */
data class Article(
    var id: Int,
    var author: String,
    var shareUser : String,
    var chapterName: String?,
    var desc: String,
    var link: String,
    var originId: Int,
    var title: String,
    var collect: Boolean,
    var superChapterName: String?,
    var niceDate: String,
    var fresh: Boolean,
    var top: Boolean,
    var envelopePic : String
)