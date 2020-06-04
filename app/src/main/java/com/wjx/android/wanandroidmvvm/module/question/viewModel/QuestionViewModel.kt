package com.wjx.android.wanandroidmvvm.module.question.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.module.question.questionrepository.QuestionRepository
import com.wjx.android.wanandroidmvvm.module.question.model.QuestionResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 15:41
 */

class QuestionViewModel(application: Application) :
    ArticleViewModel<QuestionRepository>(application) {
    var mQuestionData : MutableLiveData<BaseResponse<QuestionResponse>> = MutableLiveData()

    fun loadQuestionList(pageNum : Int) {
        mRepository.loadQuestionList(pageNum, mQuestionData)
    }
}