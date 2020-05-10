package nz.co.seclib.dbroker.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wordplat.ikvstockchart.entry.Entry
import com.wordplat.ikvstockchart.entry.EntrySet
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type
import java.net.CookieManager

class NZXWeb {
    //handle cookies by default with lib com.squareup.okhttp3:okhttp-urlconnection:4.2.2.
    private val cookieJar = JavaNetCookieJar(CookieManager())
    private val okClient = OkHttpClient.Builder().cookieJar(cookieJar).build()



    fun convertJsonToInterdayInfoList(inString:String) : List<NZXInterDayInfo> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<NZXInterDayInfo>?>() {}.getType()
        return gson.fromJson(inString, type)
    }

    //candle entry, 6 elements.
    fun copyInterDayInfoToChartEntrySet(interDayInfoList:List<NZXInterDayInfo>) : EntrySet{
        val entrySet = EntrySet()
        for (interDayInfo in interDayInfoList) {
            entrySet.addEntry(
                Entry(
                    interDayInfo.open_price,
                    interDayInfo.high_price,
                    interDayInfo.low_price,
                    interDayInfo.close_price,
                    interDayInfo.volume,
                    interDayInfo.date
                )
            )
        }
        return entrySet
    }

    //get stock history data from NZX against stock code.
    fun getInterDayJson(stockCode:String) : String {
        var jsonString = ""
        var url = "https://www.nzx.com/statistics/"+stockCode+"/interday.json"
        var request = Request.Builder()
            .url(url)
            .build()

        okClient.newCall(request).execute().use {
            jsonString = it.body?.string().toString()
            it.body?.close()
        }
        return jsonString
    }


    //timeline entry, only 3 elements.
    fun copyIntraDayInfoToChartEntrySet(intraDayInfoList:List<NZXIntraDayInfo>) : EntrySet{
        val entrySet = EntrySet()
        for (intraDayInfo in intraDayInfoList) {
            if( intraDayInfo.price < 0.001 ) continue
            entrySet.addEntry(
                Entry(
                    intraDayInfo.price,
                    intraDayInfo.volume,
                    intraDayInfo.time
                )
            )
        }
        return entrySet
    }

    fun convertJsonToIntradayInfoList(inString:String) : List<NZXIntraDayInfo> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<NZXIntraDayInfo>?>() {}.getType()
        return gson.fromJson(inString, type)
    }

    //get stock detailed day data from NZX against stock code.
    fun getIntraDayJson(stockCode:String) : String {
        var jsonString = ""
        var url = "https://www.nzx.com/statistics/"+stockCode+"/intraday.json?market_id=NZSX"
        var request = Request.Builder()
            .url(url)
            .build()

        okClient.newCall(request).execute().use {
            jsonString = it.body?.string().toString()
            it.body?.close()
        }
        return jsonString
    }
}