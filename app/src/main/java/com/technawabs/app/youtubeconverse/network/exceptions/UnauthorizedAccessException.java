package com.technawabs.app.youtubeconverse.network.exceptions;

import com.technawabs.app.youtubeconverse.constants.AppConstant;

public class UnauthorizedAccessException extends HttpException {

    public UnauthorizedAccessException() {
        super(AppConstant.EXCEPTION.UNAUTHORIZED_ACCESS);
    }
}
