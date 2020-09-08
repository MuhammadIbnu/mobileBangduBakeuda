package mibnu.team.mobiledinkes.webservice

import com.google.gson.annotations.SerializedName
import mibnu.team.mobiledinkes.models.Data
import mibnu.team.mobiledinkes.models.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        private var retrofit: Retrofit? =null
        private const val ENDPOINT="https://bangdu.herokuapp.com/"
        private  var options = OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30,TimeUnit.SECONDS)
            writeTimeout(30,TimeUnit.SECONDS)
        }.build()

        private fun getClient(): Retrofit{
            return if (retrofit == null){
                retrofit = Retrofit.Builder().apply {
                    baseUrl(ENDPOINT)
                    addConverterFactory(GsonConverterFactory.create())
                    client(options)
                }.build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
        fun instance() = getClient().create(ApiService::class.java)
    }
}
interface ApiService{
    @FormUrlEncoded
    @POST("api/bakuda/login")
    fun login(@Field("username") username:String, @Field("password") password:String): Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("api/bakuda/update")
    fun update(@Header("Authorization") token: String, @Field("password") password:String): Call<WrappedResponse<User>>

    @GET("api/bakuda/profile")
    fun profile(@Header("Authorization") token: String): Call<WrappedResponse<User>>

    @GET("api/bakuda/dataconfirmedI")
    fun  dataConfirmedI(@Header("Authorization") token:String): Call<WrappedListResponse<Data>>

    @GET("api/bakuda/dataconfirmedII")
    fun  dataConfirmedII(@Header("Authorization") token:String): Call<WrappedListResponse<Data>>

    @FormUrlEncoded
    @POST("api/bakuda/berkas/{kd_berkas}")
    fun dataUpdate(@Header("Authorization") token: String, @Path("kd_berkas") id:String,
                   @Field("confirmed_II") confirmed_II: Boolean,
                   @Field("keterangan_II") keteranganII : String,
                   @Field("ket_ktp_meninggal") ket_ktp_meninggal : String,
                   @Field("ket_kk_meninggal") ket_kk_meninggal : String,
                   @Field("ket_jamkesmas") ket_jamkesmas : String,
                   @Field("ket_ktp_waris") ket_ktp_waris : String,
                   @Field("ket_kk_waris") ket_kk_waris : String,
                   @Field("ket_akta_kematian") ket_akta_kematian : String,
                   @Field("ket_pernyataan_waris") ket_pernyataan_waris : String,
                   @Field("ket_pakta_waris") ket_pakta_waris : String,
                   @Field("ket_buku_tabungan") ket_buku_tabungan : String): Call<WrappedResponse<Data>>
}

data class WrappedListResponse<T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status :Boolean,
    @SerializedName("data") var  data : List<T>
)


data class WrappedResponse<T>(
    @SerializedName("message") var message : String,
    @SerializedName("status") var status :Boolean,
    @SerializedName("data") var  data : T
)