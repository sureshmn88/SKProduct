package com.sk.myproduct.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sk.myproduct.R
import com.sk.myproduct.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var viewDataBinding: ActivityLoginBinding
    val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewDataBinding.loginModel = viewModel
        viewDataBinding.lifecycleOwner = this
        setContentView(viewDataBinding.root)
        viewModel.errorAlert.observe(this, Observer {
            if (it.isNotEmpty()) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }
        })
    }
}