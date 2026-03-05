package edu.cnm.deepdive.codebreaker.app.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import dagger.hilt.android.qualifiers.ActivityContext
import edu.cnm.deepdive.codebreaker.app.R
import jakarta.inject.Inject

class SymbolMap @Inject constructor(
    @param:ActivityContext private val context: Context
) {

    private val symbols: Map<Int, SymbolAttributes>

    init {
        val resources = context.resources
        val names = resources.getStringArray(R.array.color_names)
        val values = resources.obtainTypedArray(R.array.color_values).use { valuesTyped ->
            (0 until valuesTyped.length()).map { valuesTyped.getColor(it, Color.TRANSPARENT) }
        }
        val keys = resources.getStringArray(R.array.color_keys)
        val drawables = resources.getIntArray(R.array.color_drawables)
            .map { ContextCompat.getDrawable(context, it)!! }
        symbols = keys.indices.associate { i ->
            val key = keys[i].codePointAt(0)
            key to SymbolAttributes(values[i], names[i], drawables[i])
        }
    }

    private data class SymbolAttributes(
        val value: Int,
        val name: String,
        val drawable: Drawable
    )

}
