package com.sen.cooey.widget.shape

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.sen.cooey.widget.CornerFamily
import com.sen.cooey.widget.NeumorphShapeAppearanceModel
import com.sen.cooey.widget.util.NeumorphShapeDrawable
import com.sen.cooey.widget.util.onCanvas
import com.sen.cooey.widget.util.withClipOut
import com.sen.cooey.widget.util.withTranslation
import kotlin.math.roundToInt

internal class FlatShape(
    private var drawableState: NeumorphShapeDrawable.NeumorphShapeDrawableState
) : Shape {

    private var lightShadowBitmap: Bitmap? = null
    private var darkShadowBitmap: Bitmap? = null
    private val lightShadowDrawable = GradientDrawable()
    private val darkShadowDrawable = GradientDrawable()

    override fun setDrawableState(newDrawableState: NeumorphShapeDrawable.NeumorphShapeDrawableState) {
        this.drawableState = newDrawableState
    }

    override fun draw(canvas: Canvas, outlinePath: Path) {
        canvas.withClipOut(outlinePath) {
            val elevation = drawableState.shadowElevation
            val z = drawableState.shadowElevation + drawableState.translationZ
            val left: Float
            val top: Float
            val padding = drawableState.padding
            left = padding.left.toFloat()
            top = padding.top.toFloat()
            lightShadowBitmap?.let {
                val offset = -elevation - z
                drawBitmap(it, offset + left, offset + top, null)
            }
            darkShadowBitmap?.let {
                val offset = -elevation + z
                drawBitmap(it, offset + left, offset + top, null)
            }
        }
    }

    override fun updateShadowBitmap(bounds: Rect) {
        fun GradientDrawable.setCornerShape(shapeAppearanceModel: NeumorphShapeAppearanceModel) {
            when (shapeAppearanceModel.getCornerFamily()) {
                CornerFamily.OVAL -> {
                    shape = GradientDrawable.OVAL
                }
                CornerFamily.ROUNDED -> {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadii = shapeAppearanceModel.getCornerSize().let {
                        floatArrayOf(it, it, it, it, it, it, it, it)
                    }
                }
            }
        }

        lightShadowDrawable.apply {
            setColor(drawableState.shadowColorLight)
            setCornerShape(drawableState.shapeAppearanceModel)
        }
        darkShadowDrawable.apply {
            setColor(drawableState.shadowColorDark)
            setCornerShape(drawableState.shapeAppearanceModel)
        }

        val w = bounds.width()
        val h = bounds.height()
        lightShadowDrawable.setSize(w, h)
        lightShadowDrawable.setBounds(0, 0, w, h)
        darkShadowDrawable.setSize(w, h)
        darkShadowDrawable.setBounds(0, 0, w, h)
        lightShadowBitmap = lightShadowDrawable.toBlurredBitmap(w, h)
        darkShadowBitmap = darkShadowDrawable.toBlurredBitmap(w, h)
    }

    private fun Drawable.toBlurredBitmap(w: Int, h: Int): Bitmap? {
        fun Bitmap.blurred(): Bitmap? {
            if (drawableState.inEditMode) {
                return this
            }
            return drawableState.blurProvider.blur(this)
        }

        val shadowElevation = drawableState.shadowElevation
        val width = (w + shadowElevation * 2).roundToInt()
        val height = (h + shadowElevation * 2).roundToInt()
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            .onCanvas {
                withTranslation(shadowElevation, shadowElevation) {
                    draw(this)
                }
            }
            .blurred()
    }
}