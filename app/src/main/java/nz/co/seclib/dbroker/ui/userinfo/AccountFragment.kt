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
import kotlinx.android.synthetic.main.fragment_user_account.*
import kotlinx.android.synthetic.main.recyclerview_user_account.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.adapter.AccountInfoAdapter
import nz.co.seclib.dbroker.utils.MyApplication
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModel
import nz.co.seclib.dbroker.viewmodel.UserInfoViewModelFactory

class AccountFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAccountInfo = view.findViewById<RecyclerView>(R.id.rvAccountInfo)
        val accountInfoAdapter =
            AccountInfoAdapter(rvAccountInfo.context)
        rvAccountInfo.apply {
            //layoutManager must be set! otherwise adapter doesn't work.
            layoutManager = LinearLayoutManager(rvAccountInfo.context)
            adapter = accountInfoAdapter
        }

        val userInfoViewModel = UserInfoViewModelFactory(
            MyApplication.instance
        ).create(UserInfoViewModel::class.java)
        userInfoViewModel.accountList.observe(viewLifecycleOwner, Observer { pl->
            accountInfoAdapter.setAccountInfo(pl)
        })

        ivWithdraw.setOnClickListener {
            val withdrawAmount = etWithdrawAmount.text.toString().toFloat()
            val accountBalance = tvAccountWithdrawalBalance.text.toString().replace(",","").toFloat()
            if(withdrawAmount < 0 || withdrawAmount > accountBalance){
                Toast.makeText(this.context,"Withdraw amount is wrong, please check it again!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            userInfoViewModel.actionWithdraw(withdrawAmount.toString())
            Toast.makeText(this.context,
                "Withdraw amount: $withdrawAmount has been requested, please check your bank account!", Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_user_account, container, false)

    companion object {

        @JvmStatic
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}