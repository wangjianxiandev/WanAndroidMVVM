package com.wjx.android.wanandroidmvvm.base.BaseArticle

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjx.android.wanandroidmvvm.R
import com.wjx.android.wanandroidmvvm.base.BaseArticle.data.Article
import com.wjx.android.wanandroidmvvm.base.BaseViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 21:09
 */
class BaseArticleAdapter (layoutId : Int, listData : MutableList<Article>?)
    : BaseQuickAdapter<Article, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder, article: Article?) {
        viewHolder?.let {
            holder ->
            article?.let {
                holder.setText(R.id.item_home_author, handleAuthor(it))
                    .setText(R.id.item_home_content, it.title)
                    .setText(R.id.item_home_date, it.niceDate)
                    .setText(R.id.item_article_type, handleCategory(it))
                    .setImageResource(R.id.item_list_collect, isCollect(it))
                    .addOnClickListener(R.id.item_list_collect)
                    .setVisible(R.id.item_home_top_article, it.isTop)
                    .setVisible(R.id.item_home_new, it.isFresh)
            }
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

    private fun isCollect(article: Article) :Int = if (article.isCollect) R.drawable.collect_selector_icon else R.drawable.uncollect_selector_icon
}