package com.vtech.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 716634003288155720L;

	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
}
