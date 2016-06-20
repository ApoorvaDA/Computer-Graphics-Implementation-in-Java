import java.awt.*;
import java.awt.event.*;

public class Program_5 extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args){
		new Program_5();
	}
	@SuppressWarnings("deprecation")
	Program_5(){
		super("Dashed Lines");
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
		setSize(600,600);
		add("Center", new Lines());
		show();
	}
}	
	
class Lines extends Canvas {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int maxX, maxY;
	void dashedLine(Graphics g, float xA, float yA, float xB, float yB, float dashLength){
		float u1 = xB - xA, u2 = yB - yA,
				L = (float) Math.sqrt(u1 * u1 + u2 * u2);
		int n = Math.round((L/dashLength + 1)/2);
		float h1 = u1/(2 * n - 1), h2 = u2/(2 * n - 1);
		for(int i=0; i<n; i++){
			float x1 = xA + 2 * i * h1,
					y1 = yA + 2 * i * h2,
					x2 = x1 + h1,
					y2 = y1 + h2;
			drawLine(g, x1, y1, x2, y2);
		}
	}
	
	void initgr(){
		Dimension d = getSize();
		maxX = d.width - 1; maxY = d.height -1;
	}
	int iX(float x){
		return Math.round(x);
	}
	
	int iY(float y){
		return maxY - Math.round(y);
	}
	
	private void drawLine(Graphics g, float x1, float y1, float x2, float y2) {
		// TODO Auto-generated method stub
		if(x1 != x2 || y1 != y2){
			g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
		}
	}
	
	public void paint(Graphics g){
		initgr();
		g.drawRect(100, 100, 400, 200);
		dashedLine(g,200,325,400,325,20);
		dashedLine(g,200,425,400,425,20);
		dashedLine(g,200,325,200,425,20);
		dashedLine(g,400,325,400,425,20);
		dashedLine(g,100,277,200,325,20);
		dashedLine(g,100,477,200,425,20);
		dashedLine(g,500,277,400,325,20);
		dashedLine(g,500,477,400,425,20);
		
	}
}
