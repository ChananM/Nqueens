package csp;


import java.util.List;

public class Driver {

    private Board board;
    int station_num=0;
    int move_num=0;
    boolean wasPrint = false;

    public Driver(int boardSize) {
        this.board = new Board(boardSize);
    }

    public Board getBoard() {
        return board;
    }

    public boolean placeMoves()
    {
        if(!wasPrint)
        {
            System.out.println("\n---------------------- Possible states: ----------------------------\n-");
            wasPrint = true;
        }

        List<Integer> possible_num = board.getPossibleMoves();
        station_num += possible_num.size();
        for (int move: possible_num) {
            board.makeMove(move);
            move_num++;

            if (board.getQueenSpaces().size() == board.getN()) {
                System.out.println("\n---------------------------------------------------------------");

                System.out.println("\nStates that were calculated: " + station_num);
                System.out.println("States that were opened: " + move_num + "\n");
                System.out.println("----------------------- Solution ------------------------------\n");

                return true;
            } else {
                boolean retVal = placeMoves();

                if (retVal)
                    return true;
                else
                    board.removeMove(move);
            }
            possible_num = board.getPossibleMoves();
        }

        return false;
    }


}
