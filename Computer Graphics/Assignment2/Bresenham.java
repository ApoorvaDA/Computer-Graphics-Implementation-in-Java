import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class Bresenham extends Frame {
	public static void main(String[] args) {
		new Bresenham();
	}

	@SuppressWarnings("deprecation")
	Bresenham() {
		super("Bresenham");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(340, 230);
		add("Center", new CvBresenham());
		show();
	}
}

//Class for points in logical coordinates.
class Point2D {
	float x, y;

	Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

@SuppressWarnings("serial")
class CvBresenham extends Canvas {
	Point2D[] p = new Point2D[2]; // Array of two points for points P and Q
	int np = 0; // number of points initialized to 0
	int n=0,m =0; // Count of number of cycles for FastBres and Bresenham
	
	float rWidth = 10.0F, rHeight = 7.5F, pixelSize;
	int centerX, centerY, dGrid = 10, maxX, maxY;

	CvBresenham() { //Gets two points upon mouse click
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				float x = fx(evt.getX()), y = fy(evt.getY());
				if (np == 2)
					np = 0;
				p[np++] = new Point2D(x, y);
				repaint();
			}
		});
	}

	void initgr() {
		Dimension d = getSize();
		maxX = d.width - 1;
		maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2;
		centerY = maxY / 2;
	}

	int iX(float x) {
		return Math.round((centerX + x / pixelSize) / 10);
	}

	int iY(float y) {
		return Math.round((centerY - y / pixelSize) / 10);
	}

	float fx(int x) {
		return (x - centerX) * pixelSize;
	}

	float fy(int y) {
		return (centerY - y) * pixelSize;
	}

	void putPixel(Graphics g, int x, int y) {
		int x1 = x * dGrid, y1 = y * dGrid, h = dGrid / 2;
		g.drawOval(x1 - h, y1 - h, dGrid, dGrid);
	}

	//Faster version of drawLine by considering symmetry across mid-point 
	void drawLine(Graphics g, int xP, int yP, int xQ, int yQ) {
		m++; 
		g.setColor(Color.black);
		int x = xP, y = yP, D = 0, H = xQ - xP, c = 2 * H, M = 2 * (yQ - yP);
		int x2 = xQ, y2 = yQ; //Considers a second point (x2,y2) that traverses from xQ to xmid
		int xmid = (xP+xQ)/2; // mid point between xP and xQ
		
		for (;;) {
			g.setXORMode(Color.white);
			putPixel(g, x, y); 
			if (x != x2) // To handle overwriting of pixels at mid point
				putPixel(g, x2, y2); 
			if ((x == xmid) || (x2 == xmid))
				break;
			x++; // incrementing to put pixels from xP to xmid 
			x2--; // decrementing to put pixels from xQ to xmid
			D += M;
			if (D > H) {
				y++; // incrementing to put pixels from xP to xmid 
				y2--; // decrementing to put pixels from xQ to xmid
				D -= c;
			}
		}
		g.setPaintMode();
	}

	void FasterBres(Graphics g, int xP, int yP, int xQ, int yQ) {
		n++;
		int x = xP, y = yP, D = 0, HX = (xQ - xP), HY = (yQ - yP), c, M, xInc = 1, yInc = 1;
		if (HX < 0) {
			xInc = -1;
			HX = -HX;
		}
		if (HY < 0) {
			yInc = -1;
			HY = -HY;
		}
		// This handles the case for lines less than 45 degrees
		if (HY <= HX) {
			c = 2 * HX;
			M = 2 * HY;
			for (;;) {
				putPixel(g, x, y);
				if (x == xQ)
					break;
				x += xInc;
				D += M;
				if (D > HX) {
					y += yInc;
					D -= c;
				}
			}
		// For all other angles, the roles of x and y are interchanged
		} else {
			c = 2 * HY;
			M = 2 * HX;
			for (;;) {
				putPixel(g, x, y);
				if (y == yQ)
					break;
				y += yInc;
				D += M;
				if (D > HY) {
					x += xInc;
					D -= c;
				}
			}
		}
	}

	void showGrid(Graphics g) {
		for (int x = dGrid; x <= maxX; x += dGrid)
			for (int y = dGrid; y <= maxY; y += dGrid)
				g.drawLine(x, y, x, y);
	}

	public void paint(Graphics g) {
		initgr();
		showGrid(g);
		
		for (int i = 0; i < np; i++) {
			if (i > 0)
				drawLine(g, iX(p[i - 1].x), iY(p[i - 1].y), iX(p[i].x), iY(p[i].y));
				
			if (i > 0)
				FasterBres(g,iX(p[i-1].x), iY(p[i-1].y),iX(p[i].x),iY(p[i].y));
			
		}
		System.out.printf("FasterBres: %d cycles; Bresenham: %d cycles\n",n,m);
	}
}
