package nz.co.seclib.dbroker.ui.sysinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nz.co.seclib.dbroker.data.SystemConfigRepository
import nz.co.seclib.dbroker.utils.AESEncryption

class SystemConfigViewModel(private val systemConfigRepository: SystemConfigRepository) : ViewModel() {
    //TimerInterval
    private var _timerInterval = MutableLiveData<String>()
    val timerInterval = _timerInterval

    //TimerEnable
    private var _timerEnable = MutableLiveData<String>()
    val timerEnable = _timerEnable

    //UserName
    private var _userName = MutableLiveData<String>()
    val userName = _userName

    //Password
    private var _password = MutableLiveData<String>()
    val password = _password

    private val viewModelJob = SupervisorJob()

    init {
        CoroutineScope(viewModelJob).launch{
            var sTemp = getTimerIntervalFromDB()
            if (sTemp != "")
                _timerInterval.postValue(sTemp)

            sTemp = getTimerEnableFromDB()
            if (sTemp != "")
                _timerEnable.postValue(sTemp)

            sTemp = getUserNameFromDB()
            if (sTemp != "")
                _userName.postValue(sTemp)

            sTemp = getPasswordFromDB()
            if (sTemp != "")
                _password.postValue(sTemp)
        }
    }

    fun getTimerIntervalFromDB():String{
        return systemConfigRepository.getPropertyValuebyPropertyName("TimerInterval")
    }

    fun saveTimerIntervalToDB(timerInterval:String){
        CoroutineScope(viewModelJob).launch {
            systemConfigRepository.saveProperty("TimerInterval", timerInterval)
        }
    }

    fun getTimerEnableFromDB():String{
        return systemConfigRepository.getPropertyValuebyPropertyName("TimerEnable")
    }

    fun saveTimerEnableToDB(timerEnable:String){
        CoroutineScope(viewModelJob).launch {
            systemConfigRepository.saveProperty("TimerEnable", timerEnable)
        }
    }

    fun getUserNameFromDB():String{
        return systemConfigRepository.getPropertyValuebyPropertyName("UserName")
    }

    fun saveUserNameToDB(timerInterval:String){
        CoroutineScope(viewModelJob).launch {
            systemConfigRepository.saveProperty("UserName", timerInterval)
        }
    }
    fun getPasswordFromDB():String{
        return AESEncryption.decrypt( systemConfigRepository.getPropertyValuebyPropertyName("Password")).toString()
    }

    fun savePasswordToDB(timerInterval:String){
        CoroutineScope(viewModelJob).launch {
            systemConfigRepository.saveProperty("Password", timerInterval)
        }
    }
}