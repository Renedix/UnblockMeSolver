public class WordNode extends Node
{
    public String word;
    
    public WordNode(String word){
        super();
        this.word=word;
    }
    
    public WordNode(String word, DynSet<Node> checked, DynSet<Node> unchecked){
        super(checked, unchecked);
        this.word=word;
    }

    public DynSet<WordNode> getChildren(){
        DynSet<WordNode> children = new DynSet<WordNode>();
        
        for(int i = 0;i<word.length()-1;i++){
            WordNode node = new WordNode(switchPlaces(word,i,i+1), checkedStates, uncheckedStates); 
            if(!uncheckedStates.contains(node)&&!checkedStates.contains(node))
                children.push(node);
        }
        return children;
    }
    
    private String switchPlaces(String word, int index1, int index2){
        String returned = "";
        char ch1 = word.charAt(index1);
        char ch2 = word.charAt(index2);
        for(int i=0;i<word.length();i++){
            if(i==index1)
                returned+=ch2;
            else if(i==index2)
                returned+=ch1;
            else            
                returned += word.charAt(i);
        }
        
        return returned;
    }
    
        
    public String getKey(){
        return word;
    }
    
    public boolean nodeEquals(Object nodeObject){
        
        WordNode node = (WordNode) nodeObject;
        return node.word.equals(word);
    }
    
}
