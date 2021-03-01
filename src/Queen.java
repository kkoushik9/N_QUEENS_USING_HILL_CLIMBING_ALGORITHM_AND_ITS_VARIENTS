public class Queen implements Cloneable{
    int row;
    int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Queen(int row, int column) {
        this.row = row;
        this.column = column;
    }

    boolean walk_up(int n) {
        if(row > 0) {
            --row;
            return true;
        }
        return false;
    }

    boolean walk_down(int n) {
        if(row < n-1) {
            ++row;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Queen: Row = "+row+" Column = "+column;
    }
}
