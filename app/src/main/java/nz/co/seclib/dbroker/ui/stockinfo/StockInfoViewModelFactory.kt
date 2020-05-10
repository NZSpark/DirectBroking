package nz.co.seclib.dbroker.ui.stockinfo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import nz.co.seclib.dbroker.data.DBrokerRoomDatabase
import nz.co.seclib.dbroker.data.DirectBrokingWeb
import nz.co.seclib.dbroker.data.TradeLogRepository

class StockInfoViewModelFactory(var application: Application): ViewModelProvider.Factory  {
    companion object{
        var instance:StockInfoViewModel? = null
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockInfoViewModel::class.java)) {
            if(instance == null)
                instance = StockInfoViewModel(
                    tradeLogRepository = TradeLogRepository(
                        dbDao = DBrokerRoomDatabase.getDatabase(application).dbrokerDAO(),
                        dbWeb = DirectBrokingWeb()
                    )
                )
            return instance as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}