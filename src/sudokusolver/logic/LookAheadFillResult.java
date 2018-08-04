package sudokusolver.logic;

public class LookAheadFillResult extends FillResult {

    private Cell newFilledCell;

    public LookAheadFillResult(Board board, Boolean isValidBoard, Cell newFilledCell) {
        super(board, isValidBoard);
        this.newFilledCell = newFilledCell;
    }

    public Cell getNewFilledCell() {
        return newFilledCell;
    }

}
