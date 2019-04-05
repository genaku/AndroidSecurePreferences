package com.genaku.securepreferences

import com.cossacklabs.themis.SecureCellException
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/*
 * <h2>Secure preference property delegate</h2>
 * <b>Description:</b>
 * Facade on Android SharedPreferences class with data encryption/decryption
 *
 * @param securePreferences  SecurePreferences instance.
 * @param key  The name of the preference to retrieve.
 * @param default  Value to return if this preference does not exist.
 *
 * @author Gennadiy Kuchergin
 */
open class SecurePreference<T>(
    private val securePreferences: SecurePreferences,
    private val key: String,
    private val default: T
) : ReadWriteProperty<Any?, T> {

    @Throws(IllegalArgumentException::class, SecureCellException::class)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when (value) {
            is String -> securePreferences[key] = value
            is Date -> securePreferences[key] = value.time.toString()
            is Long,
            is Int,
            is Float,
            is Double,
            is Boolean,
            is BigDecimal,
            is BigInteger -> securePreferences[key] = value.toString()
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
    }

    @Throws(IllegalArgumentException::class, SecureCellException::class)
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val result: Any? = when (default) {
            is String -> securePreferences[key]
            is Long -> securePreferences[key]?.toLong()
            is Int -> securePreferences[key]?.toInt()
            is Float -> securePreferences[key]?.toFloat()
            is Double -> securePreferences[key]?.toDouble()
            is Boolean -> securePreferences[key]?.toBoolean()
            is BigDecimal -> securePreferences[key]?.toBigDecimal()
            is BigInteger -> securePreferences[key]?.toBigInteger()
            is Date -> getDate(securePreferences[key])
            else -> throw IllegalArgumentException("This type can't be load from Preferences")
        }
        return result as T? ?: default
    }

    private fun getDate(value: String?): Date {
        value ?: return default as Date
        return Date(value.toLong())
    }
}
