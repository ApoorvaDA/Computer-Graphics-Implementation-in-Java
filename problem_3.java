import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class problem_3 extends Frame {
	public static void main(String[] args){
		new problem_3();
	}
	@SuppressWarnings("deprecation")
	problem_3(){
		super("Concentric squares");
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
		setSize(600,600);
		add("Center", new CvSquares());
		show();
	}
}

@SuppressWarnings("serial")
class CvSquares extends Canvas {
	
	public void paint(Graphics g){
		Graphics2D g1 = (Graphics2D) g;
		
		double rotation_angle = Math.toRadians(45);
		double factor = 1 / (Math.sin(rotation_angle) + Math.cos(rotation_angle));
		int size = 300;
		
		g1.translate(size, size);
		
		for(int i=0; i<50; i++){
			 g1.drawRect(-size / 2, -size / 2, size, size);
             size = (int) (size * factor);
             g1.rotate(rotation_angle);
		}
	}
}