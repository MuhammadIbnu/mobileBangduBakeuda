package mibnu.team.mobiledinkes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import mibnu.team.mobiledinkes.MainActivity
import mibnu.team.mobiledinkes.R
import mibnu.team.mobiledinkes.utils.Utilities
import mibnu.team.mobiledinkes.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userViewModel= ViewModelProvider(this).get(UserViewModel::class.java)
        dologin()
        userViewModel.listenUIState().observer(this, Observer { handlesState(it) })

    }

    private fun dologin() {
        login.setOnClickListener {
            val Username = username.text.toString().trim()
            val Password = password.text.toString().trim()
            if (userViewModel.validate(Username,Password)){
                userViewModel.login(Username,Password)
            }
        }
    }

    private fun toast(m : String) = Toast.makeText(this,m,Toast.LENGTH_SHORT).show()
    private fun setErrorUsername(err:String?){in_username.error=err}
    private  fun setErrorPassword(err:String?){in_password.error=err}
    private fun handlesState(it:UserViewModel.UserState){
        when(it){
            is UserViewModel.UserState.ShowAlert -> UserViewModel.UserState.ShowAlert(it.message)
            is UserViewModel.UserState.ShowToast ->toast(it.message)
            is UserViewModel.UserState.IsLoading -> login.isEnabled = !it.state
            is UserViewModel.UserState.Reset->{
                setErrorUsername(null)
                setErrorPassword(null)
            }
            is UserViewModel.UserState.Validate->{
                it.username?.let{ e -> setErrorUsername(e)}
                it.password?.let{ e -> setErrorPassword(e) }
            }
            is UserViewModel.UserState.Success ->{
                Utilities.setToken(this@LoginActivity, it.param)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }
    private fun showAlert(message : String){
        AlertDialog.Builder(this).apply {
            setMessage(message)
            setPositiveButton("Mengerti"){dialog, which ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        if(Utilities.getToken(this) != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
}
