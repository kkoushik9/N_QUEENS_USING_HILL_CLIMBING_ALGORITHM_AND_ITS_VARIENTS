import java.util.ArrayList;
import java.util.Random;

public class Board {
    int dimensions;
    ArrayList<Queen> queens = new ArrayList<>();
    //int[] array = new int[]{4,5,6,3,4,5,6,5};
    int successor_index = -1;
    ArrayList<Board> successors = new ArrayList<>();

    /**
     * It sets the list of Queens
     * @param queens list of queens
     */
    public void setQueens(ArrayList<Queen> queens) {
        this.queens = queens;
    }

    /**
     * It generates the list of queens with its random positions on the board
     * @param dimensions
     * @param isRandomPlacement indicates Whether to place the queens randomly or not
     */
    public Board(int dimensions, boolean isRandomPlacement) {
        this.dimensions = dimensions;

        if(isRandomPlacement) {
            Random r = new Random();
            for (int i = 0; i < dimensions; i++) {
                Queen q = new Queen(r.nextInt(dimensions), i);
                this.addQueen(q);
            }
        }
    }

    /**
     * It adds the queen into the current list of queens
     * @param q Queen to be added to the list
     */
    public void addQueen(Queen q){
        if(queens.size() < dimensions)
            queens.add(q);
    }

    /**
     * It generates the successors of the current board
     * @return returns the successors generated
     */
    public ArrayList<Board> getSuccessors(){
        ArrayList<Board> successors = new ArrayList<>();
        int prev_row;
        int prev_column;

        for(int i=0; i < dimensions; i++){

            prev_row = queens.get(i).getRow();
            prev_column = queens.get(i).getColumn();

            while(queens.get(i).walk_up(dimensions)){
                ArrayList<Queen> newqueenlist = new ArrayList<>();
                Board b = new Board(dimensions, false);
                for(Queen q : queens){
                    newqueenlist.add(new Queen(q.getRow(), q.getColumn()));
                }
                b.setQueens(newqueenlist);
                successors.add(b);
            }
            queens.get(i).setRow(prev_row);
            queens.get(i).setColumn(prev_column);

            while(queens.get(i).walk_down(dimensions)){
                ArrayList<Queen> newqueenlist = new ArrayList<>();
                Board b = new Board(dimensions, false);
                for(Queen q : queens){
                    newqueenlist.add(new Queen(q.getRow(), q.getColumn()));
                }
                b.setQueens(newqueenlist);
                successors.add(b);
            }
            queens.get(i).setRow(prev_row);
            queens.get(i).setColumn(prev_column);
        }
        return successors;
    }

    /**
     * It computes the heuristic cost (cost of collisions) of the current board
     * @return returns the heuristic cost
     */
    public int gethCost(){
        int rowattacks = 0;
        int[] row_wise = new int[dimensions];
        for(int i = 0; i < dimensions; i++)
            row_wise[i] = 0;
        // variable for counting number of attacks in diagonals
        int diagonalattacks = 0;

        int[] diagonal_wise = new int[2*dimensions-1];
        int[] diagonal_wise_2 = new int[2*dimensions-1];

        for(int i = 0; i < 2*dimensions-1; i++) {
            diagonal_wise[i] = 0;
            diagonal_wise_2[i] = 0;
        }
        // counting number of queens
        int x, y;
        for(int i = 0; i < dimensions; i++) {
            x = queens.get(i).getRow();
            y = queens.get(i).getColumn();
            ++row_wise[x]; // in each row
            ++diagonal_wise[x+y]; // in each antidiagonal direction
            ++diagonal_wise_2[dimensions-1+y-x]; // in each main diagonal direction
        }
        // number of attacks in rows
        for(int i = 0; i < dimensions; i++)
            if(row_wise[i] > 1)
                rowattacks += row_wise[i]*(row_wise[i]-1)/2;
        // number of attacks in diagonals
        for(int i = 0; i < 2*dimensions-1; i++) {
            if(diagonal_wise[i] > 1)
                diagonalattacks += diagonal_wise[i]*(diagonal_wise[i]-1)/2;
            if(diagonal_wise_2[i] > 1)
                diagonalattacks += diagonal_wise_2[i]*(diagonal_wise_2[i]-1)/2;
        }
        return diagonalattacks + rowattacks;
    }

    /**
     * It returns the next successor in the successors list
     * @return
     */
    public Board getNextSuccessor(){
        if(successor_index == -1){
            successors = getSuccessors();
            return successors.get(++successor_index);
        }
        if(++successor_index < successors.size())
            return successors.get(successor_index);

        successor_index = -1;
        return null;
    }

    /**
     * It finds the best successor out of all the generated successors
     * @return returns the best successor
     */
    public Board getBestSuccessor(){
        ArrayList<Board> successors = getSuccessors();
        ArrayList<Board> bestsuccessors = new ArrayList<>();
        Random r = new Random();

        int minattacks = dimensions*dimensions;
        int currentboardattack;

        for (int i = 0; i < successors.size(); i++){
            currentboardattack = successors.get(i).gethCost();
            if(minattacks > currentboardattack){
                minattacks = currentboardattack;
                bestsuccessors.clear();
                bestsuccessors.add(successors.get(i));
            }
            else if(minattacks == currentboardattack){
                bestsuccessors.add(successors.get(i));
            }
        }
        return bestsuccessors.get(r.nextInt(bestsuccessors.size()));
    }

    /**
     * It prints the current State in board format
     */
    public void printQueens(){
        char[][] NbyNBoard = new char[dimensions][dimensions];

        //System.out.println(this.queens);
        for(int i = 0; i < dimensions; i++)
            for(int j = 0; j < dimensions; j++)
                NbyNBoard[i][j] = '_';
        // filling board with its queens
        for(int i = 0; i < dimensions; i++)
            NbyNBoard[queens.get(i).getRow()][queens.get(i).getColumn()] = 'Q';
        // actually printing
        for(int i = 0; i < dimensions; i++) {
            System.out.println("");
            for (int j = 0; j < dimensions; j++)
                System.out.print(NbyNBoard[i][j]+" ");
        }
        System.out.print("\n\n"+"collisions = " + gethCost()+"\n\n");

    }

}
