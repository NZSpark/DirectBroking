package nz.co.seclib.dbroker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nz.co.seclib.dbroker.data.AsksRow
import nz.co.seclib.dbroker.data.BidsRow
import nz.co.seclib.dbroker.data.TradesRow
import java.lang.reflect.Type
import java.sql.Date


class StockMarketInfo {
    //val usrStockCodeList = mutableListOf("ABA","AIA","AIR","ANZ","ATM","CEN", "KMD","FPH","FBU","GMT","MCY", "MEL","MET","MFT","OCA","RYM","RBD","SAN","SKC","SPK","SUM","VHP","ZEL")
    val usrStockCodeList = mutableListOf("ABA","AIA")

    val stockCodeList = listOf("ABA","AFC","AFI","AFT","AGG","AIA","AIR","ALF","AMP","ANZ","AOR","APA","APL","ARB","ARG","ARV","ASD","ASF","ASP","ASR","ATM","AUG","AWF","BFG","BGI","BGP","BIT","BLT","BOT","BRM","CAV","CBD","CDI","CEN","CGF","CMO","CNU","CO2","CRP","CVT","DGL","DIV","DOW","EBO","EMF","EMG","ENS","ENSRB","ERD","ESG","EUF","EUG","EVO","FBU","FCT","FIN","FNZ","FPH","FRE","FSF","FWL","GBF","GEN","GENWB","GEO","GFL","GMT","GNE","GSH","GTK","GXH","HFL","HGH","HLG","IFT","IKE","IPL","JLG","JPG","JPN","KFL","KFLWF","KMD","KPG","LIC","LIV","MCK","MCKPA","MCY","MDZ","MEE","MEL","MET","MFT","MGL","MHJ","MLN","MLNWD","MMH","MOA","MPG","MWE","MZY","NPF","NPH","NTL","NTLOB","NWF","NZB","NZC","NZK","NZM","NZO","NZR","NZX","OCA","OZY","PCT","PCTHA","PEB","PFI","PGW","PIL","PLP","PLX","POT","PPH","PYS","QEX","RAK","RBD","RYM","SAN","SCL","SCT","SCY","SDL","SEA","SEK","SKC","SKL","SKO","SKT","SML","SNC","SNK","SPG","SPK","SPN","SPY","SRF","STU","SUM","TCL","TEM","TGG","THL","TLL","TLS","TLT","TNZ","TPW","TRA","TRS","TRU","TWF","TWR","USA","USF","USG","USM","USS","USV","VCT","VGL","VHP","VTL","WBC","WDT","WHS","ZEL")

