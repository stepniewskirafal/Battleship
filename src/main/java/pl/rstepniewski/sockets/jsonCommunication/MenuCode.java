package pl.rstepniewski.sockets.jsonCommunication;

public enum MenuCode {
    CLIENT_CONSOLE(1),
    CLIENT_AI(2),
    SERVER_AI(3),
    GAME_HISTORY(1),
    EXIT_GAME(0);

    final int menuNumberCode;

    public int getMenuNumberCode() {
        return menuNumberCode;
    }

    MenuCode(int menuNumberCode) {
        this.menuNumberCode = menuNumberCode;
    }
}
