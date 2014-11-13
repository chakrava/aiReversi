package aireversiOld;

/**
 *
 * @author Erik Storla
 */
public class Node {
    private int value;

    public Node(){
        value=0;
    }
    
    public Node(int v) {
        if(v<-1||v>1){
            throw new RuntimeException("Invalid range for node, must be -1,0, or 1");
        }
        value = v;
    }

    public void flip(){
        value*=-1;
    }
    
    public void setBlack(){
        value=1;
    }
    
    public void setWhite(){
        value=-1;
    }
    
    public boolean isBlank(){
        return value==0;
    }
    
    public boolean isBlack(){
        return value==1;
    }
    
    public boolean isWhite(){
        return value==-1;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int v) {
        if(v<-1||v>1){
            throw new RuntimeException("Invalid range for node, must be -1,0, or 1");
        }
        value = v;
    }
    
    
}
