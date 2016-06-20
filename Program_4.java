import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Program_4 extends Frame {
	public static void main(String[] args){
		new Program_4();
	}
	@SuppressWarnings("deprecation")
	Program_4(){
		super("Hexagons");
		addWindowListener(new WindowAdapter()
				{
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
		setSize(600,600);
		add("Center", new cvHexagons());
		show();
	}
}

@SuppressWarnings("serial")
class cvHexagons extends Canvas {
	
	int centerX, centerY;
	float pixelSize, rWidth = 200.0F, rHeight = 200.0F,xP = -1.0F,yP;
	
	cvHexagons(){
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent evt){
				xP = evt.getX(); yP = evt.getY();
				repaint();
			}
		});
	}
	
	void initgr(){
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth/maxX, rHeight/maxY);
		centerX = maxX/2; centerY = maxY/2;
		
	}
	
	int iX(float x){return Math.round(centerX + x/pixelSize);}
	int iY(float y){return Math.round(centerY + y/pixelSize);}
	
	void drawLine(Graphics g, float xA, float yA, float xB, float yB){
		g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
	}
	
	public void paint(Graphics g){
		initgr();
		Point origin = new Point(0, 0);
		double value1 = Math.pow(xP-origin.x,2);
	    double value2 =Math.pow(yP- origin.y,2);
	    float r = (float)Math.sqrt(value1+value2);
	    
		float halfr = r/2, horpitch = 1.5F * r, w = (float) (r * Math.sqrt(3)), h = w/2, marginleft, marginbottom;
		int nhor = (int) Math.floor((rWidth - halfr)/horpitch),
				nvert = (int) Math.floor(rHeight/w);
		marginleft = -rWidth/2 + 0.5F * (rWidth - halfr - nhor * horpitch);
		marginbottom = -rHeight/2 + 0.5F * (rHeight - nvert * w);
		for(int i=0; i<nhor; i++){
			float x = marginleft + r + i * horpitch,
					y0 = marginbottom + (1 + i % 2) * h;
			int m = nvert - i % 2;
			for(int j=0; j<m; j++){
				float y = y0 + j * w;
				drawLine(g, x + halfr, y + h, x - halfr, y+h);
				drawLine(g, x - halfr, y + h, x - r, y);
				drawLine(g, x - r, y, x - halfr, y-h);
				g.setXORMode(Color.green);
				drawLine(g, x - halfr, y-h, x + halfr, y-h);
				drawLine(g, x + halfr, y-h, x + r, y);
				drawLine(g, x + r, y, x + halfr, y+h);
				g.setPaintMode();
				
			}
		}
	}
}
