package com.wjx.android.wanandroidmvvm.module.question.questionrepository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.module.common.repository.ArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.common.state.State
import com.wjx.android.wanandroidmvvm.module.question.model.QuestionResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 15:42
 */
class QuestionRepository(loadState: MutableLiveData<State>) : ArticleRepository(loadState) {
    fun loadQuestionList(pageNum : Int, liveData : MutableLiveData<BaseResponse<QuestionResponse>>) {
        apiService.loadQuestionList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                BaseObserver(
                    liveData,
                    loadState,
                    this
                )
            )
    }
}