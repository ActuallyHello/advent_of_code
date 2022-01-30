package advent.of.code.day4;

import advent.of.code.day.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day4 extends Day {

    private List<String> bingoList; // bingo numbers from first row
    private List<BingoBoard> boardList; // boards

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>(Day4.readFileIntoList("file_day4.txt"));

        Day4 day4 = new Day4();
        day4.bingoList = Arrays.asList(input.get(0).split(","));
        day4.boardList = getAllBoards(input);

        System.out.println(day4.part1());
        System.out.println(day4.part2());
    }

    /*
     *  take each bingo number and compare it throuhg all boards
     *  if the board won then return it
     */
    public int part1() {
        int winner_result = -1;
        for (String s : bingoList) {
            for (BingoBoard bingoBoard : boardList) {
                winner_result = bingoBoard.game(s);
                if (winner_result != -1) return winner_result;
            }
        }
        return winner_result;
    }

    /*
     *  take each bingo number and compare it throuhg all boards
     *  if board won then remove it
     *  return last winning result
     */
    public int part2() {
        int last_winner = -1;
        for (String s : bingoList) {
            Iterator<BingoBoard> iterator = boardList.iterator();
            while (iterator.hasNext()) {
                BingoBoard bingoBoard = iterator.next();
                last_winner = bingoBoard.game(s);
                if (last_winner != -1) {
                    iterator.remove();
                }
            }
        }
        return last_winner;
    }

    /*
     *  insert each row from file to the board
     *  row.length == 1 means we have to finish this board and create new one
     */
    public static List<BingoBoard> getAllBoards(List<String> input) {
        List<BingoBoard> boardList = new ArrayList<>();
        BingoBoard bingoBoard = new BingoBoard();
        for (int i = 2; i < input.size(); i++) {
            String[] row = input.get(i).trim().split("\\s+");
            if (row.length == 1) {
                boardList.add(bingoBoard);
                bingoBoard = new BingoBoard();
            } else {
                bingoBoard.insertRow(row);
            }
        }
        return boardList;
    }

}

class BingoBoard {
    private final int rows = 5, columns = 5; // size of board
    private final String[][] board = new String[rows][columns];
    private int rowIndex = 0; // index to add numbers to the board
    private int countHIT = 0; // if bingo number equals to some board number

    public void insertRow(String[] row) {
        if (rowIndex != rows) {
            board[rowIndex] = row;
            rowIndex++;
        } else {
            System.out.println("TABLE IS FULL");
        }
    }

    /*
     *  go through all numbers in board and find the bingo number
     *  if bingo is in the board - change board's bingo number to -> "+"
     *  if there is enough hits we must check if the board won
     *  if board won - calculate result and return it
     */
    public int game(String bingo) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bingo.equals(board[row][col])) {
                    board[row][col] = "+";
                    countHIT++;
                    if (countHIT >= 5) {
                        if (isWin()) {
                            return calculate(Integer.parseInt(bingo));
                        }
                    }
                }
            }
        }
        return -1;
    }

    /*
     *  the board is considered winning if there are 5 bingo in a row or column
     *  check every row and column, count bingo numbers and return result
     */
    public boolean isWin() {
        for (int i = 0; i < rows; i++) {
            int countRow = 0; int countCol = 0;
            for (int j = 0; j < columns; j++) {
                if ("+".equals(board[i][j])) {
                    countRow++;
                }
                if ("+".equals(board[j][i])) {
                    countCol++;
                }
                if (countRow == 5 || countCol == 5) return true;

            }
        }
        return false;
    }

    /*
     *  sum all numbers in board if they are not changed to "+"
     *  the result of sum multiply by the last bingo (winning number)
     */
    public int calculate(int winning_number) {
        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!"+".equals(board[i][j])) total += Integer.parseInt(board[i][j]);
            }
        }
        return total * winning_number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TABLE IS = \n[ \n");
        for (String[] row : board) {
            for (String element : row) {
                String s = String.format("%2s", element);
                sb.append(s).append(" ");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
