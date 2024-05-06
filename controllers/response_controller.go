package controllers

import (
	"encoding/json"
	"net/http"
)

func DataResponse(w http.ResponseWriter, status int, message string, data interface{}) {
	var response Response
	response.Status = status
	response.Message = message
	response.Data = data
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func MessageResponse(w http.ResponseWriter, status int, message string) {
	var response ErrorResponse
	response.Status = status
	response.Message = message
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}
func sendResponse(w http.ResponseWriter, status int, message string) {
	var response Response
	response.Status = status
	response.Message = message
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}

func sendDataResponse(w http.ResponseWriter, status int, message string, req interface{}) {
	var response ResponseData
	response.Status = status
	response.Message = message
	response.Data = req
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(response)
}
