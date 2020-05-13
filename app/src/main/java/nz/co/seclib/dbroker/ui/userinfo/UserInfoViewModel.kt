package nz.co.seclib.dbroker.ui.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.FtsOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.co.seclib.dbroker.data.UserInfoRepository
import nz.co.seclib.dbroker.data.model.AccountInfo
import nz.co.seclib.dbroker.data.model.OrderInfo
import nz.co.seclib.dbroker.data.model.Portfolio

class UserInfoViewModel (private val  userInfoRepository : UserInfoRepository): ViewModel() {
    private val _portfolioList = MutableLiveData<List<Portfolio>>()
    val portfolioList : LiveData<List<Portfolio>> = _portfolioList

    private val _ordersList = MutableLiveData<List<OrderInfo>>()
    val ordersList : LiveData<List<OrderInfo>> = _ordersList

    private val _accountList = MutableLiveData<List<AccountInfo>>()
    val accountList : LiveData<List<AccountInfo>> = _accountList


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

    fun getAccountInfoList()= viewModelScope.launch(Dispatchers.IO) {
        _accountList.postValue(userInfoRepository.getAccountInfoList())
    }

}