package controllers

import (
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func GetAllTransactions(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	query := "SELECT * FROM transactions"

	userID := r.URL.Query()["userID"]
	productID := r.URL.Query()["productID"]
	if userID != nil {
		fmt.Println(userID[0])
		query += " WHERE userID='" + userID[0] + "'"
	}
	if productID != nil {
		if userID != nil {
			query += " AND"
		} else {
			query += " WHERE"
		}
		query += " productID='" + productID[0] + "'"
	}
	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		MessageResponse(w, 400, "Something went wrong, please try again.")
		return
	}

	var transaction Transaction
	var transactions []Transaction
	for rows.Next() {
		if err := rows.Scan(&transaction.ID, &transaction.UserID, &transaction.ProductID, &transaction.Quantity); err != nil {
			log.Println(err)
			return
		} else {
			transactions = append(transactions, transaction)
		}
	}

	if len(transactions) < 5 {
		DataResponse(w, 200, "Success", transactions)
	} else {
		MessageResponse(w, 400, "Error, Incorrect Array Size")
	}
}

func DeleteTransaction(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}
	vars := mux.Vars(r)
	transactionId := vars["transaction_id"]

	_, errQuery := db.Exec("DELETE FROM transactions WHERE id=?", transactionId)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Delete Failed")
	}
}

func InsertTransaction(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		MessageResponse(w, 400, "failed")
	}
	userID, _ := strconv.Atoi(r.Form.Get("userID"))
	productID, _ := strconv.Atoi(r.Form.Get("productID"))
	quantity, _ := strconv.Atoi(r.Form.Get("quantity"))

	_, errQuery := db.Exec("INSERT INTO transactions (UserID, ProductID, Quantity) values (?,?,?)",
		userID,
		productID,
		quantity,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Insert Failed")
	}
}

func UpdateTransaction(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}

	userID, _ := strconv.Atoi(r.Form.Get("userID"))
	productID, _ := strconv.Atoi(r.Form.Get("productID"))
	quantity, _ := strconv.Atoi(r.Form.Get("quantity"))
	vars := mux.Vars(r)
	transactionId := vars["transaction_id"]

	var transaction Transaction
	transaction.ID, _ = strconv.Atoi(transactionId)
	transaction.UserID = userID
	transaction.ProductID = productID
	transaction.Quantity = quantity

	_, errQuery := db.Exec("UPDATE transactions SET UserID = ?, ProductID = ?, Quantity = ? WHERE id = ?",
		userID,
		productID,
		quantity,
		transactionId,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Update Failed")
	}
}
