package common;

public enum HTTP_CODES {
    SUCCESS(200),
    FAIL_PUT(403),
    FAIL_GET(404),
    FAIL_DELETE(404);
    final int code;
    HTTP_CODES(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
