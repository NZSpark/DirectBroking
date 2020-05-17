package nz.co.seclib.dbroker.ui.sysinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_system_config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.ui.stockinfo.SearchActivity
import nz.co.seclib.dbroker.ui.stockinfo.SelectedStocksActivity
import nz.co.seclib.dbroker.ui.stockinfo.StockInfoActivity
import nz.co.seclib.dbroker.ui.stockinfo.TradeLogActivity
import nz.co.seclib.dbroker.utils.AESEncryption
import nz.co.seclib.dbroker.viewmodel.SystemConfigViewModel
import nz.co.seclib.dbroker.viewmodel.SystemConfigViewModelFactory

class SystemConfigActivity : AppCompatActivity() , CoroutineScope by MainScope(){
    private lateinit var systemConfigViewModel: SystemConfigViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_config)

        //val etTimerInterval = findViewById<EditText>(R.id.etTimerInterval)

        systemConfigViewModel = SystemConfigViewModelFactory(
            this.application
        ).create(
            SystemConfigViewModel::class.java)

        systemConfigViewModel.timerInterval.observe(this, Observer {
            etTimerInterval.setText(it)
        })
        systemConfigViewModel.timerEnable.observe(this, Observer {
            etTimerEnable.setText(it)
        })
        systemConfigViewModel.userName.observe(this, Observer {
            etUserName.setText(it)
        })
        systemConfigViewModel.password.observe(this, Observer {
            etPassword.setText(it)
        })


        ivSave.setOnClickListener {
            systemConfigViewModel.saveTimerIntervalToDB(etTimerInterval.text.toString())
            systemConfigViewModel.saveTimerEnableToDB(etTimerEnable.text.toString())
            systemConfigViewModel.saveUserNameToDB(etUserName.text.toString())
            systemConfigViewModel.savePasswordToDB(AESEncryption.encrypt( etPassword.text.toString()).toString())
            Toast.makeText(this,"System properties are stored in database!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }


    override fun onOptionsItemSelected( item: MenuItem) :Boolean{
        when (item.itemId){
            R.id.menu_selected_stocks -> {
                val intent = Intent(this, SelectedStocksActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_info -> {
                val intent = Intent(this, StockInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_stock_trade_info -> {
                val intent = Intent(this, TradeLogActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_system_parameters -> {
                val intent = Intent(this, SystemConfigActivity::class.java)
                startActivity(intent)
            }

        }
        return true
    }
}
