package nz.co.seclib.dbroker.ui.userinfo

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request_confirm.*
import nz.co.seclib.dbroker.R

class RequestConfirmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_confirm)

        imConfirmCancle.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        imConfirmOK.setOnClickListener{
//            val returnIntent = Intent()
//            returnIntent.putExtra("result", result)
//            setResult(Activity.RESULT_OK, returnIntent)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
