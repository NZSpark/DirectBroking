package nz.co.seclib.dbroker.ui.userinfo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModel
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModelFactory

class UserInfoManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_manager)



        val userInfoPagerAdapter = UserInfoPagerAdapter(this,supportFragmentManager)
        val vpUserInfo = findViewById<ViewPager>(R.id.vpUserInfo)
        vpUserInfo.adapter = userInfoPagerAdapter
        val tlUserInfo = findViewById<TabLayout>(R.id.tlUserInfo)
        tlUserInfo.setupWithViewPager(vpUserInfo)

        val userInfoViewModel = UserInfoViewModelFactory(
            this.application
        ).create(UserInfoViewModel::class.java)
        tlUserInfo.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            //update user information when clicking the corresponding pages.
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> userInfoViewModel.getPortfolioList()
                    1 -> userInfoViewModel.getTradeRecordsList()
                    2 -> userInfoViewModel.getOrderInfoList()
                    3 -> userInfoViewModel.getAccountInfoList()
                }
                return
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //defautl page with values.
        userInfoViewModel.getPortfolioList()
        //userInfoViewModel.getAccountInfoList()
        //userInfoViewModel.getOrderInfoList()
    }


    private inner class UserInfoPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val listOfTitles = arrayOf(R.string.user_info_portfolio,R.string.user_info_trade_records, R.string.user_info_orders,R.string.user_info_account)

        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return PortfolioFragment.newInstance()
                1 -> return TradeRecordsFragment.newInstance()
                2 -> return OrdersFragment.newInstance()
                3 -> return AccountFragment.newInstance()
            }
            return PortfolioFragment.newInstance()
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return context.resources.getString(listOfTitles[position])
        }

        override fun getCount(): Int {
            return listOfTitles.size
        }
    }
}
