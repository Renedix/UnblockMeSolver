import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
public class BoardFrame extends JFrame implements ActionListener
{
    Board b;
    
    JMenuItem solve;
    JMenuItem reset;
    
    public static Color bg;
    public static Color fg;
    
    public BoardFrame(Board b){
        super("Java Unblockme");
        this.b=b;
        setSize(new Dimension(500,500));
        setLayout(new FlowLayout());
        
        getContentPane().add(b);
        setJMenuBar(makeJMenuBar());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(2);
        setResizable(false);
    }
    
    private JMenuBar makeJMenuBar(){
        bg = new Color(193,205,193);
        fg = new Color(16,78,139);
        
        JMenuBar bar = new JMenuBar();
            bar.setBackground(bg);
            
            JMenu Game = new JMenu("Game");
                Game.setForeground(fg);
                Game.setBackground(bg);
                
            //add mode
            solve = new JMenuItem("Solve");
                solve.addActionListener(this);
                Game.add(solve);
                
            reset = new JMenuItem("Reset");
                reset.addActionListener(this);
                Game.add(reset);
                
            solve.setForeground(fg);
            solve.setBackground(bg);
            
        bar.add(Game);
            
        return bar;
    }
    
    public void actionPerformed(ActionEvent evt){
      if(evt.getSource()==solve){
        //get Solution
        DynSet<UBMNode> solution = Node.breadthSearch(UBMNode.getGoal(b.squares),new UBMNode(b));
        
        //create solver
        BoardMover solver = new BoardMover(solution,b);
        
        if(solution==null)
            JOptionPane.showMessageDialog(null, "This board cannot be solved.");
        else{
            Thread thread = new Thread(solver);
            thread.start();
        }
      } else if (evt.getSource()==reset){
          b.resetBoard();
        }
    }
    
    public static void main(String[] args){
        Board b = new Board(new Dimension(900,900),6);

        new BoardFrame(b);
    }
}
