
public class BoardMover implements Runnable
{
    DynSet<UBMNode> steps;
    Board board;
    
    
    public BoardMover(DynSet<UBMNode> solution, Board board){
        this.steps=solution;
        this.board=board;
    }

    public void run(){
        if(steps!=null){
            int countdown = 5;
            System.out.println("SOLUTION WILL COMMENCE IN..");
            while(countdown>0){
                try{Thread.currentThread().sleep(1000);}catch(Exception e){}            
                countdown--;
                System.out.println(countdown+"...");
            }

            System.out.println("GO!");
            
            steps.pop();
            int count = steps.length();
            while(count>0){
                System.out.println("Solving in "+count+" moves.");
                count--;
                UBMNode node = steps.pop();
                board.movePiece(node.move);
                board.setVisible(false);
                board.setVisible(true);
                try{Thread.currentThread().sleep(500);}catch(Exception e){}
            }
            
        }
    }
}