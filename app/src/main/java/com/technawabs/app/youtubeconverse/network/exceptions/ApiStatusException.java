package com.technawabs.app.youtubeconverse.network.exceptions;

public class ApiStatusException extends HttpException {
    public ApiStatusException(String message) {
        super(message);
    }
}
