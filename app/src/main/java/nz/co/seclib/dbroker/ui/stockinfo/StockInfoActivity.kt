package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_stock_info.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.*
import nz.co.seclib.dbroker.ui.sysinfo.SystemConfigActivity


class StockInfoActivity : AppCompatActivity() {
    val bidsTable = BidsTable()
    val asksTable = AsksTable()
    val recentTradesTable = TradesTable()
    var mCurrentState : CurrentState? = null
    var stockCode = ""
    //var stockTradeInfo = StockTradeInfo()
    //private lateinit var stockInfoViewModel: DBrokerViewModel
    private lateinit var stockInfoViewModel: TradeLogViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_info)
        //val btRefresh = findViewById<Button>(R.id.btRefresh)

        stockCode = intent.getStringExtra("STOCKCODE") ?:""

        val picasso = Picasso.Builder(this).build()
        //stockInfoViewModel = DBrokerViewModelFactory(this.application).create(DBrokerViewModel::class.java)
        stockInfoViewModel = TradeLogViewModelFactory(this.application).create(TradeLogViewModel::class.java)
        stockInfoViewModel.initWithStockCode(stockCode)

        stockInfoViewModel.stockCurrentTradeInfo.observe(this, Observer {stockCurrentTradeInfo ->
            if(stockCurrentTradeInfo == null) return@Observer

            tvCompanyName.text = stockCurrentTradeInfo.companyName + "    " + stockCurrentTradeInfo.infoTime
            tvTradeInfoPercent.text = stockCurrentTradeInfo.change
            tvTradeInfoPrice.text = stockCurrentTradeInfo.price
            tvTradeInfoHigh.text = stockCurrentTradeInfo.sHigh
            tvTradeInfoLow.text = stockCurrentTradeInfo.sLow
            tvTradeInfoValue.text = stockCurrentTradeInfo.value
            tvTradeInfoVolume.text = stockCurrentTradeInfo.volume

            if(stockCurrentTradeInfo.pictLink != "") {
                //println(stockTradeInfo.pictLink)
                picasso.load(stockCurrentTradeInfo.pictLink).into(imageView)
            }
        })

        stockInfoViewModel.askBidLog.observe(this, Observer { askBidLog ->

            if(askBidLog == null) return@Observer

            val tlStockPrice = findViewById<TableLayout>(R.id.tlStockPrice)
            tlStockPrice.removeAllViews()

            for (i in 0..14) {
                val tableRow = TableRow(tlStockPrice.context)
                //part1. Bids Table
                if (askBidLog.bidList.size > i) {
                    val bidsrow = askBidLog.bidList[i]
                    val tvPrice = TextView(tableRow.context)
                    tvPrice.text = bidsrow.price + "    "
                    tvPrice.gravity = Gravity.RIGHT
                    tvPrice.setTypeface(null, Typeface.BOLD)
                    val tvOrderNumber = TextView(tableRow.context)
                    tvOrderNumber.text = "           " + bidsrow.orderNumber
                    tvOrderNumber.gravity = Gravity.RIGHT
                    val tvQuantity = TextView(tableRow.context)
                    tvQuantity.text = bidsrow.quantity + "  "
                    tvQuantity.gravity = Gravity.RIGHT


                    tableRow.addView(tvOrderNumber)
                    tableRow.addView(tvQuantity)
                    tableRow.addView(tvPrice)
                }

                //part2. Asks Table
                if (askBidLog.askList.size > i) {
                    val asksRow = askBidLog.askList[i]
                    val tvPrice2 = TextView(tableRow.context)
                    tvPrice2.text = asksRow.price + "  "
                    tvPrice2.gravity = Gravity.RIGHT
                    tvPrice2.setTypeface(null, Typeface.BOLD)

                    val tvOrderNumber2 = TextView(tableRow.context)
                    tvOrderNumber2.text = asksRow.orderNumber + "  "
                    tvOrderNumber2.gravity = Gravity.RIGHT
                    val tvQuantity2 = TextView(tableRow.context)
                    tvQuantity2.text = asksRow.quantity + "  "
                    tvQuantity2.gravity = Gravity.RIGHT

                    tableRow.addView(tvPrice2)
                    tableRow.addView(tvQuantity2)
                    tableRow.addView(tvOrderNumber2)
                }

                //part3. Trade records.
                if (askBidLog.tradeList.size > i) {
                    val tradesRow = askBidLog.tradeList[i]
                    val tvPrice3 = TextView(tableRow.context)
                    tvPrice3.text = tradesRow.price + "  "
                    tvPrice3.gravity = Gravity.RIGHT
                    tvPrice3.setTypeface(null, Typeface.BOLD)
                    val tvVolume = TextView(tableRow.context)
                    tvVolume.text = tradesRow.tradeVolume + "  "
                    tvVolume.gravity = Gravity.RIGHT
                    val tvTradeTime = TextView(tableRow.context)
                    tvTradeTime.text = tradesRow.tradeTime + "       "
                    tvTradeTime.gravity = Gravity.RIGHT

                    tableRow.addView(tvPrice3)
                    tableRow.addView(tvVolume)
                    tableRow.addView(tvTradeTime)
                }

                //tableRow.gravity = Gravity.RIGHT
                tableRow.setLayoutParams(
                    TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                )

                tlStockPrice.addView(tableRow)
            }
        })

        ivAdd.setOnClickListener{
            stockInfoViewModel.insertUserStock(stockCode)
            Toast.makeText(this,stockCode+ " is added into the selected list!", Toast.LENGTH_LONG).show()
        }

