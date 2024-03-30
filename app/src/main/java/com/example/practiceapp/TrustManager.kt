package com.example.practiceapp

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class TrustManager: X509TrustManager {
    private val trustManagers: Array<TrustManager> = arrayOf<TrustManager>(TrustManager())
    private val _AcceptedIssuers: Array<X509Certificate> = arrayOf<X509Certificate>()

    override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
        // Do nothing
    }

    override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
        // Do nothing
    }

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
        return _AcceptedIssuers
    }
    fun isClientTrusted(chain: Array<out X509Certificate>?, authType: String?): Boolean {
        return true
    }
    fun isServerTrusted(chain: Array<out X509Certificate>?, authType: String?): Boolean {
        return true
    }
    fun allowAllSSL() {
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, trustManagers, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
    }
}