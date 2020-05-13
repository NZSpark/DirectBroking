package nz.co.seclib.dbroker.ui.userinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.model.Portfolio

class PortfolioAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var portfolioList = listOf<Portfolio>() // Cached copy of words

    inner class PortfolioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPortfolioStockCode: TextView = itemView.findViewById(R.id.tvPortfolioStockCode)
        val tvPortfolioQuantity: TextView = itemView.findViewById(R.id.tvPortfolioQuantity)
        val tvPortfolioCostPrice: TextView = itemView.findViewById(R.id.tvPortfolioCostPrice)
        val tvPortfolioCostValue: TextView = itemView.findViewById(R.id.tvPortfolioCostValue)
        val tvPortfolioMarketPrice: TextView = itemView.findViewById(R.id.tvPortfolioMarketPrice)
        val tvPortfolioMarketValue: TextView = itemView.findViewById(R.id.tvPortfolioMarketValue)
        val tvPortfolioUnrealised: TextView = itemView.findViewById(R.id.tvPortfolioUnrealised)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_user_portforlio, parent, false)
        return PortfolioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val portfolio = portfolioList[position]

        holder.tvPortfolioStockCode.text = portfolio.stockCode
        holder.tvPortfolioQuantity.text = portfolio.quantity
        holder.tvPortfolioCostPrice.text = portfolio.costPrice
        if(portfolio.costValue == "" && portfolio.quantity != "" && portfolio.costPrice!= "" )
            portfolio.costValue = (portfolio.quantity.replace(",","").toInt() * portfolio.costPrice.toFloat()/100).toString()
        holder.tvPortfolioCostValue.text = portfolio.costValue
        holder.tvPortfolioMarketPrice.text = portfolio.marketPrice
        holder.tvPortfolioMarketValue.text = portfolio.marketValue
        holder.tvPortfolioUnrealised.text = portfolio.unRealised
    }



    internal fun setPortfolio(portfolioList: List<Portfolio>) {
        this.portfolioList = portfolioList
        notifyDataSetChanged()
    }

    override fun getItemCount() = portfolioList.size
}