package com.technawabs.app.youtubeconverse.network.exceptions;

import com.technawabs.app.youtubeconverse.constants.AppConstant;

public class UnsupportedFileTypeException extends HttpException {

    public UnsupportedFileTypeException() {
        super(AppConstant.EXCEPTION.MESSAGE_UNSUPPORTED_FILE_TYPE);
    }

}
