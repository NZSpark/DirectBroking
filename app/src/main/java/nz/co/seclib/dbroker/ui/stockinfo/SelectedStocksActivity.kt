package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_selected_stock_list.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.ui.sysinfo.SystemConfigActivity

class SelectedStocksActivity : AppCompatActivity(){
    private lateinit var selectStockViewModel: StockInfoViewModel



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_stock_list)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Set up system parameters: ", Snackbar.LENGTH_LONG)
                .setAction("Setup") {
                    val intent = Intent(this, SystemConfigActivity::class.java)
                    startActivity(intent)
                }.show()
        }


        val recyclerView = findViewById<RecyclerView>(R.id.rvStockList)
        val adapter = StockListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //selectStockViewModel = DBrokerViewModelFactory(this.application).create(DBrokerViewModel::class.java)
        selectStockViewModel = StockInfoViewModelFactory(this.application).create(StockInfoViewModel::class.java)
        selectStockViewModel.initWithStockCode("") //initial timer.

//        selectStockViewModel.stockCurrentTradeInfo.observe(this, Observer {
//            it?.let{
//                adapter.setStocks(it)
//            }
//        })

        selectStockViewModel.stockCurrentTradeInfoList.observe(this, Observer {
            it?.let{
                adapter.setStocks(it)
            }
        })


//        selectStockViewModel.stockCodeList?.observe(this, Observer {
//            it?.let{
//                adapter.setStocks(it)
//            }
//        })

//
//        ivRefresh.setOnClickListener {
//            //adapter.setStocks(emptyList<String>())
//            adapter.setStocks(stockCodeList)
//        }

        ivSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
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