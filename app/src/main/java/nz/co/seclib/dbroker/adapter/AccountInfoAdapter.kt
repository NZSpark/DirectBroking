package nz.co.seclib.dbroker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.database.AccountInfo

class AccountInfoAdapter  internal constructor(
    context: Context
) : RecyclerView.Adapter<AccountInfoAdapter.AccountInfoViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var accountInfoList = listOf<AccountInfo>()

    inner class AccountInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvAccountDescription: TextView = itemView.findViewById(R.id.tvAccountDescription)
        val tvAccountCCY: TextView = itemView.findViewById(R.id.tvAccountCCY)
        val tvAccountBalance: TextView = itemView.findViewById(R.id.tvAccountBalance)
        val tvAccountInterestRate: TextView = itemView.findViewById(R.id.tvAccountInterestRate)
        val tvAccountReservedFunds: TextView = itemView.findViewById(R.id.tvAccountReservedFunds)
        val tvAccountUnclearedFunds: TextView = itemView.findViewById(R.id.tvAccountUnclearedFunds)
        val tvAccountWithdrawalBalance: TextView = itemView.findViewById(R.id.tvAccountWithdrawalBalance)
        val tvAccountTradingBalance: TextView = itemView.findViewById(R.id.tvAccountTradingBalance)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountInfoViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_user_account, parent, false)
        return AccountInfoViewHolder(itemView)
    }

    override fun getItemCount(): Int = accountInfoList.size

    override fun onBindViewHolder(holder: AccountInfoViewHolder, position: Int) {
        val accountInfo = accountInfoList[position]
        holder.tvAccountDescription.text = accountInfo.description
        holder.tvAccountCCY.text = accountInfo.curreny
        holder.tvAccountBalance.text = accountInfo.balance
        holder.tvAccountInterestRate.text = accountInfo.interestRate
        holder.tvAccountReservedFunds.text = accountInfo.reservedFunds
        holder.tvAccountUnclearedFunds.text = accountInfo.unClearedFunds
        holder.tvAccountWithdrawalBalance.text = accountInfo.withdrawalBalance
        holder.tvAccountTradingBalance.text = accountInfo.tradeingBalance
    }

    internal fun setAccountInfo(accountInfoList: List<AccountInfo>) {
        this.accountInfoList = accountInfoList
        notifyDataSetChanged()
    }

}