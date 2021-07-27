package nz.co.seclib.dbroker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nz.co.seclib.dbroker.data.webdata.AsksRow
import nz.co.seclib.dbroker.data.webdata.BidsRow
import nz.co.seclib.dbroker.data.webdata.TradesRow
import org.jsoup.Jsoup
import java.lang.reflect.Type
import java.sql.Date


class StockMarketInfo {

    val stockCodeList = listOf("ABA","AFC","AFI","AFT","AGG","AIA","AIR","ALF","AMP","ANZ","AOR","APA","APL","ARB","ARG","ARV","ASD","ASF","ASP","ASR","ATM","AUG","AWF","BFG","BGI","BGP","BIT","BLT","BOT","BRM","CAV","CBD","CDI","CEN","CGF","CMO","CNU","CO2","CRP","CVT","DGL","DIV","DOW","EBO","EMF","EMG","ENS","ENSRB","ERD","ESG","EUF","EUG","EVO","FBU","FCT","FIN","FNZ","FPH","FRE","FSF","FWL","GBF","GEN","GENWB","GEO","GFL","GMT","GNE","GSH","GTK","GXH","HFL","HGH","HLG","IFT","IKE","IPL","JLG","JPG","JPN","KFL","KFLWF","KMD","KPG","LIC","LIV","MCK","MCKPA","MCY","MDZ","MEE","MEL","MET","MFT","MGL","MHJ","MLN","MLNWD","MMH","MOA","MPG","MWE","MZY","NPF","NPH","NTL","NTLOB","NWF","NZB","NZC","NZK","NZM","NZO","NZR","NZX","OCA","OZY","PCT","PCTHA","PEB","PFI","PGW","PIL","PLP","PLX","POT","PPH","PYS","QEX","RAK","RBD","RYM","SAN","SCL","SCT","SCY","SDL","SEA","SEK","SKC","SKL","SKO","SKT","SML","SNC","SNK","SPG","SPK","SPN","SPY","SRF","STU","SUM","TCL","TEM","TGG","THL","TLL","TLS","TLT","TNZ","TPW","TRA","TRS","TRU","TWF","TWR","USA","USF","USG","USM","USS","USV","VCT","VGL","VHP","VTL","WBC","WDT","WHS","ZEL")
}

class StockInfo {
    var stockCode = ""
    var companyName = ""
    var price = ""
    var change = ""
    var volume = ""
    var value = ""
    var capitalisation =""
    var infoTime = ""
}

class StockCurrentTradeInfo{
    var stockCode = ""
    var companyName = ""
    var price = ""
    var change = ""
    var sVWAP = ""
    var sBuy = ""
    var sSell = ""
    var sHigh =""
    var sLow = ""
    var sFirst = ""
    var volume = ""
    var value = ""
    var infoTime = ""
    var pictLink = ""
}

class StockScreenInfo {
    var stockCode = ""
    var companyName = ""
    var changeValue = ""
    var changePercent = ""
    var price = ""
    var value = ""
    var volume = ""
    var marketCap = ""
    var tradeNumber = ""
    var infoTime = ""

    /*
    <tr class="dgitTR" onmouseover="Highlight(this);" onmouseout="UnHighlight(this);">
        <td nowrap="nowrap" class="dgitfirstcolumn">FISHERHEALTH</td>
        <td><a href='../dynamic/quote.aspx?qqsc=FPH&amp;qqe=NZSE'>FPH.NZ</a></td>
        <td align="Right">75</td>
        <TD><img src="../images/spacer.gif" alt="Down" class="downarrow" width="15" height="10"></TD>
        <TD>2.5%</TD>
        <td align="Right"><b>2905</b></td>
        <td align="Right">$12,531,117</td>
        <td align="Right">429,727</td>
        <td align="Right">16.69B</td>
        <td align="Right" class="dgitlastcolumn">3,214</td>
    </tr>
     */

