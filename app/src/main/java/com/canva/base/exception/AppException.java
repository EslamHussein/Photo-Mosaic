package com.canva.base.exception;

public class AppException extends Exception {

    private String message;

    public static AppException map(Throwable t) {

        AppException exception = new AppException();

        exception.setMessage(t.getMessage());
        return exception;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
