package nz.co.seclib.dbroker.ui.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_account.*
import nz.co.seclib.dbroker.R

class AccountFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAccountInfo = view.findViewById<RecyclerView>(R.id.rvAccountInfo)
        rvAccountInfo.apply {
            //layoutManager must be set! otherwise adapter doesn't work.
            layoutManager = LinearLayoutManager(rvAccountInfo.context)
            adapter = AccountInfoAdapter(rvAccountInfo.context)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_account, container, false)
    }
    companion object {

        @JvmStatic
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}