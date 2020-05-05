package com.wjx.android.wanandroidmvvm.ui.project.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.wanandroidmvvm.network.initiateRequest
import com.wjx.android.wanandroidmvvm.ui.common.viewmodel.ArticleViewModel
import com.wjx.android.wanandroidmvvm.network.response.BaseResponse
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectResponse
import com.wjx.android.wanandroidmvvm.ui.project.data.ProjectTabResponse
import com.wjx.android.wanandroidmvvm.ui.project.repository.ProjectRespository
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 15:48
 */
class ProjectViewModel(application: Application) :
    ArticleViewModel<ProjectRespository>(application) {
//    val mProjectTabData : MutableLiveData<BaseResponse<List<ProjectTabResponse>>> = MutableLiveData()
//    val mProjectArticleData : MutableLiveData<BaseResponse<ProjectResponse>> = MutableLiveData()
//
//    fun loadProjectTab() {
//        mRepository.loadProjectTab(mProjectTabData)
//    }
//
//    fun loadProjectArticle(pageNum : Int, cid : Int) {
//        mRepository.loadProjectArticle(pageNum, cid, mProjectArticleData)
//    }

    val mProjectTabData: MutableLiveData<List<ProjectTabResponse>> = MutableLiveData()
    val mProjectArticleData: MutableLiveData<ProjectResponse> = MutableLiveData()

    fun loadProjectTab() {
        initiateRequest({ mProjectTabData.value = mRepository.loadProjectTabCo() }, loadState)
    }

    fun loadProjectArticle(pageNum: Int, cid: Int) {
        initiateRequest({
            mProjectArticleData.value = mRepository.loadProjectArticleCo(pageNum, cid)
        }, loadState)
    }
}