import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class HiddenLineElimination extends JPanel
{	private JFrame frame;
	private JButton openButton, eyeUpButton, eyeDownButton,
		eyeLeftButton, eyeRightButton, incrDistButton, decrDistButton;
	private JButton testButton, triangButton;
	private JRadioButton testOnButton, triangOnButton, testTriangOffButton;
	private HiddenLineDrawPanel drawPanel;
	private JPanel buttonPanel;
	private JPanel menuPanel;
	private String sDir = "C:/Java/GraphicsDemo/";

	HiddenLineElimination(JFrame fr)
	{	frame = fr;

		setLayout(new BorderLayout());

		MenuCommands mListener = new MenuCommands();

		menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		openButton = new JButton("   Open   ");
		eyeUpButton = new JButton("Viewpoint Up");
		eyeDownButton = new JButton("Viewpoint Down");
		eyeLeftButton = new JButton("Viewpoint Left");
		eyeRightButton = new JButton("Viewpoint Right");
		incrDistButton = new JButton("Increase Distance ");
		decrDistButton = new JButton("Decrease Distance");
		openButton.addActionListener(mListener);
		eyeUpButton.addActionListener(mListener);
		eyeDownButton.addActionListener(mListener);
		eyeLeftButton.addActionListener(mListener);
		eyeRightButton.addActionListener(mListener);
		incrDistButton.addActionListener(mListener);
		decrDistButton.addActionListener(mListener);
		menuPanel.add(openButton);
		menuPanel.add(eyeUpButton);
		menuPanel.add(eyeDownButton);
		menuPanel.add(eyeLeftButton);
		menuPanel.add(eyeRightButton);
		menuPanel.add(incrDistButton);
		menuPanel.add(decrDistButton);
		add(menuPanel, "East");

		drawPanel = new HiddenLineDrawPanel();
		drawPanel.setBackground(Color.white);
		drawPanel.setSize(800,500);
		add(drawPanel, "Center");

		/*buttonPanel = new JPanel();
		testButton = new JButton("Test On ");
		triangButton = new JButton("Triangulate On ");
		testButton.addActionListener(mListener);
		triangButton.addActionListener(mListener);
		buttonPanel.add(testButton);
		buttonPanel.add(triangButton);
		add(buttonPanel, "South");*/


		testOnButton = new JRadioButton("Test On");
		triangOnButton = new JRadioButton("Triangulate On");
		testTriangOffButton = new JRadioButton("Test/Triangulate Off");

		testOnButton.addActionListener(mListener);
		triangOnButton.addActionListener(mListener);
		testTriangOffButton.addActionListener(mListener);
		testTriangOffButton.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(testOnButton);
		group.add(triangOnButton);
		group.add(testTriangOffButton);

		buttonPanel = new JPanel();
		buttonPanel.add(testOnButton);
		buttonPanel.add(triangOnButton);
		buttonPanel.add(testTriangOffButton);
		add(buttonPanel, "South");

		String fName = "C:/Java/GraphicsDemo/steps.dat";
		HiddenLineDemo1Obj3D obj = new HiddenLineDemo1Obj3D();
		if (obj.read(fName))
		{	drawPanel.setObj(obj);
			repaint();
		}

	}

	class MenuCommands implements ActionListener
	{	public void actionPerformed(ActionEvent ae)
		{	if (ae.getSource() instanceof JRadioButton)
			{	JRadioButton jrb = (JRadioButton)ae.getSource();
				//boolean testOn = drawPanel.getTestOn();
				//boolean triangOn = drawPanel.getTriangOn();
				if (jrb == testOnButton)
				{	drawPanel.setTestOn(true);
					drawPanel.setTriangOn(false);
					repaint();
				} else
				if (jrb == triangOnButton)
				{	drawPanel.setTriangOn(true);
					drawPanel.setTestOn(false);
					repaint();
				} else
				if (jrb == testTriangOffButton)
				{	drawPanel.setTriangOn(false);
					drawPanel.setTestOn(false);
					repaint();
				}
			} else

			if (ae.getSource() instanceof JButton)
			{	JButton jb = (JButton)ae.getSource();
				if (jb == openButton)
				{	FileDialog fDia = new FileDialog(frame, "Open", FileDialog.LOAD);
					fDia.setDirectory(sDir);
					fDia.setFile("*.dat");
					fDia.show();
					String sDir1 = fDia.getDirectory();
					String sFile = fDia.getFile();
					String fName = sDir1 + sFile;
					HiddenLineDemo1Obj3D obj = new HiddenLineDemo1Obj3D();
					if (obj.read(fName))
					{	sDir = sDir1;
						drawPanel.setObj(obj);
						repaint();
					}
				} else
				if (jb == eyeUpButton)		drawPanel.vp(0, -.1F, 1);	else
				if (jb == eyeDownButton)	drawPanel.vp(0, .1F, 1);	else
				if (jb == eyeLeftButton)	drawPanel.vp(-.1F, 0, 1);	else
				if (jb == eyeRightButton)	drawPanel.vp(.1F, 0, 1);	else
				if (jb == incrDistButton)	drawPanel.vp(0, 0, 2);		else
				if (jb == decrDistButton)	drawPanel.vp(0, 0, .5F);
			}
		}
	}
}