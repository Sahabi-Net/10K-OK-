package com.example.a10kok.api

import com.example.a10kok.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import com.example.a10kok.response.BaseResponse
import com.example.a10kok.response.CategoryResponse
import com.example.a10kok.response.CheckoutResponse
import com.example.a10kok.response.MitraResponse
import com.example.a10kok.response.OrderDetailResponse
import com.example.a10kok.response.OrderResponse
import com.example.a10kok.response.PaymentResponse
import com.example.a10kok.response.ProductResponse
import com.example.a10kok.response.ProductDetailResponse
import com.example.a10kok.response.UploadResponse

interface ApiService {
    @GET("users/get_mitra.php")
    fun getMitra(): Call<MitraResponse>

    @GET("orders/get_orders_mitra.php")
    fun getOrdersMitra(
        @Query("id_mitra") idMitra:String
    ): Call<OrderResponse>

    @FormUrlEncoded
    @POST("users/update_status.php")
    fun updateStatusMitra(

        @Field("id_user")
        idUser:String,

        @Field("status")
        status:String

    ):Call<BaseResponse>

    @FormUrlEncoded
    @POST("orders/update_status.php")
    fun updateStatus(
        @Field("id_order") idOrder: String,
        @Field("status") status: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("orders/checkout.php")
    fun checkout(
        @Field("id_user") idUser: String,
        @Field("metode_pengambilan") metodePengambilan: String,
        @Field("alamat_pengiriman") alamatPengiriman: String,
        @Field("catatan") catatan: String,
        @Field("items") items: String,
        @Field("payment_method") paymentMethod: String
    ): Call<CheckoutResponse>

    @FormUrlEncoded
    @POST("payment/payment.php")
    fun payment(
        @Field("id_order") idOrder: String,
        @Field("metode_pembayaran") metodePembayaran: String
    ): Call<PaymentResponse>

    @GET("orders/get_orders.php")
    fun getOrders(): Call<OrderResponse>

    @GET("orders/get_order_detail.php")
    fun getOrderDetail(
        @Query("id_order") idOrder: String
    ): Call<OrderDetailResponse>

    @Multipart
    @POST("products/upload_image.php")
    fun uploadImage(
        @Part gambar: MultipartBody.Part
    ): Call<UploadResponse>

    @GET("categories/get_categories.php")
    fun getCategory(): Call<CategoryResponse>

    @GET("products/get_products.php")
    fun getProducts(
        @Query("id_mitra") idMitra: String? = null
    ): Call<ProductResponse>

    @GET("products/get_product.php")
    fun getProduct(
        @Query("id_produk")
        idProduk:String
    ): Call<ProductDetailResponse>

    @FormUrlEncoded
    @POST("auth/login.php")
    fun login(
        @Field("email")
        email:String,
        @Field("password")
        password:String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/register.php")
    fun register(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<BaseResponse>
    
    @FormUrlEncoded
    @POST("auth/register_mitra.php")
    fun registerMitra(
        @Field("nama") nama: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_hp") noHp: String,
        @Field("alamat") alamat: String
    ): Call<BaseResponse>
    
    @FormUrlEncoded
    @POST("auth/forgot_password.php")
    fun forgotPassword(
        @Field("email") email: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("reviews/insert_reviews.php")
    fun insertReview(
        @Field("id_order")
        idOrder:String,
        @Field("id_produk")
        idProduk:String,
        @Field("id_user")
        idUser:String,
        @Field("rating")
        rating:Int,
        @Field("komentar")
        komentar:String
    ):Call<BaseResponse>

    //CRUD PRODUCT
    @FormUrlEncoded
    @POST("products/delete_product.php")
    fun deleteProduct(
        @Field("id_produk")
        idProduk:String
    ):Call<BaseResponse>

    @FormUrlEncoded
    @POST("products/insert_product.php")
    fun tambahProduk(
        @Field("id_produk")
        idProduk:String,
        @Field("id_mitra")
        idMitra:String,
        @Field("id_category")
        idCategory:String,
        @Field("nama_produk")
        nama:String,
        @Field("deskripsi")
        deskripsi:String,
        @Field("harga")
        harga:String,
        @Field("stok")
        stok:String,
        @Field("gambar")
        gambar:String

    ):Call<BaseResponse>

    @FormUrlEncoded
    @POST("products/update_product.php")
    fun updateProduct(
        @Field("id_produk")
        idProduk:String,
        @Field("id_category")
        idCategory:String,
        @Field("nama_produk")
        nama:String,
        @Field("deskripsi")
        deskripsi:String,
        @Field("harga")
        harga:String,
        @Field("stok")
        stok:String,
        @Field("gambar")
        gambar:String
    ):Call<BaseResponse>


}
