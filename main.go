package main

import (
	"fmt"
	"log"
	"modul3/controllers"
	"net/http"

	"github.com/gorilla/mux"
)

func main() {
	router := mux.NewRouter()

	router.HandleFunc("/users", controllers.GetAllUsers).Methods("GET")
	router.HandleFunc("/products", controllers.GetAllProducts).Methods("GET")
	router.HandleFunc("/transactions", controllers.GetAllTransactions).Methods("GET")

	router.HandleFunc("/users", controllers.InsertUser).Methods("POST")
	router.HandleFunc("/products", controllers.InsertProduct).Methods("POST")
	router.HandleFunc("/transactions", controllers.InsertTransaction).Methods("POST")

	router.HandleFunc("/users/{user_id}", controllers.UpdateUser).Methods("PUT")
	router.HandleFunc("/products/{product_id}", controllers.UpdateProduct).Methods("PUT")
	router.HandleFunc("/transactions/{transaction_id}", controllers.UpdateTransaction).Methods("PUT")

	router.HandleFunc("/users/{user_id}", controllers.DeleteUser).Methods("DELETE")
	router.HandleFunc("/products/{product_id}", controllers.DeleteProduct).Methods("DELETE")
	router.HandleFunc("/transactions/{transaction_id}", controllers.DeleteTransaction).Methods("DELETE")
	router.HandleFunc("/users/{user_id}/cart", controllers.AddToCart).Methods("POST")
	router.HandleFunc("/users/{user_id}/cart", controllers.ViewCart).Methods("GET")
	router.HandleFunc("/users/{user_id}/cart/{product_id}", controllers.RemoveFromCart).Methods("DELETE")
	router.HandleFunc("/users/{user_id}/cart/clear", controllers.ClearCart).Methods("DELETE")
	router.HandleFunc("/users/{user_id}/cart/checkout", controllers.Checkout).Methods("POST")

	router.HandleFunc("/login", controllers.UserLogin).Methods("POST")

	http.Handle("/", router)
	fmt.Println("Connected to port 8080")
	log.Println("Connected to port 8080")
	log.Fatal(http.ListenAndServe(":8080", router))
}
