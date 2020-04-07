package com.wjx.android.wanandroidmvvm.ui.wechat.data

import com.wjx.android.wanandroidmvvm.ui.common.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 14:44
 */
data class WeChatArticleResponse(
    var curPage: Int,
    var datas: List<Article>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)