package com.wjx.android.wanandroidmvvm.ui.search.data

import com.wjx.android.wanandroidmvvm.ui.common.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/05
 * Time: 17:26
 */
data class SearchResultResponse(
    var curPage: Int,
    var datas: List<Article>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)