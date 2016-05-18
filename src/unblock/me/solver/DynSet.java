import java.util.ArrayList;
import java.util.HashMap;

public class DynSet<E extends Node>
{
    HashMap<String,E> set;
    ArrayList<E> list;

    public DynSet(){
        set = new HashMap<String, E>();
        list = new ArrayList<E>();
    }
    
    public boolean push(E item){
        if(!contains(item)){
            set.put(item.getKey(), item);
            list.add(item);
            return true;
        }
        return false;
    }
    
    public E pop(){
        return set.remove(list.remove(0).getKey());
    }
    
    public E getTop(){return list.get(0);}
    
    public boolean contains(E item){
        return set.containsKey(item.getKey());
    }
    
    public void addAll(DynSet<E> items){
        int number = items.length();
        for(int i=0;i<number;i++)
            push(items.pop());
    }
    
    public int length(){return list.size();}
    
    public DynSet<E> getReverse(){
        DynSet<E> set = new DynSet<E>();
            set.addAll(this);
            return set;
    }
    
    public void setParent(Node parent){
        for(int i=0;i<list.size();i++)
            list.get(i).setParent(parent);
    }
    
    public void printContents(){
        for(int i=0;i<list.size();i++)
            System.out.println(i+":"+list.get(i).getKey());
    }
}
