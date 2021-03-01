import java.util.Random;
import java.util.Scanner;

public class HillClimbing {
    int isSuccess;
    int isFailure;
    int successMoves;
    int failureMoves;
    int moves;
    int sideMoveCount;
    int randomRestartCount;
    int noOfRestarts;
    boolean isRestarted = false;

    int sideMovesLimit = 100;

    /**
     * It performs Hill Climbing search by creating board with given dimensions.
     * @param dimensions dimensions of the board
     * @param taskToBeDone indicates the type of task (Report Generation or Search Sequence Generation)
     * @return returns the metrics of the search performed
     */
    public int[] SimpleHillClimbing(int dimensions, int taskToBeDone){
        Board board = new Board(dimensions, true);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**********Initial State**********");
        board.printQueens();

        while(true){
            Board bestsuccessor = board.getBestSuccessor();
            int hcost = bestsuccessor.gethCost();
            int boardhcost = board.gethCost();

            if(hcost == 0){
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("*****Goal State******");
                bestsuccessor.printQueens();
                System.out.println("------------------------------------------------------------------------");
                ++isSuccess;
                successMoves += moves;
                break;
            }
            else if(hcost < boardhcost){
                board = bestsuccessor;
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    board.printQueens();
                }
                ++moves;
            }
            else {
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("******Reached plateau/Local Minima State*****");
                bestsuccessor.printQueens();
                System.out.println("------------------------------------------------------------------------");
                ++isFailure;
                failureMoves += moves;
                return new int[]{isSuccess, successMoves, isFailure, failureMoves};
            }
        }

