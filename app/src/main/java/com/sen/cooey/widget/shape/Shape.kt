package com.sen.cooey.widget.shape

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import com.sen.cooey.widget.util.NeumorphShapeDrawable

internal interface Shape {
    fun setDrawableState(newDrawableState: NeumorphShapeDrawable.NeumorphShapeDrawableState)
    fun draw(canvas: Canvas, outlinePath: Path)
    fun updateShadowBitmap(bounds: Rect)
}
