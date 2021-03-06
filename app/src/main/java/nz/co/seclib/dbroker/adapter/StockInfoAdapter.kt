package nz.co.seclib.dbroker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.database.AskBidLog
import java.lang.Math.max

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
            if(position < askBidLog!!.bidList.size ) {
                holder.tvBidQuantity.text = askBidLog!!.bidList[position].quantity
                holder.tvBidOrderNumber.text = askBidLog!!.bidList[position].orderNumber
                holder.tvBidPrice.text = askBidLog!!.bidList[position].price
            }else{
                holder.tvBidQuantity.text = ""
                holder.tvBidOrderNumber.text = ""
                holder.tvBidPrice.text = ""
            }


            if(position < askBidLog!!.askList.size ) {
                holder.tvAskQuantity.text = askBidLog!!.askList[position].quantity
                holder.tvAskOrderNumber.text = askBidLog!!.askList[position].orderNumber
                holder.tvAskPrice.text = askBidLog!!.askList[position].price
            }else{
                holder.tvAskQuantity.text = ""
                holder.tvAskOrderNumber.text = ""
                holder.tvAskPrice.text = ""
            }

            if(position < askBidLog!!.tradeList.size ) {
                holder.tvTradePrice.text = askBidLog!!.tradeList[position].price
                holder.tvTradeVolume.text = askBidLog!!.tradeList[position].tradeVolume
                holder.tvTradeTime.text = askBidLog!!.tradeList[position].tradeTime
                holder.tvTradeCondition.text = askBidLog!!.tradeList[position].tradeCondition
            }else{
                holder.tvTradePrice.text = ""
                holder.tvTradeVolume.text = ""
                holder.tvTradeTime.text = ""
                holder.tvTradeCondition.text = ""
            }
        }
    }

    //override fun getItemCount(): Int = max (max( askBidLog?.askList?.size ?:0 , askBidLog?.bidList?.size ?:0), askBidLog?.tradeList?.size ?:0)
    override fun getItemCount(): Int = 15

    internal fun setAskBidLog(askBidLog:AskBidLog) {
        this.askBidLog = askBidLog
        notifyDataSetChanged()
    }
}