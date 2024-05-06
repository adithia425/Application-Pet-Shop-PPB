package Api

import Response.Cart
import Response.CartItem
import Response.CartResponse
import Response.LoginResponse
import Response.PaymentRequest
import Response.PaymentResponse
import Response.Product
import android.provider.ContactsContract
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/login")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<LoginResponse>

    @GET("/products")
    fun getProducts(): Call<List<Product>>

    @GET("/product/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product>

    @POST("/cart")
    fun addToCart(@Body cartItem: CartItem): Call<CartResponse>

    @GET("/cart")
    fun getCart(): Call<Cart>

    @DELETE("/cart/{id}")
    fun removeFromCart(@Path("id") id: Int): Call<Void>

}