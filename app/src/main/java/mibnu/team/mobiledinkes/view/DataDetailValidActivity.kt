package mibnu.team.mobiledinkes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_data_detail_valid.*
import mibnu.team.mobiledinkes.R
import mibnu.team.mobiledinkes.models.Data
import mibnu.team.mobiledinkes.viewmodels.DataState
import mibnu.team.mobiledinkes.viewmodels.DataViewModels

class DataDetailValidActivity : AppCompatActivity() {
    private lateinit var dataViewModels: DataViewModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_detail_valid)
        dataViewModels = ViewModelProvider(this).get(DataViewModels::class.java)
        dataViewModels.listenToUIState().observer(this, Observer {
            handleui(it)
        })
        fill()
    }
    private fun handleui(it:DataState){
        when(it){
            is DataState.ShowToast -> Toast.makeText(this, it.message,Toast.LENGTH_SHORT).show()
            is DataState.Success -> finish()
        }
    }

    private fun fill() {
        getPassedData()?.let {
            nama.text = it.waris!!.nama
            kk.text = it.waris!!.kk
            nik.text = it.waris!!.nik
            Glide.with(this)
                .load(it.ktpMeninggalUrl)
                .apply(RequestOptions())
                .into(img_Ktp_Meninggal)
            Glide.with(this)
                .load( it.kkMeninggalUrl)
                .apply(RequestOptions())
                .into(img_kk_meninggal)
            Glide.with(this)
                .load( it.jamkesmasMeninggalUrl)
                .apply(RequestOptions())
                .into(img_jamkesmas)
            Glide.with(this)
                .load(it.ktpWarisUrl)
                .apply(RequestOptions())
                .into(img_ktp_waris)
            Glide.with(this)
                .load( it.kkWarisUrl)
                .apply(RequestOptions())
                .into(img_kk_waris)
            Glide.with(this)
                .load( it.aktaKematianUrl)
                .apply(RequestOptions())
                .into(img_Akta_kematian)
            Glide.with(this)
                .load( it.paktaWarisUrl)
                .apply(RequestOptions())
                .into(img_integritas_waris)
            Glide.with(this)
                .load(it.pernyataanWarisUrl)
                .apply(RequestOptions())
                .into(img_pernyataan_waris)
            Glide.with(this)
                .load(it.bukuTabunganUrl)
                .apply(RequestOptions())
                .into(img_buku_tabungan)

        }
    }
    private fun getPassedData() = intent.getParcelableExtra<Data>("DATA")
}
