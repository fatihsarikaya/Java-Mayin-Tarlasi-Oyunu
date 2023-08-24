import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        
        int rowSize = 3; // Satır sayısı
        int colSize = 3; // Sütun sayısı
        char[][] board = new char[rowSize][colSize];
        char[][] mineBoard = new char[rowSize][colSize];
        int totalMines = (rowSize * colSize) / 4;
        
        initializeBoards(board, mineBoard);
        placeMines(mineBoard, totalMines);
        printBoard(board);
        
        boolean gameWon = false;
        
        while (!gameWon) {
            System.out.print("Satır Giriniz: ");
            int row = scanner.nextInt();
            
            System.out.print("Sütun Giriniz: ");
            int col = scanner.nextInt();
            
            if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
                System.out.println("Geçersiz bir nokta girdiniz. Lütfen tekrar deneyin.");
                continue;
            }
            
            if (mineBoard[row][col] == '*') {
                System.out.println("Game Over!!");
                revealMines(board, mineBoard);
                printBoard(board);
                break;
            } else {
                int count = countAdjacentMines(mineBoard, row, col);
                board[row][col] = (char) (count + '0');
                printBoard(board);
                
                if (count == 0) {
                    revealEmptyCells(board, mineBoard, row, col);
                }
                
                if (checkWin(board, mineBoard)) {
                    System.out.println("Oyunu Kazandınız !");
                    printBoard(board);
                    gameWon = true;
                }
            }
        }
        
        scanner.close();
    }
    
    public static void initializeBoards(char[][] board, char[][] mineBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = '-';
                mineBoard[i][j] = '-';
            }
        }
    }
    
    public static void placeMines(char[][] mineBoard, int totalMines) {
        Random random = new Random();
        int minesPlaced = 0;
        
        while (minesPlaced < totalMines) {
            int row = random.nextInt(mineBoard.length);
            int col = random.nextInt(mineBoard[0].length);
            
            if (mineBoard[row][col] != '*') {
                mineBoard[row][col] = '*';
                minesPlaced++;
            }
        }
    }
    
    public static void printBoard(char[][] board) {
        System.out.println("===========================");
        
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    public static int countAdjacentMines(char[][] mineBoard, int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < mineBoard.length && j >= 0 && j < mineBoard[0].length && mineBoard[i][j] == '*') {
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void revealEmptyCells(char[][] board, char[][] mineBoard, int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != '-') {
            return;
        }
        
        int count = countAdjacentMines(mineBoard, row, col);
        board[row][col] = (char) (count + '0');
        
        if (count == 0) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    revealEmptyCells(board, mineBoard, i, j);
                }
            }
        }
    }
    
    public static void revealMines(char[][] board, char[][] mineBoard) {
        for (int i = 0; i < mineBoard.length; i++) {
            for (int j = 0; j < mineBoard[0].length; j++) {
                if (mineBoard[i][j] == '*') {
                    board[i][j] = '*';
                }
            }
        }
    }
    
    public static boolean checkWin(char[][] board, char[][] mineBoard) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '-' && mineBoard[i][j] != '*') {
                    return false;
                }
            }
        }
        return true;
    }
}
