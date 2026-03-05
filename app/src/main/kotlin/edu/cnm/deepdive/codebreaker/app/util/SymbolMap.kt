package edu.cnm.deepdive.codebreaker.app.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ActivityContext
import edu.cnm.deepdive.codebreaker.app.R
import jakarta.inject.Inject

class SymbolMap @Inject constructor(
    @param:ActivityContext private val context: Context
) {

    private val symbols = mutableMapOf<Int, SymbolAttributes>()

    init {
        val names: Array<String> = context.resources.getStringArray(R.array.color_names)
        val valuesTyped = context.resources.obtainTypedArray(R.array.color_values)
        val values = (0 until valuesTyped.length()).map { valuesTyped.getColor(it, Color.TRANSPARENT) }
        valuesTyped.recycle()
        val keys = context.resources.getStringArray(R.array.color_keys)
        val drawableIds = context.resources.getIntArray(R.array.color_drawables)
        val drawables = drawableIds.map { ContextCompat.getDrawable(context, it)!! }
        for (i in keys.indices) {
            val key = keys[i].codePointAt(0)
            symbols[key] = SymbolAttributes(values[i], names[i], drawables[i])
        }
    }

    private data class SymbolAttributes(
        val value: Int,
        val name: String,
        val drawable: Drawable
    )

}
