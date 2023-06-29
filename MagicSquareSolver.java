import java.util.Scanner;

class MagicSquareSolver {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    //get n
    System.out.print("n: ");
    int n = scanner.nextInt();
    //start writing to the square
    if (n % 2 == 1){
      printSquare(solveOddSquare(n,0));
    }else if (n % 4 == 0){
      printSquare(solveDoublyEvenSquare(n));
    }else{
      printSquare(solveSinglyEvenSquare(n));
    }
    scanner.close();
  }

  static int[][] solveOddSquare(int n, int bias){
    int[][] square = new int[n][n];
    int y = 0;
    int x = (n-1)/2;
    for (int i=1; i<= n*n; i++){
      square[y][x] = i+bias;
      if (y==0 && x==n-1){
        y++;
      }else if (y>0 && x<n-1 && square[y-1][x+1]!=0){
        y++;
      }else{
        x = (x+1) % n;
        y = (y-1+n) % n;  
      }
    }
    return square;
  }

  static int[][] solveDoublyEvenSquare(int n){
    int[][] square = new int[n][n];
    int number = 1;
    for (int i=0;i<n; i++){
      for (int j=0; j<n; j++){
         if (((i<n/4 || n-i-1<n/4) && (j<n/4 || n-j-1<n/4)) || (i>=n/4 && i<n-(n/4) && j>=n/4 && j<n-(n/4))){
           square[i][j] = number;
         }else{
           square[i][j] = n*n - number +1;
         }
        number ++;
      }
    }
    return square;
  }

  static int[][] solveSinglyEvenSquare(int n){
    int qn = n/2;
    int[][] squareA = solveOddSquare(qn ,0);
    int[][] squareB = solveOddSquare(qn, qn*qn);
    int[][] squareC = solveOddSquare(qn, 2*qn*qn);
    int[][] squareD = solveOddSquare(qn, 3*qn*qn);
    for (int i=qn-1; i>qn-((qn-1)/2); i--){
      for (int j=0;j<qn;j++){
        int tempB = squareB[j][i];
        squareB[j][i] = squareC[j][i];
        squareC[j][i] = tempB;
      }
    }

    for (int i=0; i<n/2; i++){
      if (i == (qn-1)/2){
        for (int j=1;j<=(qn-1)/2;j++){
          int tempA = squareA[i][j];
          squareA[i][j] = squareD[i][j];
          squareD[i][j] = tempA;
        }
      }else{
        for (int j=0; j<(qn-1)/2; j++){
          int tempA = squareA[i][j];
          squareA[i][j] = squareD[i][j];
          squareD[i][j] = tempA;
        }
      }
    }

    int[][] square = new int[n][n];
    
    for (int i=0; i<n; i++){
      for (int j=0; j<n; j++){
        if (i < qn && j<qn){
          square[i][j] = squareA[i][j];
        }else if (i<qn){
          square[i][j] = squareC[i][j-qn];
        }else if (j<qn){
          square[i][j] = squareD[i-qn][j];
        }else{
          square[i][j] = squareB[i-qn][j-qn];
        }
      }
    }
    
    return square;
  }

  static void printSquare(int[][] square){
    int n = square.length;
    for (int i=0; i<n; i++){
      for (int j=0; j<n; j++){
        System.out.printf(String.format("%%%dd",String.valueOf(n*n).length()+1),square[i][j]);
      }
      System.out.print("\n");
    }
  }
}