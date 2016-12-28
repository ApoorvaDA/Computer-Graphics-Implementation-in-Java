import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class BezierCurve extends JPanel {

	JInternalFrame frame = new JInternalFrame(); //Internal frame that holds the Canvas
	JNumberTextField num = new JNumberTextField(); // To take granularity input
	JLabel headingSpeed = new JLabel(); // Text field for Slider heading
	JLabel headingCurvature = new JLabel(); // Text field for Granularity field heading
	JPanel panel1 = new JPanel(); //Panel to hold input field for n + submit button
	JPanel panel2 = new JPanel(); //Panel to hold Slider
	JButton Submit = new JButton("Submit");
	JButton Terminate = new JButton("Terminate"); // Button to exit program
	JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
	JRadioButtonMenuItem inputSelector = new JRadioButtonMenuItem();
	int input_points=4; // Default set to Cubic
	int granularity=100; // Default n set to 100
	int speed=0;
	
	//Setter definitions
	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setInput_points(int input_points) {
		this.input_points = input_points;
	}

	CvBezier2 bezier_canvas = new CvBezier2();
	
	public BezierCurve() {
		setLayout(new BorderLayout());
		
		//Setting default values
		bezier_canvas.setInputPoints(input_points);
		bezier_canvas.setSpeed(speed);
		bezier_canvas.setGranularity(granularity);
		
		// Setting the dimensions and input format
		num.setColumns(15);
		num.setFormat(JNumberTextField.DECIMAL);
		num.setMaxLength(7);
		num.setPrecision(4);
		num.setAllowNegative(true);

		// Setting slider parameters
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent slider) {
				setSpeed(((JSlider) slider.getSource()).getValue());
				bezier_canvas.setSpeed(speed);
			}});
		
		// Action listener for Submit button
		Submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(num.getNumber() == null){
					setGranularity(granularity);
				} else {
					setGranularity(num.getInt());
					bezier_canvas.setGranularity(granularity);
				}
			}});
		
		// Action listener for Submit button
		Terminate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}});
		
		//GroupLayout for panel to input granularity
		GroupLayout grouplayout = new GroupLayout(panel1);
		panel1.setLayout(grouplayout);
		panel1.setSize(10, 10);
		grouplayout.setAutoCreateGaps(true);
		grouplayout.setAutoCreateContainerGaps(true);
		headingCurvature.setText("Enter the Granularity required(n). Default is n=100");
		grouplayout.setHorizontalGroup(
				grouplayout.createSequentialGroup()
				
				.addGroup(grouplayout.createParallelGroup(GroupLayout.Alignment.LEADING,false)
						.addComponent(headingCurvature)
				    	.addComponent(num,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				    	.addComponent(Submit,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		grouplayout.setVerticalGroup(
				   grouplayout.createSequentialGroup()
				   .addComponent(headingCurvature)
				      .addGroup(grouplayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				      .addComponent(num)
				      .addComponent(Submit) )   
				);
		
		//GroupLayout for panel to input speed
		GroupLayout grouplayout2 = new GroupLayout(panel2);
		panel2.setLayout(grouplayout2);
		panel2.setSize(10, 10);
		grouplayout2.setAutoCreateGaps(true);
		grouplayout2.setAutoCreateContainerGaps(true);
		headingSpeed.setText("Select the speed");
		grouplayout2.setHorizontalGroup(
				grouplayout2.createSequentialGroup()
				.addGroup(grouplayout2.createParallelGroup(GroupLayout.Alignment.LEADING,false)
						.addComponent(headingSpeed)
				    	.addComponent(slider,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			
				);
		grouplayout2.setVerticalGroup(
				   grouplayout2.createSequentialGroup()
				   .addComponent(headingSpeed)
				      .addGroup(grouplayout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
				      .addComponent(slider))   
				);

		// Menu Bar to allow users Select the degree
		JMenuBar menuBar = new JMenuBar();
		JMenu selectDegree = new JMenu("Select Degree");
		ActionListener actionPrinter = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(arg0.toString().contains("Linear")){
					setInput_points(2);
					bezier_canvas.setInputPoints(input_points);
				} else if(arg0.toString().contains("Quadratic")){
					setInput_points(3);
					bezier_canvas.setInputPoints(input_points);
				} else if(arg0.toString().contains("Cubic")) {
					setInput_points(4);
					bezier_canvas.setInputPoints(input_points);
				} else if(arg0.toString().contains("Quartic")) {
					setInput_points(5);
					bezier_canvas.setInputPoints(input_points);
				}
			}
		};
		
		// Adding canvas to internal frame
		frame.setSize(500,300);
		frame.setVisible(true);
		frame.add(bezier_canvas);
		
		JRadioButtonMenuItem first = new JRadioButtonMenuItem("Linear(First degree)");
		first.setHorizontalTextPosition(JMenuItem.RIGHT);
		first.addActionListener(actionPrinter);
		
		JRadioButtonMenuItem second = new JRadioButtonMenuItem("Quadratic(Second degree)");
		second.setHorizontalTextPosition(JMenuItem.RIGHT);
		second.addActionListener(actionPrinter);
		
		JRadioButtonMenuItem third = new JRadioButtonMenuItem("Cubic(Third degree)");
		third.setHorizontalTextPosition(JMenuItem.RIGHT);
		third.addActionListener(actionPrinter);
		
		JRadioButtonMenuItem fourth = new JRadioButtonMenuItem("Quartic(Fourth degree)");
		fourth.setHorizontalTextPosition(JMenuItem.RIGHT);
		fourth.addActionListener(actionPrinter);
		// Button group for Selecting degrees
		ButtonGroup group = new ButtonGroup();
		group.add(first);
		group.add(second);
		group.add(third);
		group.add(fourth);

		selectDegree.add(first);
		selectDegree.add(second);
		selectDegree.add(third);
		selectDegree.add(fourth);

		menuBar.add(selectDegree);
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

		// Adding all the components to the Panel
		add(menuBar,BorderLayout.PAGE_START);
		add(frame,BorderLayout.CENTER);
		add(panel1,BorderLayout.EAST);
		add(panel2,BorderLayout.WEST);
		add(Terminate,BorderLayout.PAGE_END);
	}

}

@SuppressWarnings("serial")
class CvBezier2 extends Canvas {
	
	private int input_points;
	private int speed;
	private int granularity;
	
	public void setGranularity(int granularity) {
		this.granularity = granularity;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setInputPoints(int input_points) { 
		this.input_points = input_points; 
	}
	
	Point2D[] p = new Point2D[6];

	int np = 0, centerX, centerY;
	float rWidth = 10.0F, rHeight = 7.5F, eps = rWidth / 100F, pixelSize;

	CvBezier2() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {

				float x = fx(evt.getX()), y = fy(evt.getY());
				if (np == input_points)
					np = 0;
				p[np++] = new Point2D(x, y);
				repaint();
			}
		});
	}

	void initgr() {
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2;
		centerY = maxY / 2;
	}

	int iX(float x) {
		return Math.round(centerX + x / pixelSize);
	}

	int iY(float y) {
		return Math.round(centerY - y / pixelSize);
	}

	float fx(int x) {
		return (x - centerX) * pixelSize;
	}

	float fy(int y) {
		return (centerY - y) * pixelSize;
	}

	//Bezier Curve method for degree 1
	void bezier_linear(Graphics g, Point2D[] P, int speed, int n) {
		Graphics2D g2 = (Graphics2D) g;
		float dt = 1.0F / n;
		float cx1 = P[1].x - P[0].x, cy1 = P[1].y - P[0].y, cx0 = P[0].x, cy0 = P[0].y, x = P[0].x, y = P[0].y, x0, y0;

		g2.drawString("Selected Speed= "+speed+"ms", 5, 60);
		g2.drawString("Granularity(n)= "+n, 5, 80);
		for (int i = 0; i <= n; i++) {
			float t = i * dt;
			x0 = x;
			y0 = y;
			x = cx1 * t + cx0;
			y = cy1 * t + cy0;
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.RED);
			g2.drawLine(iX(x0), iY(y0), iX(x), iY(y));
			g2.setColor(Color.BLACK);
			g2.clearRect(20, 90, 40, 10);
			String formattedString = String.format("%.02f", t);
			g2.drawString("t: " + formattedString, 20, 100);
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//Bezier Curve method for degree 2
	void bezier_quadratic(Graphics g, Point2D[] P, int speed, int n) {
		Graphics2D g2 = (Graphics2D) g;
		float dt = 1.0F / n;
		float cx2 = (P[0].x - 2 * P[1].x + P[2].x), cy2 = (P[0].y - 2 * P[1].y + P[2].y),
				cx1 = (2 * P[1].x - 2 * P[0].x), cy1 = (2 * P[1].y - 2 * P[0].y), cx0 = P[0].x, cy0 = P[0].y,
				x = P[0].x, y = P[0].y, x0, y0;
		g2.drawString("Selected Speed= "+speed+"ms", 5, 60);
		g2.drawString("Granularity(n)= "+n, 5, 80);
		
		for (int i = 0; i <= n; i++) {
			float t = i * dt;
			x0 = x;
			y0 = y;

			x = (cx2 * t + cx1) * t + cx0;
			y = (cy2 * t + cy1) * t + cy0;

			float q0X = (float) (P[0].x * (1 - t) + t * P[1].x);
			float q0Y = (float) (P[0].y * (1 - t) + t * P[1].y);

			float q1X = (float) (P[1].x * (1 - t) + t * P[2].x);
			float q1Y = (float) (P[1].y * (1 - t) + t * P[2].y);

			g2.setColor(Color.GREEN);
			g2.drawLine(iX(q0X), iY(q0Y), iX(q1X), iY(q1Y));
			
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.RED);
			g2.drawLine(iX(x0), iY(y0), iX(x), iY(y));

			g2.setColor(Color.BLACK);
			g2.clearRect(10, 90, 30, 10);
			String formattedString = String.format("%.02f", t);
			g2.drawString("t= " + formattedString, 5, 100);
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	//Bezier Curve method for degree 3
	void bezier_cubic(Graphics g, Point2D[] P, int speed, int n) {
		Graphics2D g2 = (Graphics2D) g;
		float dt = 1.0F / n;
		float cx3 = -P[0].x + 3 * (P[1].x - P[2].x) + P[3].x, cy3 = -P[0].y + 3 * (P[1].y - P[2].y) + P[3].y,
				cx2 = 3 * (P[0].x - 2 * P[1].x + P[2].x), cy2 = 3 * (P[0].y - 2 * P[1].y + P[2].y),
				cx1 = 3 * (P[1].x - P[0].x), cy1 = 3 * (P[1].y - P[0].y), cx0 = P[0].x, cy0 = P[0].y, x = P[0].x,
				y = P[0].y, x0, y0;
		g2.drawString("Selected Speed= "+speed+"ms", 5, 60);
		g2.drawString("Granularity(n)= "+n, 5, 80);
		
		for (int i = 0; i <= n; i++) {
			float t = i * dt;
			x0 = x;
			y0 = y;
			x = ((cx3 * t + cx2) * t + cx1) * t + cx0;
			y = ((cy3 * t + cy2) * t + cy1) * t + cy0;
			
			float q0X = (float)((1 - t)*P[0].x + t*P[1].x); 
			float q1X =	(float)((1 - t)*P[1].x + t*P[2].x); 	
			float q2X = (float)((1 - t)*P[2].x + t*P[3].x);
			float q3X = (float)((1 - t)*q0X + t*q1X); 
			float q4X = (float)((1 - t)*q1X + t*q2X);
			
			float q0Y = (float)((1 - t)*P[0].y + t*P[1].y); 
			float q1Y =	(float)((1 - t)*P[1].y + t*P[2].y); 	
			float q2Y = (float)((1 - t)*P[2].y + t*P[3].y);
			float q3Y = (float)((1 - t)*q0Y + t*q1Y); 
			float q4Y = (float)((1 - t)*q1Y + t*q2Y);
			
			g2.setColor(Color.GREEN);
			g2.drawLine(iX(q0X),iY(q0Y),iX(q1X),iY(q1Y));
			g2.drawLine(iX(q1X),iY(q1Y),iX(q2X),iY(q2Y));
			g2.drawLine(iX(q3X),iY(q3Y),iX(q4X),iY(q4Y));
			
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.RED);
			g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
			
			g2.setColor(Color.BLACK);
			g2.clearRect(10, 90, 30, 10);
			String formattedString = String.format("%.02f", t);
			g2.drawString("t= " + formattedString, 5, 100);
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	//Bezier Curve method for degree 4
	void bezier_quartic(Graphics g, Point2D[] P	, int speed, int n) {
		Graphics2D g2 = (Graphics2D) g;
		float dt = 1.0F / n;
		float 	cx4 = P[0].x - 4 * P[1].x + 6 * P[2].x - 4 * P[3].x + P[4].x,
				cy4 = P[0].y - 4 * P[1].y + 6 * P[2].y - 4 * P[3].y + P[4].y,
				cx3 = 4 *( -P[0].x + 3 * (P[1].x - P[2].x) + P[3].x), cy3 =4*( -P[0].y + 3 * (P[1].y - P[2].y) + P[3].y),
				cx2 = 6 * (P[0].x - 2 * P[1].x + P[2].x), cy2 = 6 * (P[0].y - 2 * P[1].y + P[2].y),
				cx1 = 4 * (P[1].x - P[0].x), cy1 = 4 * (P[1].y - P[0].y), cx0 = P[0].x, cy0 = P[0].y, x = P[0].x,
				y = P[0].y, x0, y0;
		
		g2.drawString("Selected Speed= "+speed+"ms", 5, 60);
		g2.drawString("Granularity(n)= "+n, 5, 80);
		
		for (int i = 0; i <= n; i++) {
			float t = i * dt;
			x0 = x;
			y0 = y;
			x = (((cx4 * t + cx3) * t + cx2) * t + cx1)*t + cx0;
			y = (((cy4 * t + cy3) * t + cy2) * t + cy1)*t + cy0;
			
			float q0X = (float)((1 - t)*P[0].x + t*P[1].x); 
			float q1X =	(float)((1 - t)*P[1].x + t*P[2].x); 	
			float q2X = (float)((1 - t)*P[2].x + t*P[3].x);
			float q3X = (float)((1 - t)*P[3].x + t*P[4].x);
			float q4X = (float)((1 - t)*q0X + t*q1X); 
			float q5X = (float)((1 - t)*q1X + t*q2X);
			float q6X = (float)((1 - t)*q2X + t*q3X);
			float q7X = (float)((1 - t)*q4X + t*q5X);
			float q8X = (float)((1 - t)*q5X + t*q6X);
			
			float q0Y = (float)((1 - t)*P[0].y + t*P[1].y); 
			float q1Y =	(float)((1 - t)*P[1].y + t*P[2].y); 	
			float q2Y = (float)((1 - t)*P[2].y + t*P[3].y);
			float q3Y = (float)((1 - t)*P[3].y + t*P[4].y);
			float q4Y = (float)((1 - t)*q0Y + t*q1Y); 
			float q5Y = (float)((1 - t)*q1Y + t*q2Y);
			float q6Y = (float)((1 - t)*q2Y + t*q3Y);
			float q7Y = (float)((1 - t)*q4Y + t*q5Y);
			float q8Y = (float)((1 - t)*q5Y + t*q6Y);
			
			g2.setColor(Color.GREEN);
			g2.drawLine(iX(q0X),iY(q0Y),iX(q1X),iY(q1Y));
			g2.drawLine(iX(q1X),iY(q1Y),iX(q2X),iY(q2Y));
			g2.drawLine(iX(q2X),iY(q2Y),iX(q3X),iY(q3Y));
			g2.drawLine(iX(q4X),iY(q4Y),iX(q5X),iY(q5Y));
			g2.drawLine(iX(q5X),iY(q5Y),iX(q6X),iY(q6Y));
			g2.drawLine(iX(q7X),iY(q7Y),iX(q8X),iY(q8Y));
			
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.RED);
			g.drawLine(iX(x0), iY(y0), iX(x), iY(y));
			
			g2.setColor(Color.BLACK);
			g2.clearRect(10, 90, 30, 10);
			String formattedString = String.format("%.02f", t);
			g2.drawString("t= " + formattedString, 5, 100);
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	public void paint(Graphics g) {
		super.paint(g);
		initgr();
		int left = iX(-rWidth / 2), right = iX(rWidth / 2), bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
		g.drawRect(left, top, right - left, bottom - top);

		for (int i = 0; i < np; i++) {
			// Show tiny rectangle around point:
			g.drawRect(iX(p[i].x) - 2, iY(p[i].y) - 2, 4, 4);
			if (i > 0)
				// Draw line p[i-1]p[i]:
				g.drawLine(iX(p[i - 1].x), iY(p[i - 1].y), iX(p[i].x), iY(p[i].y));
		}
		
		//Calls the bezier_curve methods according to the selection in menu bar
		if (input_points == 2 && np == 2){
			bezier_linear(g, p, speed, granularity);
		}else if (np == 3 && input_points == 3){
			bezier_quadratic(g, p, speed, granularity);
		}else if (np == 4 && input_points == 4){
			bezier_cubic(g,p,speed, granularity);
		}else if (np == 5 && input_points == 5){
			bezier_quartic(g,p,speed, granularity);
		}	
	}

}
