package com.sk.myproduct.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sk.myproduct.data.Result
import com.sk.myproduct.utils.AppController
import com.sk.myproduct.utils.checkTrim
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel(application:Application):AndroidViewModel(application) {
    val context = application as AppController
    val apiBaseRequest = context.apiRepository

    val emailTxt = MutableLiveData<String>("")
    val passwordTxt = MutableLiveData<String>("")
    val errorAlert = MutableLiveData<String>("")


    fun onSubmit() {

        //val strEmail = emailTxt.value!!.trim()
        val strEmail = emailTxt.value!!.checkTrim()
        val strPwd = passwordTxt.value!!.trim()

        when {
            strEmail.isEmpty() -> {
                errorAlert.value = "Email is empty"
            }
            strPwd.isEmpty() -> {
                errorAlert.value = "Password is empty"
            }
            else -> {
                errorAlert.value = ""
                loginApi()
            }
        }

    }

    fun loginApi() {

        // Check internet connection

        if (context.checkInternet()) {
            viewModelScope.launch {
                // Loader show

                val jObj = JSONObject()
                jObj.put("member_id", emailTxt.value!!.trim())
                jObj.put("password", passwordTxt.value!!.trim())
                jObj.put("deviceToken", "dsadasdascde");
                jObj.put("status", "1");

                val apiRequest = apiBaseRequest.apiCall(context, "login", jObj)

                if (apiRequest is Result.Success) {

                    // hide loader
                    Log.e("TAG", "Success:${apiRequest.data}")

                    val jsonObject = JSONObject(apiRequest.data.toString())

                    if (jsonObject.getString("status") == "200") {
                        // process data
                        val userresponse: JSONObject = jsonObject.getJSONObject("user")
                        emailTxt.value = userresponse.getString("email")

                    } else if (jsonObject.getString("status") == "422") {
                        // show alert
                    }


                    emailTxt.value = jsonObject.getString("email")


                    // API request success method
                } else if (apiRequest is Result.Error) {
                    // API failure
                    //hide loader
                    Log.e("TAG", "Error:${apiRequest.errorMessage}")
                }

            }
        }

    }

}