package com.enigmacamp.tokonyadia.constant;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class ResponseMessage {
    public static final String SUCCESS_SAVE_DATA = "successfully save data";
    public static final String SUCCESS_GET_DATA = "successfully fetch data";
    public static final String SUCCESS_UPDATE_DATA = "successfully update data";
    public static final String SUCCESS_DELETE_DATA = "successfully delete data";
    public static final String ERROR_NOT_FOUND = "data not found";
    public static final String ERROR_INTERNAL_SERVER = "internal server error";
    public static final String ERROR_FORBIDDEN = "cannot access this resource";
    public static final String ERROR_OUT_OF_STOCK = "the product currently out of stock";
    public static final String ERROR_CREATING_JWT = "error while creating jwt token";
    public static final String SUCCESS_LOGIN = "login successfully";
    public static final String ERROR_INVALID_CONTENT_TYPE = "invalid image type";
    public static final String ERROR_INVALID_JWT = "invalid jwt";
}
