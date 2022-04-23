package com.example.fytatest.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {

    @Multipart
    @POST("service?api-key=2b10LPPlQzCC2m7EXa2lAV380")
    fun uploadFile(@Part body: MultipartBody.Part)

}
