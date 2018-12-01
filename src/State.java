import java.util.ArrayList;

class State
{
    static int dimensions=8;
    private String[][] reversiBoard;
    private static boolean someoneInLine=false;
    private ArrayList<String[][]> children =  new ArrayList<>();
    static final  String BLACK = " X ", WHITE = " 0 ";
    private final String EMPTY = "|_|";
    State(String[][] Board)
    {

        reversiBoard = Board;
    }
    State()
    {
        reversiBoard= new String[dimensions][dimensions];
        initializeBoard();
    }


    void showBoard(String[][] inputBoard)
    {
        System. out.println("     A   B   C   D   E   F   G   H \n");
        for(int i=0; i<dimensions; i++)
        {
            StringBuilder row = new StringBuilder((i + 1) + "   ");
            for (int j=0; j<dimensions; j++)
            {
                row.append(inputBoard[i][j]).append(" ");
            }
            System.out.println(row+"\n");
        }
    }
    void updateBoard(String[][] board)
    {

        reversiBoard = board;
        showBoard(reversiBoard);
    }
    private void initializeBoard()
    {
        for(int i=0; i<dimensions; i++)
        {
            for (int j = 0; j <dimensions; j++)
            {
                reversiBoard[i][j]=EMPTY;
            }
        }
        reversiBoard[3][3] = BLACK;   reversiBoard[4][4] = BLACK;
        reversiBoard[3][4] = WHITE;   reversiBoard[4][3] = WHITE;
    }
    ArrayList<String[][]> getChildren()
    {

        return children;
    }


    Boolean isValid(int length, int width, String player)
    {
        if(length>dimensions||width>dimensions || length<0 || width<0)
        {
            System.out.println("cell out of bounds or invalid input!");
            return false;
        }
        if(!reversiBoard[length][width].equals(EMPTY))
        {
            System.out.println("not an empty cell!");
            return false;
        }
        return flipsAPiece(length, width, player, true);
    }
    private Boolean flipsAPiece(int length,int width,String Player,Boolean changes)
    {
        boolean flips = false;
        for(int i=length-1;i<=length+1;i++)
        {
            if(i<0) continue;
            if(i>=dimensions)continue;
            for (int j = width - 1; j <= width + 1; j++)
            {
                if(j<0) continue;
                if(j>=dimensions)continue;
                if(i==length&&j==width)continue;

                if (!((reversiBoard[i][j].equals(EMPTY))||(reversiBoard[i][j].equals(Player))))
                {
                    if(hasCoPlayerInLine(length,width,Player,i,j,changes))
                    {
                        if (someoneInLine) flips = true;
                    }

                }
            }
        }
        return flips;
    }
    private Boolean hasCoPlayerInLine(int moveX,int moveY,String player,int posX,int posY,Boolean changes)
    {
        String [][] testBoard =  new String[dimensions][dimensions];
        for (int i=0;i<dimensions;i++){
            System.arraycopy(reversiBoard[i], 0, testBoard[i], 0, dimensions);
        }
        someoneInLine=false;
        boolean oppPlayerInLine = false;
        int counterForLoops = 0;
        do{
            counterForLoops++;
            if (posX < moveX&&posX>0)
            {
                posX--;
                if(posX<0)continue;
                //X<moveX Y<moveY
                if (posY < moveY&&posY>0)
                {
                    posY--;
                    if(posY<0)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));
                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {

                            for (int i = posX; i <= moveX; i++)
                            {
                                if(posY>=dimensions)continue;
                                testBoard[i][posY++] = player;
                            }
                            if(!changes)
                            { children.add(testBoard);}

                        break;

                    }
                }
                //x<moveX y>moveY
                else if (posY> moveY&&posY<dimensions)
                {
                    posY++;
                    if(posY>=dimensions)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));
                    someoneInLine = checkForCoplayer(posX, posY, player);

                    if (someoneInLine)
                    {

                            for (int i = posX; i <= moveX; i++)
                            {
                                if(posY<0)continue;
                                testBoard[i][posY--] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}

                        break;
                    }

