import java.util.Scanner;
public class Main
{
    private static boolean PlayerTurn = false,minimaxTurn = true;
    private static String Player;
    static  String minimax;

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        State state = new State();

        System.out.println("Welcome to Reversi!");
        int depth = chooseLevel(input);
        MiniMaxPlayer minmaxPlayer = new MiniMaxPlayer(depth);
        System.out.println("Would you like to go first?");
        userDesire(input);


        defineTurn();
        state.showBoard(state.getBoard());
        int counter =0;
        while (true)
        {

            String whoPlays;
            if(PlayerTurn){
                whoPlays = Player;}else whoPlays = minimax;

            if(state.countValidMoves(whoPlays)<=0)
            {
                    System.out.println("No more valid moves for "+ whoPlays);
                    counter++;
                    PlayerTurn = !PlayerTurn;
                    minimaxTurn = !minimaxTurn;
                    if(minimaxTurn)
                    {
                        if(state.countValidMoves(minimax)<=0)
                            System.out.println("No more valid moves for "+minimax);
                            counter++;
                    }
                    else if(PlayerTurn)
                    {
                        if(state.countValidMoves(Player)<=0)
                            System.out.println("No more valid moves for "+Player);
                            counter++;
                    }
            }
            if(counter == 2)
            {
                System.out.println("No more Valid Moves...");
                int countPlayer = state.countForPlayer(Player);
                int countMinimax = state.countForPlayer(minimax);
                if(countPlayer>countMinimax)
                    System.out.println("\n\n\n"+Player+" wins!!");
                else
                    System.out.println("\n\n\n"+minimax+" wins!!!");
                break;
            }
            else
                counter=0;

            if(minimaxTurn)
            {
                String[][] selectedState = minmaxPlayer.MiniMax(state, minmaxPlayer.getMaxDepth(),-10000,10000, minimaxTurn);
                if(MiniMaxPlayer.move.size()!=0)
                {
                    selectedState = MiniMaxPlayer.move.get(0);
                }
                state = new State(selectedState);
                System.out.println("\n\n Minimax Played: \n\n");
                state.updateBoard(selectedState);


            }else
            {
                EnterMove(input,state);
                state.updateBoard(state.getBoard());
            }
            PlayerTurn = !PlayerTurn;
            minimaxTurn = !minimaxTurn;


        }
    }


    private static int inputToColumn(String s)
    {

       return ((s.substring(1,2).toUpperCase().charAt(0))-65)-1;
    }
    private static int inputToRow(String s)
    {
        int result;
        try{
            result =  Integer.parseInt(s.substring(0,1) )-1;
        }catch (NumberFormatException e){result = 9;}

        return result;
    }
    private static void EnterMove(Scanner input,State state)
    {
        String move;
        do {
            System.out.println( "Enter your move (e.g. 1a  or 2g etc) : ");
            move = input.nextLine();
            move = move.replaceAll("\\s+","");
        }while (move.length() != 2);

        int i = inputToRow(move);
        int j = inputToColumn(move) + 1;


        while(!state.isValid(i,j,Player))
        {
            System.out.println( "Enter your move: ");
            move = input.nextLine().replaceAll("\\s+","");
            i = inputToRow(move);
            j = inputToColumn(move) + 1;
        }


    }



    private static void defineTurn()
    {
        if(PlayerTurn)
        {
            Player = State.BLACK;
            minimax = State.WHITE;

        }
        else
        {
            Player = State.WHITE;
            minimax = State.BLACK;
        }

    }
    private static void userDesire(Scanner input)
    {
        String userDesire;
        do
        {
            System.out.print("(type Yes or No)\t");
            userDesire = input.nextLine();
        }
        while (!userDesire.equals("Yes") && !userDesire.equals("No"));

        if(userDesire.equals("Yes"))
        {
            PlayerTurn = true;
            minimaxTurn = false;

        }
    }
    private static int chooseLevel(Scanner input)
    {
        int level = 3;
        String user;
        do
        {
            System.out.println("Choose level (type easy, medium or hard)");
            user = input.nextLine().toLowerCase();
        }
        while (!user.equals("easy") && !user.equals("medium")&&!user.equals("hard"));

        if(user.equals("medium"))
            level = 5;
        else if(user.equals("hard"))
            level = 7;

        return level;
    }

}

