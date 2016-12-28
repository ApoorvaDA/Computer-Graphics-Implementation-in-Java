import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JPanel;

class HiddenLineDrawPanel extends JPanel
{

    private HiddenLineDemo1Obj3D obj;
    private int maxX;
    private int maxY;
    private int centerX;
    private int centerY;
    private int nTria;
    private int nVertices;
    private Point2D imgCenter;
    private Tria tr[];
    private int refPol[];
    private int connect[][];
    private int nConnect[];
    private int chunkSize;
    private double hLimit;
    private Vector polyList;
    private float maxScreenRange;
    private int curTriang;
    private int curTr;
    private int curLni;
    private int curLnj;
    private int curTest;
    private boolean clicked;
    private boolean testOn;
    private boolean triangOn;
    private boolean drawTr;
    private boolean drawLines;
    private Font textFont;
    private Font textBoldFont;
    private int PX;
    private int PY;
    private int QX;
    private int QY;
    private int IX;
    private int IY;
    private int JX;
    private int JY;

    HiddenLineDemo1Obj3D getObj()
    {
        return obj;
    }

    void setObj(HiddenLineDemo1Obj3D inObj)
    {
        obj = inObj;
    }

    boolean getTestOn()
    {
        return testOn;
    }

    void setTestOn(boolean inTestOn)
    {
        testOn = inTestOn;
    }

    boolean getTriangOn()
    {
        return triangOn;
    }

    void setTriangOn(boolean inTriangOn)
    {
        triangOn = inTriangOn;
    }

