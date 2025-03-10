public class Card {
    private int number; 
    private boolean isFaceUp; 

    public Card(int cardNumber){
        number = cardNumber; 
        isFaceUp = false; 
    }

    public int getCardNumber(){
        return number; 
    }

    public void flip(){
        isFaceUp = !isFaceUp; 
    }

    public boolean _isFaceUp(){
        return isFaceUp; 
    }
}

