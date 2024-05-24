package game;

import javafx.util.Pair;
import models.*;

import java.util.*;

public class Game {
    Deque<Player> players;
    Board gameBoard;

    public void initializeGame(){

        //creating 2 Players
        players = new LinkedList<>();
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player player1 = new Player("Player1", crossPiece);

        PlayingPieceO noughtsPiece = new PlayingPieceO();
        Player player2 = new Player("Player2", noughtsPiece);

        players.add(player1);
        players.add(player2);

        //initializeBoard
        gameBoard = new Board(3);
    }


    public String startGame(){
        boolean noWinner=true;
        while(noWinner){
            //Get the player in queue
            Player playingPlayer = players.poll();

            //Print board
            gameBoard.printBoard();

            //Get the free spaces and check for winner
            List<Pair<Integer, Integer>> freeSpaces =  gameBoard.getFreeCells();
            //No more places to place a piece
            if(freeSpaces.isEmpty()){
                noWinner=false;
                continue;
            }
            //read the user input
            System.out.println("Player:"+ playingPlayer.name + "Enter row, column:");
            Scanner input = new Scanner(System.in);
            String s=input.nextLine();
            String[] values = s.split(",");
            int inputRow=Integer.valueOf(values[0]);
            int inputColumn=Integer.valueOf(values[1]);

            //Add players input
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputColumn, playingPlayer.getPlayingPiece());

            if(!pieceAddedSuccessfully){
                System.out.print("Incorrect possition chosen, try again");
                players.removeFirst();
                players.addFirst(playingPlayer);
                continue;
            }
            //If added successfully he will go back to the queue
            players.addLast(playingPlayer);

            boolean winner = isThereWinner(inputRow, inputColumn, playingPlayer.playingPiece.pieceType);
            if(winner) {
                return playingPlayer.name;
            }

        }
        return "tie";
    }


    public boolean isThereWinner(int row, int col, PieceType pieceType){
        boolean rowMatch=true;
        boolean colMatch=true;
        boolean diagMatch=true;
        boolean antidiagMatch=true;
        //Check rows
        for(int i=0; i<gameBoard.size; i++){
            if(gameBoard.board[row][i]==null || gameBoard.board[row][i].pieceType!=pieceType){
                rowMatch= false;
            }
        }
        //Check columns
        for(int i=0; i<gameBoard.size; i++){
            if(gameBoard.board[i][col]==null || gameBoard.board[i][col].pieceType!=pieceType){
                colMatch= false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antidiagMatch = false;
            }
        }

        return rowMatch || colMatch || diagMatch || antidiagMatch;

    }

}
