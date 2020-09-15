package mibnu.team.mobiledinkes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_data.*
import mibnu.team.mobiledinkes.R
import mibnu.team.mobiledinkes.models.Data
import mibnu.team.mobiledinkes.utils.Utilities
import mibnu.team.mobiledinkes.viewmodels.DataState
import mibnu.team.mobiledinkes.viewmodels.DataViewModels

class DetailDataActivity : AppCompatActivity() {
    private lateinit var dataViewModels: DataViewModels
    private lateinit var keterangan_II: String
    private  var ket_ktp_meninggal =""
    private  var ket_kk_meninggal= ""
    private  var ket_jamkesmas=""
    private  var ket_ktp_waris=""
    private  var ket_kk_waris=""
    private  var ket_akta_kematian=""
    private  var ket_pakta_waris=""
    private  var ket_pernyataan_waris=""
    private  var ket_buku_tabungan=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_data)
        dataViewModels = ViewModelProvider(this).get(DataViewModels::class.java)
        dataViewModels.listenToUIState().observer(this, Observer {
            handleui(it)
        })
        fill()
        confirmed_II()
        checkBoxBehavior()
        checkButtonYesAcceptable()
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
                .load(it.kkMeninggalUrl)
                .apply(RequestOptions())
                .into(img_kk_meninggal)
            Glide.with(this)
                .load(it.jamkesmasMeninggalUrl)
                .apply(RequestOptions())
                .into(img_jamkesmas)
            Glide.with(this)
                .load( it.ktpWarisUrl)
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
                .load(it.paktaWarisUrl)
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
    private  fun confirmed_II (){
        btnYes.setOnClickListener {
            val id = getPassedData()?.kodeBerkas!!

            if (opsi1.isChecked()){
                keterangan_II = resources.getString(R.string.opsiSatu)
            }
            val confirmedII = true
            dataViewModels.updateConfirmedII("Bearer ${Utilities.getToken(this@DetailDataActivity)!!}",id,confirmedII,keterangan_II,ket_ktp_meninggal,ket_kk_meninggal,ket_jamkesmas,ket_ktp_waris,ket_kk_waris,ket_akta_kematian,ket_pakta_waris,ket_pernyataan_waris,ket_buku_tabungan)
        }

        btnNo.setOnClickListener {
            val id = getPassedData()?.kodeBerkas!!
            if (opsi2.isChecked()){
                keterangan_II = resources.getString(R.string.opsiDua)
            }
            if (cb_ketKtp.isChecked()){
                ket_ktp_meninggal = resources.getString(R.string.ktp_meninggal)
            }
            if (cb_ketKk.isChecked()){
                ket_kk_meninggal = resources.getString(R.string.kk_meninggal)
            }
            if (cb_ketJamkesmas.isChecked()){
                ket_jamkesmas = resources.getString(R.string.jamkesmas)
            }
            if (cb_ketKtpWaris.isChecked()){
                ket_ktp_waris = resources.getString(R.string.ktp_ahli_waris)
            }
            if (cb_ketKkWaris.isChecked()){
                ket_kk_waris = resources.getString(R.string.kk_ahli_waris)
            }
            if (cb_ketAktaKematian.isChecked()){
                ket_akta_kematian = resources.getString(R.string.akta_kematian)
            }
            if (cb_ketIntegritas.isChecked()){
                ket_pakta_waris = resources.getString(R.string.pakta_integritas_ahli_waris)
            }
            if (cb_ketPernyataanWaris.isChecked()){
                ket_pernyataan_waris = resources.getString(R.string.surat_pernyataan_waris)
            }
            if (cb_ketBukuTabungan.isChecked()){
                ket_buku_tabungan = resources.getString(R.string.buku_tabungan)
            }

            val confirmedII = false
            dataViewModels.updateConfirmedII("Bearer ${Utilities.getToken(this@DetailDataActivity)!!}",id,confirmedII,keterangan_II,ket_ktp_meninggal,ket_kk_meninggal,ket_jamkesmas,ket_ktp_waris,ket_kk_waris,ket_akta_kematian,ket_pernyataan_waris,ket_pakta_waris,ket_buku_tabungan)
        }
    }

    private fun getPassedData() = intent.getParcelableExtra<Data>("DATA")


    private fun checkButtonYesAcceptable(){

        btnYes.isEnabled = !(!cb_ktp.isChecked || !cb_kk_meninggal.isChecked || !cb_jamkesmas.isChecked || !cb_ktp_waris.isChecked || !cb_kk_waris.isChecked
                || !cb_akta_kematian.isChecked || !cb_pernyataan_waris.isChecked || !cb_integritas_waris.isChecked || !cb_buku_tabungan.isChecked || !opsi1.isChecked)
        btnNo.isEnabled = !(!cb_ktp.isChecked || !cb_kk_meninggal.isChecked || !cb_jamkesmas.isChecked || !cb_ktp_waris.isChecked || !cb_kk_waris.isChecked
                || !cb_akta_kematian.isChecked || !cb_pernyataan_waris.isChecked || !cb_integritas_waris.isChecked || !cb_buku_tabungan.isChecked || !opsi2.isChecked)
        opsi2.isChecked = !(!cb_ketKtp.isChecked && !cb_ketKk.isChecked && !cb_ketJamkesmas.isChecked && !cb_ketKtpWaris.isChecked && !cb_ketKkWaris.isChecked && !cb_ketAktaKematian.isChecked && !cb_ketPernyataanWaris.isChecked && !cb_ketIntegritas.isChecked && !cb_ketBukuTabungan.isChecked)
        opsi1.isChecked= (!cb_ketKtp.isChecked && !cb_ketKk.isChecked && !cb_ketJamkesmas.isChecked && !cb_ketKtpWaris.isChecked && !cb_ketKkWaris.isChecked && !cb_ketAktaKematian.isChecked && !cb_ketPernyataanWaris.isChecked && !cb_ketIntegritas.isChecked && !cb_ketBukuTabungan.isChecked)
    }

    private fun checkBoxBehavior(){
        cb_ktp.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_kk_meninggal.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_jamkesmas.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_ktp_waris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_akta_kematian.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_pernyataan_waris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_integritas_waris.setOnCheckedChangeListener { v,isChecked -> checkButtonYesAcceptable() }
        cb_kk_waris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable() }
        cb_buku_tabungan.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        opsi1.setOnCheckedChangeListener { v, isChecked ->  checkButtonYesAcceptable()}
        opsi2.setOnCheckedChangeListener { v, isChecked ->  checkButtonYesAcceptable()}
        cb_ketKk.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketKtp.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketJamkesmas.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketKtpWaris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketKkWaris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketAktaKematian.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketIntegritas.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketBukuTabungan.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
        cb_ketPernyataanWaris.setOnCheckedChangeListener { v, isChecked -> checkButtonYesAcceptable()  }
    }
}

