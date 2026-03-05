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
    private val kpv: List<Int>

    init {
        val resources = context.resources
        val names = resources.getStringArray(R.array.color_names)
        val values = resources.obtainTypedArray(R.array.color_values).use { valuesTyped ->
            (0 until valuesTyped.length()).map { valuesTyped.getColor(it, Color.TRANSPARENT) }
        }
        val keys = resources.getStringArray(R.array.color_keys)
        val drawables = resources.getIntArray(R.array.color_drawables)
            .map { ContextCompat.getDrawable(context, it) as Drawable }
        kpv = keys.map { it.codePointAt(0) }
        symbols = kpv.indices.associate {
            kpv[it] to SymbolAttributes(values[it], names[it], drawables[it])
        }
    }

    /**
     * Returns an unmodifiable list of symbol key codepoints in the original resource order.
     */
    fun getKeys(): List<Int> = kpv

    /**
     * Returns the color value associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getColor(key: Int): Int = symbols.getValue(key).value

    /**
     * Returns the name associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getName(key: Int): String = symbols.getValue(key).name

    /**
     * Returns the [Drawable] associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getDrawable(key: Int): Drawable = symbols.getValue(key).drawable

    private data class SymbolAttributes(
        val value: Int,
        val name: String,
        val drawable: Drawable
    )

}
