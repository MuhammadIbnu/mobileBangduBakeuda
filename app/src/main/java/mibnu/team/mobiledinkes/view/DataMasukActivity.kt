package mibnu.team.mobiledinkes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_data_masuk.*
import mibnu.team.mobiledinkes.R
import mibnu.team.mobiledinkes.adapter.DataAdapter
import mibnu.team.mobiledinkes.models.Data
import mibnu.team.mobiledinkes.utils.Utilities
import mibnu.team.mobiledinkes.viewmodels.DataState
import mibnu.team.mobiledinkes.viewmodels.DataViewModels

class DataMasukActivity : AppCompatActivity() {
    private lateinit var dataViewModels: DataViewModels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_masuk)
        setupUI()

        dataViewModels = ViewModelProvider(this).get(DataViewModels::class.java)
        dataViewModels.listenToUIState().observer(this, Observer { handleUIState(it) })
        dataViewModels.listenToDatas().observe(this, Observer { handleData(it) })

        swipe.setOnRefreshListener {
            dataViewModels.listenToDatas().observe(this, Observer { handleData(it) })
            swipe.isRefreshing = false
        }
    }

    private fun setupUI() {
        rv_data.apply {
            layoutManager = LinearLayoutManager(this@DataMasukActivity)
            adapter = DataAdapter(mutableListOf(),this@DataMasukActivity)
        }
    }

    private fun handleData(it:List<Data>){
        rv_data.adapter?.let { adapter -> adapter as DataAdapter
        adapter.updateList(it)}
    }
    private fun handleUIState(it: DataState){
        when(it){
            is DataState.ShowToast -> toast(it.message)
            is DataState.IsLoading -> {
                if(it.state){
                    loading.visibility = View.VISIBLE
                }else{
                    loading.visibility = View.GONE
                }
            }
        }
    }

    private fun toast(m : String) = Toast.makeText(this, m, Toast.LENGTH_LONG).show()

    override fun onResume() {
        super.onResume()
        Utilities.getToken(this)?.let { t -> dataViewModels.fetchDatas("Bearer $t") }
    }


}
