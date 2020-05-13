package nz.co.seclib.dbroker.ui.userinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.model.OrderInfo

class OrderInfoAdapter  internal constructor(
    context: Context
) : RecyclerView.Adapter<OrderInfoAdapter.OrderInfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var orderInfoList = listOf<OrderInfo>()

    inner class OrderInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOrderStockCode: TextView = itemView.findViewById(R.id.tvOrderStockCode)
        val tvOrderRemainning: TextView = itemView.findViewById(R.id.tvOrderRemainning)
        val tvOrderPlacedTime: TextView = itemView.findViewById(R.id.tvOrderPlacedTime)
        val tvOrderExpireTime: TextView = itemView.findViewById(R.id.tvOrderExpireTime)
        val tvOrderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val tvOrderRefNumber: TextView = itemView.findViewById(R.id.tvOrderRefNumber)
        val tvOrderOptionsNumber: TextView = itemView.findViewById(R.id.tvOrderOptionsNumber)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderInfoAdapter.OrderInfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_user_orders, parent, false)
        return OrderInfoViewHolder(itemView)
    }

    override fun getItemCount(): Int = orderInfoList.size

    override fun onBindViewHolder(holder: OrderInfoAdapter.OrderInfoViewHolder, position: Int) {
        val orderInfo = orderInfoList[position]
        holder.tvOrderStockCode.text = orderInfo.stockCode
        holder.tvOrderRemainning.text = orderInfo.remainning
        holder.tvOrderPlacedTime.text = orderInfo.placedTime
        holder.tvOrderExpireTime.text = orderInfo.expiresTime
        holder.tvOrderStatus.text = orderInfo.status
        holder.tvOrderRefNumber.text = orderInfo.refCode
        holder.tvOrderOptionsNumber.text = orderInfo.orderID
    }

    internal fun setOrderInfo(orderInfoList: List<OrderInfo>) {
        this.orderInfoList = orderInfoList
        notifyDataSetChanged()
    }
}