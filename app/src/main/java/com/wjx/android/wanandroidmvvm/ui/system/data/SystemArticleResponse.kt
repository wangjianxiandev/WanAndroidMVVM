package com.wjx.android.wanandroidmvvm.ui.system.data

import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:06
 */
data class SystemArticleResponse(
    var curPage: Int,
    var datas: List<Article>,
    var pageCount: Int,
    var total: Int
)