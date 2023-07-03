package pl.rstepniewski.sockets.game.fleet;

public interface ShipTypeConverter {
    static int convertToNumber(String shipType) {
         switch (shipType){
             case "4-mast": return 4;
             case "3-mast": return 3;
             case "2-mast": return 2;
             case "1-mast": return 1;
             default: return 0;
         }
    }
}