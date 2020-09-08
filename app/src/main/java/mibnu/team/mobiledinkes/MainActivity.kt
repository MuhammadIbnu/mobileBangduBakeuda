package mibnu.team.mobiledinkes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mibnu.team.mobiledinkes.utils.Utilities
import mibnu.team.mobiledinkes.view.DataMasukActivity
import mibnu.team.mobiledinkes.view.DataValidActivity
import mibnu.team.mobiledinkes.view.ProfileActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread(Runnable {
            if (Utilities.getToken(this@MainActivity) == null) {
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                finish()
            }
        }).start()
        dashboardMenus()
    }

    private fun dashboardMenus() {
        dataMasuk.setOnClickListener {
            startActivity(Intent(this,DataMasukActivity::class.java))
        }

        dataValid.setOnClickListener {
            startActivity(Intent(this,DataValidActivity::class.java))
        }
        imageView2.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }
}
