import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class DemoDisplayFrame extends JFrame
{

	SplashWindow splash = new SplashWindow("CGDemoSplash.jpg", this, 7000);

	final static String BRESENHAM = "Bresenham Line-Drawing";
	final static String COHENSUTHERLAND = "Cohen-Sutherland Line Clipping";
	final static String POLYGONCLIP = "Sutherland-Hodgman Polygon Clipping";
	final static String HIDDENLINE = "Hidden Line Elimination";
    final static String VIEWTRANSFORM = "Viewpoint Transformation";
    final static String BEZIERCURVE = "Bezier Curve";
    final static String DOUBLESTEP = "Double Step";

    DemoDisplayFrame()
    {
    	setTitle("CGDemo - Computer Graphics Algorithm Demonstrations");

    	addWindowListener(new WindowAdapter()
    	{
    		public void windowClosing(WindowEvent e)
    		{
    			System.exit(0);
    		}
    	});

		BresenhamLineDrawingDemo bresenham = new BresenhamLineDrawingDemo();
		CohenSutherlandLineClipping cohensutherland = new CohenSutherlandLineClipping();
		PolygonClip polygonclip = new PolygonClip();
		HiddenLineElimination hiddenline = new HiddenLineElimination(this);
		ViewTransformDemo viewtransform = new ViewTransformDemo();
		DoubleStepLineDrawingDemo doubleStep = new DoubleStepLineDrawingDemo();
		BezierCurve beziercurve = new BezierCurve();
		

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(BRESENHAM, bresenham);
		tabbedPane.addTab(COHENSUTHERLAND, cohensutherland);
		tabbedPane.addTab(POLYGONCLIP, polygonclip);
		tabbedPane.addTab(HIDDENLINE, hiddenline);
		tabbedPane.addTab(VIEWTRANSFORM, viewtransform);
		tabbedPane.addTab(DOUBLESTEP, doubleStep);
		tabbedPane.addTab(BEZIERCURVE, beziercurve);
		

		Container contentPane = getContentPane();
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
}