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
            .map { ContextCompat.getDrawable(context, it) as Drawable }
        symbols = keys.indices.associate {
            keys[it].codePointAt(0) to SymbolAttributes(values[it], names[it], drawables[it])
        }
    }

    /**
     * Returns an unmodifiable list of symbol key codepoints.
     */
    fun getKeys(): List<Int> = symbols.keys.toList()

    /**
     * Returns the color value associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getColor(key: Int): Int = symbols[key]?.value
        ?: throw NoSuchElementException("Key $key not found in SymbolMap")

    /**
     * Returns the name associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getName(key: Int): String = symbols[key]?.name
        ?: throw NoSuchElementException("Key $key not found in SymbolMap")

    /**
     * Returns the [Drawable] associated with the given [key] codepoint.
     * Throws [NoSuchElementException] if the key is not found.
     */
    fun getDrawable(key: Int): Drawable = symbols[key]?.drawable
        ?: throw NoSuchElementException("Key $key not found in SymbolMap")

    private data class SymbolAttributes(
        val value: Int,
        val name: String,
        val drawable: Drawable
    )

}
