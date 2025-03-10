public class App {
    public static void main(String[] args) throws Exception {
        GameLogic game = new GameLogic(); 

        Card[][] deck = game.getDeck(); 
        
        for (int r = 0; r < deck.length; r++){
            for (int c = 0; c < deck[r].length; c++){
                System.out.print(deck[r][c].getCardNumber() + " "); 
            }
            System.out.println(); 
        }

        game.incrementScore();
        System.out.println(game.getScore());

        game.reset(); 

        for (int r = 0; r < deck.length; r++){
            for (int c = 0; c < deck[r].length; c++){
                System.out.print(deck[r][c].getCardNumber() + " "); 
            }
            System.out.println(); 
        }

        System.out.println(game.getScore());

        System.out.println(game.CardsMatch(deck[0][0], deck[1][0])); 


        deck[3][3].flip();
        System.out.println(deck[3][3]._isFaceUp());
    }
}
