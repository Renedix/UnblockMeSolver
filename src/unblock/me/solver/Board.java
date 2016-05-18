import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.math.*;

public class Board extends JComponent implements MouseListener
{
    public int squares;
    int sqWidth;
    int sqHeight;
    boolean[][] slots;
    boolean coordinates;
    boolean move;
    
    Color coords;
    
    JMenuItem addItem;
    JMenuItem moveItem;
    
    boolean create;
    int xCreate;
    int yCreate;
    
    public ArrayList<Plank> planks;

    public Board(Dimension size, int squares){
        planks = new ArrayList<Plank>();
        move = true;
        create = false;
        coords = BoardFrame.fg;
        coordinates = true;
        this.setPreferredSize(size);
        this.squares=squares;
        this.setLayout(new FlowLayout());
        slots = new boolean[squares][squares];
        for(int i=0;i<squares;i++)
            for(int j=0;j<squares;j++)
                slots[i][j] = false;
        addMouseListener(this);
        sqWidth = (int) size.getWidth()/squares;
        sqHeight = (int) size.getHeight()/squares;
        
        placeKeyBlock(1,2,2,'h');
    }
    
    public void resetBoard(){
        for(int i=0;i<squares;i++)
            for(int j=0;j<squares;j++)
                slots[i][j] = false;
        for(int i=0;i<planks.size();i++)
            remove(planks.get(i));
        planks = new ArrayList<Plank>();
        repaint();
    }
    
    public Board(Board b){
        planks = new ArrayList<Plank>();
        this.squares=b.squares;
        slots = new boolean[squares][squares];
        for(int i=0;i<squares;i++)
            for(int j=0;j<squares;j++)
                slots[i][j] = false;
        sqWidth = 0;
        sqHeight = 0;
            
        for(int i=0;i<b.planks.size();i++){
            Plank p = b.planks.get(i);
            placeBlock(p.xPos,p.yPos,p.size,p.dir);
        }
        
        placeKeyBlock(1,2,2,'h');
    }
    
    public void undoCreate(){create=false;}

    public void paint(Graphics g){
        g.setColor(BoardFrame.bg);
        g.fillRect(0,0,this.getWidth(),this.getWidth());
        super.paint(g);
        if(coordinates)
            drawCoords(g);
                
    }
    
    private void drawCoords(Graphics g){
        g.setColor(coords);
        int size = getWidth()/squares;
        for(int i=0;i<slots.length;i++){
            for(int j=0;j<slots[0].length;j++)
                g.drawString(("["+j+"]"+"["+i+"]"),j*size+50,i*size+50);
        }
    }
    
    public void placeBlock(int x, int y, int size, char dir){
        if( (dir=='h'||dir=='v') &&
             size < squares &&
             x<squares &&
             y<squares){
                 Plank plank  = new Plank(this,x,y,size,dir,sqWidth,sqHeight,false);
                 add(plank);
                 planks.add(plank);
                 fillSlots(x,y,size,dir);
                 repaint();
        }else{
            System.out.println("INVALID");
        }
    }
    
    public void placeKeyBlock(int x, int y, int size, char dir){
        if( (dir=='h'||dir=='v') &&
             size < squares &&
             x<squares &&
             y<squares){
                 Plank plank  = new Plank(this,x,y,size,dir,sqWidth,sqHeight,true);
                 add(plank);
                 planks.add(plank);
                 fillSlots(x,y,size,dir);
                 repaint();
        }
    }
    
    private void fillSlots(int x, int y, int size, char dir){
        if(dir=='h')
            for(int i=x;i<x+size;i++)
                slots[i][y] = true;
        else
            for(int i=y;i<y+size;i++)
                slots[x][i] = true;
    }
    
    public void printSlots(){
        System.out.println("==========================");
        for(int i=0;i<slots.length;i++){
            for(int j=0;j<slots[0].length;j++)
                System.out.print(slots[j][i]+"|\t|");
         System.out.println();
        }
        System.out.println("==========================");
    }
    
    public boolean slotConsumed(int x, int y){
        return slots[x][y];
    }
    
    public void setSlot(int x, int y, boolean val){
        slots[x][y] = val;
    }
    
    
    public void mouseExited(MouseEvent e){}
    
    public void mouseClicked(MouseEvent e){}
    
    public void mouseReleased(MouseEvent e){
        if(create){ //create a new plank
            //get released coordinates
            int relX = e.getX();
            int relY = e.getY();
            
            //get pressed coordinates
            int preX = xCreate;
            int preY = yCreate;
            
            //ensure pre's < rel's
            if(preX>relX){
                int temp = preX;
                preX = relX;
                relX=temp;
            }
            
            if(preY>relY){
                int temp = preY;
                preY = relY;
                relY = temp;
            }
            
            
            //calculate which boxes where selected
            int x = getHoriCord(preX);
            int y = getVertCord(preY);
            
            //work out the oreigntation
            char dir = getRot(relX-preX,relY-preY);
            
            //get size
            int size = getPlankSize(relX,relY,preX,preY,dir);
            
            //create/add new plank
            placeBlock(x,y,size,dir);
            
            //reset create flag
            create=false;
            
            setVisible(false);
            setVisible(true);
        }
    }
    
    private int getPlankSize(int relX, int relY, int preX, int preY, char dir){
        if(dir=='h')
            return getHoriCord(relX)-getHoriCord(preX)+1;
        return getVertCord(relY)-getVertCord(preY)+1;
    }
    
    private int getHoriCord(int relX){
        int count = 0;
        int decr = getWidth()/squares;
        int start = relX;
        
        while(start>0){
            count++;
            start-=decr;
        }
        
        return count-1;
    }
    
    private int getVertCord(int relY){
        int count = 0;
        int decr = getHeight()/squares;
        int start = relY;
        
        while(start>0){
            count++;
            start-=decr;
        }
        
        return count-1;
    }
    
    private char getRot(int xDif, int yDif){
        if(xDif>yDif)
            return 'h';
        
        return 'v';
    }
    
    public void mouseEntered(MouseEvent e){}
    
    public void mousePressed(MouseEvent e){
        create = true;
        xCreate = e.getX();
        yCreate = e.getY();
    }
    
    public boolean movePiece(BoardMove move){
        for(int i=0;i<planks.size();i++)
            {
            Plank p = planks.get(i); //get plank
                if(p.xPos==move.x && p.yPos==move.y){ //if the plank matches
                                                      //attempt to move the plank in x-direction and return result
                    if(move.movement.equals("up")){
                        return planks.get(i).moveUp(1);
                    }else if(move.movement.equals("down")){
                        return planks.get(i).moveDown(1);
                    }else if(move.movement.equals("right")){
                        return planks.get(i).moveRight(1);
                    }else{
                        return planks.get(i).moveLeft(1);
                    }
                }
            }
        return false;
    }
    
    public Board retrieveBoard(BoardMove move){
        Board newBoard = null;
        
        boolean legal=false;
        
        newBoard = new Board(this); //replicate this board
            legal = newBoard.movePiece(move);
        
        if(legal)
            return newBoard;
        else
            return null;
    }
    
    public static Board testBoard(){
        Board b = new Board(new Dimension(500,500),7);
            b.placeBlock(0,0,2,'v');
            b.placeBlock(2,2,2,'h');
            return b;
    }
    
    public static Board testBoard2(){
        Board b = new Board(new Dimension(500,500),7);
            b.placeBlock(0,0,2,'v');
            
            return b;
    }
    
}
