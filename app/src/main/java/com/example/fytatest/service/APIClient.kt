package com.example.fytatest.service

import retrofit2.Retrofit


class APIClient {
    var retrofit = Retrofit.Builder()
        .baseUrl("https://example.identification.")
        .build()

    var service: APIService = retrofit.create(APIService::class.java)




}