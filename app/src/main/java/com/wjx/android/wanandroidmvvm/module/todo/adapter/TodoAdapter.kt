package com.wjx.android.wanandroidmvvm.module.todo.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.common.utils.ColorUtil
import com.wjx.android.wanandroidmvvm.common.utils.DateUtil
import com.wjx.android.wanandroidmvvm.module.todo.model.TodoResponse
import kotlinx.android.synthetic.main.todo_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/04
 * Time: 16:38
 */
class TodoAdapter(layoutId: Int, listData: MutableList<TodoResponse>?) :
    BaseQuickAdapter<TodoResponse, BaseViewHolder>(layoutId, listData) {
    override fun convert(helper: BaseViewHolder?, item: TodoResponse?) {
        helper?.let { holder ->
            item?.let {
                holder.itemView.todo_material_card.rippleColor = ColorUtil.getOneColorStateList(mContext)
                holder.itemView.todo_material_card.strokeColor = ColorUtil.getColor(mContext)
                holder.setText(R.id.item_todo_title, it.title)
                    .setText(R.id.item_todo_content, it.content)
                    .setText(R.id.item_todo_date, it.dateStr)
                    .addOnClickListener(R.id.item_todo_more)
                    .setText(R.id.item_todo_priority, handlePriority(it.priority))
                    .setText(R.id.item_todo_type, handleStatus(it.type))

                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.BR_TL,
                    intArrayOf(
                        ColorUtil.calculateGradient(0.5f, ColorUtil.randomColor(), Color.WHITE),
                        Color.WHITE
                    )
                )
                holder.itemView.todo_card.setBackgroundDrawable(gradientDrawable)

                if (it.status == 1) {
                    holder.setVisible(R.id.item_todo_status, true)
                    holder.setImageResource(R.id.item_todo_status, R.drawable.todo_done)
                } else {
                    if (it.date < DateUtil.getNowTime()!!.getTime()) {
                        holder.setVisible(R.id.item_todo_status, true)
                        holder.setImageResource(R.id.item_todo_status, R.drawable.todo_not_done)
                    } else {
                        holder.setGone(R.id.item_todo_status, false)
                    }
                }
            }
        }
    }

    private fun handlePriority(priority: Int): String {
        if (priority == 1) {
            return "重要"
        } else {
            return "非紧急"
        }
    }

    private fun handleStatus(type: Int): String {
        if (type == 1 || type == 0) {
            return "工作"
        } else {
            return " 学习"
        }
    }
}