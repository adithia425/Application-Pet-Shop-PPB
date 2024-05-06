package controllers

type User struct {
	ID       int    `json:"id"`
	Name     string `json:"name"`
	Age      int    `json:"age"`
	Address  string `json:"address"`
	UserType int    `json:"user_type"`
	Email    string `json:"email"`
	Password string `json:"password"`
}

type Product struct {
	ID    int    `json:"id"`
	Name  string `json:"name"`
	Price int    `json:"price"`
}

type Transaction struct {
	ID        int `json:"TransactionID"`
	UserID    int `json:"UserID"`
	ProductID int `json:"ProductID"`
	Quantity  int `json:"quantity"`
}

type Response struct {
	Message string      `json:"error"`
	Status  int         `json:"status"`
	Data    interface{} `json:"data"`
}

type ErrorResponse struct {
	Message string `json:"error"`
	Status  int    `json:"status"`
}

// CartItem represents an item in the shopping cart.
type CartItem struct {
	ID    int    `json:"id"`
	Name  string `json:"name"`
	Price int    `json:"price"`
	Qty   int    `json:"quantity"`
}

// ShoppingCart represents the shopping cart.
type ShoppingCart struct {
	Items []CartItem `json:"items"`
}
type FailedAttempt struct {
	Id       int    `json:"userid"`
	User     User   `json:"user"`
	Time     string `json:"time"`
	Platform string `json:"platform"`
}

type ResponseData struct {
	Message string      `json:"message"`
	Status  int         `json:"status"`
	Data    interface{} `json:"data"`
}
