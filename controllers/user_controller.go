package controllers

import (
	"fmt"
	"log"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
)

func GetAllUsers(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	query := "SELECT * FROM users"

	name := r.URL.Query()["name"]
	age := r.URL.Query()["age"]
	if name != nil {
		fmt.Println(name[0])
		query += " WHERE name='" + name[0] + "'"
	}
	if age != nil {
		if name != nil {
			query += " AND"
		} else {
			query += " WHERE"
		}
		query += " age='" + age[0] + "'"
	}
	rows, err := db.Query(query)
	if err != nil {
		log.Println(err)
		MessageResponse(w, 400, "Something went wrong, please try again.")
		return
	}

	var user User
	var users []User
	for rows.Next() {
		if err := rows.Scan(&user.ID, &user.Name, &user.Age, &user.Address); err != nil {
			log.Println(err)
			return
		} else {
			users = append(users, user)
		}
	}

	if len(users) < 5 {
		DataResponse(w, 200, "Success", users)
	} else {
		MessageResponse(w, 400, "Error, Incorrect Array Size")
	}
}

func DeleteUser(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}
	vars := mux.Vars(r)
	userId := vars["user_id"]

	_, errQuery := db.Exec("DELETE FROM users WHERE id=?", userId)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Delete Failed")
	}
}

func InsertUser(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		MessageResponse(w, 400, "failed")
	}
	name := r.Form.Get("name")
	age, _ := strconv.Atoi(r.Form.Get("age"))
	address := r.Form.Get("address")

	_, errQuery := db.Exec("INSERT INTO users (name, age, address) values (?,?,?)",
		name,
		age,
		address,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Insert Failed")
	}
}

func UpdateUser(w http.ResponseWriter, r *http.Request) {
	db := connect()
	defer db.Close()

	err := r.ParseForm()
	if err != nil {
		return
	}

	name := r.Form.Get("name")
	age, _ := strconv.Atoi(r.Form.Get("age"))
	address := r.Form.Get("address")
	vars := mux.Vars(r)
	userId := vars["user_id"]

	var user User
	user.ID, _ = strconv.Atoi(userId)
	user.Name = name
	user.Age = age
	user.Address = address

	_, errQuery := db.Exec("UPDATE users SET name = ?, age = ?, address = ? WHERE id = ?",
		name,
		age,
		address,
		userId,
	)

	if errQuery == nil {
		MessageResponse(w, 200, "Success")
	} else {
		fmt.Println(errQuery)
		MessageResponse(w, 400, "Update Failed")
	}

}
