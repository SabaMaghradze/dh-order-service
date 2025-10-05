package com.dailyhaul.order.exception;

public record ErrorResponse(int status, String message, String path) {
}
