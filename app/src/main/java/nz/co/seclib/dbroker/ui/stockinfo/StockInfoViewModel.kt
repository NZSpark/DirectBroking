package nz.co.seclib.dbroker.ui.stockinfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.*
import nz.co.seclib.dbroker.data.TradeLogRepository
import nz.co.seclib.dbroker.data.model.AskBidLog
import nz.co.seclib.dbroker.data.model.StockCurrentTradeInfo
import nz.co.seclib.dbroker.data.model.TradeLog
import nz.co.seclib.dbroker.data.model.UserStock
import nz.co.seclib.dbroker.utils.AESEncryption
import java.lang.Thread.sleep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import nz.co.seclib.dbroker.R
import com.wordplat.ikvstockchart.entry.*
import nz.co.seclib.dbroker.data.NZXWeb

@RequiresApi(Build.VERSION_CODES.O)
class StockInfoViewModel(private val tradeLogRepository:TradeLogRepository) : ViewModel(){
    //for TradeLogActivity ----begin
    private val _tradeLogList = MutableLiveData<List<TradeLog>>()
    val tradeLogList: LiveData<List<TradeLog>> = _tradeLogList
    //for TradeLogActivity ----end

    //for SelectedStocksActivity --begin
    private var userName = "UserID"
    private var password = ""
    private val _stockCurrentTradeInfoList = MutableLiveData<MutableList<StockCurrentTradeInfo>>()
    val stockCurrentTradeInfoList : LiveData<List<StockCurrentTradeInfo>> = _stockCurrentTradeInfoList as LiveData<List<StockCurrentTradeInfo>>
    //for SelectedStocksActivity --end

    //for StockInfoActivity ----begin
    private val _stockCurrentTradeInfo = MutableLiveData<StockCurrentTradeInfo>()
    val stockCurrentTradeInfo: LiveData<StockCurrentTradeInfo> = _stockCurrentTradeInfo
    private val _askBidLog = MutableLiveData<AskBidLog>()
    val askBidLog = _askBidLog
    //for StockInfoActivity ----end

    private val timer = Timer()
    private var bTimerEnable = false
    private var bTimerIdle = true
    private var timerInterval:Long = 30000

    private var stockCode = "KMD"

    private var bInitilized = false

    private var backupTradeLog = emptyList<TradeLog>()

    private val viewModelJob = SupervisorJob()

    //get parameters from database. (UserName, Password, TimerInterval, TimerEnable)
    fun initWithStockCode(inStockCode: String){
        if(inStockCode == "")
            stockCode = "KMD"
        else
            stockCode = inStockCode

        CoroutineScope(viewModelJob).launch {

            if (!bInitilized) {
                //get UserName
                userName = tradeLogRepository.getPropertyValuebyPropertyName("UserName")
                if(userName == "") userName="UserID"

                //get Password
                password = AESEncryption.decrypt(tradeLogRepository.getPropertyValuebyPropertyName("Password")).toString()

                //get TimeInterval
                timerInterval = tradeLogRepository.getPropertyValuebyPropertyName("TimerInterval").toLong()
                if (timerInterval < 5000) timerInterval = 30000  //must be larger than 5s.

                //get TimeEnable
                val sTimerEnable = tradeLogRepository.getPropertyValuebyPropertyName("TimerEnable")
                if (sTimerEnable== "TRUE")
                    bTimerEnable = true

                _stockCurrentTradeInfoList.postValue(mutableListOf<StockCurrentTradeInfo>())
                tradeLogRepository.setNetWortConfidential(userName,password)
            }

            //initial for SelectedStocksActivity
            // will update _stockCurrentTradeInfoList, _stockCurrentTradeInfo at the same time.
            updateSelectedStockListByUserID(userName)

            //initial for TradeLogActivity
            _tradeLogList.postValue(tradeLogRepository.getTodayTradeLog(stockCode).reversed())

            //initial for StockInfoActivity
            _stockCurrentTradeInfo.postValue(
                tradeLogRepository.getCurrentTradeInfoByStockCode(
                    stockCode
                )
            )
            _askBidLog.postValue(tradeLogRepository.getAskBidListByStockCode(stockCode))

            //only to start timer during the market trading time.
            if (bTimerEnable && bTimerIdle && checkMarketTradingTime()) {
                setTimerWithStockCode()
                bTimerIdle = false
            }
        }
    }


    fun resetTimer()= viewModelScope.launch(Dispatchers.IO) {
        //TODO
    }

    //get data from web and store data into database periodically.
    private fun setTimerWithStockCode(){
        //if(stockCode == "") return
        timer.scheduleAtFixedRate(
            object : TimerTask(){
                override fun run() {
                    CoroutineScope(viewModelJob).launch {
                        tradeLogRepository.storeTradeInfoFromWebToDBByStockCode(stockCode)
                        // _tradeLogList.postValue(tradeLogRepository.diffList)
                        _tradeLogList.postValue(tradeLogRepository.getTodayTradeLog(stockCode).reversed())
                        //_stockCurrentTradeInfo.postValue(tradeLogRepository.getCurrentTradeInfoByStockCode(stockCode))
                        updateSelectedStockListByUserID(userName)
                        _askBidLog.postValue(tradeLogRepository.getAskBidListByStockCode(stockCode))
                        //tradeLogRepository.diffList = emptyList()
                    }
                }
            },0,timerInterval
        )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    //add one item
    fun addNewTradeLog(tradeLog:TradeLog){
        val temp = mutableListOf<TradeLog>()
        temp.add(tradeLog)
        _tradeLogList.postValue(temp)
    }

    //add a collection of items
    fun addNewTradeLog(tradeLogList:List<TradeLog>){
        _tradeLogList.postValue(tradeLogList)
    }

    //for StockInfoActivity ---------------begin
    fun insertUserStock(stockCode: String) = viewModelScope.launch(Dispatchers.IO) {
        tradeLogRepository.insertUserStock(UserStock(userName,stockCode))
    }

    fun deleteUserStock(stockCode: String) = viewModelScope.launch(Dispatchers.IO) {
        tradeLogRepository.deleteUserStock(UserStock(userName,stockCode))
    }

    //update data by selected stocks list.
    private fun updateSelectedStockListByUserID(userName: String) =
        viewModelScope.launch(Dispatchers.IO) {
        val stockCurrentTradeInfoList = mutableListOf<StockCurrentTradeInfo>()
        val stockCodeList =  tradeLogRepository.selectStockCodeByUserID(userName)
        stockCodeList.forEach { newStockCode ->
            val currentTradeInfo = tradeLogRepository.getCurrentTradeInfoByStockCode(newStockCode)?:return@forEach
            stockCurrentTradeInfoList.add(currentTradeInfo)
            if(currentTradeInfo.stockCode == stockCode)
                _stockCurrentTradeInfo.postValue(currentTradeInfo)
        }
        _stockCurrentTradeInfoList.postValue(stockCurrentTradeInfoList)
    }
    //for StockInfoActivity ---------------end

    //market open time. from "09:45" to "17:15".
    private fun checkMarketTradingTime():Boolean{
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val openTime = "09:45"
        val closeTime = "17:15"
        if(currentTime > openTime && currentTime < closeTime)
            return true
        return false
    }
}