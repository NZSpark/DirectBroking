package nz.co.seclib.dbroker.data.model

import android.R
import android.view.View
import android.widget.ListView
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jsoup.Jsoup

@Entity (
    tableName = "UserInfo"
)
data class UserInfo (
    @PrimaryKey
    val userID : String,
    val userName : String,
    val password : String
)


@Entity (
    tableName = "UserStock",
    primaryKeys = arrayOf("userID","stockCode")
)
data class UserStock(
    val userID : String,
    val stockCode : String
)

//https://www.directbroking.co.nz/DirectTrade/secure/orders.aspx?id=4919245&a=history
@Entity (
    tableName = "OrderInfo"
)
data class OrderInfo(
    val userID:String,
    val stockCode: String,
    val remainning:String,
    val placedTime:String,
    val expiresTime:String,
    val refCode: String,
    @PrimaryKey
    val orderID: String
)

@Entity (
    tableName = "AccountInfo",
    primaryKeys = arrayOf("userID","description")
)
data class AccountInfo(
    var userID:String = "",
    var description :String = "",
    var curreny:String = "",
    var balance:String = "",
    var interestRate:String = "",
    var withdrawalBalance:String = "",
    var tradeingBalance:String = "",
    var reservedFunds:String = "",
    var unClearedFunds:String = "",
    var outStandingBuySettlements:String = "",
    var overDueBuySettlement:String = ""
){
    private fun processAccounts(htmlData: String) : List<AccountInfo> {
        val accountInfoList = mutableListOf<AccountInfo>()
        
        val document = Jsoup.parse(htmlData)
        val tableRows = document.select("table[id=CAccountListTable] tr")

        // The first two rows contain unwanted information 
        tableRows.removeAt(0)
        tableRows.removeAt(1)
        
        for (row in tableRows) {
            val accountInfo = AccountInfo()
            val tds = row.getElementsByTag("td")
            println("DEBUG: " + tds.size)
            if (tds.size < 4) continue
            var currency: String
            var balance: String
            var rate: String
            var name: String
            name = tds.first().text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            currency = tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            balance = tds[2].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            rate = tds[3].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            accountInfo.description = name
            accountInfo.curreny = curreny
            accountInfo.balance = balance
            accountInfo.interestRate = rate
            accountInfoList.add(accountInfo)
        }
        return accountInfoList
    }
}

@Entity (
    tableName = "Portfolio",
    primaryKeys = arrayOf("userID","stockCode")
)
data class Portfolio(
    var userID:String = "",
    var stockCode: String = "",
    var quantity:String = "",
    var costPrice:String = "",
    var costValue:String = "",
    var marketPrice:String= "",
    var marketValue:String= "",
    var unRealised:String= ""
){
    
    private fun processPortfolio(htmlData: String) : List<Portfolio> {
        val portfolioList = mutableListOf<Portfolio>()
        
        val document = Jsoup.parse(htmlData)
        val tableRows = document.select("table[id=PortfolioPositionsTable] tr")
        
        tableRows.removeAt(0)
        
        println("DEBUG: " + tableRows.size)
        for (row in tableRows) {
            var portfolio = Portfolio()
            val tds = row.getElementsByTag("td")
            println("DEBUG: " + tds.size)
            if (tds.size < 4) continue

            portfolio.stockCode =
                tds.first().text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            
            if (portfolio.stockCode.contentEquals("Code")) {
                println("skipping title row")
                continue
            }
            if (portfolio.stockCode.contentEquals("NZD Subtotal") || portfolio.stockCode.contains("AUD Subtotal")) {
                println("NZD Subtotal")
                portfolio.marketValue = tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                val td = tds[3]
                println("td html: " + td.html())
                val imgDoc = Jsoup.parse(td.html())
                val img = imgDoc.select("img").first()
                val profit = img.attr("alt")
                println("profit: $profit")
                if (profit.contentEquals("Up")) {
                    portfolio.marketPrice = tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                } else {
                    portfolio.marketPrice =
                        "-" + tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                }
                portfolio.quantity = ""
                portfolio.costPrice = ""
                portfolio.unRealised =""
                
                portfolioList.add(portfolio)
                continue
            }
            if (portfolio.stockCode.contentEquals("Total")) {
                portfolio.marketValue =
                    tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                portfolio.marketPrice =
                    tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                portfolio.quantity = ""
                portfolio.costPrice =""
                portfolio.unRealised =""
                
                portfolioList.add(portfolio)
                break
            }

            portfolio.quantity =
                tds[2].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            portfolio.costPrice =
                tds[3].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            portfolio.marketPrice =
                tds[5].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            portfolio.marketValue =
                tds[6].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            portfolio.unRealised =
                tds[7].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
            
            portfolioList.add(portfolio)
        }
        return portfolioList
    }
}
