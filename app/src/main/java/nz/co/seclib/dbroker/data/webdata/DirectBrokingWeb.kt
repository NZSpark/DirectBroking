package nz.co.seclib.dbroker.data.webdata

import nz.co.seclib.dbroker.data.database.StockCurrentTradeInfo
import okhttp3.*
import java.io.IOException
import java.net.CookieManager


class CurrentState  (var stockCode: String, var cookieJar:JavaNetCookieJar, var viewState:String, var viewValidation:String, var viewStateGenerator:String, var webPage:String) {
//    var _StockCode = stockCode
//    var _CookieJar = cookieJar
//    var _ViewState = viewState
//    var _ViewValidation = viewValidation
//    var _ViewStateGenerator = viewStateGenerator
//    var _WebPage = depthWebPage
}

class DirectBrokingWeb {

    
    //handle cookies by default with lib com.squareup.okhttp3:okhttp-urlconnection:4.2.2.
    private val cookieJar = JavaNetCookieJar(CookieManager())
    private val okClient = OkHttpClient.Builder().cookieJar(cookieJar).build()
    
    private var mCurrentState = CurrentState(
        "KMD",
        cookieJar,
        "",
        "",
        "",
        ""
    )
    
    var _username  = ""
    var _password  = ""
    var bAuthenticated = false

    //extract tables from string. 3 tables:BidsTable,AsksTable,RecentTradesTable, RankTable
    fun getTableString(sIn:String, tableName:String):String {
        var sTable:String = ""

        if(sIn.indexOf("quotedepth") < 0 ) return sTable

        var sTemp = sIn.substring(sIn.indexOf("quotedepth"))
        sTemp = sTemp.replace("&nbsp;"," ")
        var iTagStartPos = sTemp.indexOf("<TABLE")

        while (iTagStartPos > 0) {
            val iTagEndPos = sTemp.indexOf("</TABLE>", iTagStartPos) + 8
            if (iTagEndPos > 8) {
                sTable = sTemp.substring(iTagStartPos, iTagEndPos)
                if(tableName == "BidsTable" && sTable.indexOf("biddepth") > 0 ) break
                if(tableName == "AsksTable" && sTable.indexOf("askdepth") > 0 ) break
                if(tableName == "RecentTradesTable" && sTable.indexOf("tblRecentTrades") > 0 ) break
                sTemp = sTemp.substring(iTagEndPos)
            }
            iTagStartPos = sTemp.indexOf("<TABLE")
        }
        return sTable
    }

    //get Tag's value. <"SomeTag" value="SomeValue">
    fun getValueByTag(sIn:String?, sTag:String?):String {
        if(sIn == null || sTag == null) return ""
        if(sIn.length < sTag.length) return ""
        var iTagPos = sIn.indexOf("\""+sTag +"\"")
        if(iTagPos >=0 ){
            iTagPos += sTag.length
            val iValueStartPos = sIn.indexOf("value=\"",iTagPos) + 7  //7 -> length of "value="""
            if(iValueStartPos > 0 ){
                val iValueEndPos = sIn.indexOf("\"",iValueStartPos)
                if(iValueEndPos>0)
                    return sIn.substring(iValueStartPos,iValueEndPos)
            }

        }
        return ""
    }

