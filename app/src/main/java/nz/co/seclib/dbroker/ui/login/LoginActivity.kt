package nz.co.seclib.dbroker.ui.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.ui.stockinfo.SearchActivity
import nz.co.seclib.dbroker.ui.stockinfo.SelectedStocksActivity
import nz.co.seclib.dbroker.viewmodel.LoginViewModel
import nz.co.seclib.dbroker.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        //supportActionBar!!.setTitle("DBroker (Direct Broking Mobile Client)")

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)

//        btShowCharts.setOnClickListener{
//            //val intent = Intent(this, TrendChartsActivity::class.java)
//            //val intent = Intent(this, TradeLogActivity::class.java)
//            val intent = Intent(this, SystemConfigActivity::class.java)
//            startActivity(intent)
//        }
//
//        btShowSelectedStocks.setOnClickListener{
//            val intent = Intent(this, SelectedStocksActivity::class.java)
//            startActivity(intent)
//        }



        loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory(this.application)
        )
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
                val intent = Intent(this, SearchActivity::class.java).apply {
                    putExtra("STOCKCODE","KMD")
                }
                startActivity(intent)
            }
            if (loginResult.success != null) {
                if(cbSaveToDB.isChecked)
                    loginViewModel.saveNetworkConfidential(username.text.toString(), password.text.toString())
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        loginViewModel.userName.observe(this, Observer {
            if(cbSaveToDB.isChecked)
                username.setText (it.toString())
        })

        loginViewModel.password.observe(this, Observer {
            if(cbSaveToDB.isChecked)
                password.setText (it.toString())
        })

//        username.afterTextChanged {
//            loginViewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//            )
//        }

//        password.apply {
////            afterTextChanged {
////                loginViewModel.loginDataChanged(
////                        username.text.toString(),
////                        password.text.toString()
////                )
////            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        loginViewModel.login(
//                                username.text.toString(),
//                                password.text.toString()
//                        )
//                }
//                false
//            }

        login.setOnClickListener {

//            val intent = Intent(this, StockChartActivity::class.java).apply {
//                putExtra("STOCKCODE","AIR")
//            }
//            val intent = Intent(this, StockInfoActivity::class.java).apply {
//                putExtra("STOCKCODE","AIR")
//            }
//            val intent = Intent(this, SystemConfigActivity::class.java)
            //val intent = Intent(this, SelectedStocksActivity::class.java)
            //val intent = Intent(this, UserInfoManagerActivity::class.java)

//            val intent = Intent(this, TradeLogActivity::class.java).apply {
//                putExtra("STOCKCODE","KMD")
//            }
//            startActivity(intent)

            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        //email feed back.
        tvAuthorEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL,tvAuthorEmail.text.toString())
                putExtra(Intent.EXTRA_SUBJECT,"Feed back on DBroker.app")
            }
            startActivity(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
//        val welcome = getString(R.string.welcome)
//        val displayName = model.displayName
//        // TODO : initiate successful logged in experience
//        Toast.makeText(
//                applicationContext,
//                "$welcome $displayName",
//                Toast.LENGTH_LONG
//        ).show()
        //val intent = Intent(this, SystemConfigActivity::class.java)
        val intent = Intent(this, SelectedStocksActivity::class.java)
        //val intent = Intent(this, UserInfoManagerActivity::class.java)
        startActivity(intent)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
