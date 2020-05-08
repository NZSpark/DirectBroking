package nz.co.seclib.dbroker.ui.stockinfo

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_stock_tradelog.*
import kotlinx.coroutines.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.R.id.rvTradeLog
import nz.co.seclib.dbroker.data.model.TradeLog
import java.time.LocalDateTime
import java.util.*

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

        tradeLogViewModel = TradeLogViewModelFactory(this.application).create(TradeLogViewModel::class.java)
//      tradeLogViewModel = ViewModelProviders.of(this, TradeLogViewModelFactory(this.application))
//            .get(TradeLogViewModel::class.java)

        tradeLogViewModel.initWithStockCode(stockCode)

        tradeLogViewModel.tradeLogList.observe(this, Observer {
            adapter.setTradeLog(it)
        })

    }


}
