package com.wjx.android.mvvm.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 11:12
 */
class GlideImageLoader :ImageLoader() {
    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}