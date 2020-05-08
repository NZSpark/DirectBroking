package nz.co.seclib.dbroker.data.model

import android.app.Application
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import nz.co.seclib.dbroker.utils.MyApplication
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.OpenOption


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val userId: String,
        val displayName: String
){

        val fileName = "userinfo"
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        var file: File = File(MyApplication.instance.applicationContext.filesDir, fileName)

        var encryptedFile = EncryptedFile.Builder(
                file,
                MyApplication.instance.applicationContext,
                masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()


        fun savePass(password: String){
                file.delete()
                val encryptedOutputStream: FileOutputStream = encryptedFile.openFileOutput()
                encryptedOutputStream.write(password.toByteArray(Charsets.UTF_8))
                encryptedOutputStream.close()
        }

        fun readPass():String{
                val encryptedInputStream: FileInputStream = encryptedFile.openFileInput()
                val password = encryptedInputStream.readBytes().toString(Charsets.UTF_8)
                encryptedInputStream.close()
                return password
        }
}
