package pl.rstepniewski.sockets.game.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardHistory {
    private Map<Integer, List<List<BoardCellStatus>>> boardShipsHistory = new HashMap<>();
    private Map<Integer, List<List<BoardCellStatus>>> boardShotsHistory = new HashMap<>();

    public Map<Integer, List<List<BoardCellStatus>>> getBoardShipsHistory() {
        return boardShipsHistory;
    }

    public Map<Integer, List<List<BoardCellStatus>>> getBoardShotsHistory() {
        return boardShotsHistory;
    }

    public void addToBoardHistory(Map<Integer, List<List<BoardCellStatus>>> boardHistory, List<List<BoardCellStatus>> board){
        List<List<BoardCellStatus>> copiedboard = deepCopyBoard(board);

        boardHistory.put(boardHistory.size() + 1, copiedboard);
        copiedboard= null;
    }

    public List<List<BoardCellStatus>> deepCopyBoard(List<List<BoardCellStatus>> board) {
        List<List<BoardCellStatus>> copiedboard = new ArrayList<>();

        for (List<BoardCellStatus> innerList : board) {
            List<BoardCellStatus> copiedInnerList = new ArrayList<>(innerList);
            copiedboard.add(copiedInnerList);
        }

        return copiedboard;
    }
}
