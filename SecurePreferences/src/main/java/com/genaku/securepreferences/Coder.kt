package com.genaku.securepreferences

import android.util.Base64
import com.cossacklabs.themis.SecureCell
import com.cossacklabs.themis.SecureCellData

/*
 * <h2>Encryption class</h2>
 * <b>Description:</b>
 * Encrypts and decrypts value for given key using Themis cryptographic framework.
 *
 * @author Gennadiy Kuchergin
 */
class Coder {

    companion object {

        /**
         * <b>Description:</b> Decrypts data using an Themis cryptographic SecureCell decryption algorithm
         *
         * @param key   Key of data to decrypt.
         * @param value Data to decrypt.
         * @return Decrypted string for given key-value pair.
         */
        @JvmStatic
        fun decrypt(key: String, value: String): String {
            val storeKeyBytes = key.toUtf8ByteArray()

            val decodedString = Base64.decode(value, Base64.NO_WRAP)
            val encryptedData = SecureCellData(decodedString, null)

            val sc = SecureCell(storeKeyBytes, SecureCell.MODE_SEAL)
            val unprotectedData = sc.unprotect(storeKeyBytes, encryptedData)

            return unprotectedData.toUtf8String()
        }

        /**
         * <b>Description:</b> Encrypts data using an Themis cryptographic SecureCell encryption algorithm
         *
         * @param key   Key of data to encrypt.
         * @param value Data to encrypt.
         * @return Encrypted string for given key-value pair.
         */
        @JvmStatic
        fun encrypt(key: String, value: String): String {
            val storeKeyBytes = key.toUtf8ByteArray()

            val sc = SecureCell(storeKeyBytes, SecureCell.MODE_SEAL)
            val protectedData = sc.protect(storeKeyBytes, value.toUtf8ByteArray())
            return Base64.encodeToString(protectedData.protectedData, Base64.NO_WRAP)
        }

        private val UTF8_CHARSET = charset("UTF-8")

        private fun String.toUtf8ByteArray() =
            this.toByteArray(UTF8_CHARSET)

        private fun ByteArray.toUtf8String() =
            this.toString(UTF8_CHARSET)
    }
}