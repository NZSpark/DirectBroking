package nz.co.seclib.dbroker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.co.seclib.dbroker.data.repository.UserInfoRepository
import nz.co.seclib.dbroker.data.database.AccountInfo
import nz.co.seclib.dbroker.data.database.OrderInfo
import nz.co.seclib.dbroker.data.database.Portfolio
import nz.co.seclib.dbroker.data.database.TradeRecords

class UserInfoViewModel (private val  userInfoRepository : UserInfoRepository): ViewModel() {
    private val _portfolioList = MutableLiveData<List<Portfolio>>()
    val portfolioList : LiveData<List<Portfolio>> = _portfolioList

    private val _ordersList = MutableLiveData<List<OrderInfo>>()
    val ordersList : LiveData<List<OrderInfo>> = _ordersList

    private val _accountList = MutableLiveData<List<AccountInfo>>()
    val accountList : LiveData<List<AccountInfo>> = _accountList

    private val _tradeRecordsList = MutableLiveData<List<TradeRecords>>()
    val tradeRecordsList : LiveData<List<TradeRecords>> = _tradeRecordsList

//    init{
//        viewModelScope.launch(Dispatchers.IO) {
//            userInfoRepository.setNetWortConfidential()
//            _portfolioList.postValue(userInfoRepository.getPortfolioList())
//        }
//    }

    fun getPortfolioList() = viewModelScope.launch(Dispatchers.IO) {
        _portfolioList.postValue(userInfoRepository.getPortfolioList())
    }

    fun getOrderInfoList()= viewModelScope.launch(Dispatchers.IO) {
        _ordersList.postValue(userInfoRepository.getOrderInfoList())
    }

    fun getTradeRecordsList()= viewModelScope.launch(Dispatchers.IO) {
        _tradeRecordsList.postValue(userInfoRepository.getTradeRecordsList())
    }

    fun getAccountInfoList()= viewModelScope.launch(Dispatchers.IO) {
        _accountList.postValue(userInfoRepository.getAccountInfoList())
    }

    fun actionWithdraw(withdraAmount:String)= viewModelScope.launch(Dispatchers.IO){
        userInfoRepository.actionWithdraw(withdraAmount)
    }

    fun actionBuy(stockCode:String,price:String,quantity:String,type:String,date:String)= viewModelScope.launch(Dispatchers.IO){
        userInfoRepository.actionBuy(stockCode,price,quantity,type,date)
    }

    fun actionSell(stockCode:String,price:String,quantity:String,type:String,date:String)= viewModelScope.launch(Dispatchers.IO){
        userInfoRepository.actionSell(stockCode,price,quantity,type,date)
    }

    fun getCurrentPrice(stockCode:String):String {
        return userInfoRepository.getCurrentPrice(stockCode)
    }

    fun actionRequestUrl(url:String)= viewModelScope.launch(Dispatchers.IO){
        userInfoRepository.actionRequestUrl(url)
    }
}