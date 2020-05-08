package nz.co.seclib.dbroker.ui.stockinfo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_stock_charts.*
import nz.co.seclib.dbroker.R
import nz.co.seclib.dbroker.utils.MyApplication

class SearchActivity: AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_search)


        val spAdepter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, MyApplication.stockMarketInfo.stockCodeList)
        spStockCodeList.adapter = spAdepter
        spStockCodeList.setSelection(spAdepter.getPosition("KMD"))

        btShowStockInfo.setOnClickListener {
            val intent = Intent(this,StockInfoActivity::class.java).apply {
                putExtra("STOCKCODE",spStockCodeList.selectedItem.toString())
            }
            startActivity(intent)
        }
    }

}