    fun findStockTradeInfo(sIn:String): StockCurrentTradeInfo? {

        if(sIn.length < 15) return  null

        val stockTradeInfo =
            StockCurrentTradeInfo()
        if(sIn.indexOf("Price view end") < 0 ) return null
        //var sTemp = sIn.substring(sIn.indexOf("Price view start"),sIn.indexOf("Price view end"))
        var sTemp = sIn.substring(sIn.indexOf("lblSecurityName"),sIn.indexOf("Price view end"))
        sTemp = sTemp.replace("&nbsp;"," ")
        sTemp = sTemp.replace("&nbsp"," ")

        //0. extract company name
        var iTagStartPos = sTemp.indexOf("lblSecurityName")
        var iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        var iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.companyName = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //1. extract price
        iTagStartPos = sTemp.indexOf("quotelast") + 10
        iValueStartPos = sTemp.indexOf("\">",iTagStartPos) + 2
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.price = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //2.extract quotechangepercent
        iTagStartPos = sTemp.indexOf("quotechangepercent")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.change = sTemp.substring(iValueStartPos,iValueEndPos)
        if(sTemp.lastIndexOf("downarrow",iTagStartPos) > 0)
            stockTradeInfo.change = "-" + stockTradeInfo.change //decrease change
        sTemp = sTemp.substring(iValueEndPos+1)

        //3.extract quotelasttradedate
        iTagStartPos = sTemp.indexOf("quotelasttradedate")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.infoTime = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //4.extract VWAP
        iTagStartPos = sTemp.indexOf("quoteVWAP")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sVWAP = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //5.extract quotebuy
        iTagStartPos = sTemp.indexOf("quotebuy")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sBuy = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //6.extract quotesell
        iTagStartPos = sTemp.indexOf("quotesell")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sSell = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //7.extract quotehigh
        iTagStartPos = sTemp.indexOf("quotehigh")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sHigh = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //6.extract quotelow
        iTagStartPos = sTemp.indexOf("quotelow")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos) + 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sLow = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //7.extract quoteopen
        iTagStartPos = sTemp.indexOf("quoteopen")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos)+ 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.sFirst = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //8.extract quotevolume
        iTagStartPos = sTemp.indexOf("quotevolume")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos)+ 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.volume = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        //9.extract quotevolume
        iTagStartPos = sTemp.indexOf("quotevalue")
        iValueStartPos = sTemp.indexOf(">",iTagStartPos)+ 1
        iValueEndPos = sTemp.indexOf("<",iValueStartPos)
        stockTradeInfo.value = sTemp.substring(iValueStartPos,iValueEndPos)
        sTemp = sTemp.substring(iValueEndPos+1)

        val key = findCharUrl(sTemp)
        if (key.length > 0)
            stockTradeInfo.pictLink =  "https://www.directbroking.co.nz/DirectTrade/dynamic/chart.aspx?key=" + key


        return stockTradeInfo
    }

    fun findCharUrl(sIn:String?):String {
        val sTag = "/chart.aspx?key="

        if(sIn == null) return ""
        if(sIn.length < sTag.length) return ""

        var iTagPos = sIn.indexOf(sTag)
        if(iTagPos >=0 ){
            val iValueStartPos = sIn.indexOf("key=",iTagPos) + 4  //7 -> length of "value="""
            if(iValueStartPos > 0 ){
                val iValueEndPos = sIn.indexOf("\"",iValueStartPos)
                if(iValueEndPos>0)
                    return sIn.substring(iValueStartPos,iValueEndPos)
            }
        }
        return ""
    }

    fun getLoginCookie(username:String,password:String): Boolean {
        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/signon.aspx"

        var viewState = ""
        var viewValidation = ""
        var viewStateGenerator = "" //__VIEWSTATEGENERATOR

        fun run() {
            //stage 1: get login page state
            //------------------------------------------------------------
            // * Server will add time stamp, "rst=xxxxxx" , to each url, and the new url should be extracted.
            // * ASP.NET_SessionId should be extracted from response header.
            // * MACHINE_ID should be extracted from response header.
            // * __VIEWSTATE, __EVENTVALIDATION should be extracted from web page.
            //------------------------------------------------------------
            var request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                val body = it.body?.string()

                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //stage 2: get login authentication cookie
            //-----------------------------------------------
            // * ASP.NET_SessionId, MACHINE_ID should be set into request header.
            // * __VIEWSTATE, __EVENTVALIDATION should be added into request data.
            // * username, password should be added into request data.
            // * if authentication is successed, RolesBasedAthentication in set-cookie items can be found in response header.
            //-----------------------------------------------
            var formBody = FormBody.Builder()
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("username", username)
                .add("password", password)
                .add("LoginStartIn1:ddlStartin", "../secure/orders.aspx")
                .add("btnSignon", "Login")
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                if(mCurrentState.webPage.indexOf("Welcome") > 0) {
                    _username = username
                    _password = password
                    bAuthenticated = true
                }
                it.body?.close()
            }
        }
        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return bAuthenticated
    }

    //stockCode = "KMD"
    fun getDepthPage(stockCode:String) :String {
        val username = _username
        val password = _password

        //OkHttpClient will fetch moved web page automatically by default.
        //When to store authentication cookie items in response headers of a moved web page,
        //to set followRedirects option to false to turn off automatic fetch function.
        //val okClient2 = OkHttpClient.Builder().followRedirects(false).build()


        /*  --- for Burp Suite -----
        //"BuipSuiteCAcert.cer" is certificate provided by Burp Suite and it should be placed into assets folder.
        //Maybe it is not necessary since all certificates are trusted.
        val sslContextAndTrustManagers = SslUtils.getSslContextForCertificateFile("BuipSuiteCAcert.cer")
        val trustAllCerts=
            object : X509TrustManager  {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    return
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    return
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }

        okClient = OkHttpClient.Builder().cookieJar(cookieJar)
            .sslSocketFactory(sslContextAndTrustManagers.sslContext.socketFactory, trustAllCerts)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectionSpecs(
                Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS
                )
            )
            .build()

        val okClient2 = OkHttpClient.Builder()
            .followRedirects(false)
            .sslSocketFactory(sslContextAndTrustManagers.sslContext.socketFactory, trustAllCerts)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .connectionSpecs(
                Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS
                )
            )
            .build()
        */

        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/signon.aspx"

        var viewState = ""
        var viewValidation = ""
        var viewStateGenerator = "" //__VIEWSTATEGENERATOR
        var depthWebPage: String = ""

        fun run() {
            //stage 1: get login page state
            //------------------------------------------------------------
            // * Server will add time stamp, "rst=xxxxxx" , to each url, and the new url should be extracted.
            // * ASP.NET_SessionId should be extracted from response header.
            // * MACHINE_ID should be extracted from response header.
            // * __VIEWSTATE, __EVENTVALIDATION should be extracted from web page.
            //------------------------------------------------------------
            var request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                val body = it.body?.string()

                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //stage 2: get login authentication cookie
            //-----------------------------------------------
            // * ASP.NET_SessionId, MACHINE_ID should be set into request header.
            // * __VIEWSTATE, __EVENTVALIDATION should be added into request data.
            // * username, password should be added into request data.
            // * if authentication is successed, RolesBasedAthentication in set-cookie items can be found in response header.
            //-----------------------------------------------
            var formBody = FormBody.Builder()
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("username", username)
                .add("password", password)
                .add("LoginStartIn1:ddlStartin", "../secure/orders.aspx")
                .add("btnSignon", "Login")
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .addHeader("Connection","close")
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                it.body?.close()
            }

            //get login validation cookie: RolesBasedAthentication
            //getLoginCookie(_username,_password)

            //stage 3: get quote data
            //-----------------------------------------------
            // * ASP.NET_SessionId, MACHINE_ID, RolesBasedAthentication should be set into request header.
            // * __VIEWSTATE, __EVENTVALIDATION should be added into request data.
            // * quote page must be accessed first before to depth data.
            //-----------------------------------------------
            url = "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

            formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnGetQuote")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("ddlView", "depth")
                .add("btnGetQuote", "Go")
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //stage 4: get depth data
            //-----------------------------------------------
            // * ASP.NET_SessionId, MACHINE_ID, RolesBasedAthentication should be set into request header.
            // * __VIEWSTATE, __EVENTVALIDATION should be added into request data.
            // * quote page must be accessed first before to depth data.
            //-----------------------------------------------
            formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnGetQuote")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("ddlView", "depth")
                .add("btnGetQuote", "Go")
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }

        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()
        return mCurrentState.webPage
    }

    //get (rank page/screen page) by rank/sort type
    //type includs: "VALUE","VOLUME", "PERCENTCHANGE","MKTCAP" (Market Capital)
    fun getScreenPage(sortType : String) :String {

        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/securityviews.aspx?e=NZSE"

        var viewState = ""
        var viewValidation = ""
        var viewStateGenerator = ""

        mCurrentState.webPage = ""

        fun run(){
            var request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            val   formBody = FormBody.Builder()
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchange", "NZSE")
                .add("ddlSortBy", sortType)
                .add("ddlShow", "TOP")
                .add("ddllastprice", "0")
                .add("btnGo", "Go")
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }

        }

        if (viewState.length > -1) {
            val thread = Thread(Runnable { run() })
            thread.start()
            thread.join()
        }

        return mCurrentState.webPage
    }

    fun getLastDepthPage(stockCode:String) :String {
        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

        var viewState = mCurrentState.viewState
        var viewValidation = mCurrentState.viewValidation
        var viewStateGenerator = mCurrentState.viewStateGenerator
        mCurrentState.webPage = ""

        fun run(){
            //stage 4: get depth data
            val   formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnGetQuote")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("ddlView", "depth")
                .add("btnGetQuote", "Go")
                .add("txtSecurityCode", stockCode)
                .build()

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }

        }


        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return mCurrentState.webPage
    }


    /*
    fun getLastDepthPage(currentState: CurrentState) :CurrentState {
        val stockCode = currentState._StockCode
        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

        var viewState = currentState._ViewState
        var viewValidation = currentState._ViewValidation
        var viewStateGenerator = currentState._ViewStateGenerator

        var depthWebPage: String = ""

        fun run(){
            //stage 4: get depth data
            val   formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnGetQuote")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("ddlView", "depth")
                .add("btnGetQuote", "Go")
                .build()

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) throw  IOException("Unexpected code $it")
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                depthWebPage = body.toString()
                it.body?.close()
            }

        }

        if (viewState.length > -1) {
            val thread = Thread(Runnable { run() })
            thread.start()
            thread.join()
        }

        return CurrentState(stockCode,cookieJar,viewState,viewValidation,viewStateGenerator,depthWebPage)
    }
    */

    //convert web page to class.
    fun getStockInfo(stockCode: String): StockCurrentTradeInfo? {
        var stockCurrentTradeInfo : StockCurrentTradeInfo? = null

        val stockInfoPage = getStockInfoPage(stockCode)

        stockCurrentTradeInfo = findStockTradeInfo(stockInfoPage)
        stockCurrentTradeInfo?.stockCode = stockCode

        return stockCurrentTradeInfo
    }

    fun getStockInfoPage(stockCode: String) :String {

        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

//        var viewState = mCurrentState.viewState
//        var viewValidation = mCurrentState.viewValidation
//        var viewStateGenerator = mCurrentState.viewStateGenerator
        mCurrentState.webPage = ""

        fun run () {
            //stage 4: get depth data
            val   formBody = FormBody.Builder()
//                .add("__EVENTTARGET", "ddlView")
//                .add("__VIEWSTATE", viewState)
//                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
//                .add("__EVENTVALIDATION", viewValidation)
//                .add("ddlExchangeGroupCode", "NZ")
//                .add("ddlView", "basic")
                .build()

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }
        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return mCurrentState.webPage
    }

    /*
    fun getStockInfoByCurrentState(currentState: CurrentState) :CurrentState {
        val stockCode = currentState._StockCode
        var url = "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

        var viewState = currentState._ViewState
        var viewValidation = currentState._ViewValidation
        var viewStateGenerator = currentState._ViewStateGenerator

        var stockInfoWebPage: String = ""

        fun run () {
            //stage 4: get depth data
            val   formBody = FormBody.Builder()
                .add("__EVENTTARGET", "ddlView")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("ddlView", "basic")
                .build()

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) throw  IOException("Unexpected code $it")
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                stockInfoWebPage = body.toString()
                it.body?.close()
            }
        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return CurrentState(stockCode,cookieJar,viewState,viewValidation,viewStateGenerator,stockInfoWebPage)
    }
    */

    /*
    fun getStockInfoByStockCode(stockCode: String): StockCurrentTradeInfo? {
        var result : StockCurrentTradeInfo? = null

        fun run () {
            val url =
                "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    //throw  IOException("Unexpected code $it")
                    it.body?.close()
                    return
                }
                val body = it.body?.string().toString()

                result = findStockTradeInfo(body)
                result?.stockCode = stockCode

                it.body?.close()
            }
        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return result
    }
    */

    fun getStockPicture(stockCode: String):String{
        var result = ""

        fun run () {
            val url =
                "https://www.directbroking.co.nz/DirectTrade/dynamic/quote.aspx?qqsc=" + stockCode + "&qqe=NZSE"

            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                val key = findCharUrl(body)
                if (key.length > 0)
                    result =  "https://www.directbroking.co.nz/DirectTrade/dynamic/chart.aspx?key=" + key
                it.body?.close()
            }
        }

        val thread = Thread(Runnable { run() })
            thread.start()
            thread.join()

        return result
    }


    //https://www.directbroking.co.nz/DirectTrade/secure/orders.aspx
    //https://www.directbroking.co.nz/DirectTrade/secure/portfolios.aspx
    //https://www.directbroking.co.nz/DirectTrade/secure/accounts.aspx?view=bal
    fun getWebPageByUrl(url:String) :String {
        var webPage = ""

        fun run(){
            val request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful){
                    it.body?.close()
                    //throw  IOException("Unexpected code $it")
                    return
                }
                webPage = it.body?.string().toString()
                it.body?.close()
            }

        }


        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()

        return webPage
    }


    //withdraw money from cash account.
    fun actionWithdrawMoneyFromCashAccount(withdraAmount:String){
        var url = "https://www.directbroking.co.nz/DirectTrade/secure/withdrawfunds.aspx"

        var viewState = ""
        var viewValidation = ""
        var viewStateGenerator = ""

        mCurrentState.webPage = ""

        fun run() {

            //Step 1: get page state.
            var request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //Step 2: setup withdraw amount.
            var formBody = FormBody.Builder()
                .add("__EVENTTARGET", "CAWithdrawFunds1:btnGoConfirm")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("CAWithdrawFunds1:FromAccount", ".CMT.NZD")
                .add("CAWithdrawFunds1:ToAccount", ".SE.01")
                .add("CAWithdrawFunds1:Amount", withdraAmount)
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //Step 3: confirm withdraw action.
            formBody = FormBody.Builder()
                .add("__EVENTTARGET", "CAWithdrawFunds1:btnConfirm")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }

        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()
    }

    //place buy/sell order.
    fun actionOrder(stockCode:String,price:String,quantity:String,type:String,date:String,action:String){
        var url = "https://www.directbroking.co.nz/DirectTrade/secure/order.aspx?a=" + action

        var viewState = ""
        var viewValidation = ""
        var viewStateGenerator = ""

        mCurrentState.webPage = ""

        fun run() {

            //Step 1: get page state.
            var request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //Step 2: setup stock code.
            var formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnInstrument")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("ddlExchangeGroupCode", "NZ")
                .add("txtSecurityCode", stockCode)
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }

            //Step 3: setup buy/sell price and quantity.
            formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnGoConfirmOrder")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .add("QUANTITY", quantity)
                .add("LIMIT", price)
                .add("EXP_WHEN", type)
                .add("EXP_DATE", date)
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                viewState = getValueByTag(body, "__VIEWSTATE")
                viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                it.body?.close()
            }


            //Step 4: confirm buy/sell action.
            formBody = FormBody.Builder()
                .add("__EVENTTARGET", "btnConfirm")
                .add("__VIEWSTATE", viewState)
                .add("__VIEWSTATEGENERATOR", viewStateGenerator)
                .add("__EVENTVALIDATION", viewValidation)
                .build()

            request = Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15")
                .url(url)
                .post(formBody)
                .build()

            okClient.newCall(request).execute().use {
                if (!it.isSuccessful) {
                    it.body?.close()
                    throw  IOException("Unexpected code $it")
                }
                val body = it.body?.string()
                mCurrentState.viewState = getValueByTag(body, "__VIEWSTATE")
                mCurrentState.viewValidation = getValueByTag(body, "__EVENTVALIDATION")
                mCurrentState.viewStateGenerator = getValueByTag(body, "__VIEWSTATEGENERATOR")
                mCurrentState.webPage = body.toString()
                it.body?.close()
            }

        }

        val thread = Thread(Runnable { run() })
        thread.start()
        thread.join()
    }

    companion object {
        private val instance =
            DirectBrokingWeb()
        @JvmStatic
        fun newInstance(): DirectBrokingWeb {
            return instance
        }
    }
}