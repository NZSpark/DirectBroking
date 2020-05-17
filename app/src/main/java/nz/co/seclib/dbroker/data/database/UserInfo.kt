package nz.co.seclib.dbroker.data.database

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
    var userID:String = "",
    var stockCode: String = "",
    var remainning:String = "",
    var placedTime:String = "",
    var expiresTime:String = "",
    var status:String = "",
    var refCode: String = "",
    @PrimaryKey
    var orderID: String = ""
){
    companion object {
        fun getOrderInfoListFromString(htmlData: String): List<OrderInfo> {
            val orderInfoList = mutableListOf<OrderInfo>()

            val document = Jsoup.parse(htmlData)
            val tableRows = document.select("table[class=dgtbl] tr")
            if(tableRows.size > 2){
                tableRows.removeAt(0)
                tableRows.removeAt(1)
            }

//<tr class="dgitTR" onmouseover="Highlight(this);" onmouseout="UnHighlight(this);">
//   <td align="Left" class="dgitfirstcolumn">
//            <Table border="0" cellpadding="0" cellspacing="0">
//            <tr class="dgTRnobdr">
//            <td></td>
//            <td></td>
//            <td></td>
//            </tr>
//            </Table>
//   </td>
//            <td><a href='../dynamic/quote.aspx?qqsc=KMD&amp;qqe=NZSE'>KMD.NZ</a></td>
//            <td class='ask' nowrap="nowrap">SELL 4,000 at 129</td>
//            <td nowrap="nowrap">12/05 10:37</td>
//            <td align="Right">0</td>
//            <td nowrap="nowrap" align="Center">09/06</td>
//            <td nowrap="nowrap">Limit Order</td>
//            <td nowrap="nowrap">D656814</td>
//            <td nowrap="nowrap" align="Right" class="dgitlastcolumn">
//            <Table border="0" cellpadding="0" cellspacing="0" id="OrdersEditOptions">
//            <tr class="dgTRnobdr">
//            <td><a href='../secure/orders.aspx?id=4928903&a=history'>[history]</a></td>
//            <td>
//            <a href='#' onclick='return confirmcancel("KMD.NZ SELL 4,000 at 129","../secure/orders.aspx?id=4928903&a=cancel")'>[cancel]</a>
//            </td>
//            <td><a href='../secure/order.aspx?id=4928903'>[edit]</a></td>
//            </tr>
//            </Table>
//            </td>
// </tr>
            for (row in tableRows) {
                val orderInfo = OrderInfo()
                val tds = row.getElementsByTag("td")
                println("DEBUG: " + tds.size)
                if (tds.size < 12) continue

                orderInfo.stockCode = tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                orderInfo.remainning = tds[5].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                orderInfo.placedTime = tds[6].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                orderInfo.expiresTime = tds[8].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                orderInfo.status = tds[9].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                orderInfo.refCode = tds[10].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                val tmp = tds[11].html()
                if(tmp.indexOf("id=") > 0) {
                    val startPos = tmp.indexOf("aspx?id=") + 8
                    val endPos = tmp.indexOf("&", startPos)
                    orderInfo.orderID = tmp.substring(startPos,endPos)
                }

                orderInfoList.add(orderInfo)
            }

            return orderInfoList
        }
    }
}

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
    companion object {
        fun getAccountInfoListFromString(htmlData: String): List<AccountInfo> {

            val accountInfoList = mutableListOf<AccountInfo>()

            val document = Jsoup.parse(htmlData)
            val tableRows = document.select("table[id=CAccountListTable] tr")

            // The first two rows contain unwanted information
            if(tableRows.size > 2) {
                tableRows.removeAt(0)
                tableRows.removeAt(1)
            }
            //<tr class="dgitTR" onmouseover="Highlight(this);" onmouseout="UnHighlight(this);">
            // <td nowrap class="dgitfirstcolumn cadescription"><a href="../secure/accounts.aspx?view=txn&amp;ac=.CMT.NZD">Cash Management</a></td>
            // <td align="right" nowrap class="caccy">NZD</td>
            // <td align="right" nowrap class="cabalance"><b>5,670.30 CR</b></td>
            // <td align="right" nowrap class="cainterest">0.25% p.a.&nbsp;</td>
            // <td align="right" nowrap class="careservedfunds">0.00</td>
            // <td align="right" nowrap class="caunclearedfunds">0.00</td>
            // <td align="right" nowrap class="cawithdrawalbalance"><a href="../secure/withdrawalbalance.aspx?ac=.CMT.NZD">5,670.30</a></td>
            // <td align="right" nowrap class="dgitlastcolumn catradingbalance"><a href="../secure/tradingbalance.aspx?ac=.CMT.NZD">11,760.40</a></td>
            //</tr>
            for (row in tableRows) {
                val accountInfo =
                    AccountInfo()
                val tds = row.getElementsByTag("td")
                println("DEBUG: " + tds.size)
                if (tds.size < 7) continue

                accountInfo.description = tds.first().text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.curreny = tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.balance = tds[2].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.interestRate = tds[3].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.reservedFunds = tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.unClearedFunds = tds[5].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.withdrawalBalance = tds[6].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                accountInfo.tradeingBalance = tds[7].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }

                accountInfoList.add(accountInfo)
            }
            return accountInfoList
        }
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
    companion object {
        fun getPortfolioListFromString(htmlData: String): List<Portfolio> {
            val portfolioList = mutableListOf<Portfolio>()

            val document = Jsoup.parse(htmlData)
            val tableRows = document.select("table[id=PortfolioPositionsTable] tr")

            if(tableRows.size > 1)
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
                if (portfolio.stockCode.contentEquals("NZD Subtotal") || portfolio.stockCode.contains(
                        "AUD Subtotal"
                    )
                ) {
                    println("NZD Subtotal")
                    portfolio.marketValue =
                        tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                    val td = tds[3]
                    println("td html: " + td.html())
                    val imgDoc = Jsoup.parse(td.html())
                    val img = imgDoc.select("img").first()
                    val profit = img.attr("alt")
                    println("profit: $profit")
                    if (profit.contentEquals("Up")) {
                        portfolio.marketPrice =
                            tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                    } else {
                        portfolio.marketPrice =
                            "-" + tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                    }
                    portfolio.quantity = ""
                    portfolio.costPrice = ""
                    portfolio.unRealised = ""

                    portfolioList.add(portfolio)
                    continue
                }
                if (portfolio.stockCode.contentEquals("Total")) {
                    portfolio.marketValue =
                        tds[1].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                    portfolio.marketPrice =
                        tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                    portfolio.quantity = ""
                    portfolio.costPrice = ""
                    portfolio.unRealised = ""

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
}