    companion object {
        fun convertScreenInfoListToStockCurrentTradeInfoList(stockScreenInfoList: List<StockScreenInfo>): List<StockCurrentTradeInfo> {

            val stockCurrentTradeInfoList = mutableListOf<StockCurrentTradeInfo>()
            stockScreenInfoList.forEach { stockScreenInfo ->
                val stockCurrentTradeInfo =
                    StockCurrentTradeInfo()
                stockCurrentTradeInfo.stockCode = stockScreenInfo.stockCode
                stockCurrentTradeInfo.companyName = stockScreenInfo.companyName
                stockCurrentTradeInfo.change = stockScreenInfo.changePercent
                stockCurrentTradeInfo.price = stockScreenInfo.price
                stockCurrentTradeInfo.value = stockScreenInfo.value
                stockCurrentTradeInfo.volume = stockScreenInfo.volume
                stockCurrentTradeInfo.infoTime = stockScreenInfo.infoTime
                stockCurrentTradeInfoList.add(stockCurrentTradeInfo)
            }
            return stockCurrentTradeInfoList
        }

        fun getStockScreenInfoFromString(sIn: String): List<StockScreenInfo> {
            val stockScreenInfoList = mutableListOf<StockScreenInfo>()

            val document = Jsoup.parse(sIn)
            val tableRows = document.select("table[class=dgtbl] tr")

            // The first two rows contain unwanted information
//            tableRows.removeAt(0)
//            tableRows.removeAt(1)
//            tableRows.removeAt(2)

            for (i in 3..tableRows.size-1) {
                val row = tableRows[i]
                val stockScreenInfo =
                    StockScreenInfo()
                val tds = row.getElementsByTag("td")
                println("DEBUG: " + tds.size)
                if (tds.size < 8) continue

                stockScreenInfo.companyName =
                    tds.first().text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                stockScreenInfo.stockCode =
                    tds[1].text().replace("\u00a0".toRegex(), "").replace(".NZ", "").trim { it <= ' ' }
                stockScreenInfo.changeValue =
                    tds[2].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                val arrow = tds[3].html().indexOf("downarrow")
                stockScreenInfo.changePercent =
                    tds[4].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                if (arrow > 0) stockScreenInfo.changePercent = "-" + stockScreenInfo.changePercent
                stockScreenInfo.price =
                    tds[5].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                stockScreenInfo.value =
                    tds[6].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                stockScreenInfo.volume =
                    tds[7].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                stockScreenInfo.marketCap =
                    tds[8].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }
                stockScreenInfo.tradeNumber =
                    tds[9].text().replace("\u00a0".toRegex(), "").trim { it <= ' ' }

                stockScreenInfoList.add(stockScreenInfo)
            }

            return stockScreenInfoList
        }
    }
}


@Entity(
        tableName = "TradeLog"
)
data class TradeLog(
        @PrimaryKey(autoGenerate = true)
        var id : Int,
        var stockCode:String = "",
        var tradeVolume : String = "",
        var tradeTime : String = "", // need to be patched with date.
        var price : String = "",
        var tradeCondition: String = ""
)


@Entity(
        tableName = "StockDailyLog"
)
data class StockDailyLog(
        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val stockCode:String,
        val openPrice : String,
        val closePrice : String,
        val highPrice : String,
        val lowPrice : String,
        val change: String,
        var volume: String,
        var value : String,
        var date : Date
)

@Entity(
        tableName = "AskBidLog"
)
data class AskBidLog(
    @PrimaryKey(autoGenerate = true)
        var id : Int,
    val stockCode:String,

    val askList : List<AsksRow>,
    val bidList : List<BidsRow>,
    val tradeList : List<TradesRow>,
    var tradeTime : String // need to be patched with date.


)

class DataConverter {
    @TypeConverter
    fun fromBidList(bidList: List<BidsRow>?): String? {
        if (bidList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<BidsRow>?>() {}.getType()
        return gson.toJson(bidList, type)
    }

    @TypeConverter
    fun toBidList(bidListString: String?): List<BidsRow>? {
        if (bidListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken< List<BidsRow>?>() {}.getType()
        return gson.fromJson(bidListString, type)
    }
    @TypeConverter
    fun fromAskList(askList: List<AsksRow>?): String? {
        if (askList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<AsksRow>>() {}.getType()
        return gson.toJson(askList, type)
    }

    @TypeConverter
    fun toAskList(askListString: String?): List<AsksRow>? {
        if (askListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken< List<AsksRow>?>() {}.getType()
        return gson.fromJson(askListString, type)
    }

    @TypeConverter
    fun fromTradeList(tradeList: List<TradesRow>?): String? {
        if (tradeList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<TradesRow>>() {}.getType()
        return gson.toJson(tradeList, type)
    }

    @TypeConverter
    fun toTradeList(tradeListString: String?): List<TradesRow>? {
        if (tradeListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken< List<TradesRow>?>() {}.getType()
        return gson.fromJson(tradeListString, type)
    }
}

