package com.wjx.android.wanandroidmvvm.base.callback

import com.kingja.loadsir.callback.Callback
import com.wjx.android.wanandroidmvvm.R

/**
 * Created with Android Studio.
 * Description:
 * @author: 王拣贤
 * @date: 2020/02/22
 * Time: 14:37
 */
class LoadingCallBack : Callback() {
    override fun onCreateView(): Int = R.layout.layout_loading
}