package controllers

import (
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func GetAllProducts(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	query := "SELECT * FROM products"

	name := r.URL.Query()["name"]
	price := r.URL.Query()["price"]
	if name != nil {
		fmt.Println(name[0])
		query += " WHERE name='" + name[0] + "'"
	}
	if price != nil {
		if name != nil {
			query += " AND"
		} else {
			query += " WHERE"
		}
		query += " price='" + price[0] + "'"
	}
	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		MessageResponse(w, 400, "Something went wrong, please try again.")
		return
	}

	var product Product
	var products []Product
	for rows.Next() {
		if err := rows.Scan(&product.ID, &product.Name, &product.Price); err != nil {
			log.Println(err)
			return
		} else {
			products = append(products, product)
		}
	}

	if len(products) < 5 {
		DataResponse(w, 200, "Success", products)
	} else {
		MessageResponse(w, 400, "Error, Incorrect Array Size")
	}
}

func DeleteProduct(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}
	vars := mux.Vars(r)
	productId := vars["product_id"]

	_, errQuery := db.Exec("DELETE FROM products WHERE id=?", productId)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Delete Failed")
	}
}

func InsertProduct(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		MessageResponse(w, 400, "failed")
	}
	name := r.Form.Get("name")
	price, _ := strconv.Atoi(r.Form.Get("price"))

	_, errQuery := db.Exec("INSERT INTO products (name, price) values (?,?)",
		name,
		price,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Insert Failed")
	}
}

func UpdateProduct(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}

	name := r.Form.Get("name")
	price, _ := strconv.Atoi(r.Form.Get("price"))
	vars := mux.Vars(r)
	productId := vars["product_id"]

	var product Product
	product.ID, _ = strconv.Atoi(productId)
	product.Name = name
	product.Price = price

	_, errQuery := db.Exec("UPDATE products SET name = ?, price = ? WHERE id = ?",
		name,
		price,
		productId,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Update Failed")
	}

}