    val stockCompanyList = listOf("Abano Healthcare Group Limited" ,
            "AFC Group Holdings Limited" ,
            "Australian Foundation Investment Company Limited" ,
            "AFT Pharmaceuticals Limited" ,
            "Smartshares Global Aggregate Bond ETF" ,
            "Auckland International Airport Limited" ,
            "Air New Zealand Limited (NS)" ,
            "Allied Farmers Limited" ,
            "AMP Limited" ,
            "Australia and New Zealand Banking Group Limited" ,
            "Aorere Resources Limited" ,
            "Smartshares Asia Pacific ETF" ,
            "Asset Plus Limited" ,
            "ArborGen Holdings Limited" ,
            "Argosy Property Limited" ,
            "Arvida Group Limited" ,
            "Smartshares Australian Dividend ETF" ,
            "Smartshares Australian Financials ETF" ,
            "Smartshares Australian Property ETF" ,
            "Smartshares Australian Resources ETF" ,
            "The a2 Milk Company Limited" ,
            "Augusta Capital Limited" ,
            "AWF Madison Group Limited" ,
            "Burger Fuel Group Limited" ,
            "Blackwell Global Holdings Limited" ,
            "Briscoe Group Limited" ,
            "The Bankers Investment Trust Plc" ,
            "Blis Technologies Limited" ,
            "Smartshares Automation and Robotics ETF" ,
            "Barramundi Limited" ,
            "Cavalier Corporation Limited" ,
            "Cannasouth Limited" ,
            "CDL Investments New Zealand Limited" ,
            "Contact Energy Limited" ,
            "Cooks Global Foods Limited" ,
            "The Colonial Motor Company Limited" ,
            "Chorus Limited (NS)" ,
            "Carbon Fund" ,
            "Chatham Rock Phosphate Limited" ,
            "Comvita Limited" ,
            "Delegat Group Limited" ,
            "Smartshares NZ Dividend ETF" ,
            "Downer EDI Limited" ,
            "Ebos Group Limited" ,
            "Smartshares Emerging Markets ETF" ,
            "Smartshares Emerging Markets Equities ESG ETF" ,
            "Enprise Group Limited" ,
            "Enprise Group Limited" ,
            "EROAD Limited" ,
            "Smartshares Global Equities ESG ETF" ,
            "Smartshares Europe ETF" ,
            "Smartshares Europe Equities ESG ETF" ,
            "Evolve Education Group Limited" ,
            "Fletcher Building Limited" ,
            "F&C Investment Trust PLC" ,
            "Finzsoft Solutions Limited" ,
            "Smartshares NZ Top 50 ETF" ,
            "Fisher & Paykel Healthcare Corporation Limited" ,
            "Freightways Limited" ,
            "Fonterra Shareholders' Fund (NS)" ,
            "Foley Wines Limited" ,
            "Smartshares Global Bond ETF" ,
            "General Capital Limited" ,
            "General Capital Limited" ,
            "Geo Limited" ,
            "Geneva Finance Limited" ,
            "Goodman Property Trust (NS)" ,
            "Genesis Energy Limited (NS)" ,
            "Good Spirits Hospitality Limited" ,
            "Gentrack Group Limited" ,
            "Green Cross Health Limited" ,
            "Henderson Far East Income Limited" ,
            "Heartland Group Holdings Limited" ,
            "Hallenstein Glasson Holdings Limited" ,
            "Infratil Limited" ,
            "ikeGPS Group Limited" ,
            "Investore Property Limited (NS)" ,
            "Just Life Group Limited" ,
            "JPMorgan Global Growth & Income plc" ,
            "Smartshares Japan Equities ESG ETF" ,
            "Kingfish Limited" ,
            "Kingfish Limited" ,
            "Kathmandu Holdings Limited" ,
            "Kiwi Property Group Limited" ,
            "Livestock Improvement Corporation Limited (NS)" ,
            "Smartshares Healthcare Innovation ETF" ,
            "Millennium & Copthorne Hotels New Zealand Limited" ,
            "Millennium & Copthorne Hotels New Zealand Limited" ,
            "Mercury NZ Limited (NS)" ,
            "Smartshares NZ Mid Cap ETF" ,
            "Me Today Limited" ,
            "Meridian Energy Limited (NS)" ,
            "Metlifecare Limited" ,
            "Mainfreight Limited" ,
            "Mercer Group Limited" ,
            "Michael Hill International Limited" ,
            "Marlin Global Limited" ,
            "Marlin Global Limited" ,
            "Marsden Maritime Holdings Limited (NS)" ,
            "Moa Group Limited" ,
            "Metro Performance Glass Limited" ,
            "Marlborough Wine Estates Group Limited" ,
            "Smartshares Australian Mid Cap ETF" ,
            "Smartshares NZ Property ETF" ,
            "Napier Port Holdings Limited" ,
            "New Talisman Gold Mines Limited" ,
            "New Talisman Gold Mines Limited" ,
            "NZ Windfarms Limited" ,
            "Smartshares NZ Bond ETF" ,
            "Smartshares NZ Cash ETF" ,
            "New Zealand King Salmon Investments Limited" ,
            "NZME Limited" ,
            "New Zealand Oil & Gas Limited" ,
            "The New Zealand Refining Company Limited" ,
            "NZX Limited" ,
            "Oceania Healthcare Limited" ,
            "Smartshares Australian Top 20 ETF" ,
            "Precinct Properties New Zealand Limited" ,
            "Precinct Properties New Zealand Limited" ,
            "Pacific Edge Limited" ,
            "Property for Industry Limited" ,
            "PGG Wrightson Limited" ,
            "Promisia Integrative Limited" ,
            "Private Land and Property Fund" ,
            "Plexure Group Limited" ,
            "Port of Tauranga Limited" ,
            "Pushpay Holdings Limited" ,
            "PaySauce Limited" ,
            "QEX Logistics Limited" ,
            "Rakon Limited" ,
            "Restaurant Brands New Zealand Limited" ,
            "Ryman Healthcare Limited" ,
            "Sanford Limited (NS)" ,
            "Scales Corporation Limited" ,
            "Scott Technology Limited" ,
            "Smiths City Group Limited" ,
            "Solution Dynamics Limited" ,
            "SeaDragon Limited" ,
            "Seeka Limited" ,
            "SkyCity Entertainment Group Limited (NS)" ,
            "Skellerup Holdings Limited" ,
            "Serko Limited" ,
            "Sky Network Television Limited" ,
            "Synlait Milk Limited (NS)" ,
            "Southern Charter Financial Group Limited" ,
            "Snakk Media Limited (in liquidation)" ,
            "Stride Property Ltd & Stride Investment Management Ltd (NS)" ,
            "Spark New Zealand Limited" ,
            "South Port New Zealand Limited" ,
            "Smartpay Holdings Limited" ,
            "Senior Trust Retirement Village Listed Fund" ,
            "Steel & Tube Holdings Limited" ,
            "Summerset Group Holdings Limited" ,
            "The City of London Investment Trust Plc" ,
            "Templeton Emerging Markets Plc" ,
            "T&G Global Limited" ,
            "Tourism Holdings Limited" ,
            "TIL Logistics Group Limited" ,
            "Telstra Corporation Limited" ,
            "Tilt Renewables Limited" ,
            "Smartshares NZ Top 10 ETF" ,
            "Trustpower Limited" ,
            "Turners Automotive Group Limited" ,
            "TRS Investments Limited" ,
            "TruScreen Limited" ,
            "Smartshares Total World ETF" ,
            "Tower Limited" ,
            "Smartshares US Equities ESG ETF" ,
            "Smartshares US 500 ETF" ,
            "Smartshares US Large Growth ETF" ,
            "Smartshares US Mid Cap ETF" ,
            "Smartshares US Small Cap ETF" ,
            "Smartshares US Large Value ETF" ,
            "Vector Limited" ,
            "Vista Group International Limited" ,
            "Vital Healthcare Property Trust" ,
            "Vital Limited" ,
            "Westpac Banking Corporation" ,
            "Wellington Drive Technologies Limited" ,
            "The Warehouse Group Limited" ,
            "Z Energy Limited")
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

class StockScreenInfo{
    var stockCode = ""
    var companyName = ""
    var price = ""
    var change = ""
    var volume = ""
    var value = ""
    var marketCap = ""
    var tradeNumber = ""
    var infoTime = ""
}


@Entity(
        tableName = "TradeLog"
)
data class TradeLog(
        @PrimaryKey(autoGenerate = true)
        var id : Int,
        val stockCode:String,
        val tradeVolume : String,
        var tradeTime : String, // need to be patched with date.
        val price : String,
        val tradeCondition: String
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

