package nz.co.seclib.dbroker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.database.AskBidLog

class StockInfoAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<StockInfoAdapter.StockInfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var askBidLog :  AskBidLog? = null

    inner class StockInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvBidQuantity: TextView = itemView.findViewById(R.id.tvBidQuantity)
        val tvBidOrderNumber: TextView = itemView.findViewById(R.id.tvBidOrderNumber)
        val tvBidPrice: TextView = itemView.findViewById(R.id.tvBidPrice)
        val tvAskPrice: TextView = itemView.findViewById(R.id.tvAskPrice)
        val tvAskOrderNumber: TextView = itemView.findViewById(R.id.tvAskOrderNumber)
        val tvAskQuantity: TextView = itemView.findViewById(R.id.tvAskQuantity)
        val tvTradePrice: TextView = itemView.findViewById(R.id.tvTradePrice)
        val tvTradeVolume: TextView = itemView.findViewById(R.id.tvTradeVolume)
        val tvTradeTime: TextView = itemView.findViewById(R.id.tvTradeTime)
        val tvTradeCondition: TextView = itemView.findViewById(R.id.tvTradeCondition)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_bid_ask_trade, parent, false)
        return StockInfoViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: StockInfoViewHolder, position: Int) {
        askBidLog?.let {
            holder.tvBidQuantity.text = askBidLog!!.bidList[position].quantity
            holder.tvBidOrderNumber.text = askBidLog!!.bidList[position].orderNumber
            holder.tvBidPrice.text = askBidLog!!.bidList[position].price
            holder.tvAskQuantity.text = askBidLog!!.askList[position].quantity
            holder.tvAskOrderNumber.text = askBidLog!!.askList[position].orderNumber
            holder.tvAskPrice.text = askBidLog!!.askList[position].price
            holder.tvTradePrice.text = askBidLog!!.tradeList[position].price
            holder.tvTradeVolume.text = askBidLog!!.tradeList[position].tradeVolume
            holder.tvTradeTime.text = askBidLog!!.tradeList[position].tradeTime
            holder.tvTradeCondition.text = askBidLog!!.tradeList[position].tradeCondition
        }
    }

    override fun getItemCount(): Int = 15

    internal fun setAskBidLog(askBidLog:AskBidLog) {
        this.askBidLog = askBidLog
        notifyDataSetChanged()
    }
}