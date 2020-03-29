package com.wjx.android.wanandroidmvvm.ui.rank.adapter

import android.os.Build
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.utils.Util
import com.wjx.android.wanandroidmvvm.ui.rank.data.IntegralResponse
import kotlinx.android.synthetic.main.rank_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/3/29 18:30
 */

class RankAdapter(layoutId: Int, listData: MutableList<IntegralResponse>?) :
    BaseQuickAdapter<IntegralResponse, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder, integralResponse: IntegralResponse?) {
        viewHolder?.let {
                holder ->
            holder.itemView.rank_material_card.rippleColor = Util.getOneColorStateList(mContext)
            holder.itemView.rank_material_card.strokeColor = Util.getColor(mContext)
            holder.itemView.integral_level.setTextColor(Util.getColor(mContext))
            holder.itemView.integral_name.setTextColor(Util.getColor(mContext))
            holder.itemView.integral_count.setTextColor(Util.getColor(mContext))
            integralResponse?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.setText(R.id.integral_level, "等级：" + integralResponse.level)
                        .setText(R.id.integral_name, "用户：" + integralResponse.username)
                        .setText(R.id.integral_count, "积分：" + integralResponse.coinCount)
                }
            }
        }
    }

}