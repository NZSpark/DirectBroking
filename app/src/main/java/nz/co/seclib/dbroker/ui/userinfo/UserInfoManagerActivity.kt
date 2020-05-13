package nz.co.seclib.dbroker.ui.userinfo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import nz.co.seclib.dbroker.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_user_info_manager.*
import kotlinx.android.synthetic.main.fragment_user_account.*
import kotlinx.android.synthetic.main.fragment_user_orders.*
import kotlinx.android.synthetic.main.fragment_user_portfolio.*

class UserInfoManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_manager)



        val userInfoPagerAdapter = UserInfoPagerAdapter(this,supportFragmentManager)
        val vpUserInfo = findViewById<ViewPager>(R.id.vpUserInfo)
        vpUserInfo.adapter = userInfoPagerAdapter
        val tlUserInfo = findViewById<TabLayout>(R.id.tlUserInfo)
        tlUserInfo.setupWithViewPager(vpUserInfo)

        val userInfoViewModel = UserInfoViewModelFactory(this.application).create(UserInfoViewModel::class.java)
        tlUserInfo.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> userInfoViewModel.getPortfolioList()
                    1 -> userInfoViewModel.getOrderInfoList()
                    2 -> userInfoViewModel.getAccountInfoList()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        userInfoViewModel.portfolioList.observe(this, Observer { pl->
            rvPortfolio?.let { rv ->
                val portfolioAdapter = rv.adapter as PortfolioAdapter
                portfolioAdapter.setPortfolio(pl)
            }
        })
        userInfoViewModel.accountList.observe(this, Observer { pl->
            rvAccountInfo?.let { rv ->
                val accountInfoAdapter = rv.adapter as AccountInfoAdapter
                accountInfoAdapter.setAccountInfo(pl)
            }
        })

        userInfoViewModel.ordersList.observe(this, Observer { pl->
            rvOrderInfo?.let { rv ->
                val orderInfoAdapter = rv.adapter as OrderInfoAdapter
                orderInfoAdapter.setOrderInfo(pl)
            }
        })

        //default show portfolio.
        userInfoViewModel.getPortfolioList()
    }


    private inner class UserInfoPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val listOfTitles = arrayOf(R.string.user_info_portfolio, R.string.user_info_orders,R.string.user_info_account)

        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return PortfolioFragment.newInstance()
                1 -> return OrdersFragment.newInstance()
                2 -> return AccountFragment.newInstance()
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
