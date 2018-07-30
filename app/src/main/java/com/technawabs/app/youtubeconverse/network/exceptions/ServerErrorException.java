package com.technawabs.app.youtubeconverse.network.exceptions;

import com.technawabs.app.youtubeconverse.constants.AppConstant;

public class ServerErrorException extends HttpException {

    public ServerErrorException() {
        super(AppConstant.MESSAGE_SERVER_ERROR);
    }
}
