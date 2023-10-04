package pl.rstepniewski.sockets.jsonCommunication;

public enum ErrorCode {
    ILLEGAL_ARGUMENTS(2),
    BAD_REQUEST(4);

    final int errorNumberCode;

    public int getErrorNumberCode() {
        return errorNumberCode;
    }

    ErrorCode(int errorNumberCode) {
        this.errorNumberCode = errorNumberCode;
    }
}
