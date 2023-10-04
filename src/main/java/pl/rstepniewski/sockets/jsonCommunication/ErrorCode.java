package pl.rstepniewski.sockets.jsonCommunication;

public enum ErrorCode {
    ILLEGAL_ARGUMENTS(2),
    BAD_REQUEST(4);

    final int errorNumberCode;

    ErrorCode(int errorNumberCode) {
        this.errorNumberCode = errorNumberCode;
    }

    public int getErrorNumberCode() {
        return errorNumberCode;
    }
}
