package mibnu.team.mobiledinkes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mibnu.team.mobiledinkes.models.User
import mibnu.team.mobiledinkes.utils.SingleLiveEvent
import mibnu.team.mobiledinkes.utils.Utilities
import mibnu.team.mobiledinkes.webservice.ApiClient
import mibnu.team.mobiledinkes.webservice.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel(){
    private  var api = ApiClient.instance()
    private  var state : SingleLiveEvent<UserState> = SingleLiveEvent()
    private  var curruntUser = MutableLiveData<User>()

    fun login(username : String, password:String){
        state.value = UserState.IsLoading(true)
        api.login(username, password).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                state.value = UserState.ShowToast(t.message.toString())
                state.value = UserState.IsLoading(false)
            }

            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful){
                    val body =response.body()
                    body?.let{
                        if (it.status){
                            state.value = UserState.Success(it.data.token!!)
                        }else{
                            state.value= UserState.ShowToast("Login gagal")
                        }
                    }
                }else{
                    state.value = UserState.ShowAlert("Tidak dapat masuk. Periksa kembali username dan kata sandi anda")
                }
                state.value = UserState.IsLoading(false)
            }

        })
    }

    fun validate(username: String, password: String):Boolean{
        state.value = UserState.Reset
        if (!Utilities.isValidUsername(username)){
            state.value = UserState.Validate(username = "Username tidak valid")
            return false
        }
        if (!Utilities.isValidPassword(password)){
            state.value = UserState.Validate(password = "Password tidak valid")
            return false
        }
        return true
    }
    fun profile(token:String){
        state.value = UserState.IsLoading(true)
        api.profile(token).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                state.value = UserState.ShowToast(t.message.toString())
                state.value = UserState.IsLoading(false)
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val b = response.body()
                    b?.let {
                        if(it.status){
                            curruntUser.postValue(it.data)
                            state.value = UserState.ShowToast(it.data?.name.toString())
                        }else{
                            state.value = UserState.ShowToast("gagal")
                        }
                    }
                }else{
                    state.value = UserState.ShowToast("gagal mengambil info")
                }
                state.value = UserState.IsLoading(false)
            }
        })
    }
    fun updateProfile(token: String, password: String) {
        state.value = UserState.IsLoading(true)
        api.update(token, password).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println("onFailure :" + t.message)
                println(t.printStackTrace())
                state.value = UserState.ShowToast("onFailure :" + t.message)
            }
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status!!) {
                        state.value = UserState.ShowToast("berhasil update Data")
                        state.value = UserState.Sucess
                    } else {
                        println("Update gagal")
                        state.value = UserState.ShowToast("gagal")
                    }
                } else {
                    state.value = UserState.ShowToast("gagal")
                    println("gagal")
                }
                state.value = UserState.IsLoading(false)
            }

        })
    }

    fun listenUIState() = state
    fun listenToCurrentUser() = curruntUser

    sealed class UserState{
        object Reset : UserState()
        data class ShowAlert(var message : String) : UserState()
        data class IsLoading(var state : Boolean) : UserState()
        data class ShowToast(var message: String) : UserState()
        data class Validate(var username: String? = null, var  password: String? = null) : UserState()
        data class Success(var param:String) : UserState()
        object Sucess: UserState()

    }
}