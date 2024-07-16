package oms.kisvan.emart.model.dto;

//!@@0701 new

public class ErrorResponse {
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