//        btAdd.setOnClickListener{
//            MyApplication.stockMarketInfo.usrStockCodeList.add(stockCode)
//        }

        ivRemove.setOnClickListener(){
            stockInfoViewModel.deleteUserStock(stockCode)
            Toast.makeText(this,stockCode+ " is removed from the selected list!", Toast.LENGTH_LONG).show()
        }

//        btRemove.setOnClickListener(){
//            MyApplication.stockMarketInfo.usrStockCodeList.remove(stockCode)
//        }

        ivRefresh.setOnClickListener {
            //QuickFillStockPriceTable()
            Toast.makeText(this,stockCode+ " will be refreshed automatically!", Toast.LENGTH_LONG).show()
            stockInfoViewModel.resetTimer()
        }

//        btRefresh.setOnClickListener {
//            QuickFillStockPriceTable()
//        }

        ivTradeLog.setOnClickListener {
            val intent = Intent(this, TradeLogActivity::class.java).apply {
                putExtra("STOCKCODE", stockCode)
            }
            startActivity(intent)
        }

//        Toast.makeText(this,"Stock asks/bids prices are updating.....",Toast.LENGTH_LONG).show()
//
//        val thread = Thread(Runnable { FillStockPriceTable() })
//        thread.start()
    }

    /*
    fun QuickFillStockPriceTable(){
        var currentState : CurrentState = mCurrentState ?: CurrentState(stockCode,
            JavaNetCookieJar(CookieManager()),"","","",""
        )
        currentState = MyApplication.dbWeb.getLastDepthPage(currentState)

        mCurrentState = currentState

        bidsTable.GetBidsTableFromString(MyApplication.dbWeb.getTableString(currentState._WebPage,"BidsTable"))
        //session maybe expired.
        if (bidsTable.bidsList.size < 1) {
            FillStockPriceTable()
            return
        }
        asksTable.GetAsksTableFromString(MyApplication.dbWeb.getTableString(currentState._WebPage, "AsksTable"))
        recentTradesTable.GetRecentTradesTableFromString(MyApplication.dbWeb.getTableString(currentState._WebPage,"RecentTradesTable"))

//        var stockTradeInfo = MyApplication.stockTradeInfoList[stockCode]
//        if(stockTradeInfo == null) {
//            stockTradeInfo = MyApplication.dbWeb.GetStockInfo(stockCode)
//            MyApplication.stockTradeInfoList.put(stockCode,stockTradeInfo)
//        }
//        if(stockTradeInfo.pictLink != "") {
//            Picasso.Builder(this).build().load(stockTradeInfo.pictLink).into(imageView)
//        }

        UIFillTable()
    }

    fun FillStockPriceTable(){

        var currentState = MyApplication.dbWeb.getDepthPage(stockCode)
        mCurrentState = currentState

        bidsTable.GetBidsTableFromString(
            MyApplication.dbWeb.getTableString(
                currentState._WebPage,
                "BidsTable"
            )
        )
        asksTable.GetAsksTableFromString( MyApplication.dbWeb.getTableString(currentState._WebPage, "AsksTable"))
        recentTradesTable.GetRecentTradesTableFromString(MyApplication.dbWeb.getTableString(currentState._WebPage,"RecentTradesTable"))

//        stockTradeInfo = MyApplication.dbWeb.GetStockInfo(stockCode)
//        Picasso.Builder(this).build().load(stockTradeInfo.pictLink).fit().into(imageView)

        //val pictureUrl = dbWeb.GetStockPicture(stockCode)
        //Picasso.Builder(this).build().load(pictureUrl).into(imageView);
//        var stockTradeInfo = MyApplication.stockTradeInfoList[stockCode]
//        if(stockTradeInfo == null) {
//            stockTradeInfo = MyApplication.dbWeb.GetStockInfo(stockCode)
//            MyApplication.stockTradeInfoList.put(stockCode,stockTradeInfo)
//        }
//        if(stockTradeInfo.pictLink != "") {
//            Picasso.Builder(this).build().load(stockTradeInfo.pictLink).into(imageView)
//        }

        UIFillTable()
    }


    fun UIFillTable(){

        val picasso = Picasso.Builder(this).build()

        var stockTradeInfo: StockCurrentTradeInfo? = MyApplication.dbWeb.GetStockInfo(stockCode)
            ?: return

        if(stockTradeInfo?.pictLink != "") {
            //println(stockTradeInfo.pictLink)
            picasso.load(stockTradeInfo?.pictLink).into(imageView)
        }

        runOnUiThread(Runnable() {
            run {
                //Only the original thread that created a view hierarchy can touch its views.
                tvCompanyName.text = stockTradeInfo?.companyName + "    " + stockTradeInfo?.infoTime
                tvTradeInfoPercent.text = stockTradeInfo?.change
                tvTradeInfoPrice.text = stockTradeInfo?.price
                tvTradeInfoHigh.text = stockTradeInfo?.sHigh
                tvTradeInfoLow.text = stockTradeInfo?.sLow
                tvTradeInfoValue.text = stockTradeInfo?.value
                tvTradeInfoVolume.text = stockTradeInfo?.volume

                //if (bidsTable.bidsList.size < 15) return@run

                val tlStockPrice = findViewById<TableLayout>(R.id.tlStockPrice)
                tlStockPrice.removeAllViews()

                for (i in 0..14) {
                    val tableRow = TableRow(tlStockPrice.context)

                    //part1. Bids Table
                    if (bidsTable.bidsList.size > i) {
                        val bidsrow = bidsTable.bidsList[i]
                        val tvPrice = TextView(tableRow.context)
                        tvPrice.text = bidsrow.price + "    "
                        tvPrice.gravity = Gravity.RIGHT
                        tvPrice.setTypeface(null, Typeface.BOLD)
                        val tvOrderNumber = TextView(tableRow.context)
                        tvOrderNumber.text = "           " + bidsrow.orderNumber
                        tvOrderNumber.gravity = Gravity.RIGHT
                        val tvQuantity = TextView(tableRow.context)
                        tvQuantity.text = bidsrow.quantity + "  "
                        tvQuantity.gravity = Gravity.RIGHT


                        tableRow.addView(tvOrderNumber)
                        tableRow.addView(tvQuantity)
                        tableRow.addView(tvPrice)
                    }

                    //part2. Asks Table
                    if (asksTable.asksList.size > i) {
                        val asksRow = asksTable.asksList[i]
                        val tvPrice2 = TextView(tableRow.context)
                        tvPrice2.text = asksRow.price + "  "
                        tvPrice2.gravity = Gravity.RIGHT
                        tvPrice2.setTypeface(null, Typeface.BOLD)

                        val tvOrderNumber2 = TextView(tableRow.context)
                        tvOrderNumber2.text = asksRow.orderNumber + "  "
                        tvOrderNumber2.gravity = Gravity.RIGHT
                        val tvQuantity2 = TextView(tableRow.context)
                        tvQuantity2.text = asksRow.quantity + "  "
                        tvQuantity2.gravity = Gravity.RIGHT

                        tableRow.addView(tvPrice2)
                        tableRow.addView(tvQuantity2)
                        tableRow.addView(tvOrderNumber2)
                    }

                    //part3. Trade records.
                    if (recentTradesTable.tradesList.size > i) {
                        val tradesRow = recentTradesTable.tradesList[i]
                        val tvPrice3 = TextView(tableRow.context)
                        tvPrice3.text = tradesRow.price + "  "
                        tvPrice3.gravity = Gravity.RIGHT
                        tvPrice3.setTypeface(null, Typeface.BOLD)
                        val tvVolume = TextView(tableRow.context)
                        tvVolume.text = tradesRow.tradeVolume + "  "
                        tvVolume.gravity = Gravity.RIGHT
                        val tvTradeTime = TextView(tableRow.context)
                        tvTradeTime.text = tradesRow.tradeTime + "       "
                        tvTradeTime.gravity = Gravity.RIGHT

                        tableRow.addView(tvPrice3)
                        tableRow.addView(tvVolume)
                        tableRow.addView(tvTradeTime)
                    }

                    //tableRow.gravity = Gravity.RIGHT
                    tableRow.setLayoutParams(
                        TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                        )
                    )

                    tlStockPrice.addView(tableRow)
                }

                Toast.makeText(this,"Stock asks/bids prices are updated!",Toast.LENGTH_LONG).show()
            }
        })
    }
    */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }


    override fun onOptionsItemSelected( item: MenuItem) :Boolean{
        when (item.itemId){
            R.id.menu_selected_stocks -> {
                val intent = Intent(this, SelectedStocksActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_info -> {
                val intent = Intent(this, StockInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_trade_info -> {
                val intent = Intent(this, TradeLogActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_system_parameters -> {
                val intent = Intent(this, SystemConfigActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_night_mode ->{
                if (delegate.localNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
                }
            }

        }
        return true
    }
}