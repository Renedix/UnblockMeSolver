public class BoardMove
{
    public int x;
    public int y;
    public String movement;
    
    public BoardMove(int x, int y, String movement){
        this.x=x;
        this.y=y;
        this.movement=movement;
    }
    
    public String toString(){
        return "x:"+x+", y:"+y+", movement: "+movement;
    }
}
