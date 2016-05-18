import java.util.ArrayList;
import java.awt.Dimension;
public class UBMNode extends Node
{
    public Board b;
    public BoardMove move;

    public UBMNode(Board b){
        super();
        this.b=b;
    }
    
    public UBMNode(Board b, BoardMove move, DynSet<Node> unchecked, DynSet<Node> checked){
        super();
        this.b=b;
        this.move=move;
        this.checkedStates=checked;
        this.uncheckedStates=unchecked;
    }
    
    public DynSet<UBMNode> getChildren(){
        DynSet<UBMNode> children = new DynSet<UBMNode>();
        
        //get planks
        ArrayList<Plank> planks = b.planks;
        
        
        
        for(int i=0;i<planks.size();i++){ //go through all planks in game
            Plank p = planks.get(i);
            if(planks.get(i).dir=='h'){ //if plank is horizontal..
                BoardMove moveright = new BoardMove(p.xPos,p.yPos,"right"); //create a right-move
                BoardMove moveleft = new BoardMove(p.xPos,p.yPos,"left"); //create a left-move
                Board b1 = b.retrieveBoard(moveright); //attempt to create a new board using the right move
                Board b2 = b.retrieveBoard(moveleft); //attempt to create a new board using the left move
                
                
                if(b1!=null){
                    UBMNode node1 = new UBMNode(b1,moveright,uncheckedStates,checkedStates);
                    if(!uncheckedStates.contains(node1) && !checkedStates.contains(node1))
                        children.push(node1);
                }
                if(b2!=null){
                    UBMNode node2 = new UBMNode(b2,moveleft,uncheckedStates,checkedStates);
                    if(!uncheckedStates.contains(node2) && !checkedStates.contains(node2))
                        children.push(new UBMNode(b2,moveleft,uncheckedStates,checkedStates));
                }
            }else{
                BoardMove moveup = new BoardMove(p.xPos,p.yPos,"up");
                BoardMove movedown = new BoardMove(p.xPos,p.yPos, "down");
                Board b3 = b.retrieveBoard(moveup);
                Board b4 = b.retrieveBoard(movedown);
                if(b3!=null){
                    UBMNode node3 = new UBMNode(b3,moveup,uncheckedStates,checkedStates);
                    if(!uncheckedStates.contains(node3) && !checkedStates.contains(node3))                    
                        children.push(new UBMNode(b3,moveup,uncheckedStates,checkedStates));
                }
                if(b4!=null){
                    UBMNode node4 = new UBMNode(b4,movedown,uncheckedStates,checkedStates);
                    if(!uncheckedStates.contains(node4) && !checkedStates.contains(node4))
                        children.push(new UBMNode(b4,movedown,uncheckedStates,checkedStates));
                }
            }
        }
        
        return children;
    }
    
    public String getKey(){
        String key = "";
        
        for(int i=0;i<b.planks.size();i++){
            Plank p = b.planks.get(i);
            key+=p.xPos+"|"+p.yPos+"|"+p.dir+"|"+p.size+"||";
        }
        
        return key;
    }
    

    public boolean nodeEquals(Object nodeObject){
        UBMNode node = (UBMNode) nodeObject;
        
        boolean match = false;
        
        for(int i=0;i<node.b.planks.size();i++){
            match = false;
            Plank p = node.b.planks.get(i);
            for(int j=0;j<b.planks.size();j++)
                if(p.pEquals(b.planks.get(j))){
                    match=true;
                }
            if(!match)
                return false;
        }
        return match;
    }
    
    public static UBMNode getGoal(int gridSize){
        
        Board b = new Board(new Dimension(500,500),gridSize);
            b.placeKeyBlock(4,2,2,'h');
        
        return new UBMNode(b);
    }
    
    
    
    
}
