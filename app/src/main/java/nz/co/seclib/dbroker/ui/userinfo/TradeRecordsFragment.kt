package nz.co.seclib.dbroker.ui.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wordplat.easydivider.RecyclerViewCornerRadius
import com.wordplat.easydivider.RecyclerViewLinearDivider
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.adapter.AccountInfoAdapter
import nz.co.seclib.dbroker.adapter.TradeRecordsAdapter
import nz.co.seclib.dbroker.utils.AppUtils
import nz.co.seclib.dbroker.utils.MyApplication
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModel
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModelFactory

class TradeRecordsFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvTradeRecords = view.findViewById<RecyclerView>(R.id.rvTradeRecords)
        val tradeRecordsAdapter =
            TradeRecordsAdapter(rvTradeRecords.context)
        rvTradeRecords.apply {
            //layoutManager must be set! otherwise adapter doesn't work.
            layoutManager = LinearLayoutManager(rvTradeRecords.context)
            adapter = tradeRecordsAdapter
        }

        //RecyclerView Decoration---------------------->> begin
        val cornerRadius = RecyclerViewCornerRadius(rvTradeRecords)
        cornerRadius.setCornerRadius(AppUtils.dpTopx(view.context, 10F))

        val linearDivider =
            RecyclerViewLinearDivider(view.context, LinearLayoutManager.VERTICAL)
        linearDivider.setDividerSize(1)
        linearDivider.setDividerColor(-0x777778)
        linearDivider.setDividerMargin(
            AppUtils.dpTopx(view.context, 10F),
            AppUtils.dpTopx(view.context, 10F)
        )
        linearDivider.setDividerBackgroundColor(-0x1)
        linearDivider.setShowHeaderDivider(false)
        linearDivider.setShowFooterDivider(false)

        // 圆角背景必须第一个添加
        rvTradeRecords.addItemDecoration(cornerRadius)
        rvTradeRecords.addItemDecoration(linearDivider)
        //RecyclerView Decoration --------------------<< end


        val userInfoViewModel = UserInfoViewModelFactory(
            MyApplication.instance
        ).create(UserInfoViewModel::class.java)
        userInfoViewModel.tradeRecordsList.observe(viewLifecycleOwner, Observer {tl->
            tradeRecordsAdapter.setTradeRecords(tl)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_user_trade_records, container, false)


    companion object {

        @JvmStatic
        fun newInstance(): TradeRecordsFragment {
            return TradeRecordsFragment()
        }
    }
}