package nz.co.seclib.dbroker.data

import nz.co.seclib.dbroker.data.model.AccountInfo
import nz.co.seclib.dbroker.data.model.OrderInfo
import nz.co.seclib.dbroker.data.model.Portfolio
import nz.co.seclib.dbroker.utils.AESEncryption

class UserInfoRepository (private val dbDao: DBrokerDAO, private val dbWeb: DirectBrokingWeb) {

    private var mCurrentState: CurrentState? = null

    fun setNetWortConfidential(){
        dbWeb._username = getPropertyValuebyPropertyName("UserName")
        dbWeb._password = AESEncryption.decrypt(getPropertyValuebyPropertyName("Password")).toString()
    }

    fun actionRequestUrl(url:String) {
        dbWeb.getWebPageByUrl(url)
    }

    fun actionBuy(stockCode:String,price:String,quantity:String,type:String,date:String){
        dbWeb.actionOrder(stockCode,price,quantity,type,date,"buy")
    }

    fun actionSell(stockCode:String,price:String,quantity:String,type:String,date:String){
        dbWeb.actionOrder(stockCode,price,quantity,type,date,"sell")
    }

    fun getCurrentPrice(stockCode:String):String {
        val currentTradeInfo = dbWeb.getStockInfo(stockCode)
        return currentTradeInfo?.price.toString()
    }

    fun actionWithdraw(withdraAmount:String){
        dbWeb.actionWithdrawMoneyFromCashAccount(withdraAmount)
    }

    fun getOrderInfoList(): List<OrderInfo>{
        //if(!dbWeb.bAuthenticated)
        //    dbWeb.getLoginCookie( dbWeb._username, dbWeb._password )
        val url = "https://www.directbroking.co.nz/DirectTrade/secure/orders.aspx"
        val orderInfoWebPage = dbWeb.getWebPageByUrl(url)
        val orderInfoList = OrderInfo.getOrderInfoListFromString(orderInfoWebPage)
        return orderInfoList
    }

    fun getAccountInfoList(): List<AccountInfo>{
//        if(!dbWeb.bAuthenticated)
//            dbWeb.getLoginCookie( dbWeb._username, dbWeb._password )
        val url = "https://www.directbroking.co.nz/DirectTrade/secure/accounts.aspx?view=bal"
        val accountInfoWebPage = dbWeb.getWebPageByUrl(url)
        val accountInfoList = AccountInfo.getAccountInfoListFromString(accountInfoWebPage)
        return accountInfoList
    }

    fun getPortfolioList(): List<Portfolio>{
//        if(!dbWeb.bAuthenticated)
//            dbWeb.getLoginCookie( dbWeb._username, dbWeb._password )
        val url = "https://www.directbroking.co.nz/DirectTrade/secure/portfolios.aspx"
        val portfolioWebPage = dbWeb.getWebPageByUrl(url)
        val portfolioList = Portfolio.getPortfolioListFromString(portfolioWebPage)
        return portfolioList
    }


    fun getPropertyValuebyPropertyName(propertyName:String):String{
        val propertyList = dbDao.selectSystemConfigInfoByPropertyName(propertyName)
        if(propertyList.size == 0) return ""
        return propertyList[0].propertyValue.toString()
    }
}