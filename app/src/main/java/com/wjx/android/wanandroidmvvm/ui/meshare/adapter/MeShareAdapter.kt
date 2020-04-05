package com.wjx.android.wanandroidmvvm.ui.meshare.adapter

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.utils.ColorUtil
import kotlinx.android.synthetic.main.article_item.view.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/26
 * Time: 19:07
 */
class MeShareAdapter(layoutId: Int, listData: MutableList<Article>?) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder, article: Article?) {
        viewHolder?.let {
                holder ->
            holder.itemView.article_material_card.rippleColor = ColorUtil.getOneColorStateList(mContext)
            holder.itemView.article_material_card.strokeColor = ColorUtil.getColor(mContext)
            article?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.setText(R.id.item_home_author, handleAuthor(it))
                        .setText(R.id.item_home_content, handleTitle(it))
                        .setText(R.id.item_home_date, it.niceDate)
                        .setText(R.id.item_article_type, handleCategory(it))
                        .setImageResource(R.id.item_list_collect, handleDelete())
                        .addOnClickListener(R.id.item_list_collect)
                        .setVisible(R.id.item_home_new, it.fresh)
                        .setVisible(R.id.item_home_top_article, it.top)
                        .setGone(R.id.item_home_top_article, it.top)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleTitle(article: Article) : String {
        article.let {
            return Html.fromHtml(it.title, Html.FROM_HTML_MODE_COMPACT).toString()
        }
    }


    private fun handleAuthor(article: Article) : String {
        article.let {
            return when{
                it.author.isNullOrEmpty() and it.shareUser.isNullOrEmpty() -> "匿名用户"
                it.author.isNullOrEmpty() -> "作者：${it.shareUser}" ?: ""
                it.shareUser.isNullOrEmpty() -> "作者：${it.author}" ?: ""
                else -> "作者：${it.author}"
            }
        }
    }

    private fun handleCategory(article :Article) : String {
        article.let {
            return when{
                it.superChapterName.isNullOrEmpty() and it.chapterName.isNullOrEmpty() -> ""
                it.superChapterName.isNullOrEmpty() -> it.chapterName ?: ""
                it.chapterName.isNullOrEmpty() -> it.superChapterName ?: ""
                else -> "${it.superChapterName}:${it.chapterName}"
            }
        }
    }

    private fun handleDelete() : Int = R.drawable.ic_delete_share
}