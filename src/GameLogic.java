public class GameLogic {
    private Card deck[][]; 
    private int score; 

    public boolean test(int row1, int col1, int row2, int col2){
        if (CardsMatch(deck[row1][col1], deck[row2][col2])) {
            incrementScore(); 
            return true; 
        }
        return false; 
    }

    public boolean gameOver(){
        for (int r = 0; r < deck.length; r++){
            for (int c = 0; c < deck[r].length; c++){
                if (!deck[r][c]._isFaceUp()){
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard(){ 
        for (int r = 0; r < deck.length; r++){
            for (int c = 0; c < deck[r].length; c++){
                if (deck[r][c]._isFaceUp()){
                    System.out.print(deck[r][c].getCardNumber() + " ");
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    
    public GameLogic(){
        score = 0; 
        deck = new Card[4][4]; 

        int pairValue = 1; 
        int count = 0;

        for (int r = 0; r < deck.length; r++){
            for (int c = 0; c < deck[r].length; c++){
                deck[r][c] = new Card(pairValue); 
                count++;
                if (count == 2){
                    pairValue++;
                    count = 0; 
                }
            }
        }
        shuffle(); 
    }

    public void shuffle(){
        for (int i = 0; i < deck.length; i++){
            for (int j = 0; j < deck[i].length; j++){
                int r = (int)(Math.random() * deck.length);
                int c = (int)(Math.random() * deck[i].length);
                Card temp = deck[i][j];
                deck[i][j] = deck[r][c];
                deck[r][c] = temp; 
            }
        }
    }

    public boolean CardsMatch(Card card1, Card card2){
        return card1.getCardNumber() == card2.getCardNumber(); 
    }

    public int incrementScore(){
        score++;
        return score; 
    }

    public void reset(){
        score = 0; 
        shuffle(); 
    }

    public Card[][] getDeck(){
        return deck; 
    }

    public int getScore(){
        return score; 
    }

}