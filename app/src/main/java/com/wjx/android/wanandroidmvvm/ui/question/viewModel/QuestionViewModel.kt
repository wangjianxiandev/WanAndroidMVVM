package com.wjx.android.wanandroidmvvm.ui.question.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.question.questionrepository.QuestionRepository
import com.wjx.android.wanandroidmvvm.ui.question.data.QuestionResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 15:41
 */

class QuestionViewModel(application: Application) :
    BaseArticleViewModel<QuestionRepository>(application) {
    var mQuestionData : MutableLiveData<BaseResponse<QuestionResponse>> = MutableLiveData()

    fun loadQuestionList(pageNum : Int) {
        mRepository.loadQuestionList(pageNum, mQuestionData)
    }
}