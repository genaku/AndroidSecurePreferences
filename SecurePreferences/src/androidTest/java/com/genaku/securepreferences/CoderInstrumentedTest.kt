package com.genaku.securepreferences

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoderInstrumentedTest {

    @Test
    fun inputAndEncodedShouldNotBeSame() {
        Assert.assertNotEquals(value, Coder.encrypt(key, value))
    }

    @Test
    fun shouldDecodeEncoded() {
        Assert.assertEquals(value, Coder.decrypt(key, Coder.encrypt(key, value)))
    }

    companion object {
        private const val key = "key"
        private const val value = "test value"
    }
}