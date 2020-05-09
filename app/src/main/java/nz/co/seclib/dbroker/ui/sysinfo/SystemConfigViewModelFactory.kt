package nz.co.seclib.dbroker.ui.sysinfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nz.co.seclib.dbroker.data.DBrokerRoomDatabase
import nz.co.seclib.dbroker.data.SystemConfigRepository

class SystemConfigViewModelFactory(var application: Application): ViewModelProvider.Factory  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SystemConfigViewModel::class.java)) {
            return SystemConfigViewModel(
                systemConfigRepository = SystemConfigRepository(
                    dbBrokerDao = DBrokerRoomDatabase.getDatabase(application).dbrokerDAO()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}