package nz.co.seclib.dbroker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.database.TradeRecords

class TradeRecordsAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TradeRecordsAdapter.TradeRecordsViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tradeRecordsList = listOf<TradeRecords>() // Cached copy of words

    inner class TradeRecordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTradeRecordsStockCode: TextView = itemView.findViewById(R.id.tvTradeRecordsStockCode)
        val tvTradeRecordsDate: TextView = itemView.findViewById(R.id.tvTradeRecordsDate)
        val tvTradeRecordsAction: TextView = itemView.findViewById(R.id.tvTradeRecordsAction)
        val tvTradeRecordsQuantity: TextView = itemView.findViewById(R.id.tvTradeRecordsQuantity)
        val tvTradeRecordsPrice: TextView = itemView.findViewById(R.id.tvTradeRecordsPrice)
        val tvTradeRecordsValue: TextView = itemView.findViewById(R.id.tvTradeRecordsValue)
        val tvTradeRecordsSettlement: TextView = itemView.findViewById(R.id.tvTradeRecordsSettlement)
        val tvTradeRecordsDueDate: TextView = itemView.findViewById(R.id.tvTradeRecordsDueDate)
        val tvTradeRecordsStatus: TextView = itemView.findViewById(R.id.tvTradeRecordsStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeRecordsViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_user_trade_records, parent, false)
        return TradeRecordsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TradeRecordsViewHolder, position: Int) {
        val tradeRecords = tradeRecordsList[position]
        holder.tvTradeRecordsStockCode.text = tradeRecords.stockCode
        holder.tvTradeRecordsDate.text = tradeRecords.date
        holder.tvTradeRecordsAction.text = tradeRecords.action
        holder.tvTradeRecordsQuantity.text = tradeRecords.quantity
        holder.tvTradeRecordsPrice.text = tradeRecords.price
        holder.tvTradeRecordsValue.text = tradeRecords.value
        holder.tvTradeRecordsSettlement.text = tradeRecords.settlement
        holder.tvTradeRecordsDueDate.text = tradeRecords.dueDate
        holder.tvTradeRecordsStatus.text = tradeRecords.status

    }



    internal fun setTradeRecords(tradeRecordsList: List<TradeRecords>) {
        this.tradeRecordsList = tradeRecordsList
        notifyDataSetChanged()
    }

    override fun getItemCount() = tradeRecordsList.size
}