                    //x<moveX Y=moveY
                }
                else
                {
                    if(posX==dimensions||posX<0||posY==dimensions||posY<0)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));
                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {

                            for (int i = posX; i <= moveX; i++)
                            {
                                testBoard[i][posY] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}


                        break;
                    }

                }
            }

            else if (posX > moveX&&posX<dimensions)
            {
                posX++;
                if(posX>=dimensions)continue;
                //x>moveX y<moveY
                if (posY < moveY&&posY>0)
                {
                    posY--;
                    if(posY<0)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));
                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {

                            for (int i = posX; i >= moveX; i--)
                            {
                                if(posY>=dimensions)continue;
                                testBoard[i][posY++] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}


                        break;
                    }
                    //x>movex y>movey
                }
                else if (posY > moveY&&posY<dimensions)
                {
                    posY++;
                    if(posY>=dimensions)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));

                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {

                            for (int i = posX; i >= moveX; i--)
                            {
                                if(posY<0)continue;
                                testBoard[i][posY--] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}

                        break;
                    }
                    //x>moveX y=movey
                } else {
                    if(posX==dimensions||posX<0||posY==dimensions||posY<0)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));

                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {

                            for (int i = posX; i >= moveX; i--)
                            {
                                testBoard[i][posY] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}

                        break;
                    }

                }
            }
            else
            {
                if(posX==dimensions||posX<0||posY==dimensions||posY<0)continue;
                //x=movex y<moveY
                if (posY < moveY&&posY>0)
                {
                    posY--;
                    if(posY<0)continue;
                    oppPlayerInLine =!(testBoard[posX][posY].equals(player)||testBoard[posX][posY].equals(EMPTY));
                    someoneInLine =checkForCoplayer(posX,posY,player);
                    if(someoneInLine)
                    {
                            for (int i = posY; i <= moveY; i++)
                            {
                                testBoard[posX][i] = player;
                            }
                        if(!changes)
                        {
                            //placeMove(moveX,moveY,player,testBoard);
                            children.add(testBoard);}

                        break;

                    }
                }
                //x=movex y>moveY
                else
                {
                    if(posX==dimensions||posX<0||posY==dimensions||posY<0)continue;
                    if(posY<dimensions)
                    {
                        posY++;
                        if(posY>=dimensions)continue;
                        oppPlayerInLine = !(testBoard[posX][posY].equals(player) || testBoard[posX][posY].equals(EMPTY));
                        someoneInLine = checkForCoplayer(posX, posY, player);

                        if (someoneInLine)
                        {

                                for (int i = posY; i >= moveY; i--)
                                {
                                    testBoard[posX][i] = player;
                                }
                            if(!changes)
                            {
                                //placeMove(moveX,moveY,player,testBoard);
                                children.add(testBoard);}
                            break;
                        }
                    }

                }

            }

        }while((oppPlayerInLine)&&counterForLoops<=100);


        if(changes)
        {
            for (int i=0;i<dimensions;i++){
                System.arraycopy(testBoard[i], 0, reversiBoard[i], 0, dimensions);
            }
        }
        return someoneInLine;
    }
    private boolean checkForCoplayer(int posX,int posY,String Player)
    {

        return reversiBoard[posX][posY].equals(Player);
    }



    int countForPlayer(String player)
    {
        int count =0;
        for(int i=0; i<dimensions; i++)
        {
            for(int j=0; j<dimensions; j++)
            {
                if(reversiBoard[i][j].equals(player))
                    count++;
            }
        }
        return count;
    }
    int countValidMoves(String Player)
    {
        int validCount = 0;
        for(int i=0;i<dimensions;i++)
        {
            for(int j=0;j<dimensions;j++)
            {
                if((reversiBoard[i][j].equals(EMPTY)))
                {
                    if (flipsAPiece(i, j, Player, false))
                    {
                        validCount++;
                    }
                }
            }
        }
        return validCount;
    }
    void pickValidMoves(String currentPlayer)
    {
        children  = new ArrayList<>();

        for(int i=0;i<dimensions;i++)
        {
            for(int j=0;j<dimensions;j++)
            {
                if((reversiBoard[i][j].equals(EMPTY)))
                {
                    if (flipsAPiece(i, j, currentPlayer, false))
                    {
                        //TODO in later version
                        //Give possible moves
                    }
                }
            }

        }



    }


    String[][] getBoard() {
        return reversiBoard;
    }
}


