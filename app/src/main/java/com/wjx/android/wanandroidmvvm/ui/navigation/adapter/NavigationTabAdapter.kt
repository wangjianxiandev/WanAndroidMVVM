package com.wjx.android.wanandroidmvvm.ui.navigation.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.custom.interpolator.CustomScaleInterpolator
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import kotlinx.android.synthetic.main.navigation_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/29
 * Time: 20:12
 */
class NavigationTabAdapter (layoutId : Int, listData : MutableList<String>?)
    : BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {

    var selectedPosition : Int = -1

    override fun convert(viewHolder: BaseViewHolder?, item: String?) {
        viewHolder?.let {
            with(it) {
                addOnClickListener(R.id.circle_imageView)
                setText(R.id.nav_tab_name, item)
                itemView.circle_imageView.setCircleName(item.toString())
                itemView.circle_imageView.setmBackgroundColor(ColorUtil.getColor(mContext))
                if (adapterPosition == selectedPosition) {
                    val animatorX =
                        ObjectAnimator.ofFloat(viewHolder.itemView.circle_imageView, "scaleX", 1.0f, 1.2f, 1.0f)
                    val animatorY =
                        ObjectAnimator.ofFloat(viewHolder.itemView.circle_imageView, "scaleY", 1.0f, 1.2f, 1.0f)
                    val set = AnimatorSet()
                    set.duration = 1000
                    set.interpolator = CustomScaleInterpolator(0.4f)
                    set.playTogether(animatorX, animatorY)
                    set.start()
                }
            }
        }
    }
}