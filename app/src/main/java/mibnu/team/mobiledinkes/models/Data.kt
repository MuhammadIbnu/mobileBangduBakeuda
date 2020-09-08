package mibnu.team.mobiledinkes.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @SerializedName("kd_berkas") var kodeBerkas : Int? = null,
    @SerializedName("ktp_meninggal") var ktpMeninggalUrl : String?=null,
    @SerializedName("kk_meninggal") var kkMeninggalUrl : String?=null,
    @SerializedName("jamkesmas") var jamkesmasMeninggalUrl : String?=null,
    @SerializedName("ktp_waris") var ktpWarisUrl : String?=null,
    @SerializedName("kk_waris") var kkWarisUrl : String?=null,
    @SerializedName("akta_kematian") var aktaKematianUrl : String?=null,
    @SerializedName("pakta_waris") var paktaWarisUrl : String?=null,
    @SerializedName("pernyataan_waris") var pernyataanWarisUrl : String?=null,
    @SerializedName("buku_tabungan") var bukuTabunganUrl : String?=null,
    @SerializedName("keterangan") var keterangan : String?=null,
    @SerializedName("keterangan_II") var keteranganII : String?=null,
    @SerializedName("keterangan_III") var keteranganIII : String?=null,
    @SerializedName("ket_ktp_meninggal") var ket_ktp_meninggal : String?=null,
    @SerializedName("ket_kk_meninggal") var ket_kk_meninggal : String?=null,
    @SerializedName("ket_jamkesmas") var ket_jamkesmas : String?=null,
    @SerializedName("ket_ktp_waris") var ket_ktp_waris : String?=null,
    @SerializedName("ket_kk_waris") var ket_kk_waris : String?=null,
    @SerializedName("ket_akta_kematian") var ket_akta_kematian : String?=null,
    @SerializedName("ket_pakta_waris") var ket_pakta_waris : String?=null,
    @SerializedName("ket_pernyataan_waris") var ket_pernyataan_waris : String?=null,
    @SerializedName("ket_buku_tabungan") var ket_buku_tabungan : String?=null,
    @SerializedName("confirmed_I") var confirmedI : Boolean?=null,
    @SerializedName("confirmed_II") var confirmedII : Boolean?=null,
    @SerializedName("waris") var waris : Waris? = null,
    @SerializedName("petugas") var petugas: Petugas?=null,
    @SerializedName("bakuda") var bakuda: Bakuda?=null
):Parcelable