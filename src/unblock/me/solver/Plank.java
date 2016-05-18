import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Plank extends JComponent implements MouseListener
{
    Board board; //used for drawing relative size
    public int xPos;
    public int yPos;
    public int size;
    public char dir;
    
    
    Color bgColor;
    Color fgColor;
    Color hColor;
    Color keyColor;
    Color keyHColor;
    Color keyFgColor;
    
    boolean highlight;
    
    int mouseXBuf;
    int mouseYBuf;
    
    int sqWidth;
    int sqHeight;
    
    boolean key;
   
    public Plank(Board b, int xPos, int yPos, int size, char dir, int width, int height, boolean key){
        this.xPos=xPos;
        this.yPos=yPos;
        this.size=size;
        this.dir=dir;
        this.board=b;
        bgColor = new Color(64,224,208);
        fgColor = new Color(0,206,209);
        hColor = new Color(16,78,139);
        keyColor = new Color(255,36,0);
        keyHColor = new Color(128,0,0);
        keyFgColor = new Color(205,38,38);
        highlight = false;
        addMouseListener(this);
        this.setVisible(true);
        this.sqWidth=width;
        this.sqHeight=height;
        this.key=key;
    }
    
    public void setBg(Color color){
        bgColor = color;
    }

    public void paint(Graphics g){
        //calc size of square
        int bWSize = sqWidth;
        int bHSize = sqHeight;
        
        //calc position of start block
        int xStart = xPos*bWSize;
        int yStart = yPos*bHSize;
        
        //calc last coordinate of square
        int xLast;
        int yLast;
        if(dir=='h'){
            xLast = ((xPos+size)*bWSize)-xStart;
            yLast = ((yPos+1)*bHSize)-yStart;
        }else{
            xLast = ((xPos+1)*bWSize)-xStart;
            yLast = ((yPos+size)*bHSize)-yStart;
        }
        
        //set position of component
        setBounds(xStart,yStart,xLast,yLast);
        
        //set background
        if(!highlight){
            if(key)
                g.setColor(keyColor);
            else
                g.setColor(bgColor);
        }
        else{
            if(!key)
                g.setColor(hColor);
            else
                g.setColor(keyHColor);
        }
        g.fillRect(0,0,getWidth(),getHeight());
        
        //set border
        if(!key)
            g.setColor(fgColor);
        else
            g.setColor(keyFgColor);
        g.drawRect(0,0,getWidth()-1,getHeight()-1);
        g.drawRect(1,1,getWidth()-3,getHeight()-3);
        
    }

    
    public void mouseExited(MouseEvent e){
        highlight=false;
        repaint();    
    }
    
    public void mouseClicked(MouseEvent e){}
    
    public void mouseReleased(MouseEvent e){
        if(dir=='h'){
            int xCoord = e.getX();
            if(xCoord-50 > mouseXBuf)
                moveRight(1);
            else if(xCoord+50 < mouseXBuf)
                moveLeft(1);
        }else{
            int yCoord = e.getY();
            if(yCoord-50 > mouseYBuf)
                moveDown(1);
            else if(yCoord+50 < mouseYBuf)
                moveUp(1);    
            
        }
        repaint();
        
    }
    
    
    public void mouseEntered(MouseEvent e){
        highlight=true;
        board.undoCreate();
        repaint();
    }
    
    public void mousePressed(MouseEvent e){
        mouseXBuf = e.getX();
        mouseYBuf = e.getY();
    }
    
    public boolean moveUp(int val){
        if(dir=='v' && yPos > 0 && !board.slotConsumed(xPos,yPos-1)){
            for(int i=0;i<val;i++){
                board.setSlot(xPos,yPos+size-1,false);
                yPos--;
                board.setSlot(xPos,yPos,true);
            }
            return true;
        }else
            return false;
    }
    
    public boolean moveDown(int val){
        if(dir=='v'&& yPos+size < board.squares && !board.slotConsumed(xPos,yPos+size)){
            for(int i=0;i<val;i++){
                board.setSlot(xPos,yPos,false);
                yPos++;
                board.setSlot(xPos,yPos+size-1,true);
            }
            return true;
        }else
            return false;
    }
    
    public boolean moveLeft(int val){
        if(dir=='h'&& xPos > 0&& !board.slotConsumed(xPos-1,yPos)){
            for(int i=0;i<val;i++){
                board.setSlot(xPos+size-1,yPos,false);
                xPos--;
                board.setSlot(xPos,yPos,true);
            }
            return true;
        }else
            return false;
    }
    
    public boolean moveRight(int val){
        if(dir=='h' && xPos+size < board.squares && !board.slotConsumed(xPos+size,yPos)){
            for(int i=0;i<val;i++){
                board.setSlot(xPos,yPos,false);  
                xPos++;
                board.setSlot(xPos+size-1,yPos,true);
            }
            return true;
        }else
            return false;
    }
    
    public boolean pEquals(Plank p){
        if(p.xPos==xPos && p.yPos==yPos && p.size == size && p.dir == dir)
            return true;
        return false;
    }
}
