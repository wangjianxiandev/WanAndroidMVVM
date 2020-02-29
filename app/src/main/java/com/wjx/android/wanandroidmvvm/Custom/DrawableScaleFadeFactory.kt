package com.wjx.android.wanandroidmvvm.Custom

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.ViewAnimationUtils
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.transition.NoTransition
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/28
 * Time: 17:30
 */

class DrawableScaleFadeFactory (
    private val duration: Int,
    private val isCrossFadeEnabled: Boolean,
    private val isReveal:Boolean = false
) : TransitionFactory<Drawable> {
    private var resourceTransition: DrawableScaleFadeTransition? = null

    override fun build(dataSource: DataSource, isFirstResource: Boolean): Transition<Drawable> {
        return if (dataSource == DataSource.MEMORY_CACHE)
            NoTransition.get()
        else
            getResourceTransition()
    }


    private fun getResourceTransition(): Transition<Drawable> {
        if (resourceTransition == null) {
            resourceTransition = DrawableScaleFadeTransition(duration, isCrossFadeEnabled,isReveal)
        }
        return resourceTransition!!
    }


    class Builder
    /** @param durationMillis The duration of the cross fade animation in milliseconds.
     */
    @JvmOverloads constructor(private val durationMillis: Int = DEFAULT_DURATION_MS) {
        private var isCrossFadeEnabled: Boolean = false


        fun setCrossFadeEnabled(isCrossFadeEnabled: Boolean): DrawableScaleFadeFactory.Builder {
            this.isCrossFadeEnabled = isCrossFadeEnabled
            return this
        }

        fun build(): DrawableScaleFadeFactory {
            return DrawableScaleFadeFactory(durationMillis, isCrossFadeEnabled)
        }

        companion object {
            private const val DEFAULT_DURATION_MS = 300
        }
    }

    class DrawableScaleFadeTransition(
        private val duration: Int,
        private val isCrossFadeEnabled: Boolean,
        private val isReveal:Boolean = false
    ) : Transition<Drawable> {


        override fun transition(current: Drawable, adapter: Transition.ViewAdapter): Boolean {
            var previous = adapter.currentDrawable
            if (previous == null) {
                previous = ColorDrawable(
                    Palette.from(createBitmap(current)).generate().getVibrantColor(
                    Color.TRANSPARENT))
            }
            val view = adapter.view
            val transitionDrawable = TransitionDrawable(arrayOf(previous, current))
            transitionDrawable.isCrossFadeEnabled = isCrossFadeEnabled
            transitionDrawable.startTransition(duration * 2)
            adapter.setDrawable(transitionDrawable)
            if (view.visibility == View.VISIBLE && view.isAttachedToWindow) {
                if(isReveal){
                    val maxRadius = view.height.coerceAtLeast(view.width)
                    val animation = ViewAnimationUtils.createCircularReveal(
                        view,
                        transitionDrawable.intrinsicWidth / 2,
                        transitionDrawable.intrinsicHeight / 2,
                        0f,
                        maxRadius.toFloat()
                    )
                    animation.duration = duration.toLong()
                    animation.start()
                }
                view.scaleX = 1.2f
                view.scaleY = 1.2f
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(duration.toLong()).start()
            }else{
                view.scaleX = 1.0f
                view.scaleY = 1.0f
            }
            return true
        }

        private fun createBitmap(current: Drawable) : Bitmap {
            val bitmap = Bitmap.createBitmap(10,10, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            current.setBounds(0,0,10,10)
            current.draw(canvas)
            return bitmap
        }

    }
}
