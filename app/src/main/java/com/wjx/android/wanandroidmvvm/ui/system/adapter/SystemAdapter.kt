package com.wjx.android.wanandroidmvvm.ui.system.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.Custom.interpolator.CustomScaleInterpolator
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemLabelResponse
import com.wjx.android.wanandroidmvvm.ui.system.data.SystemTabResponse
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.system_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:32
 */
class SystemAdapter (layoutId : Int, listData : MutableList<SystemTabResponse>?)
    :BaseQuickAdapter<SystemTabResponse, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SystemTabResponse?) {
        viewHolder?.let {
            holder ->
            item?.let {
                holder.setText(R.id.item_system_title, it.name)
                holder.itemView.item_tag_layout.adapter = object : TagAdapter<SystemLabelResponse>(it.children) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        t: SystemLabelResponse?
                    ): View {
                        val tagView : TextView =
                            LayoutInflater.from(mContext).inflate(R.layout.flow_layout, parent,false) as TextView
                        tagView.setText(it.children[position].name)
                        tagView.setTextColor(Util.randomColor())
                        return tagView
                    }
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.0f, 1.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.0f, 1.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1.0f, 0.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1.0f, 0.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }
}