        return new int[] {isSuccess, successMoves, isFailure, failureMoves};

    }
    /**
     * It performs Hill Climbing with Sideways Move search by creating board with given dimensions.
     * @param dimensions dimensions of the board
     * @param taskToBeDone indicates the type of task (Report Generation or Search Sequence Generation)
     * @return returns the metrics of the search performed
     */
    public int[] HillClimbingWithSidewaysMove(int dimensions, int taskToBeDone){

        Board board = new Board(dimensions, true);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**********Initial State**********");
        board.printQueens();

        while (true){
            Board bestsuccessor = board.getBestSuccessor();

            int hcost = bestsuccessor.gethCost();
            int boardhcost = board.gethCost();

            if(hcost == 0){
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("*****Goal State******");
                bestsuccessor.printQueens();
                System.out.println("------------------------------------------------------------------------");
                ++isSuccess;
                successMoves += moves;
                break;
            }
            else if(hcost < boardhcost || (hcost == boardhcost && sideMoveCount < sideMovesLimit)){
                if(hcost < boardhcost)
                    sideMoveCount = 0;
                else
                    ++sideMoveCount;

                board = bestsuccessor;

                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    board.printQueens();
                }
                ++moves;
            }
            else{
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("******Reached plateau/Local Minima State*****");
                bestsuccessor.printQueens();
                System.out.println("------------------------------------------------------------------------");
                ++isFailure;
                failureMoves += moves;
                return new int[]{isSuccess, successMoves, isFailure, failureMoves};
            }
        }

        return new int[] {isSuccess, successMoves, isFailure, failureMoves};
    }

    /**
     * It performs Random Restart Hill Climbing Without Sideways Move search by creating board with given dimensions.
     * @param dimensions dimensions of the board
     * @param taskToBeDone indicates the type of task (Report Generation or Search Sequence Generation)
     * @return returns the metrics of the search performed
     */
    public int[] RandomRestartWithoutSidewaysMove(int dimensions, int taskToBeDone){
        Board board = new Board(dimensions, true);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**********Initial State**********");
        board.printQueens();

        while (true){
            Board bestsuccessor = board.getBestSuccessor();

            int hcost = bestsuccessor.gethCost();
            int boardhcost = board.gethCost();

            if(hcost == 0){
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("********Goal State*********");
                bestsuccessor.printQueens();
                ++isSuccess;
                successMoves += moves;
                break;
            }
            else if(hcost < boardhcost){
                board = bestsuccessor;
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    board.printQueens();
                }
                ++moves;
            }
            else{
                board = new Board(dimensions, true);
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    System.out.println("*********Restarted************");
                    board.printQueens();
                }
                ++noOfRestarts;
                isRestarted = true;
            }
        }

        if(isRestarted)
            ++randomRestartCount;

        return new int[] {isSuccess, successMoves, 0, 0, randomRestartCount, noOfRestarts};
    }

    /**
     * It performs Random Restart Hill Climbing With Sideways Move search by creating board with given dimensions.
     * @param dimensions dimensions of the board
     * @param taskToBeDone indicates the type of task (Report Generation or Search Sequence Generation)
     * @return returns the metrics of the search performed
     */
    public int[] RandomRestartWithSidewaysMove(int dimensions, int taskToBeDone){
        Board board = new Board(dimensions, true);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**********Initial State**********");
        board.printQueens();

        while (true){
            Board bestsuccessor = board.getBestSuccessor();

            int hcost = bestsuccessor.gethCost();
            int boardhcost = board.gethCost();

            if(hcost == 0){
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                }
                System.out.println("*****Goal State******");
                bestsuccessor.printQueens();
                ++isSuccess;
                successMoves += moves;
                break;
            }
            else if(hcost < boardhcost || (hcost == boardhcost && sideMoveCount < sideMovesLimit)){
                if(hcost < boardhcost)
                    sideMoveCount = 0;
                else
                    ++sideMoveCount;

                board = bestsuccessor;

                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    board.printQueens();
                }
                ++moves;
            }
            else{
                board = new Board(dimensions, true);
                if(taskToBeDone == 2) {
                    System.out.println("\t|");
                    System.out.println("\t|");
                    System.out.println("\tV");
                    System.out.println("*********Restarted************");
                    board.printQueens();
                }
                ++noOfRestarts;
                isRestarted = true;
            }
        }

        if(isRestarted)
            ++randomRestartCount;

        return new int[] {isSuccess, successMoves, 0, 0, randomRestartCount, noOfRestarts};
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int iterations = rand.nextInt(300) + 200; // as expected iterations are generated random
        int taskToBeDone;
        int noOfQueens;
        int algorithm;
        int[] metrics = new int[4];
        double totalSuccess = 0.0f, totalFailure = 0, totalSuccessSteps = 0, totalFailureSteps = 0, successRate=0, failureRate=0, totalRestarts=0, totalNoOfRestarts=0, avgRestarts=0, avgSucessSteps=0, avgFailureSteps=0;
        String[] algorithms = new String[] {"Hill Climbing Search", "Hill Climbing with Sideways Move", "Random Restart without SideWays Move", "Random Restart with Sideways Move"};

        System.out.println("Enter the value for number of queens");
        noOfQueens = scanner.nextInt();
        System.out.println("Enter 1 for hill climbing search");
        System.out.println("Enter 2 for hill climbing search with sideways move");
        System.out.println("Enter 3 for random-restart hill-climbing without sideways move");
        System.out.println("Enter 4 for random-restart hill-climbing with sideways move");
        algorithm =scanner.nextInt();

        System.out.println("Enter 1 for Report Generation");
        System.out.println("Enter 2 for Search Sequences of 4 Random Configurations");
        taskToBeDone = scanner.nextInt();

        if(taskToBeDone == 2)
            iterations = 4;

        switch (algorithm){
            case 1:
                for(int i=0; i < iterations; i++){
                    HillClimbing hillClimbing = new HillClimbing();
                    metrics = hillClimbing.SimpleHillClimbing(noOfQueens, taskToBeDone);
                    totalSuccess += metrics[0];
                    totalSuccessSteps += metrics[1];
                    totalFailure += metrics[2];
                    totalFailureSteps += metrics[3];
                }
                break;
            case 2:
                for(int i=0; i < iterations; i++){
                    HillClimbing hillClimbing = new HillClimbing();
                    metrics = hillClimbing.HillClimbingWithSidewaysMove(noOfQueens, taskToBeDone);
                    totalSuccess += metrics[0];
                    totalSuccessSteps += metrics[1];
                    totalFailure += metrics[2];
                    totalFailureSteps += metrics[3];
                }
                break;
            case 3:
                for(int i=0; i < iterations; i++){
                    HillClimbing hillClimbing = new HillClimbing();
                    metrics = hillClimbing.RandomRestartWithoutSidewaysMove(noOfQueens, taskToBeDone);
                    totalSuccess += metrics[0];
                    totalSuccessSteps += metrics[1];
                    totalFailure += metrics[2];
                    totalFailureSteps += metrics[3];
                    totalRestarts += metrics[4];
                    totalNoOfRestarts += metrics[5];
                }
                break;
            case 4:
                for(int i=0; i < iterations; i++){
                    HillClimbing hillClimbing = new HillClimbing();
                    metrics = hillClimbing.RandomRestartWithSidewaysMove(noOfQueens, taskToBeDone);
                    totalSuccess += metrics[0];
                    totalSuccessSteps += metrics[1];
                    totalFailure += metrics[2];
                    totalFailureSteps += metrics[3];
                    totalRestarts += metrics[4];
                    totalNoOfRestarts += metrics[5];
                }
                break;
        }

        if(taskToBeDone != 2) {

            if (totalSuccess != 0) {
                successRate = (totalSuccess / iterations) * 100;
                avgSucessSteps = totalSuccessSteps / totalSuccess;
            }

            if (totalFailure != 0) {
                failureRate = (totalFailure / iterations) * 100;
                avgFailureSteps = totalFailureSteps / totalFailure;
            }

            if (totalRestarts != 0)
                avgRestarts = totalNoOfRestarts / totalRestarts;

            System.out.println("Report for : " + algorithms[algorithm-1]);
            System.out.println("Total No of Iterations Performed: " + iterations);
            System.out.println("Success Rate: " + String.format("%.2f", successRate) + "%");
            System.out.println("Failure Rate: " + String.format("%.2f", failureRate) + "%");
            System.out.println("Average Number Successful Steps: " + String.format("%.2f", avgSucessSteps));
            System.out.println("Average Number of Failure Steps: " + String.format("%.2f", avgFailureSteps));
            if(algorithm != 2 && algorithm != 1) {
                System.out.println("Average Number of Restarts Done: " + String.format("%.2f", avgRestarts));
            }
        }

    }
}
