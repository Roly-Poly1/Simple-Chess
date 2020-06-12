
/*
Final Assignment
Simple Chess Lab
Millinh Apilado
920649519
CSC 210 - 03
Spring 2020
 */

import java.util.Scanner;

public class SimpleChess {
    //initialized and instantiated arrays and multiple variables as
    //global variables/objects so it could be accessed by the
    //rest of the methods outside of the main method
    static String[][] chessBoard = new String[8][8];
    static String colAns;
    static String sourcePiece;
    static String piece;
    static int sourceRow;
    static int sourceColumn;
    static int targRow;
    static int targColumn;
    static int error = 0;
    static int diffColumn;
    static int diffRow;
    static int x = 0;
    static int y = 0;
    static int z = 0;
    static int j = 0;
    static int selectPieceFlag = 0;
    static int rootAns;
    static double rootAnsDble;
    static Scanner bling = new Scanner(System.in);

    public static void main(String[] args) {

        //Assigned my chesspiece elements one by one on my integer 2D chessBoard Array
        //that was initialized/instantiated outside my main method
        //Black chesspieces
        chessBoard[0][0] = "B. Rook  ";
        chessBoard[0][1] = "B. Knight";
        chessBoard[0][2] = "B. Bishop";
        chessBoard[0][3] = "B. Queen ";
        chessBoard[0][4] = "B. King  ";
        chessBoard[0][5] = "B. Bishop";
        chessBoard[0][6] = "B. Knight";
        chessBoard[0][7] = "B. Rook  ";
        //White chesspieces
        chessBoard[7][0] = "W. Rook  ";
        chessBoard[7][1] = "W. Knight";
        chessBoard[7][2] = "W. Bishop";
        chessBoard[7][3] = "W. Queen ";
        chessBoard[7][4] = "W. King  ";
        chessBoard[7][5] = "W. Bishop";
        chessBoard[7][6] = "W. Knight";
        chessBoard[7][7] = "W. Rook  ";

        //For loops to assign "B. Pawn" since it's a repetitive
        //piece that occurs 8 times on my chessBoard array
        for (int i = 1; i == chessBoard.length - 7; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                chessBoard[i][j] = "B. Pawn  ";
            }
        }
        //For loops to also assign "W. Pawn" since it is
        //repetitive and is more efficient to do this way
        for (int i = 6; i == chessBoard.length - 2; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                chessBoard[i][j] = "W. Pawn  ";
            }
        }
        //For loops to insert whitespaces on empty tiles as a form of
        //"null" on my 2D chessBoard array
        for (int i = 2; i < chessBoard.length - 2 && i > chessBoard.length - 7; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                chessBoard[i][j] = "         ";
            }
        }

        //Simple chess instructions just so user knows what numbers corresponds to what
        //before displaying the board
        System.out.println();
        System.out.println("Welcome To Simple Chess Program!");
        System.out.println("REMINDER: Row Numbers are displayed at the left-hand side of the board while Columns are on top");
        System.out.println();

        //Method call askColorOne which asks first player what color
        //they'd choose - either black or white
        askColorOne();

        //Method call printBoard which initially prints the original state of
        //the unmoved 8x8 chess board to the user
        printBoard();

        //Main while loop which will keep the user(s) in the game
        //Inside, it contains multiple method calls
        j = 1;
        while(j == 1){

            //Error is here as a loop flag but also to always assume there is
            //an error then becomes 0 when user input is correct when
            //user input is required
            error = 1;

            //Method call validateSourceXY which asks and
            //validates user's source input
            validateSourceXY();

            //Method call pickThePiece which filters the user
            //input source piece into a keyword to be used
            //for a switch case later on
            pickThePiece();

            //Method call validateTargXY which validates user input
            //for destination tile and also validates the pieces picked
            //and the corresponding method for the source piece
            validateTargXY();

            //Method call error which assigns the piece to the
            // destination tile after validations
            error();

            //printBoard prints the new board which will continuously
            //update as long as it is still inside the main while loop
            printBoard();

            //askContinue asks user if they want to continue playing
            //the Simple chess game
            askContinue();

            //If user wants to continue, color automatically switches
            //to the opposite color
            colSwitch();
        }
    }
    static void validateSourceXY(){
        error = 1;

        //while loop just in case user's input for source
        //isn't valid, it would keep asking user for input
        while(error > 0){

            //validateSourceX validates the source row input
            //from the user separately from everything else
            validateSourceX();

            //validateSourceY validates the source column input
            //from the user separately as well
            validateSourceY();

            //validates if the piece picked matches the current
            //playing color of the user
            validatePiece();

        }
    }
    static void validateTargXY(){
        error = 1;

        //while loop to keep validating user's target input
        //if input is invalid
        while(error > 0){

            //method that asks user for the target tile's row
            validateTargetX();

            //method call that would ask user for the target column input
            validateTargetY();

            //diffrow is the difference between the target row
            //input and source row input
            diffRow = Math.abs(targRow - sourceRow);

            //diffcolumn is the difference between the target
            //column input and the source column input
            diffColumn = Math.abs(targColumn - sourceColumn);

            //rootAnsDble will be getting the hypotenuse of any piece's movement using
            //the distance of delta x (diffRow) and delta y (diffColumn) and use
            //the Pythagorean theorem
            rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));;

            //Switch cases that calls the piece validation method which
            // checks if the target input by user is valid to go to depending
            // on the piece chosen by the user to move
            switch(piece){
                case "king":
                    validateKing();
                    break;
                case "queen":
                    validateQueen();
                    break;
                case "rook":
                    validateRook();
                    break;
                case "bishop":
                    validateBishop();
                    break;
                case "knight":
                    validateKnight();
                    break;
                case "pawn":
                    validatePawn();
                    break;
            }

            //After validating the move, if the user input target is invalid for
            //source piece to go to, error count would iterate and get into this
            //if statement
            if (error > 0){

                //this asks the user if they want to choose another piece if
                //the piece they chose could not get to their target block
                System.out.println();
                System.out.println("Do You Want To Select A New Piece? (Y/N): ");
                char selectPieceSwitch = bling.next().charAt(0);

                //if user says yes, it would get out of this loop and
                //print the board from its last known state and asks them to continue
                if (selectPieceSwitch == 'Y' || selectPieceSwitch == 'y') {
                    error = 0;
                    selectPieceFlag = 1;
                }
                //if user says no, same thing, just formality since the program
                //will be asking if they want to continue the game anyway
                if (selectPieceSwitch == 'N' || selectPieceSwitch == 'n') {
                    error = 0;
                    selectPieceFlag = 1;
                }
            }

        }
    }
    static void askContinue(){

        //This asks user if they would want to continue the game
        //if user inputs yes, it just loops back within the main loop
        //if user answers N for no, it would just exit the program
        System.out.println();
        System.out.println("Do You Want To Continue Playing? (Y/N): ");
        char contAns = bling.next().charAt(0);

        if (contAns == 'Y' || contAns == 'y') {
            error = 1;
        }
        else if (contAns == 'N' || contAns == 'n') {
            System.out.println("GoodBye!");
            x = 0;
            j = 0;
        }
        else{
            askContinue();
        }
    }
    static void validateKing(){
        //I initialize error to zero every piece
        //error > 0 is my flag per loop so if error is 0
        //it will replace the piece and won't get stuck
        //inside my while loops
        error = 0;

        //Black and White pieces move on separate directions
        //where black moves down the board as forward and white
        // moves up as forward so I have two main if conditions
        if (sourcePiece.equals("B. King  ")) {

            //King may move diagonally but also horizontally
            //and vertically but only one block from it's
            //original position, and so by using pythagorean
            //theorem, the hypotenuse of a vertical/horizontal
            //direction must always be 1
            if(rootAnsDble == 1.0) {

                //if diffColumn (delta Column) is 0 and diffRow (delta Row) is 1
                //then I know that the king wants to move vertically forward or back
                if (diffColumn == 0 && diffRow == 1) {

                    //If the source row for black is less than the target row, then
                    //the piece wants to move forward and so row iterates forward
                    if (sourceRow < targRow) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j == sourceColumn; j++) {

                                //This if statement checks if the target block/tile is
                                //empty by using the trim function which takes out the whitespaces
                                //if not, error count iterates and stays in the loop
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    //If the source row is much greater than target row, then I know that
                    //the black piece is going backward
                    if (sourceRow > targRow) {

                        //Instead of incrementing, it decrements since it's a black piece
                        //initialized from the top of the chessboard
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j == sourceColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                }
                //If diffRow is 0 and diffColumn is 1, it means that user wants to move the king
                //horizontally from left to right or vice versa since Column is what's changing
                if (diffRow == 0 && diffColumn == 1) {

                    //if source column is much less than target Column, then it means
                    //that it's coming from left to right as the chessboard's
                    //numbering works that way
                    if (sourceColumn < targColumn) {

                        //since this would be a horizontal iteration, only columns
                        //or the nested for loop inside will be the iterating one
                        for (int i = sourceRow; i == sourceRow; i++) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    //if the source column is much bigger than the target column, I know that
                    //the piece wants to move from right to left
                    if (sourceColumn > targColumn) {

                        //because the counr of the chessboard columns starts from 0 (left)
                        //to 7 (right), it decrements no matter what color the pieces are
                        for (int i = sourceRow; i == sourceRow; i--) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }

                }
            }
            //If the difference of Column and difference of Row are equivalent to each other
            //then I know that it wants to move diagonally
            if (diffRow == diffColumn) {

                //However, since the King may only move one block away from its source to
                //whatever direction, using the pythagorean theorem, the hypotenuse must always be
                //equivalent to square root of 2
                if(rootAnsDble == Math.sqrt(2)) {

                    //Moving diagonally considers two factors: in this case, if the
                    //source Row is much lesser than the target row, I know that
                    //it wants to move forward and if source column is also less
                    //than targ column then I know it wants to move not only forward
                    //but also to the right diagonally
                    if (sourceRow < targRow && sourceColumn < targColumn) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }

                    //if the sourceRow is less than target row but the source column
                    //is greater than the target column, the nI know that this piece
                    //wants to move forward but to the left diagonally
                    if (sourceRow < targRow && sourceColumn > targColumn) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }

                    //If the source row is greater than target row but source column is much less
                    //than the target column, this means that the black King wants to move
                    //backwards but also from left to right diagonally
                    if (sourceRow > targRow && sourceColumn < targColumn) {
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    //If the source row is much greater than the target row and the source Column is
                    //much greater than the target column, the black king wants to move backwards and
                    //from right to left diagonally
                    if (sourceRow > targRow && sourceColumn > targColumn) {
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                }
            }
            //If the user input does not match the requirement of having a
            //hypotenuse of 2 or 1 then it means it exceeds the
            //allowed range of the King
            if(rootAnsDble != Math.sqrt(2) && rootAnsDble != 1.0){
                errorMessageExceedRange();
                error++;
            }
        }

        //For the white king piece, it's pretty much the same idea but it iterates
        //the opposite of how the black king does since the starting row
        //of the white King would be from row 7 instead of 0
        //However, the horizontal movements should remain the same as the columns
        //are read the same way despite having two colors
        if (sourcePiece.equals("W. King  ")) {
            if(rootAnsDble == 1.0) {
                if (diffColumn == 0 && diffRow == 1) {
                    if (sourceRow < targRow) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j == sourceColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    if (sourceRow > targRow) {
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j == sourceColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                }
                if (diffRow == 0 && diffColumn == 1) {
                    if (sourceColumn < targColumn) {
                        for (int i = sourceRow; i == sourceRow; i++) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    if (sourceColumn > targColumn) {
                        for (int i = sourceRow; i == sourceRow; i--) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                }
            }
            if(rootAnsDble == Math.sqrt(2)) {
                if (diffRow == diffColumn) {
                    if (sourceRow < targRow && sourceColumn < targColumn) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    if (sourceRow < targRow && sourceColumn > targColumn) {
                        for (int i = sourceRow; i < targRow; i++) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    if (sourceRow > targRow && sourceColumn < targColumn) {
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j < targColumn; j++) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                    if (sourceRow > targRow && sourceColumn > targColumn) {
                        for (int i = sourceRow; i > targRow; i--) {
                            for (int j = sourceColumn; j > targColumn; j--) {
                                if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                    errorMessageNotEmpty();
                                    error++;
                                }
                            }
                        }
                    }
                }
            }
            if(rootAnsDble != 1.0 && rootAnsDble != Math.sqrt(2)){
                errorMessageExceedRange();
                error++;
            }
        }
    }
    static void validateQueen(){
        //rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));
        //diffColumn = Math.abs(diffColumn);
        //diffRow = Math.abs(diffRow);
        error = 0;

        //The queen moves pretty much the same way as the king, however
        //it is less limited, so the hypotenuse measurements and limitations would
        //be different
        if (sourcePiece.equals("B. Queen ")) {

            //if delta column is zero, then black queen wants to move vertically
            if(diffColumn == 0) {

                //if the source row is much lesser than target row, it is moving
                //forward just like the King
                if (sourceRow < targRow) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j == sourceColumn; j++) {

                            //However, unlike the king, instead of directly checking
                            //if the target is empty, since the Queen does not have a 1
                            //square limit, it has to iterate per block to check
                            //as it cannot pass over a non vacant one
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }

                //Since source row is much bigger than target row, the black queen would be
                //moving bakcwards vertically
                if (sourceRow > targRow) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j == sourceColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            //Since diffrow is 0, I know that columns will be the ones having the
            //difference so therefore, the black queen would be moving
            //from left to right and vice versa
            if(diffRow == 0){

                //since source col is less than the target column, it means that
                //the black queen piece would be coming from left to right
                if (sourceColumn < targColumn) {
                    for (int i = sourceRow; i == sourceRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                //This will have the queen move from right to left
                if (sourceColumn > targColumn) {
                    for (int i = sourceRow; i == sourceRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }

            }

            //Now, just like the king, if the difference of row and difference of
            //column are equal, this means that the piece wants to move diagonally
            if (diffRow == diffColumn) {
                if (sourceRow < targRow && sourceColumn < targColumn) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow < targRow && sourceColumn > targColumn) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow && sourceColumn < targColumn) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow && sourceColumn > targColumn) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow != diffColumn && diffRow != 0 && diffColumn != 0){
                error++;
            }
        }

        //Just like the king, the white queen is also similar to the logic of the
        //black queen movement validations but the iteration is just flipped
        //as well as the forward and backward validations for the vertical
        //movements
        if (sourcePiece.equals("W. Queen ")) {
            if(diffColumn == 0) {
                if (sourceRow < targRow) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j == sourceColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j == sourceColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow == 0){
                if (sourceColumn < targColumn) {
                    for (int i = sourceRow; i == sourceRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
                if (sourceColumn > targColumn) {
                    for (int i = sourceRow; i == sourceRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
            }
            if (diffRow == diffColumn){
                if(sourceRow < targRow && sourceColumn < targColumn){
                    for (int i = sourceRow; i < targRow; i++){
                        for (int j = sourceColumn; j < targColumn; j++){
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow < targRow && sourceColumn > targColumn){
                    for (int i = sourceRow; i < targRow; i++){
                        for (int j = sourceColumn; j > targColumn; j--){
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow > targRow && sourceColumn < targColumn){
                    for (int i = sourceRow; i > targRow; i--){
                        for (int j = sourceColumn; j < targColumn; j++){
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow > targRow && sourceColumn > targColumn){
                    for (int i = sourceRow; i > targRow; i--){
                        for (int j = sourceColumn; j > targColumn; j--){
                            if (chessBoard[i][j].trim().length() != 0) {
                                System.out.println("Tile Blocked or Destination Not Empty!");
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow != diffColumn && diffRow != 0 && diffColumn != 0){
                System.out.println("Invalid Move For Queen: Exceeded Allowed Range");
                error++;
            }
        }
    }
    static void validateRook(){
        //rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));
        //diffColumn = Math.abs(diffColumn);
        //diffRow = Math.abs(diffRow);
        error = 0;

        //Since Rook moves only horizontally and vertically
        //with no limits, it's basically using the same validations
        //as the queen's horizontal and vertical movements
        if (sourcePiece.equals("B. Rook  ")) {
            if (diffColumn == 0) {
                if (sourceRow < targRow) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j == sourceColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j == sourceColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow == 0){
                if (sourceColumn < targColumn) {
                    for (int i = sourceRow; i == sourceRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceColumn > targColumn) {
                    for (int i = sourceRow; i == sourceRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow != 0 && diffColumn != 0){
                errorMessageExceedRange();
                error++;
            }
            if(diffColumn == diffRow){
                errorMessageExceedRange();
                error++;
            }
        }
        if(sourcePiece.equals("W. Rook  ")){
            if(diffColumn == 0) {
                if (sourceRow < targRow) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j == sourceColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j == sourceColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow == 0){
                if (sourceColumn < targColumn) {
                    for (int i = sourceRow; i == sourceRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceColumn > targColumn) {
                    for (int i = sourceRow; i == sourceRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
            if(diffRow != 0 && diffColumn != 0){
                errorMessageExceedRange();
                error++;
            }
            if(diffColumn == diffRow){
                errorMessageExceedRange();
                error++;
            }
        }
    }
    static void validateBishop(){
        //rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));
        //diffColumn = Math.abs(diffColumn);
        //diffRow = Math.abs(diffRow);
        error = 0;

        //Bishop, just like Rook, takes the same validations from the Queen
        //but only for the diagonal movements so it's
        //vital to check for errors on intended horizontal/vertical
        //movements the user inputs
        if(sourcePiece.equals("B. Bishop")){

            //This makes sure that the piece never moves vertically
            if(diffColumn == 0){
                errorMessageExceedRange();
                error++;
            }
            //and this makes sure the piece does not move horizontally
            if(diffRow == 0){
                errorMessageExceedRange();
                error++;
            }
            //and as a safety net, a diagonal move must have equal
            //delta row and delta column, so if it does not meet that
            //requirement, it would iterate the error count
            if(diffRow != diffColumn){
                errorMessageExceedRange();
                error++;
            }
            if (diffRow == diffColumn) {
                if (sourceRow < targRow && sourceColumn < targColumn) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow < targRow && sourceColumn > targColumn) {
                    for (int i = sourceRow; i < targRow; i++) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow && sourceColumn < targColumn) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j < targColumn; j++) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if (sourceRow > targRow && sourceColumn > targColumn) {
                    for (int i = sourceRow; i > targRow; i--) {
                        for (int j = sourceColumn; j > targColumn; j--) {
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
        }
        if(sourcePiece.equals("W. Bishop")){
            if(diffColumn == 0){
                errorMessageExceedRange();
                error++;
            }
            if(diffRow == 0){
                errorMessageExceedRange();
                error++;
            }
            if (diffRow != diffColumn){
                errorMessageExceedRange();
                error++;
            }
            if (diffRow == diffColumn){
                if(sourceRow < targRow && sourceColumn < targColumn){
                    for (int i = sourceRow; i < targRow; i++){
                        for (int j = sourceColumn; j < targColumn; j++){
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow < targRow && sourceColumn > targColumn){
                    for (int i = sourceRow; i < targRow; i++){
                        for (int j = sourceColumn; j > targColumn; j--){
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow > targRow && sourceColumn < targColumn){
                    for (int i = sourceRow; i > targRow; i--){
                        for (int j = sourceColumn; j < targColumn; j++){
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
                if(sourceRow > targRow && sourceColumn > targColumn){
                    for (int i = sourceRow; i > targRow; i--){
                        for (int j = sourceColumn; j > targColumn; j--){
                            if (chessBoard[i][j].trim().length() != 0) {
                                errorMessageNotEmpty();
                                error++;
                            }
                        }
                    }
                }
            }
        }
    }
    static void validateKnight(){
        //rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));
        //diffColumn = Math.abs(diffColumn);
       // diffRow = Math.abs(diffRow);
        error = 0;

        //Since the knight is quite compplicated, it requires much more validations
        if(sourcePiece.equals("B. Knight")){

            //A knight can only move two blocks forward or backwards then either one block left
            // or right so if I were to again use the pythagorean theorem, it would result to the
            //square root of 5 no matter the direction it tries to go to
            if(rootAnsDble == Math.sqrt(5)) {

                //Now, if delta row is 2 and delta column is 1, I know that the black knight
                //wants to move forward L or backward L
                if (diffRow == 2 && diffColumn == 1) {

                    //Just like the rest of the pieces, this is to know that the black knight
                    //wanted to move forward vertically
                    if (sourceRow < targRow) {

                        //but since it's in an L shape, I have to also consider the one block
                        //of left or right... in this case it wants to move from left to right
                        if (sourceColumn < targColumn) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }

                        //In this validation, it validates that the horse wants to move from
                        //right to left
                        if (sourceColumn > targColumn) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }

                    //This is to validate that the black knight wants to move backwards
                    if (sourceRow > targRow) {

                        //And again, the left and right validations
                        if (sourceColumn < targColumn) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceColumn > targColumn) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                }

                //Now this is for the horizontal L movement validations for the Black knight
                //everything else is the same but flipped since the left and right validation
                //would be through column checking and the up down tail of the L would be
                //checked through the rows
                if (diffRow == 1 && diffColumn == 2) {
                    if (sourceColumn < targColumn) {
                        if (sourceRow < targRow) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceRow > targRow) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                    if (sourceColumn > targColumn) {
                        if (sourceRow < targRow) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceRow > targRow) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Just extra validations to make sure that the piece remains in an L
            //and does not try to exceed the range
            if(sourceRow == targRow){
                errorMessageExceedRange();
                error++;
            }
            if(sourceColumn == targColumn){
                errorMessageExceedRange();
                error++;
            }
            if(diffRow == diffColumn){
                errorMessageExceedRange();
                error++;
            }
            if(rootAnsDble != Math.sqrt(5)){
                errorMessageExceedRange();
                error++;
            }
        }

        //The White Knight is basically like every other pieces' White counterpart
        //as it is the same as the Black piece but just opposite on directions and
        //iterations
        if(sourcePiece.equals("W. Knight")){
            if(rootAnsDble == Math.sqrt(5)) {
                if (diffRow == 2 && diffColumn == 1) {
                    if (sourceRow < targRow) {
                        if (sourceColumn < targColumn) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceColumn > targColumn) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                    if (sourceRow > targRow) {
                        if (sourceColumn < targColumn) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceColumn > targColumn) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                }
                if (diffRow == 1 && diffColumn == 2) {
                    if (sourceColumn < targColumn) {
                        if (sourceRow < targRow) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceRow > targRow) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j < targColumn; j++) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                    if (sourceColumn > targColumn) {
                        if (sourceRow < targRow) {
                            for (int i = sourceRow; i < targRow; i++) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                        if (sourceRow > targRow) {
                            for (int i = sourceRow; i > targRow; i--) {
                                for (int j = sourceColumn; j > targColumn; j--) {
                                    if (chessBoard[targRow][targColumn].trim().length() != 0) {
                                        errorMessageNotEmpty();
                                        error++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(sourceRow == targRow){
                errorMessageExceedRange();
                error++;
            }
            if(sourceColumn == targColumn){
                errorMessageExceedRange();
                error++;
            }
            if(diffRow == diffColumn){
                errorMessageExceedRange();
                error++;
            }
            if(rootAnsDble != Math.sqrt(5)){
                errorMessageExceedRange();
                error++;
            }
        }
    }
    static void validatePawn(){
        //rootAnsDble = Math.abs(Math.sqrt(Math.pow(diffRow, 2) + Math.pow(diffColumn, 2)));

        //The pawn can only move forward by one block and so I had to
        //reinitialize diffRow without getting the absolute value
        //so I could tell its direction through the sign
        diffRow = targRow - sourceRow;
        error = 0;

        //The pawn is an less complex piece to validate so
        if (sourcePiece.equals("B. Pawn  ")) {

            //If the tile in front of the piece which is also the target
            //destination not empty, then it iterates the error count
            if (chessBoard[targRow][targColumn].trim().length() != 0) {
                errorMessageNotEmpty();
                error++;
            }
            //For a Black Pawn, this is a validation to check if it
            //tries to move backwards
            if (sourceRow > targRow) {
                errorMessageExceedRange();
                error++;
            }
            //If it tries to go any other direction other than vertically
            //forward by one block, it will be an error through this validation
            if (diffRow != 1 && diffColumn != 0){
                errorMessageExceedRange();
                error++;
            }
            //The pythagorean theorem for hypotenuse should result to one
            //so this is kind of a safety net just in case user tries to find
            //a way around the logic
            if (rootAnsDble != 1.0){
                errorMessageExceedRange();
                error++;
            }

        }

        //Just like the black pawn but validates the row the opposite direction
        //and the delta diffRow as -1
        if (sourcePiece.equals("W. Pawn  ")) {
            if (chessBoard[targRow][targColumn].trim().length() != 0) {
                errorMessageNotEmpty();
                error++;
            }
            if (sourceRow < targRow) {
                errorMessageExceedRange();
                error++;
            }
            if (diffRow != -1 && diffColumn != 0){
                errorMessageExceedRange();
                error++;
            }
            if (rootAnsDble != 1.0){
                errorMessageExceedRange();
                error++;
            }

        }
    }
    static void validateTargetX(){
        error = 1;

        //This is to make sure that the question will continue looping for user
        //until they put a valid integer for target row as response
        while(error > 0){
            System.out.println();
            System.out.println("Please Enter The ROW Index Number Of Your Destined Block: ");
            try {
                targRow = bling.nextInt();
            }
            catch(Exception e){
                System.out.println("Invalid Input: Must Be An Integer");
                error++;
            }
            if(targRow > 7 ^ targRow < 0){
                System.out.println("Invalid Input: Out Of ChessBoard Bounds");
                error++;
            }
            else{
                error = 0;
            }
        }
    }
    static void validateTargetY(){
        error = 1;

        //Just like validateTargetX, this is to also make sure the user inputs
        //the correct input for the target Column and will continue to loop until they don't
        while(error > 0){
            System.out.println();
            System.out.println("Please Enter The COLUMN Index Number Of Your Destined Block: ");
            try {
                targColumn = bling.nextInt();
            }
            catch(Exception e){
                error++;
                System.out.println("Invalid Input: Must Be An Integer");
            }
            if(targColumn > 7 ^ targColumn < 0){
                System.out.println("Invalid Input: Out Of ChessBoard Bounds");
                error++;
            }
            else{
                error = 0;
            }
        }
    }
    static void pickThePiece(){

        //Because I didn't want to make 18 different method calls, I've decided
        //to filter the sourcePiece Black and White pieces into just their
        //general terms which I will be using on the switch cases to call
        //the appropriate validation methods for the piece chosen
        if (sourcePiece.equals("B. King  ") || sourcePiece.equals("W. King  ")) {
            piece = "king";
        }
        if (sourcePiece.equals("B. Queen ") || sourcePiece.equals("W. Queen ")){
            piece = "queen";
        }
        if (sourcePiece.equals("B. Rook  ") || sourcePiece.equals("W. Rook  ")) {
            piece = "rook";
        }
        if (sourcePiece.equals("B. Bishop") || sourcePiece.equals("W. Bishop")) {
            piece = "bishop";
        }
        if (sourcePiece.equals("B. Knight") || sourcePiece.equals("W. Knight")) {
            piece = "knight";
        }
        if (sourcePiece.equals("B. Pawn  ") || sourcePiece.equals("W. Pawn  ")) {
            piece = "pawn";
        }
    }
    static void validateSourceX(){
        error = 1;
        while(error > 0){

            //This method makes sure that the user inputs an integer and isn't out of the 0 to 7 bounds
            //for the source Row... I used try catch method to avoid the program to automatically
            // exiting the program after detecting an error
            System.out.println();
            System.out.println("Please Enter The ROW Index Number Of Your Chosen " + colAns + " ChessPiece: ");
            try {
                sourceRow = bling.nextInt();
            }
            catch(Exception e){
                System.out.println("Invalid Input: Must Be An Integer");
                error++;
            }
            if(sourceRow > 7 ^ sourceRow < 0){
                System.out.println("Invalid Input: Out Of ChessBoard Bounds");
                error++;
            }
            else{
                error = 0;
            }
        }
    }
    static void validateSourceY(){
        error = 1;

        //Just like validateSourceX, this is to validate the SourceY which is the source Column
        while(error > 0){
            System.out.println();
            System.out.println("Please Enter The COLUMN Index Number Of Your Chosen " + colAns + " ChessPiece: ");
            try {
                sourceColumn = bling.nextInt();
            }
            catch(Exception e){
                System.out.println("Invalid Input: Must Be An Integer");
                error++;
            }
            if(sourceColumn > 7 ^ sourceColumn < 0){
                System.out.println("Invalid Input: Out Of ChessBoard Bounds");
                error++;
            }
            else{
                error = 0;
            }
        }
    }
    static void validatePiece(){

        //This is to validate that when the user input/the current
        //color player is black, then it would only
        //pick the piece that starts with the character 'B'
        //Which are the black pieces
        if (colAns.equals("black")) {
            sourcePiece = chessBoard[sourceRow][sourceColumn];
            if(sourcePiece.charAt(0) == 'B') {
                System.out.println();
                System.out.println("You picked: " + sourcePiece);
            }
            else{
                System.out.println("INVALID: Tile Contains No ChessPiece Or Color Unmatched");
                error++;
            }
        }

        //Same validation but for white pieces
        if(colAns.equals("white")){
            sourcePiece = chessBoard[sourceRow][sourceColumn];
            if(sourcePiece.charAt(0) == 'W'){
                System.out.println();
                System.out.println("You picked: " + sourcePiece);
            }
            else{
                System.out.print("INVALID: Tile Contains No ChessPiece Or Color Unmatched");
                error++;
            }
        }
    }
    static void askColorOne(){
        z = 1;

        //This prompts the user to choose their first
        //playing color
        while (z > 0) {
            System.out.println("First Player!");
            System.out.println("Please Pick a Color: Black or White?");
            colAns = bling.next();

            //after taking user's input, it would put it to lowercase
            //automatically so I won't have to validate for any possible
            //casings or capitalizations
            colAns = colAns.toLowerCase();

            //if user chooses black or white, it displays their color
            //and gets out of the loop then initializes error to 1
            //which allows them to get in the rest of the loops for validating
            //rows columns etc.
            if (colAns.equals("black")) {
                j = 1;
                error = 1;

                System.out.println("You chose: " + colAns);
                z = 0;
            }
            if (colAns.equals("white")) {
                j = 1;
                error = 1;


                System.out.println("You chose: " + colAns);

                z = 0;
            }

            //However, if user inputs none of the colors, then they would be forced
            //to stay in this loop
                if (!colAns.equals("black") && !colAns.equals("white")) {
                    System.out.println("Invalid Color: Please Input Black or White");
                    z = 1;
                }
            //}
        }
    }
    static void colSwitch(){

        //After every valid move, the color would switch to the
        //opposite color for the next player and display the
        //current player's color
        if(selectPieceFlag != 1) {
            if (colAns.equals("white")) {
                colAns = "black";
            } else {
                if (colAns.equals("black")) {
                    colAns = "white";
                }
            }
            System.out.println("Player Color Is Now: " + colAns);
        }
        else{
            selectPieceFlag = 0;
        }
    }
    static void printBoard() {

        //This is the print board function which prints
        //the chessboard but also the numbering of rows
        //and columns to help the user distinguish
        //each from 0 to 7
        for(int f = 0; f < chessBoard.length; f++){
            System.out.print("       " + f + "   ");
        }
        System.out.println();
        for (int i = 0; i < chessBoard.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < chessBoard.length; j++) {
                System.out.print("[" + chessBoard[i][j] + "]");
            }
            System.out.println();
            //System.out.print(i);
        }
    }
    static void error(){

        //If error counter is greater than 0, this prompt will simply display
        //that the move is invalid and will stay in the while loop
        //to once again ask the player for inputs or if they want to switch piece
        if (error > 0){
            System.out.println("Invalid Move");
        }
        //If error is 0 and user does not want to switch new piece, then
        //it would finalize the move by putting the piece from its source
        //to its target destination
        if (error == 0 && selectPieceFlag == 0){
            chessBoard[sourceRow][sourceColumn] = chessBoard[targRow][targColumn];
            chessBoard[targRow][targColumn] = sourcePiece;
        }
    }
    static void errorMessageExceedRange(){

        //This is just a display message for exceeding the range of a piece
        System.out.println("Invalid Move For " + piece + ": Exceeded Allowed Range");
    }
    static void errorMessageNotEmpty(){
        //This is just a display message to tell the user their path is blocked
        //or that the destination they wanted to go to was not empty
        System.out.println("Path Blocked or Tile Destination Not Empty!");
    }
}
