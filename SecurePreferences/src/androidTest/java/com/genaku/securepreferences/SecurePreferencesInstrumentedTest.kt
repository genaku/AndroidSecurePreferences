package com.genaku.securepreferences

import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class SecurePreferencesInstrumentedTest {

    private lateinit var preferences: SecurePreferences

    @Before
    fun before() {
        val context = InstrumentationRegistry.getTargetContext()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences = SecurePreferences(sharedPreferences)
    }

    @Test
    fun shouldGetNullValueIfNotSet() {
        assertNull(preferences["noValueKey"])
    }

    @Test
    fun shouldSaveValue() {
        preferences["key"] = "123Something"
        assertEquals("123Something", preferences["key"])
    }

    @Test
    fun shouldReturnLastSavedValue() {
        preferences["key"] = "value"
        preferences["key"] = "value1"
        preferences["key"] = "value2"
        assertEquals("value2", preferences["key"])
    }

    @Test
    fun shouldReturnNullOnRemovedValue() {
        preferences["key"] = "value"
        assertEquals("value", preferences["key"])
        preferences.remove("key")
        assertNull(preferences["key"])
    }

    @Test
    fun differentKeysShouldReturnDifferentValues() {
        preferences["key1"] = "value1"
        preferences["key2"] = "value2"
        preferences["key3"] = "value3"
        assertEquals("value1", preferences["key1"])
        assertEquals("value2", preferences["key2"])
        assertEquals("value3", preferences["key3"])
    }

    @Test
    fun shouldSaveKeyWithWhitespaces() {
        preferences["key with whitespaces"] = "value"
        assertEquals("value", preferences["key with whitespaces"])
    }
}