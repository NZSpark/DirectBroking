package nz.co.seclib.dbroker.sysinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_system_config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.data.DBrokerRoomDatabase
import nz.co.seclib.dbroker.data.SystemConfigRepository
import nz.co.seclib.dbroker.data.TradeLogRepository
import nz.co.seclib.dbroker.utils.AESEncryption

class SystemConfigActivity : AppCompatActivity() , CoroutineScope by MainScope(){
    private lateinit var systemConfigViewModel: SystemConfigViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_config)

        //val etTimerInterval = findViewById<EditText>(R.id.etTimerInterval)

        systemConfigViewModel = SystemConfigViewModelFactory(this.application).create(SystemConfigViewModel::class.java)

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
}
