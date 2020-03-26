package com.wjx.android.wanandroidmvvm.ui.question.questionrepository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.BaseArticle.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.question.data.QuestionResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 15:42
 */
class QuestionRepository(loadState: MutableLiveData<State>) : BaseArticleRepository(loadState) {
    fun loadQuestionList(pageNum : Int, liveData : MutableLiveData<BaseResponse<QuestionResponse>>) {
        apiService.loadQuestionList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BaseObserver(liveData, loadState, this))
    }
}