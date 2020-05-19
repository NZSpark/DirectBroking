package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.text.parseAsHtml
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wordplat.easydivider.RecyclerViewCornerRadius
import com.wordplat.easydivider.RecyclerViewLinearDivider
import com.wordplat.ikvstockchart.render.TimeLineRender
import kotlinx.android.synthetic.main.activity_stock_tradelog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.adapter.TradeLogListAdapter
import nz.co.seclib.dbroker.ui.sysinfo.SystemConfigActivity
import nz.co.seclib.dbroker.utils.AppUtils
import nz.co.seclib.dbroker.viewmodel.TradeLogViewModel
import nz.co.seclib.dbroker.viewmodel.TradeLogViewModelFactory

class TradeLogActivity : AppCompatActivity() , CoroutineScope by MainScope(){
    private lateinit var tradeLogViewModel: TradeLogViewModel


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

        //RecyclerView Decoration---------------------->> begin
        val cornerRadius = RecyclerViewCornerRadius(rvTradeLog)
        cornerRadius.setCornerRadius(AppUtils.dpTopx(this, 10F))

        val linearDivider =
            RecyclerViewLinearDivider(this, LinearLayoutManager.VERTICAL)
        linearDivider.setDividerSize(1)
        linearDivider.setDividerColor(-0x777778)
        linearDivider.setDividerMargin(
            AppUtils.dpTopx(this, 10F),
            AppUtils.dpTopx(this, 10F)
        )
        linearDivider.setDividerBackgroundColor(-0x1)
        linearDivider.setShowHeaderDivider(false)
        linearDivider.setShowFooterDivider(false)

        // 圆角背景必须第一个添加
        rvTradeLog.addItemDecoration(cornerRadius)
        rvTradeLog.addItemDecoration(linearDivider)
        //RecyclerView Decoration --------------------<< end

        tradeLogViewModel = TradeLogViewModelFactory(
            this.application
        ).create(TradeLogViewModel::class.java)
//      tradeLogViewModel = ViewModelProviders.of(this, TradeLogViewModelFactory(this.application))
//            .get(TradeLogViewModel::class.java)


        tradeLogViewModel.tradeLogList.observe(this, Observer {
            adapter.setTradeLog(it)
        })

        tradeLogViewModel.entrySet.observe(this, Observer { entrySet ->
            //x-axis lable, it's fixed in TimeLineRender.
            if(entrySet.entryList.size > 5) {
                entrySet.entryList[0].xLabel = "10:00"
                entrySet.entryList[1].xLabel = "11:45"
                entrySet.entryList[2].xLabel = "13:30"
                entrySet.entryList[3].xLabel = "15:15"
                entrySet.entryList[4].xLabel = "17:00"
            }
            klTrade.setEntrySet(entrySet)
            klTrade.render = TimeLineRender()
            klTrade.notifyDataSetChanged()
        })

        tradeLogViewModel.companyAnalysis.observe(this, Observer {
            tvCompanyAnalysis.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        })
        //if only handle local data, then common init should be comment out.
        //stockInfoViewModel.initWithStockCode(stockCode)
        //initTradeLogActivity is included in initWithStockCode, only one of them should be invoked.
        tradeLogViewModel.initTradeLogActivity(stockCode)

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
