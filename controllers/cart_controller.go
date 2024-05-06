package controllers

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

var shoppingCart ShoppingCart

func init() {
	// Initialize the shopping cart
	shoppingCart = ShoppingCart{
		Items: make([]CartItem, 0),
	}
}

// AddToCart adds a product to the shopping cart.
func AddToCart(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		MessageResponse(w, 400, "Failed to parse form data.")
		return
	}

	vars := mux.Vars(r)
	productID := vars["product_id"]

	// Retrieve product details from the database
	var product Product
	err = db.QueryRow("SELECT id, name, price FROM products WHERE id=?", productID).Scan(&product.ID, &product.Name, &product.Price)
	if err != nil {
		MessageResponse(w, 404, "Product not found.")
		return
	}

	// Extract quantity from request form
	qtyStr := r.Form.Get("quantity")
	quantity, err := strconv.Atoi(qtyStr)
	if err != nil || quantity <= 0 {
		MessageResponse(w, 400, "Invalid quantity.")
		return
	}

	// Add the item to the shopping cart
	cartItem := CartItem{
		ID:    product.ID,
		Name:  product.Name,
		Price: product.Price,
		Qty:   quantity,
	}
	shoppingCart.Items = append(shoppingCart.Items, cartItem)

	MessageResponse(w, 200, "Product added to cart successfully.")
}

// ViewCart displays the contents of the shopping cart.
func ViewCart(w http.ResponseWriter, r *http.Request) {
	json.NewEncoder(w).Encode(shoppingCart)
}

// RemoveFromCart removes a product from the shopping cart.
func RemoveFromCart(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	productID := vars["product_id"]

	// Convert product ID to int
	id, err := strconv.Atoi(productID)
	if err != nil {
		MessageResponse(w, 400, "Invalid product ID.")
		return
	}

	// Find and remove the item from the shopping cart
	for i, item := range shoppingCart.Items {
		if item.ID == id {
			// Remove the item from the slice
			shoppingCart.Items = append(shoppingCart.Items[:i], shoppingCart.Items[i+1:]...)
			MessageResponse(w, 200, "Product removed from cart successfully.")
			return
		}
	}

	// Product not found in the cart
	MessageResponse(w, 404, "Product not found in the cart.")
}

// ClearCart removes all items from the shopping cart.
func ClearCart(w http.ResponseWriter, r *http.Request) {
	// Reset the shopping cart to an empty state
	shoppingCart.Items = []CartItem{}
	MessageResponse(w, 200, "Shopping cart cleared successfully.")
}

// Checkout simulates the checkout process.
func Checkout(w http.ResponseWriter, r *http.Request) {
	// Here you can implement the logic for processing the checkout,
	// such as calculating the total price, updating inventory, etc.
	// For now, let's just print a message.
	fmt.Println("Checkout process initiated.")
	MessageResponse(w, 200, "Checkout process initiated.")
}
