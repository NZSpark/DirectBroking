package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_selected_stock_list.*
import nz.co.seclib.dbroker.R

class SelectedStocksActivity : AppCompatActivity(){
    private lateinit var selectStockViewModel: TradeLogViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_stock_list)


        val recyclerView = findViewById<RecyclerView>(R.id.rvStockList)
        val adapter = StockListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //selectStockViewModel = DBrokerViewModelFactory(this.application).create(DBrokerViewModel::class.java)
        selectStockViewModel = TradeLogViewModelFactory(this.application).create(TradeLogViewModel::class.java)
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
}