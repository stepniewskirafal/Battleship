package pl.rstepniewski.sockets.jsonCommunication;

public enum MenuCode {
    CLIENT_CONSOLE(1),
    CLIENT_AI(2),
    SERVER_AI(3),
    GAME_HISTORY(4),
    EXIT_GAME(0);

    final int menuNumberCode;

    public int getMenuNumberCode() {
        return menuNumberCode;
    }

    MenuCode(int menuNumberCode) {
        this.menuNumberCode = menuNumberCode;
    }

    public static MenuCode buildMenuCodeFromInt(int menuNumberCode){
        switch (menuNumberCode) {
            case 1:
                return CLIENT_CONSOLE;
            case 2:
                return CLIENT_AI;
            case 3:
                return SERVER_AI;
            case 4:
                return GAME_HISTORY;
            case 0:
                return EXIT_GAME;
            default:
                throw new IllegalStateException("Unexpected value: " + menuNumberCode);
        }
    }

}
