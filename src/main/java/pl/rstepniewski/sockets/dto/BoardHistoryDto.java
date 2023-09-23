package pl.rstepniewski.sockets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.rstepniewski.sockets.game.board.BoardCellStatus;

import java.util.List;
import java.util.Map;

public class BoardHistoryDto {
    private final Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> boardsHistory;

    @JsonCreator
    public BoardHistoryDto(@JsonProperty("0") Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> boardsHistory) {
        this.boardsHistory = boardsHistory;
    }

    public Map<Integer, Map<Integer, List<List<BoardCellStatus>>>> getBoardsHistory() {
        return boardsHistory;
    }
}
