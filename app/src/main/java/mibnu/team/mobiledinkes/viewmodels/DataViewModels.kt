package mibnu.team.mobiledinkes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mibnu.team.mobiledinkes.models.Data
import mibnu.team.mobiledinkes.utils.SingleLiveEvent
import mibnu.team.mobiledinkes.webservice.ApiClient
import mibnu.team.mobiledinkes.webservice.WrappedListResponse
import mibnu.team.mobiledinkes.webservice.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModels  : ViewModel(){
    private val api = ApiClient.instance()
    private var state : SingleLiveEvent<DataState> = SingleLiveEvent()
    private var datas = MutableLiveData<List<Data>>()

    private fun setLoading(){state.value = DataState.IsLoading(true)}
    private fun hideLoading(){state.value = DataState.IsLoading(false)}
    private fun showToast(mesage : String){state.value = DataState.ShowToast(mesage)}

    fun fetchDatas(token : String){
        setLoading()
        api.dataConfirmedI(token).enqueue(object : Callback<WrappedListResponse<Data>>{
            override fun onFailure(call: Call<WrappedListResponse<Data>>, t: Throwable) {
                println(t.message)
                println(t.printStackTrace())
                hideLoading()
                showToast(t.message.toString())

            }

            override fun onResponse(
                call: Call<WrappedListResponse<Data>>,
                response: Response<WrappedListResponse<Data>>
            ) {
                if (response.isSuccessful){
                    val b = response.body()
                    println(b.toString())
                    datas.postValue(b?.data)
                }else{
                    showToast("kesalahan saat mengambil data")
                }
                hideLoading()
            }
        })
    }



    fun fetchDataII(token : String){
        setLoading()
        api.dataConfirmedII(token).enqueue(object : Callback<WrappedListResponse<Data>>{
            override fun onFailure(call: Call<WrappedListResponse<Data>>, t: Throwable) {
                println(t.message)
                println(t.printStackTrace())
                hideLoading()
                showToast(t.message.toString())

            }

            override fun onResponse(
                call: Call<WrappedListResponse<Data>>,
                response: Response<WrappedListResponse<Data>>
            ) {
                if (response.isSuccessful){
                    val b = response.body()
                    println(b.toString())
                    datas.postValue(b?.data)
                }else{
                    showToast("kesalahan saat mengambil data")
                }
                hideLoading()
            }
        })
    }


    fun updateConfirmedII(token: String, id:Int, confirmed_II: Boolean,keterangan_II: String, ket_ktp_meninggal: String, ket_kk_meninggal: String, ket_jamkesmas: String, ket_ktp_waris: String, ket_kk_waris: String, ket_akta_kematian: String, ket_pakta_kematian: String, ket_pernyataan_waris: String,ket_buku_tabungan:String){
        state.value = DataState.IsLoading(true)
        api.dataUpdate(token,id.toString(),confirmed_II, keterangan_II,ket_ktp_meninggal,ket_kk_meninggal,ket_jamkesmas,ket_ktp_waris,ket_kk_waris,ket_akta_kematian,ket_pakta_kematian,ket_buku_tabungan,ket_pernyataan_waris).enqueue(object  : Callback<WrappedResponse<Data>>{
            override fun onFailure(call: Call<WrappedResponse<Data>>, t: Throwable) {
                println("onFailure :"+t.message)
                println(t.printStackTrace())
                state.value = DataState.ShowToast("onFailure :"+t.message)
            }

            override fun onResponse(
                call: Call<WrappedResponse<Data>>,
                response: Response<WrappedResponse<Data>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body?.status!!){
                        val data = body.data
                        if (data.confirmedII!!){
                            state.value = DataState.ShowToast("Data Berhasil dikonfrimasi")
                            state.value = DataState.Success
                        }else{
                            state.value = DataState.ShowToast("Berhasil menolak data")
                            state.value = DataState.Success
                        }
                    }else{
                        println("Update gagal")
                        state.value = DataState.ShowToast("gagal")
                    }
                }else{

                    state.value = DataState.ShowToast("gagal")
                    println("gagal")
                }
                state.value = DataState.IsLoading(false)
            }

        })
    }

    fun listenToUIState() = state
    fun listenToDatas()= datas
    fun listeToDataValids() = datas

}

sealed class DataState{
    data class IsLoading(var state : Boolean) : DataState()
    data class ShowToast(var message : String) : DataState()
    object Reset : DataState()
    object Success: DataState()
}
