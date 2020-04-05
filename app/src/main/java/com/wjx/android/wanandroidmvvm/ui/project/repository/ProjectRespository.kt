package com.wjx.android.wanandroidmvvm.ui.project.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.repository.BaseArticleRepository
import com.wjx.android.wanandroidmvvm.base.observer.BaseObserver
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.base.state.State
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectResponse
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectTabResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:42
 */
class ProjectRespository (loadState : MutableLiveData<State>) : BaseArticleRepository(loadState) {
    fun loadProjectTab(liveData: MutableLiveData<BaseResponse<List<ProjectTabResponse>>>) {
        apiService.loadProjectTab()
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

    fun loadProjectArticle(pageNum : Int , cid : Int, liveData: MutableLiveData<BaseResponse<ProjectResponse>>) {
        apiService.loadProjectArticles(pageNum, cid)
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