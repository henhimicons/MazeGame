/**
 * Created by kanbe on 4/20/2017.
 */
import java.io.*;
import java.util.*;
/**
 * Maze Game Class.
 *
 * INFO1103 Assignment 2, Semester 1, 2017.
 *
 * The Maze Game.
 * In this assignment you will be designing a maze game.
 * You will have a maze board and a player moving around the board.
 * The player can step left, right, up or down.
 * However, you need to complete the maze within a given number of steps.
 *
 * As in any maze, there are walls that you cannot move through. If you try to
 * move through a wall, you lose a life. You have a limited number of lives.
 * There is also gold on the board that you can collect if you move ontop of it.
 *
 * Please implement the methods provided, as some of the marks are allocated to
 * testing these methods directly.
 *
 * @author YOU :)
 * @date April, 2017
 *
 */
public class MazeGame {
    /* You can put variables that you need throughout the class up here.
     * You MUST INITIALISE ALL of these class variables in your initialiseGame
     * method.
     */

    // A sample variable to show you can put variables here.
    // You would initialise it in initialiseGame method.
    // e.g. Have the following line in the initialiseGame method.
    // sampleVariable = 1;
    /**
    * Indicates the property
     * @x it indicates the x position of the player
     * @y it indicates the y position of the player
     * @total_step it indicates the limit of the step number
     * @current_step it indicates current steps have been used
     * @life it indicates the life user can use
     * @maze it stores the maze
     * @total_gold it indicates the gold the user has
    * */
    static int sampleVariable;
    private static int x,y;
    private static int des_x,des_y;
    private static int remain_step;
    private static int current_step;
    private static int life;
    private static int remain_gold;
    private static int row;
    private static String[] maze;
    private static String[] command={"help","board","status","left","right","up","down","save"};
    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", load the default
     * game configuration.
     *
     * NOTE: Please also initialise all of your class variables.
     *
     * @args configFileName The name of the game configuration file to read from.
     * @throws IOException If there was an error reading in the game.
     *         For example, if the input file could not be found.
     */
    public static void initialiseGame(String configFileName) throws IOException {
        // TODO: Implement this method.
        File file;
        if (configFileName.toLowerCase().equals("DEFAULT".toLowerCase())){
            file = new File ("DEFAULT.txt");
        }else{
            file = new File (configFileName);
            if (!file.exists()){
                System.out.println("Error: Could not load the game configuration from "+configFileName);
                return ;
            }
        }
        Scanner in = null;
        try {
            in = new Scanner(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (in.hasNextInt())life = in.nextInt();
        current_step=0;
        if (in.hasNextInt()) remain_step=in.nextInt();
        if (in.hasNextInt()) remain_gold=in.nextInt();
        if (in.hasNextInt()) row = in.nextInt();
        maze = new String [row];
        in.nextLine();
        for (int i=0;i<row;i++){
            maze[i]=in.nextLine();
        }
        int len = maze[0].length();
        for (int i=0;i<row;i++){
            for (int j=0;j<len;j++){
                if (maze[i].charAt(j)=='&'){y=i;x=j;}
                if (maze[i].charAt(j)=='@'){des_y=i;des_x=j;}
            }
        }
    }

    /**
     * Save the current board to the given file name.
     * Note: save it in the same format as you read it in.
     * That is:
     *
     * <number of lives> <number of steps> <amount of gold> <number of rows on the board>
     * <BOARD>
     *
     * @args toFileName The name of the file to save the game configuration to.
     * @throws IOException If there was an error writing the game to the file.
     */
    public static void saveGame(String toFileName) throws IOException {
        // TODO: Implement this method.
        File file = new File (toFileName);
        FileOutputStream fos = new FileOutputStream(file);
        String s = new String ();
        s+=(char)(life+'0');
        s+=' ';
        s+=(char)(remain_step+'0');
        s+=' ';
        s+=(char)(remain_gold+'0');
        s+=' ';
        s+=(char)(row+'0');
        s+='\n';
        fos.write(s.getBytes());
        for (int i=0;i<row;i++){
            fos.write(maze[i].getBytes());
        }
    }

    /**
     * Gets the current x position of the player.
     *
     * @return The players current x position.
     */
    public static int getCurrentXPosition() {
        // TODO: Implement this method.
        return x;
    }

    /**
     * Gets the current y position of the player.
     *
     * @return The players current y position.
     */
    public static int getCurrentYPosition() {
        // TODO: Implement this method.
        return y;
    }

    /**
     * Gets the number of lives the player currently has.
     *
     * @return The number of lives the player currently has.
     */
    public static int numberOfLives() {
        // TODO: Implement this method.
        return life;
    }

    /**
     * Gets the number of remaining steps that the player can use.
     *
     * @return The number of steps remaining in the game.
     */
    public static int numberOfStepsRemaining() {
        // TODO: Implement this method.
        return remain_step;
    }
    /**
     * Gets the amount of gold that the player has collected so far.
     *
     * @return The amount of gold the player has collected so far.
     */
    public static int amountOfGold() {
        // TODO: Implement this method.
        return remain_gold;
    }
    /**
     * Checks to see if the player has completed the maze.
     * The player has completed the maze if they have reached the destination.
     *
     * @return True if the player has completed the maze.
     */
    public static boolean isMazeCompleted() {
        // TODO: Implement this method.
        if (getCurrentXPosition()==des_x&&getCurrentYPosition()==des_y) return true;
        else return false;
    }

    /**
     * Checks to see if it is the end of the game.
     * It is the end of the game if one of the following conditions is true:
     *  - There are no remaining steps.
     *  - The player has no lives.
     *  - The player has completed the maze.
     *
     * @return True if any one of the conditions that end the game is true.
     */
    public static boolean isGameEnd() {
        // TODO: Implement this method.
        if (remain_step==0) return true;
        else if (life==0) return true;
        else if (isMazeCompleted()) return true;
        else return false;
    }

    /**
     * Checks if the coordinates (x, y) are valid.
     * That is, if they are on the board.
     *
     * @args x The x coordinate.
     * @args y The y coordinate.
     * @return True if the given coordinates are valid (on the board),
     *         otherwise, false (the coordinates are out or range).
     */
    public static boolean isValidCoordinates(int x, int y) {
        // TODO: Implement this method.
        int len = maze[0].length();
        if (x<0||x>=len||y<0||y>=len) return false;
        else return true;
    }

    /**
     * Checks if a move to the given coordinates is valid.
     * A move is invalid if:
     *  - It is move to a coordinate off the board.
     *  - There is a wall at that coordinate.
     *  - The game is ended.
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     * @return True if the move is valid, otherwise false.
     */
    public static boolean canMoveTo(int x, int y) {
        // TODO: Implement this method.
        if (!isValidCoordinates(x, y)) return false;
        else if (maze[y].charAt(x)=='#') return false;
        else return true;
    }

    /**
     * Move the player to the given coordinates on the board.
     * After a successful move, it prints "Moved to (x, y)."
     * where (x, y) were the coordinates given.
     *
     * If there was gold at the position the player moved to,
     * the gold should be collected and the message "Plus n gold."
     * should also be printed, where n is the amount of gold collected.
     *
     * If it is an invalid move, a life is lost.
     * The method prints: "Invalid move. One life lost."
     *
     * @args x The x coordinate to move to.
     * @args y The y coordinate to move to.
     */
    public static void moveTo(int x, int y) {
        // TODO: Implement this method.
        System.out.println("Move to ("+x+", "+y+").");
        char[] s =maze[y].toCharArray();
        if ('1'<=s[x]&&s[x]<='9') remain_gold+=(s[x]-'0');
        s[y]='&';
        maze[y]=s.toString();
    }
    /**
     * Prints out the help message.
     */
    public static void printHelp() {
        // TODO: Implement this method.
        System.out.println("Usage: You can type one of the following commands.");
        System.out.println("help         Print this help message.");
        System.out.println("board        Print the current board.");
        System.out.println("status       Print the current status.");
        System.out.println("left         Move the player 1 square to the left.");
        System.out.println("right        Move the player 1 square to the right.");
        System.out.println("up           Move the player 1 square up.");
        System.out.println("down         Move the player 1 square down.");
        System.out.println("save <file>  Save the current game configuration to the given file.");
    }

    /**
     * Prints out the status message.
     */
    public static void printStatus() {
        // TODO: Implement this method.
        System.out.println("Number of live(s): "+life);
        System.out.println("Number of step(s) remaining: "+remain_step);
        System.out.println("Amount of gold: "+remain_gold);
    }

    /**
     * Prints out the board.
     */
    public static void printBoard() {
        // TODO: Implement this method.
        for (int i=0;i<row;i++){
            System.out.println(maze[i]);
        }
    }

    /**
     * Performs the given action by calling the appropriate helper methods.
     * [For example, calling the printHelp() method if the action is "help".]
     *
     * The valid actions are "help", "board", "status", "left", "right",
     * "up", "down", and "save".
     * [Note: The actions are case insensitive.]
     * If it is not a valid action, an IllegalArgumentException should be thrown.
     *
     * @args action The action we are performing.
     * @throws IllegalArgumentException If the action given isn't one of the
     *         allowed actions.
     */
    public static void performAction(String action) throws IllegalArgumentException {
        // TODO: Implement this method.
        System.out.println("in performAction");
        String order = action;
        order = order.toLowerCase();
        int orderId=-1;
        for (int i=0;i<command.length;i++){
            if (order.equals(command[i])) orderId = i;
        }
        if (orderId==-1){
            System.out.println("Error: Could not find command "+"'"+order+"'.");
            System.out.println("To find the list of valid commands, please type 'help'.");
        }
        else if (order.equals("help"))    printHelp();
        else if (order.equals("board"))  printBoard();
        else if (order.equals("status")) printStatus();
        else if (order.equals("left")){
            if (canMoveTo(x-1,y)) {
                remain_step--;
                moveTo(--x,y);
                char[] s=maze[y].toCharArray();
                s[x+1]='.';
                maze[y]=s.toString();
            }else{
                System.out.println("Invalid move. One life lost.");
                life--;
            }
        }
        else if (order.equals("right")){
            if (canMoveTo(x+1,y)){
                remain_step--;
                moveTo(++x,y);
                char [] s= maze[y].toCharArray();
                s[x-1]='.';
                maze[y]=s.toString();

            }else{
                System.out.println("Invalid move. One life lost.");
                life--;
            }
        }
        else if (order.equals("up")){
            if (canMoveTo(x,y-1)){
                remain_step--;
                moveTo(x,--y);
                char[] s = maze[y+1].toCharArray();
                s[x]='.';
                maze[y+1]=s.toString();
            }else{
                System.out.println("Invalid move. One life lost.");
                life--;
            }
        }
        else if (order.equals("down")){
            if (canMoveTo(x,y+1)){
                remain_step--;
                moveTo(x,++y);
                char[] s=maze[y-1].toCharArray();
                s[x]='.';
                maze[y-1]=s.toString();
            }else{
                System.out.println("Invalid move. One life lost.");
                life--;
            }
        }
        else if (order.substring(0,4).equals("save")){
            String path = order.substring(order.indexOf(' ')+1);
            path=path.trim();
            try {
                saveGame(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The main method of your program.
     *
     * @args args[0] The game configuration file from which to initialise the
     *       maze game. If it is DEFAULT, load the default configuration.
     */
    public static void main(String[] args) {
         if (!(args.length==1)) {
             if (args.length == 0)
                 System.out.println("Error: Too few arguments given. Expected 1 argument, found " + args.length + ".");
             else
                 System.out.println("Error: Too many arguments given. Expected 1 argument, found " + args.length + ".");
             return;
         }
        try {
            initialiseGame(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sysin = new Scanner (System.in);
         /*
         while (sysin.hasNext()==false&&isGameEnd()==false){
         }*/
         
         while (!isGameEnd()){
             //System.out.println("action");
             String action = sysin.nextLine();
             performAction(action);
         }
         if (isGameEnd()){
             if (isMazeCompleted()){
                System.out.println("Congratulations! You completed the maze!");
                System.out.println("Your final status is:");
                printStatus();
             }else if(life==0){
                 System.out.println("Oh no! You have no lives left.");
                 System.out.println("Better luck next time!");
             }else if (remain_step==0){
                 System.out.println("Oh no! You have no steps left.");
                 System.out.println("Better luck next time!");
             }
             else{System.out.println("program failed");}
         }
    }
}
