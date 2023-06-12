package pl.rstepniewski.sockets.jsonCommunication;

public enum ErrorCode {
    OK(0),
    SERVER_BUSY(1),
    ILLEGAL_ARGUMENTS(2),
    INTERNAL_ERROR(3),
    BAD_REQUEST(4);

    final int errorNumberCode;

    ErrorCode(int errorNumberCode) {
        this.errorNumberCode = errorNumberCode;
    }
}
