package com.sk.myproduct.data.source

import android.content.Context
import com.google.gson.JsonObject
import com.sk.myproduct.data.Result
import com.sk.myproduct.data.source.remote.ApiCall
import com.sk.myproduct.data.source.remote.SafeApiRequest
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

class DefaultTaskRepository(private val apiCall: ApiCall) : SafeApiRequest(), TaskRepository {

    private fun addCommonDetail(mContext: Context, jsonObject: JSONObject) {
        try {
            jsonObject.put("Platform", "Android")
            jsonObject.put("firebaseToken", "dsw314312413")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override suspend fun apiCall(
        context: Context,
        urlPath: String,
        jsonObject: JSONObject
    ): Result<JsonObject> {
        addCommonDetail(context, jsonObject)

        val jsonData: RequestBody = jsonObject.toString().toRequestBody()

        return apiRequest {
            apiCall.common(
                urlPath,
                "AWS4-HMAC-SHA256 Credential=/20210811/us-east-2/execute-api/aws4_request, SignedHeaders=accept;content-length;content-type;host;x-amz-date;x-api-key, Signature=a9bd657fbf8b42c37be69212fa9db84b102a1f834e57c21cebde69044a3b3646",
                "z35uin2aq1.execute-api.us-east-2.amazonaws.com",
                jsonData
            )
        }
    }
}