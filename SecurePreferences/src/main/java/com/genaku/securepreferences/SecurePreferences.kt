package com.genaku.securepreferences

import android.content.SharedPreferences
import com.cossacklabs.themis.SecureCellException

/*
 * <h2>Secure preferences class</h2>
 * <b>Description:</b>
 * Facade on Android SharedPreferences class with data encryption/decryption
 *
 * @param sharedPreferences  Android SharedPreferences instance.
 * @param suffix  If value can be empty you should set not empty suffix.
 *
 * @author Gennadiy Kuchergin
 */
class SecurePreferences(
    private val sharedPreferences: SharedPreferences,
    private val suffix: String = ""
) {

    /**
     * <b>Description:</b> Set data using an encryption algorithm
     *
     * @param key   Provides access to store data.
     * @param value Data to store.
     */
    @Throws(IllegalArgumentException::class, SecureCellException::class)
    operator fun set(key: String, value: String) {
        if (key.isBlank()) {
            throw IllegalArgumentException("Key should not be empty")
        }

        val storeKey = prepareKey(key)
        val encodedString = Coder.encrypt(storeKey, prepareValue(value))

        sharedPreferences.edit().putString(storeKey, encodedString).apply()
    }

    /**
     * <b>Description:</b> Get data using an decryption algorithm
     *
     * @param key Provides access to stored data.
     * @return Decrypted data for given key. If no data returns NULL.
     */
    @Throws(IllegalArgumentException::class, SecureCellException::class)
    operator fun get(key: String): String? {
        if (key.isBlank()) {
            throw IllegalArgumentException("Key should not be empty")
        }

        val storeKey = prepareKey(key)
        val encodedString = sharedPreferences.getString(storeKey, null) ?: return null

        return sanitizeValue(Coder.decrypt(storeKey, encodedString))
    }

    /**
     * <b>Description:</b> Remove data for given key
     *
     * @param key Key of stored data to remove.
     */
    fun remove(key: String) {
        sharedPreferences.edit().remove(prepareKey(key)).apply()
    }

    private fun prepareKey(key: String): String =
        key.trim()

    private fun prepareValue(value: String): String =
        value + suffix

    private fun sanitizeValue(value: String): String =
        value.removeSuffix(suffix)

}