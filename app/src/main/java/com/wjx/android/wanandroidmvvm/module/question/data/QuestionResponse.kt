package com.wjx.android.wanandroidmvvm.module.question.data

import com.wjx.android.wanandroidmvvm.module.common.data.Article

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