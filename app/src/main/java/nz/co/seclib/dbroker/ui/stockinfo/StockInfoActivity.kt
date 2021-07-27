package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.wordplat.easydivider.RecyclerViewCornerRadius
import com.wordplat.easydivider.RecyclerViewLinearDivider
import kotlinx.android.synthetic.main.activity_stock_info.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.adapter.StockInfoAdapter
import nz.co.seclib.dbroker.ui.sysinfo.SystemConfigActivity
import nz.co.seclib.dbroker.utils.AppUtils
import nz.co.seclib.dbroker.viewmodel.StockInfoViewModel
import nz.co.seclib.dbroker.viewmodel.StockInfoViewModelFactory


class StockInfoActivity : AppCompatActivity() {

    var stockCode = ""
    private lateinit var stockInfoViewModel: StockInfoViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_info)

        stockCode = intent.getStringExtra("STOCKCODE") ?:""
        stockInfoViewModel = StockInfoViewModelFactory(
            this.application
        ).create(StockInfoViewModel::class.java)
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
                Glide.with(this).load(stockCurrentTradeInfo.pictLink).into(ivDBSimplePriceImage)
            }
        })

        val adapter = StockInfoAdapter(this)
        rvStockBidAskTradeInfo.layoutManager = LinearLayoutManager(this)
        rvStockBidAskTradeInfo.adapter = adapter

        //RecyclerView Decoration---------------------->> begin
        val cornerRadius = RecyclerViewCornerRadius(rvStockBidAskTradeInfo)
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
        rvStockBidAskTradeInfo.addItemDecoration(cornerRadius)
        rvStockBidAskTradeInfo.addItemDecoration(linearDivider)
        //RecyclerView Decoration --------------------<< end


        stockInfoViewModel.askBidLog.observe(this, Observer { askBidLog ->
            askBidLog?.let {
                adapter.setAskBidLog(askBidLog)
            }
        })

        ivDBSimplePriceImage.setOnClickListener {
            val intent = Intent(this, StockChartNZXActivity::class.java).apply {
                putExtra("STOCKCODE",stockCode)
            }
            startActivity(intent)
        }

        ivAdd.setOnClickListener{
            stockInfoViewModel.insertUserStock(stockCode)
            Toast.makeText(this,stockCode+ " is added into the selected list!", Toast.LENGTH_LONG).show()
        }

        ivRemove.setOnClickListener(){
            stockInfoViewModel.deleteUserStock(stockCode)
            Toast.makeText(this,stockCode+ " is removed from the selected list!", Toast.LENGTH_LONG).show()
        }

        ivRefresh.setOnClickListener {
            Toast.makeText(this,stockCode+ " will be refreshed automatically!", Toast.LENGTH_LONG).show()
        }

        ivTradeLog.setOnClickListener {
            val intent = Intent(this, TradeLogActivity::class.java).apply {
                putExtra("STOCKCODE", stockCode)
            }
            startActivity(intent)
        }

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
                val intent = Intent(this, TradeLogActivity::class.java).apply {
                    putExtra("STOCKCODE", stockCode)
                }
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