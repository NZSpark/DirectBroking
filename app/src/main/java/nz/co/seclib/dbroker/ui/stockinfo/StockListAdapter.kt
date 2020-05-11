package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.model.StockCurrentTradeInfo
import nz.co.seclib.dbroker.data.model.StockScreenInfo
import nz.co.seclib.dbroker.utils.MyApplication

class StockListAdapter  internal constructor(
    context: Context
) : RecyclerView.Adapter<StockListAdapter.StockInfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    //private var stocks = mutableListOf<StockCurrentTradeInfo>() // Cached copy of words
    private var stocks = listOf<StockCurrentTradeInfo>() // Cached copy of words


    inner class StockInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStockCode: TextView  = itemView.findViewById(R.id.tvStockCode)
        val tvPercent: TextView = itemView.findViewById(R.id.tvTradePercent)
        //val tvFirst: TextView  = itemView.findViewById(R.id.tvTradeFirst)
        val tvPrice: TextView  = itemView.findViewById(R.id.tvTradePrice)
        val tvHigh: TextView  = itemView.findViewById(R.id.tvTradeHigh)
        val tvLow: TextView = itemView.findViewById(R.id.tvTradeLow)
        val tvValue: TextView  = itemView.findViewById(R.id.tvTradeValue)
        val tvVolume: TextView  = itemView.findViewById(R.id.tvTradeVolume)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_stockinfo, parent, false)
        itemView.setOnClickListener {
            val intent = Intent(it.context,StockInfoActivity::class.java).apply {
                putExtra("STOCKCODE",it.findViewById<TextView>(R.id.tvStockCode).text.toString())
            }
            startActivity(it.context,intent,null)
        }
        return StockInfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StockInfoViewHolder, position: Int) {
        val currentStock = stocks[position]
        holder.tvStockCode.text = currentStock.stockCode
        holder.tvPercent.text = currentStock.change
        //holder.tvFirst.text = currentStock.sFirst
        holder.tvPrice.text = currentStock.price
        holder.tvHigh.text = currentStock.sHigh
        holder.tvLow.text = currentStock.sLow
        holder.tvValue.text = currentStock.value
        holder.tvVolume.text = currentStock.volume
    }

//    internal fun addStock(stockCode:String) {
//        val stockCurrentTradeInfo = MyApplication.dbWeb.GetStockInfo(stockCode)?:return //need to encapsulate
//        //stockCurrentTradeInfo?.stockCode = stockCode
//        if(stockCurrentTradeInfo != null)
//            this.stocks.add(stockCurrentTradeInfo)
//        //notifyDataSetChanged()
//    }

//    internal fun setStocks(stockCodeList:List<String>) {
//        this.stocks.clear()
//        stockCodeList.forEach(){stockCode ->
//                addStock(stockCode)
//        }
//        notifyDataSetChanged()
//    }

    /*
    internal fun setStocks(stockCurrentTradeInfo: StockCurrentTradeInfo) {
//        val backupList = this.stocks
//        this.stocks.clear()
//        stockCodeList.forEach(){stockCode ->
//            var bExist = false
//            backupList.forEach(){ stockCurrentTradeInfo ->
//                if(stockCurrentTradeInfo.stockCode == stockCode){
//                    this.stocks.add(stockCurrentTradeInfo)
//                    return  //break this.stocks loop
//                }
//            }
//            if(!bExist){
//                addStock(stockCode)
//            }
//        }

//        this.stocks.clear()
//        stockCodeList.forEach(){stockCode ->
//                addStock(stockCode)
//        }
//        this.stocks.removeAll {
//            it.stockCode = stockCurrentTradeInfo.stockCode
//            true
//        }

        val positon = this.stocks.indexOfFirst { it -> it.stockCode == stockCurrentTradeInfo.stockCode }
        if(positon != -1)
            this.stocks[positon] = stockCurrentTradeInfo
        else
            this.stocks.add(stockCurrentTradeInfo)

        notifyDataSetChanged()
    }
    */

    internal fun setStocks(stockCurrentTradeInfoList: List<StockCurrentTradeInfo>){
        this.stocks = stockCurrentTradeInfoList
        notifyDataSetChanged()
    }


    override fun getItemCount() = stocks.size
}
