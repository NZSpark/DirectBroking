package nz.co.seclib.dbroker.ui.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_orders.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.utils.MyApplication

class OrdersFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvOrderInfo = view.findViewById<RecyclerView>(R.id.rvOrderInfo)
        val orderInfoAdapter = OrderInfoAdapter(rvOrderInfo.context)
        rvOrderInfo.apply {
            //layoutManager must be set! otherwise adapter doesn't work.
            layoutManager = LinearLayoutManager(rvOrderInfo.context)
            adapter = orderInfoAdapter
        }

        val userInfoViewModel = UserInfoViewModelFactory(MyApplication.instance).create(UserInfoViewModel::class.java)
        userInfoViewModel.ordersList.observe(viewLifecycleOwner, Observer { pl->
            orderInfoAdapter.setOrderInfo(pl)
        })

        ivBuy.setOnClickListener {
            val stockCode = etOrderActionStockCode.text.toString()
            val price   = etOrderActionPrice.text.toString()
            val quantity = etOrderActionQuantity.text.toString()
            var type = ""
            var date = ""
            if(rbUntilCancelled.isChecked) type = "OPEN"
            if(rbUntilEOD.isChecked) type = "EOD"
            if(rbUntilDate.isChecked){
                type = "DATE"
                date = etOrderActionDate.text.toString()
            }
            val currentPrice = userInfoViewModel.getCurrentPrice(stockCode)
            if(price.toFloat() > currentPrice.toFloat())
            {
                Toast.makeText(this.context, "Current price: $currentPrice. The price of buy order is too high, please check it again!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            userInfoViewModel.actionBuy(stockCode,price,quantity,type,date)
            Toast.makeText(this.context, "Buy order is placed!", Toast.LENGTH_LONG).show()
        }
        ivSell.setOnClickListener {
            val stockCode = etOrderActionStockCode.text.toString()
            val price   = etOrderActionPrice.text.toString()
            val quantity = etOrderActionQuantity.text.toString()
            var type = ""
            var date = ""
            if(rbUntilCancelled.isChecked) type = "OPEN"
            if(rbUntilEOD.isChecked) type = "EOD"
            if(rbUntilDate.isChecked){
                type = "DATE"
                date = etOrderActionDate.text.toString()
            }
            val currentPrice = userInfoViewModel.getCurrentPrice(stockCode)
            if(price.toFloat() < currentPrice.toFloat())
            {
                Toast.makeText(this.context, "Current price: $currentPrice. The price of sell order is too low, please check it again!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            userInfoViewModel.actionSell(stockCode,price,quantity,type,date)
            Toast.makeText(this.context,
                "Sell order is placed!", Toast.LENGTH_LONG).show()
        }

        llDate.visibility = View.INVISIBLE
        rbUntilDate.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked)
                llDate.visibility = View.VISIBLE
            else
                llDate.visibility = View.INVISIBLE
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_user_orders, container, false)


    companion object {

        @JvmStatic
        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }
}