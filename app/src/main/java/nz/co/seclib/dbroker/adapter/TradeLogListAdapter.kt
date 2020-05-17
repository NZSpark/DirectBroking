package nz.co.seclib.dbroker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.database.TradeLog

class TradeLogListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TradeLogListAdapter.TradeLogViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tradeLogList = listOf<TradeLog>() // Cached copy of words

//    init {
//        tradeLogList.clear()
//        notifyDataSetChanged()
//    }

    inner class TradeLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStockCode: TextView = itemView.findViewById(R.id.tvStockCode)
        val tvTradePrice: TextView = itemView.findViewById(R.id.tvTradeLogPrice)
        val tvTradeVolume: TextView = itemView.findViewById(R.id.tvTradeLogVolume)
        val tvTradeTime: TextView = itemView.findViewById(R.id.tvTradeLogTime)
        val tvTradeCondition: TextView = itemView.findViewById(R.id.tvTradeLogCondition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeLogViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_tradelog, parent, false)
        return TradeLogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TradeLogViewHolder, position: Int) {
        val currentStock = tradeLogList[position]
        holder.tvStockCode.text = currentStock.stockCode
        holder.tvTradePrice.text = currentStock.price
        holder.tvTradeVolume.text = currentStock.tradeVolume
        holder.tvTradeTime.text = currentStock.tradeTime
        holder.tvTradeCondition.text = currentStock.tradeCondition
    }

//    internal fun addStock(stockCode:String) {
//        val stockCurrentTradeInfo = MyApplication.dbWeb.GetStockInfo(stockCode) //need to encapsulate
//        stockCurrentTradeInfo.stockCode = stockCode
//        this.stocks.add(stockCurrentTradeInfo)
//        //notifyDataSetChanged()
//    }

    internal fun setTradeLog(tradeLogList: List<TradeLog>) {
//        tradeLogList.reversed().forEach {
//            this.tradeLogList.add(0,it)  //add each log on the top
//        }
        this.tradeLogList = tradeLogList
        notifyDataSetChanged()
    }

    override fun getItemCount() = tradeLogList.size
}