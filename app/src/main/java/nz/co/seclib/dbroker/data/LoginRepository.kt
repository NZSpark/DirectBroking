package nz.co.seclib.dbroker.data

import android.os.Build
import androidx.annotation.RequiresApi
import nz.co.seclib.dbroker.data.model.LoggedInUser
import nz.co.seclib.dbroker.data.model.SystemConfigInfo
import nz.co.seclib.dbroker.data.model.UserInfo
import java.io.IOException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dbDao:DBrokerDAO, private val dbWeb: DirectBrokingWeb) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun getPropertyValuebyPropertyName(propertyName:String):String{
        val propertyList = dbDao.selectSystemConfigInfoByPropertyName(propertyName)
        if(propertyList.size == 0) return ""
        return propertyList[0].propertyValue.toString()
    }

    suspend fun saveProperty(propertyName: String,propertyValue:String){
        val propertyList = dbDao.selectSystemConfigInfoByPropertyName(propertyName)
        if(propertyList.size == 0) {
            val newSystemConfigInfo = SystemConfigInfo(propertyName,propertyValue)
            dbDao.insertSystemConfigInfo(newSystemConfigInfo)
        }else{
            val newSystemConfigInfo = SystemConfigInfo(propertyName,propertyValue)
            dbDao.updateSystemConfigInfo(newSystemConfigInfo)
        }
    }


    fun logout() {
        user = null
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try{
            if(dbWeb.getLoginCookie(username,password))
            return Result.Success(LoggedInUser(username,password))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
        return Result.Error(IOException("Error logging in",null))
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
