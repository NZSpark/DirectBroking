package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wordplat.ikvstockchart.render.TimeLineRender
import kotlinx.android.synthetic.main.activity_stock_tradelog.*
import kotlinx.coroutines.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.ui.sysinfo.SystemConfigActivity

class TradeLogActivity : AppCompatActivity() , CoroutineScope by MainScope(){
    private lateinit var stockInfoViewModel: StockInfoViewModel


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_tradelog)

        val adapter = TradeLogListAdapter(this)

        val stockCode = intent.getStringExtra("STOCKCODE") ?:""

        rvTradeLog.adapter = adapter
        rvTradeLog.layoutManager = LinearLayoutManager(this)

        stockInfoViewModel = StockInfoViewModelFactory(this.application).create(StockInfoViewModel::class.java)
//      tradeLogViewModel = ViewModelProviders.of(this, TradeLogViewModelFactory(this.application))
//            .get(TradeLogViewModel::class.java)

        stockInfoViewModel.initWithStockCode(stockCode)

        stockInfoViewModel.tradeLogList.observe(this, Observer {
            adapter.setTradeLog(it)
        })

        stockInfoViewModel.entrySet.observe(this, Observer {entrySet ->
            klTrade.setEntrySet(entrySet)
            klTrade.render = TimeLineRender()
            klTrade.notifyDataSetChanged()
        })

    }

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
