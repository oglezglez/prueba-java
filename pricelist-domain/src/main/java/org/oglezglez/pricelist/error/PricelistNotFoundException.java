package org.oglezglez.pricelist.error;

public class PricelistNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8395887007263666961L;

    public PricelistNotFoundException(String message) {
        super(message);
    }

    public PricelistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
