package com.wjx.android.wanandroidmvvm.ui.project.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.wanandroidmvvm.base.basearticle.viewmodel.BaseArticleViewModel
import com.wjx.android.wanandroidmvvm.base.https.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectResponse
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectTabResponse
import com.wjx.android.wanandroidmvvm.ui.project.repository.ProjectRespository

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:48
 */
class ProjectViewModel (application: Application) : BaseArticleViewModel<ProjectRespository>(application) {
    val mProjectTabData : MutableLiveData<BaseResponse<List<ProjectTabResponse>>> = MutableLiveData()
    val mProjectArticleData : MutableLiveData<BaseResponse<ProjectResponse>> = MutableLiveData()

    fun loadProjectTab() {
        mRepository.loadProjectTab(mProjectTabData)
    }

    fun loadProjectArticle(pageNum : Int, cid : Int) {
        mRepository.loadProjectArticle(pageNum, cid, mProjectArticleData)
    }
}