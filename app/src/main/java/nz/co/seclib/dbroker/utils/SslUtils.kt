package nz.co.seclib.dbroker.utils

import android.content.res.AssetManager
import android.content.res.Resources
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class SSLContextAndTrustManagers(
    sslContext: SSLContext,
    trustManagers: Array<TrustManager>
) {
    val sslContext: SSLContext
    val trustManagers: Array<TrustManager>

    init {
        this.sslContext = sslContext
        this.trustManagers = trustManagers
    }
}

object SslUtils {
    //private val LOG: Logger = LoggerFactory.getLogger(SslUtils::class.java.simpleName)
    /*
    fun getSslContextForCertificateFile(fileName: String): SSLContextAndTrustManagers {
        return try {
            val keyStore = getKeyStore(fileName)
            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(
                null,
                trustManagerFactory.trustManagers,
                SecureRandom()
            )
            return SSLContextAndTrustManagers(sslContext,trustManagerFactory.trustManagers)
        } catch (e: Exception) {
            val msg = "Cannot load certificate from file"
            LOG.error(msg, e)
            throw RuntimeException(msg)
        }
    }

     */

    private fun getKeyStore(fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val cf =
                CertificateFactory.getInstance("X.509")
            val am : AssetManager = MyApplication.instance.assets
            val inputStream: InputStream = am.open(fileName)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(inputStream)
//                LOG.debug(
//                    "ca={}",
//                    (ca as X509Certificate).subjectDN
//                )
            } finally {
                inputStream.close()
            }
            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: Exception) {
//            LOG.error("Error during getting keystore", e)
        }
        return keyStore
    }
}