    void vp(float dTheta, float dPhi, float fRho)
    {
        HiddenLineDemo1Obj3D obj = getObj();
        if(obj == null || !obj.vp(this, dTheta, dPhi, fRho))
        {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    HiddenLineDrawPanel()
    {
    	super();
        chunkSize = 4;
        clicked = false;
        testOn = false;
        triangOn = false;
        drawTr = false;
        drawLines = false;
        textFont = new Font("Arial", 0, 11);
        textBoldFont = new Font("Arial", 1, 11);
        curTriang = -1;
        curTr = -1;
        curLni = -1;
        curLnj = -1;
        curTest = 0;
        addMouseListener(new MouseAdapter() {

            final HiddenLineDrawPanel this$0;

            public void mousePressed(MouseEvent evt)
            {
                if(triangOn)
                {
                    curTriang++;
                    repaint();
                }
                if(testOn)
                {
                    curTr++;
                    repaint();
                }
            }

            
            {
                this$0 = HiddenLineDrawPanel.this;
                
            }
        });
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        String title[] = {
            "Hidden Line Elimination", "Demonstration"
        };
        Font titleFont = new Font("Arial", 1, 18);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title[0], 570, 30);
        g.drawString(title[1], 615, 50);
        g.drawLine(545, 70, 800, 70);
        if(obj == null)
        {
            return;
        }
        Vector polyList = obj.getPolyList();
        if(polyList == null)
        {
            return;
        }
        int nFaces = polyList.size();
        if(nFaces == 0)
        {
            return;
        }
        Dimension dim = getSize();
        maxX = dim.width - 1;
        maxY = dim.height - 1;
        centerX = maxX / 2;
        centerY = maxY / 2;
        maxScreenRange = obj.eyeAndScreen(dim);
        imgCenter = obj.getImgCenter();
        obj.planeCoeff();
        hLimit = -9.9999999999999995E-007D * (double)obj.getRho();
        buildLineSet();
        nTria = 0;
        for(int j = 0; j < nFaces; j++)
        {
            HiddenLineDemo1Polygon3D pol = (HiddenLineDemo1Polygon3D)polyList.elementAt(j);
            if(pol.getNrs().length > 2 && pol.getH() <= hLimit)
            {
                pol.triangulate(obj);
                nTria += pol.getT().length;
            }
        }

        tr = new Tria[nTria];
        refPol = new int[nTria];
        int iTria = 0;
        Point3D e[] = obj.getE();
        Point2D vScr[] = obj.getVScr();
        int colorCt = 0;
        if(triangOn && curTriang == nTria)
        {
            curTriang = 0;
        }
        for(int j = 0; j < nFaces; j++)
        {
            HiddenLineDemo1Polygon3D pol = (HiddenLineDemo1Polygon3D)polyList.elementAt(j);
            Tria t[] = pol.getT();
            if(pol.getNrs().length > 2 && pol.getH() <= hLimit)
            {
                for(int i = 0; i < t.length; i++)
                {
                    Tria tri = t[i];
                    int iA = tri.iA;
                    int iB = tri.iB;
                    int iC = tri.iC;
                    Point2D AScr = vScr[iA];
                    Point2D BScr = vScr[iB];
                    Point2D CScr = vScr[iC];
                    if(triangOn && curTriang >= iTria)
                    {
                        g.setPaintMode();
                        if(colorCt == 0)
                        {
                            g.setColor(Color.green);
                        }
                        if(colorCt == 1)
                        {
                            g.setColor(Color.red);
                        }
                        if(colorCt == 2)
                        {
                            g.setColor(Color.blue);
                        }
                        if(colorCt == 3)
                        {
                            g.setColor(Color.orange);
                        }
                        if(colorCt == 4)
                        {
                            g.setColor(Color.cyan);
                        }
                        if(colorCt == 5)
                        {
                            g.setColor(Color.pink);
                        }
                        if(colorCt == 6)
                        {
                            g.setColor(Color.magenta);
                        }
                        int xPts[] = {
                            iX(AScr.x), iX(BScr.x), iX(CScr.x)
                        };
                        int yPts[] = {
                            iY(AScr.y), iY(BScr.y), iY(CScr.y)
                        };
                        int nPts = 3;
                        g.fillPolygon(xPts, yPts, nPts);
                        if(++colorCt > 6)
                        {
                            colorCt = 0;
                        }
                    }
                    tr[iTria] = tri;
                    refPol[iTria++] = j;
                }

            }
        }

        if(testOn)
        {
            if(curTr > -1)
            {
                if(curLnj == -1 && curLni == -1)
                {
                    curLnj = 0;
                    curLni = 0;
                }
                if(curTr == nTria)
                {
                    curTr = 0;
                    curLnj++;
                }
                if(curLnj == nConnect[curLni])
                {
                    curLnj = 0;
                    curLni++;
                }
                if(curLni == nVertices)
                {
                    curLni = 0;
                }
                while(nConnect[curLni] == 0) 
                {
                    curLni++;
                    if(curLni == nVertices)
                    {
                        curLni = 0;
                    }
                }
                drawLines = true;
                Tria t = tr[curTr];
                int iA = t.iA;
                int iB = t.iB;
                int iC = t.iC;
                Point2D AScr = vScr[iA];
                Point2D BScr = vScr[iB];
                Point2D CScr = vScr[iC];
                int xPts[] = {
                    iX(AScr.x), iX(BScr.x), iX(CScr.x)
                };
                int yPts[] = {
                    iY(AScr.y), iY(BScr.y), iY(CScr.y)
                };
                int nPts = 3;
                g.setColor(Color.yellow);
                g.fillPolygon(xPts, yPts, nPts);
                g.setColor(Color.black);
                drawLine(g, AScr.x, AScr.y, BScr.x, BScr.y);
                drawLine(g, BScr.x, BScr.y, CScr.x, CScr.y);
                drawLine(g, CScr.x, CScr.y, AScr.x, AScr.y);
            } else
            {
                drawLines = false;
            }
        }
        for(int i = 0; i < nVertices; i++)
        {
            for(int j = 0; j < nConnect[i]; j++)
            {
                int jj = connect[i][j];
                if(testOn)
                {
                    g.setColor(Color.black);
                    if(curLni == i && curLnj == j)
                    {
                        drawTr = true;
                    } else
                    {
                        drawTr = false;
                        if(drawLines)
                        {
                            g.setColor(Color.blue);
                            dashedLine(g, vScr[i].x, vScr[i].y, vScr[jj].x, vScr[jj].y, 4);
                            dashedLine(g, vScr[i].x - 1.0F, vScr[i].y, vScr[jj].x - 1.0F, vScr[jj].y, 4);
                            dashedLine(g, vScr[i].x - 1.0F, vScr[i].y - 1.0F, vScr[jj].x - 1.0F, vScr[jj].y - 1.0F, 4);
                        } else
                        {
                            g.setColor(Color.GRAY);
                            dashedLine(g, vScr[i].x, vScr[i].y, vScr[jj].x, vScr[jj].y, 4);
                        }
                    }
                }
                if(testOn && curLni == i && curLnj == j)
                {
                    PX = iX(vScr[i].x);
                    PY = iY(vScr[i].y);
                    QX = iX(vScr[jj].x);
                    QY = iY(vScr[jj].y);
                }
                lineSegment(g, e[i], e[jj], vScr[i], vScr[jj], i, jj, 0);
                if(testOn && curLni == i && curLnj == j)
                {
                    g.setFont(textBoldFont);
                    g.setColor(Color.red);
                    if(curTest == 1)
                    {
                        g.drawString("Test 1: 2D", 515, 90);
                        g.drawString("Both P and Q are to the left of A, B and C OR", 535, 105);
                        g.drawString("both P and Q are to the right of A, B and C OR", 535, 120);
                        g.drawString("both P and Q are above A, B and C OR", 535, 135);
                        g.drawString("both P and Q are below A, B and C.", 535, 150);
                        g.drawString("Line is visible.", 535, 165);
                    }
                    if(curTest == 2)
                    {
                        g.drawString("Test 2: 3D", 515, 90);
                        g.drawString("PQ is identical with one of the edges", 535, 105);
                        g.drawString("of triangle ABC.", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 3)
                    {
                        g.drawString("Test 3: 3D", 515, 90);
                        g.drawString("z-coordinates of P and Q are less", 535, 105);
                        g.drawString("than those of A, B and C.", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 4)
                    {
                        g.drawString("Test 4: 2D", 515, 90);
                        g.drawString("P and Q lie on a different side of an edge", 535, 105);
                        g.drawString("of the triangle from the third vertex", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 5)
                    {
                        g.drawString("Test 5: 2D", 515, 90);
                        g.drawString("P and Q lie on a different side of a vertex", 535, 105);
                        g.drawString("of the triangle from the third edge.", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 6)
                    {
                        g.drawString("Test 6: 3D", 515, 90);
                        g.drawString("Test 3 failed, but PQ still lies in front", 535, 105);
                        g.drawString("of the plane of triangle ABC.", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 7)
                    {
                        g.drawString("Test 7: 2D", 515, 90);
                        g.drawString("PQ is completely obscured by triangle ABC.", 535, 105);
                        g.drawString("Line is NOT visible.", 535, 120);
                        curTr = nTria - 1;
                    }
                    if(curTest == 8)
                    {
                        g.drawString("Test 8: 3D", 515, 90);
                        g.drawString("Either P or Q is nearer than ABC plane", 535, 105);
                        g.drawString("and also appears inside ABC.", 535, 120);
                        g.drawString("Line is visible.", 535, 135);
                    }
                    if(curTest == 9)
                    {
                        g.drawString("Test 9: 3D", 515, 90);
                        g.drawString("PQ intersects ABC.", 535, 105);
                        g.drawString("If intersection is in front of ABC, PQ is visible.", 535, 120);
                        g.drawString("Else ABC partially obscures PQ.", 535, 135);
                        curTr = nTria - 1;
                    }
                }
            }

        }

    }

    void dashedLine(Graphics g, float xa, float ya, float xb, float yb, int dashLength)
    {
        int xA = iX(xa);
        int yA = iY(ya);
        int xB = iX(xb);
        int yB = iY(yb);
        float total_length = (float)Math.sqrt((yb - ya) * (yb - ya) + (xb - xa) * (xb - xa));
        float trunc_length = total_length - (float)(dashLength * 2);
        if(trunc_length < 0.0F)
        {
            g.drawLine(xA, yA, xB, yB);
            return;
        }
        float y_dash_offset = (yb - ya) * ((float)dashLength / total_length);
        float x_dash_offset = (xb - xa) * ((float)dashLength / total_length);
        g.drawLine(iX(xa), iY(ya), iX(xa + x_dash_offset), iY(ya + y_dash_offset));
        g.drawLine(iX(xb - x_dash_offset), iY(yb - y_dash_offset), iX(xb), iY(yb));
        int total_pairs = (int)Math.floor(trunc_length / (float)(dashLength * 2)) - 1;
        float leftover_space = trunc_length - (float)(total_pairs * dashLength);
        float empty_space_length = leftover_space / (float)(total_pairs + 1);
        float x_space_offset = (xb - xa) * (empty_space_length / total_length);
        float y_space_offset = (yb - ya) * (empty_space_length / total_length);
        float current_x = xa + x_dash_offset;
        float current_y = ya + y_dash_offset;
        current_x += x_space_offset;
        current_y += y_space_offset;
        for(int i = 0; i < total_pairs; i++)
        {
            g.drawLine(iX(current_x), iY(current_y), iX(current_x + x_dash_offset), iY(current_y + y_dash_offset));
            current_x += x_space_offset + x_dash_offset;
            current_y += y_space_offset + y_dash_offset;
        }

    }

    private void buildLineSet()
    {
        polyList = obj.getPolyList();
        nVertices = obj.getVScr().length;
        connect = new int[nVertices][];
        nConnect = new int[nVertices];
        for(int i = 0; i < nVertices; i++)
        {
            nConnect[i] = 0;
        }

        int nFaces = polyList.size();
        for(int j = 0; j < nFaces; j++)
        {
            HiddenLineDemo1Polygon3D pol = (HiddenLineDemo1Polygon3D)polyList.elementAt(j);
            int nrs[] = pol.getNrs();
            int n = nrs.length;
            int ii = Math.abs(nrs[n - 1]);
            for(int k = 0; k < n; k++)
            {
                int jj = nrs[k];
                if(jj < 0)
                {
                    jj = -jj;
                } else
                {
                    int i1 = Math.min(ii, jj);
                    int j1 = Math.max(ii, jj);
                    int nCon = nConnect[i1];
                    int l;
                    for(l = 0; l < nCon; l++)
                    {
                        if(connect[i1][l] == j1)
                        {
                            break;
                        }
                    }

                    if(l == nCon)
                    {
                        if(nCon % chunkSize == 0)
                        {
                            int temp[] = new int[nCon + chunkSize];
                            for(l = 0; l < nCon; l++)
                            {
                                temp[l] = connect[i1][l];
                            }

                            connect[i1] = temp;
                        }
                        connect[i1][nConnect[i1]++] = j1;
                    }
                }
                ii = jj;
            }

        }

    }

    int iX(float x)
    {
        return Math.round(((float)centerX + x) - imgCenter.x);
    }

    int iY(float y)
    {
        return Math.round(((float)centerY - y) + imgCenter.y);
    }

    private String toString(float t)
    {
        int i = Math.round(5000F + (t * 9000F) / maxScreenRange);
        String s = "";
        int n = 1000;
        for(int j = 3; j >= 0; j--)
        {
            s = (new StringBuilder(String.valueOf(s))).append(i / n).toString();
            i %= n;
            n /= 10;
        }

        return s;
    }

    private String hpx(float x)
    {
        return toString(x - imgCenter.x);
    }

    private String hpy(float y)
    {
        return toString(y - imgCenter.y);
    }

    private void drawLine(Graphics g, float x1, float y1, float x2, float y2)
    {
        if(x1 != x2 || y1 != y2)
        {
            g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
        }
    }

    private void lineSegment(Graphics g, Point3D Pe, Point3D Qe, Point2D PScr, Point2D QScr, int iP, int iQ, 
            int iStart)
    {
        double u1 = QScr.x - PScr.x;
        double u2 = QScr.y - PScr.y;
        double minPQx = Math.min(PScr.x, QScr.x);
        double maxPQx = Math.max(PScr.x, QScr.x);
        double minPQy = Math.min(PScr.y, QScr.y);
        double maxPQy = Math.max(PScr.y, QScr.y);
        double zP = Pe.z;
        double zQ = Qe.z;
        double minPQz = Math.min(zP, zQ);
        Point3D e[] = obj.getE();
        Point2D vScr[] = obj.getVScr();
        boolean rememberTest = false;
        int i = iStart;
        for(i = iStart; i < nTria; i++)
        {
            Tria t = tr[i];
            int iA = t.iA;
            int iB = t.iB;
            int iC = t.iC;
            Point2D AScr = vScr[iA];
            Point2D BScr = vScr[iB];
            Point2D CScr = vScr[iC];
            if(testOn && drawTr && curTr == i)
            {
                g.setColor(Color.red);
                g.drawLine(PX, PY, QX, QY);
                g.drawLine(PX - 1, PY, QX - 1, QY);
                g.drawLine(PX - 1, PY - 1, QX - 1, QY - 1);
                g.setColor(Color.black);
                g.setFont(textBoldFont);
                g.fillRect(PX - 2, PY - 2, 5, 5);
                if(PX < QX)
                {
                    g.drawString("P", PX - 15, PY + 10);
                } else
                {
                    g.drawString("P", PX + 10, PY + 10);
                }
                g.fillRect(QX - 2, QY - 2, 5, 5);
                if(QX > PX)
                {
                    g.drawString("Q", QX + 10, QY + 10);
                } else
                {
                    g.drawString("Q", QX - 15, QY + 10);
                }
                drawLines = false;
                rememberTest = true;
            } else
            {
                rememberTest = false;
            }
            if(maxPQx <= (double)AScr.x && maxPQx <= (double)BScr.x && maxPQx <= (double)CScr.x || minPQx >= (double)AScr.x && minPQx >= (double)BScr.x && minPQx >= (double)CScr.x || maxPQy <= (double)AScr.y && maxPQy <= (double)BScr.y && maxPQy <= (double)CScr.y || minPQy >= (double)AScr.y && minPQy >= (double)BScr.y && minPQy >= (double)CScr.y)
            {
                if(rememberTest)
                {
                    curTest = 1;
                }
                continue;
            }
            if((iP == iA || iP == iB || iP == iC) && (iQ == iA || iQ == iB || iQ == iC))
            {
                if(rememberTest)
                {
                    curTest = 2;
                }
                continue;
            }
            Point3D Ae = e[iA];
            Point3D Be = e[iB];
            Point3D Ce = e[iC];
            double zA = Ae.z;
            double zB = Be.z;
            double zC = Ce.z;
            if(minPQz >= zA && minPQz >= zB && minPQz >= zC)
            {
                if(rememberTest)
                {
                    curTest = 3;
                }
                continue;
            }
            double eps = 0.10000000000000001D;
            if((double)Tools2D.area2(AScr, BScr, PScr) < eps && (double)Tools2D.area2(AScr, BScr, QScr) < eps || (double)Tools2D.area2(BScr, CScr, PScr) < eps && (double)Tools2D.area2(BScr, CScr, QScr) < eps || (double)Tools2D.area2(CScr, AScr, PScr) < eps && (double)Tools2D.area2(CScr, AScr, QScr) < eps)
            {
                if(rememberTest)
                {
                    curTest = 4;
                }
                continue;
            }
            double PQA = Tools2D.area2(PScr, QScr, AScr);
            double PQB = Tools2D.area2(PScr, QScr, BScr);
            double PQC = Tools2D.area2(PScr, QScr, CScr);
            if(PQA < eps && PQB < eps && PQC < eps || PQA > -eps && PQB > -eps && PQC > -eps)
            {
                if(rememberTest)
                {
                    curTest = 5;
                }
                continue;
            }
            int iPol = refPol[i];
            HiddenLineDemo1Polygon3D pol = (HiddenLineDemo1Polygon3D)polyList.elementAt(iPol);
            double a = pol.getA();
            double b = pol.getB();
            double c = pol.getC();
            double h = pol.getH();
            double eps1 = 1.0000000000000001E-005D * Math.abs(h);
            double hP = a * (double)Pe.x + b * (double)Pe.y + c * (double)Pe.z;
            double hQ = a * (double)Qe.x + b * (double)Qe.y + c * (double)Qe.z;
            if(hP > h - eps1 && hQ > h - eps1)
            {
                if(rememberTest)
                {
                    curTest = 6;
                }
                continue;
            }
            boolean PInside = Tools2D.insideTriangle(AScr, BScr, CScr, PScr);
            boolean QInside = Tools2D.insideTriangle(AScr, BScr, CScr, QScr);
            if(PInside && QInside)
            {
                if(rememberTest)
                {
                    curTest = 7;
                }
                return;
            }
            double h1 = h + eps1;
            boolean PNear = hP > h1;
            boolean QNear = hQ > h1;
            if(PNear && PInside || QNear && QInside)
            {
                if(rememberTest)
                {
                    curTest = 8;
                }
                continue;
            }
            double lambdaMin = 1.0D;
            double lambdaMax = 0.0D;
            if(rememberTest)
            {
                curTest = 9;
            }
            for(int ii = 0; ii < 3; ii++)
            {
                double v1 = BScr.x - AScr.x;
                double v2 = BScr.y - AScr.y;
                double w1 = AScr.x - PScr.x;
                double w2 = AScr.y - PScr.y;
                double denom = u2 * v1 - u1 * v2;
                if(denom != 0.0D)
                {
                    double mu = (u1 * w2 - u2 * w1) / denom;
                    if(mu > -0.0001D && mu < 1.0001D)
                    {
                        double lambda = (v1 * w2 - v2 * w1) / denom;
                        if(lambda > -0.0001D && lambda < 1.0001D)
                        {
                            if(PInside != QInside && lambda > 0.0001D && lambda < 0.99990000000000001D)
                            {
                                lambdaMin = lambdaMax = lambda;
                                break;
                            }
                            if(lambda < lambdaMin)
                            {
                                lambdaMin = lambda;
                            }
                            if(lambda > lambdaMax)
                            {
                                lambdaMax = lambda;
                            }
                        }
                    }
                }
                Point2D temp = AScr;
                AScr = BScr;
                BScr = CScr;
                CScr = temp;
            }

            float d = obj.getD();
            if(!PInside && lambdaMin > 0.001D)
            {
                double IScrx = (double)PScr.x + lambdaMin * u1;
                double IScry = (double)PScr.y + lambdaMin * u2;
                double zI = 1.0D / (lambdaMin / zQ + (1.0D - lambdaMin) / zP);
                double xI = (-zI * IScrx) / (double)d;
                double yI = (-zI * IScry) / (double)d;
                if(a * xI + b * yI + c * zI > h1)
                {
                    continue;
                }
                Point2D IScr = new Point2D((float)IScrx, (float)IScry);
                if((double)Tools2D.distance2(IScr, PScr) >= 1.0D)
                {
                    IX = iX(IScr.x);
                    IY = iY(IScr.y);
                    if(testOn && drawTr && curTr == i)
                    {
                        g.setColor(Color.black);
                        g.setFont(textBoldFont);
                        g.fillRect(IX - 2, IY - 2, 5, 5);
                        g.drawString("I", IX - 25, IY + 20);
                    }
                    lineSegment(g, Pe, new Point3D(xI, yI, zI), PScr, IScr, iP, -1, i + 1);
                }
            }
            if(!QInside && lambdaMax < 0.999D)
            {
                double JScrx = (double)PScr.x + lambdaMax * u1;
                double JScry = (double)PScr.y + lambdaMax * u2;
                double zJ = 1.0D / (lambdaMax / zQ + (1.0D - lambdaMax) / zP);
                double xJ = (-zJ * JScrx) / (double)d;
                double yJ = (-zJ * JScry) / (double)d;
                if(a * xJ + b * yJ + c * zJ > h1)
                {
                    continue;
                }
                Point2D JScr = new Point2D((float)JScrx, (float)JScry);
                if((double)Tools2D.distance2(JScr, QScr) >= 1.0D)
                {
                    lineSegment(g, Qe, new Point3D(xJ, yJ, zJ), QScr, JScr, iQ, -1, i + 1);
                }
            }
            return;
        }

        g.setPaintMode();
        if(!testOn)
        {
            g.setColor(Color.black);
            drawLine(g, PScr.x, PScr.y, QScr.x, QScr.y);
        } else
        if(testOn && drawLines)
        {
            g.setColor(Color.blue);
            drawLine(g, PScr.x, PScr.y, QScr.x, QScr.y);
            drawLine(g, PScr.x - 1.0F, PScr.y - 1.0F, QScr.x - 1.0F, QScr.y - 1.0F);
            drawLine(g, PScr.x - 1.0F, PScr.y, QScr.x - 1.0F, QScr.y);
        }
    }
}
