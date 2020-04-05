package com.wjx.android.wanandroidmvvm.ui.question.data

import com.wjx.android.wanandroidmvvm.base.basearticle.data.Article

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 15:39
 */
data class QuestionResponse(
    var curPage : Int,
    var datas : List<Article>
)