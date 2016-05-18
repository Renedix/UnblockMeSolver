import java.util.ArrayList;

public abstract class Node
{
    Node parent;
    public DynSet<Node> checkedStates;
    public DynSet<Node> uncheckedStates;
    
    public Node(){
        if(checkedStates==null){
            checkedStates = new DynSet<Node>();
            uncheckedStates = new DynSet<Node>();
        }
    }
    
    public Node(DynSet<Node> checked, DynSet<Node> unchecked){
        checkedStates = checked;
        uncheckedStates = unchecked;
    }
    
    public void setParent(Node parent){
        this.parent=parent;
    }
    
    public Node getParent(){
        return parent;
    }
    
    public DynSet<Node> getParentTrace(){
        DynSet<Node> queue = new DynSet<Node>();
        if(parent != null)
            queue.addAll(parent.getParentTrace());
        
        queue.push(this);
        return queue;
    }
    
    private void moveToChecked(){
        checkedStates.push(uncheckedStates.pop());
    }
    
    private void addChildren(){
        DynSet<Node> children = getChildren();
        children.setParent(this);
        uncheckedStates.addAll(children);
    }
    
    public void setCheckedAddChilds(){
        
        moveToChecked();
        addChildren();
    }
    
    public abstract <P extends Node> DynSet<P> getChildren();
    public abstract String getKey();
    public abstract boolean nodeEquals(Object ob);
    
    public static DynSet breadthSearch(Node target, Node root){
        if(root.nodeEquals(target)){ //return root if it's the target
            DynSet<Node> set = new DynSet<Node>();
            set.push(target);
            return set;
        }else{
            root.uncheckedStates.push(root);
            root.setCheckedAddChilds();
            while(root.uncheckedStates.length()>0){ //go through all  unchecked states
                Node node = root.uncheckedStates.getTop();
                if(node.nodeEquals(target)) //if selected node is target, return path
                    return node.getParentTrace();
                else{
                    node.setCheckedAddChilds(); //if selected node is !target, stick in checked list
                }
                System.out.println("States checked: "+root.checkedStates.length());
            }
        }
        return null; //return null if target is not found
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
