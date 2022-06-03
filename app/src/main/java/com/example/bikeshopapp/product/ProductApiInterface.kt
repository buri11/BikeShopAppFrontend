package com.example.bikeshopapp.product

import retrofit2.Call
import retrofit2.http.*

interface ProductApiInterface {

    @GET("product")
    fun getProduct(): Call<List<ProductDataItem>>

    @POST("product")
    fun postProduct(@Body product: ProductDataItem): Call<ProductDataItem>

    @DELETE("product/{id}")
    fun deleteProduct(@Path("id") id: Long): Call<ProductDataItem>

    @FormUrlEncoded
    @PUT("product/{id}")
    fun updateProduct(
        @Path("id") id: Long,
        @Field("name") productName: String,
        @Field("brand") productBrand: String,
        @Field("quantity") productQuantity: Int,
        @Field("price") productPrice: Float
    ): Call<ProductDataItem>
}