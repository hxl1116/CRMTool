package Response;

public enum StatusResponse {
    OK (200),
    CREATED (201),
    NO_CONTENT (204),
    BAD_REQUEST (400),
    NOT_FOUND (404),
    INTERNAL_SERVER_ERROR (500),
    NOT_IMPLEMENTED (501);

    int code;

    StatusResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
