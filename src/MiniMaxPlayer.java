import java.util.ArrayList;

class MiniMaxPlayer
{

    private int maxDepth;
    private ArrayList<String[][]> children ;
    static ArrayList<String[][]> move = new ArrayList<>() ;

    MiniMaxPlayer(int depth)
    {
        maxDepth = depth;

    }

    int getMaxDepth()
    {

        return maxDepth;
    }

    String[][] MiniMax(State s, int depth,int a,int b, Boolean playsNow)
    {

        if(depth==0){
            if(move.size()!=0) {
                return move.get(0);
            }else{
                return s.getBoard();
            }
         }

        //If the X plays then it wants to MAXimize the heuristics value
        String player = Main.minimax;
        if (playsNow)
        {
           player = Main.minimax;
           int maxEvaluation=-1000;
           s.pickValidMoves(player);

           children = s.getChildren();

           if(children.size()!=0)
           {
               for (String[][] Xchild: children)
               {
                    String[][] stateForEvaluation = MiniMax(new State(Xchild),depth-1,a,b,false);
                   int stateEval = evaluate(stateForEvaluation,player);
                   maxEvaluation = max(maxEvaluation,stateEval);
                   a= max(a,stateEval);
                   if(b<=a) break;
                    if(maxEvaluation==evaluate(stateForEvaluation, player))
                    {
                        if(Xchild != null)
                        {
                            move =  new ArrayList<>();
                            move.add(Xchild);
                        }
                        return stateForEvaluation;
                    }
                 }
           }

            return s.getBoard();

        }
        //If the O plays then it wants to MINimize the heuristics value
        else
        {
            int minEvaluation=+1000000;
            if(player.equals(State.BLACK))
                player = State.WHITE;
            else if(player.equals(State.WHITE))
                player = State.BLACK;
            s.pickValidMoves(player);

            children = s.getChildren();
            if(children.size()!=0)
            {
                for (String[][] child: children)
                {
                    String[][] stateForEvaluation = MiniMax(new State(child),depth-1,a,b,true);
                    int stateEval = evaluate(stateForEvaluation,player);
                    minEvaluation = max(minEvaluation,stateEval);
                    b= min(b,stateEval);
                    if(b<=a) break;
                    minEvaluation = min(minEvaluation,stateEval);
                    if(minEvaluation==evaluate(stateForEvaluation, player))
                    {
                        return stateForEvaluation;
                    }
                    return s.getBoard();
                }
            }
            return s.getBoard();
        }

    }

    private int max(int maxEvaluation, int evaluation)
    {
        if(maxEvaluation<=evaluation){
            maxEvaluation = evaluation;
        }
        return maxEvaluation;
    }
    private int min(int maxEvaluation, int evaluationMatrix)
    {
        if(maxEvaluation>=evaluationMatrix){
            maxEvaluation = evaluationMatrix;
        }
        return maxEvaluation;
    }

    private int evaluate(String[][] BoardForEval,String player)
    {
        int evaluation=0;

        for(int i=0;i<State.dimensions;i++)
        {
            for (int j = 0; j < State.dimensions; j++)
            {
                if (BoardForEval[i][j].equals(player))
                {
                    evaluation += 10;
                    if (i == 0 || i == State.dimensions-1)
                    {
                        evaluation +=15;
                        if (j == 0 || j == State.dimensions-1)
                        {
                            evaluation += 65;
                        }
                    }
                    else if(j==0||j==State.dimensions-1)
                    {
                        evaluation+=15;
                    }
                }
            }
        }
        return evaluation ;
    }




}

















//      private final int[] evaluateMatrix ={100,30,10,-25};
//      private int Move_i = 0;
//      private int Move_j = 0;