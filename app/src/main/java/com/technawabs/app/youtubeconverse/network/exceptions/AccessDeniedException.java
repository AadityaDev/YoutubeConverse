package com.technawabs.app.youtubeconverse.network.exceptions;


import com.technawabs.app.youtubeconverse.constants.AppConstant;

public class AccessDeniedException extends HttpException {
    public AccessDeniedException() {
        super(AppConstant.EXCEPTION.ACCESS_DENIED);
    }
}
