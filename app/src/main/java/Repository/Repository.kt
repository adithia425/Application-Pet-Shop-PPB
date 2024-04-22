package Repository

import Api.ApiService
import Response.Cart
import Response.CartItem
import Response.CartResponse
import Response.LoginResponse
import Response.PaymentRequest
import Response.PaymentResponse
import Response.Product
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String?) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

class Repository(private val apiService: ApiService) {

    fun login(username: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(username, password).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to login: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to login: ${e.message}"))
        }
    }

    fun getProducts(): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProducts().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to fetch products: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to fetch products: ${e.message}"))
        }
    }

    fun getProductById(id: Int): LiveData<Result<Product>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProductById(id).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to fetch product: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to fetch product: ${e.message}"))
        }
    }

    fun addToCart(cartItem: CartItem): LiveData<Result<CartResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addToCart(cartItem).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to add to cart: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to add to cart: ${e.message}"))
        }
    }

    fun getCart(): LiveData<Result<Cart>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getCart().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to fetch cart: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to fetch cart: ${e.message}"))
        }
    }

    fun removeFromCart(id: Int): LiveData<Result<Unit>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.removeFromCart(id).execute()
            if (response.isSuccessful) {
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error("Failed to remove from cart: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to remove from cart: ${e.message}"))
        }
    }

    fun makePayment(paymentRequest: PaymentRequest): LiveData<Result<PaymentResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.makePayment(paymentRequest).execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to make payment: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Failed to make payment: ${e.message}"))
        }
    }

    fun getProfile(): LiveData<Result<ContactsContract.Profile>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProfile().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(body))
                } else {
                    emit(Result.Error("Empty response body"))
                }
            } else {
                emit(Result.Error("Failed to fetch profile: ${response.code()}"))
            }
        } catch(e: Exception) {
            emit(Result.Error("Failed to fetch profile: ${e.message}"))
        }
    }
}
