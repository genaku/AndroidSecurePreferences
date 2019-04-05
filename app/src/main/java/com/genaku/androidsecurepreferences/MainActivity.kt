package com.genaku.androidsecurepreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.genaku.securepreferences.SecurePreference
import com.genaku.securepreferences.SecurePreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }
    private val settings by lazy { Settings(sharedPreferences) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButtons()
    }

    private fun setupButtons() {
        btnClear.setOnClickListener {
            settings.clear()
            showPassword()
            showNumber()
        }
        btnSave.setOnClickListener {
            settings.password = edtToStore.text.toString()
            showPassword()
        }
        btnSaveNumber.setOnClickListener {
            settings.number = edtNumberToStore.text.toString().toLong()
            showNumber()
        }
    }

    private fun showPassword() {
        tvDecrypted.text = settings.password
        tvEncrypted.text = sharedPreferences.getString(PASSWORD_KEY, "")
    }

    private fun showNumber() {
        tvDecryptedNumber.text = settings.number.toString()
        tvEncryptedNumber.text = sharedPreferences.getString(NUMBER_KEY, "")
    }

    // Example of using SecurePreferences with property delegates
    private class Settings(sharedPreferences: SharedPreferences) {

        private val preferences = SecurePreferences(
            sharedPreferences = sharedPreferences,
            suffix = "_"
        )

        var password: String by SecurePreference(preferences, PASSWORD_KEY, "")
        var number: Long by SecurePreference(preferences, NUMBER_KEY, 0L)

        fun clear() {
            preferences.remove(PASSWORD_KEY)
            preferences.remove(NUMBER_KEY)
        }
    }

    // Example of ordinal use of SecurePreferences
    private class AltSettings(sharedPreferences: SharedPreferences) {

        private val preferences = SecurePreferences(
            sharedPreferences = sharedPreferences,
            suffix = "_"
        )

        var password: String
            get() = preferences[PASSWORD_KEY] ?: ""
            set(value) {
                preferences[PASSWORD_KEY] = value
            }

        var number: Long
            get() = preferences[NUMBER_KEY]?.toLong() ?: 0L
            set(value) {
                preferences[NUMBER_KEY] = value.toString()
            }

        fun clear() {
            preferences.remove(PASSWORD_KEY)
            preferences.remove(NUMBER_KEY)
        }
    }

    companion object {
        private const val PASSWORD_KEY = "password"
        private const val NUMBER_KEY = "number"
    }
}
