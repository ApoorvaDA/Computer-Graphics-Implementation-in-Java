import java.io.PrintStream;

class Tools2D
{

    Tools2D()
    {
    }

    static float area2(Point2D A, Point2D B, Point2D C)
    {
        return (A.x - C.x) * (B.y - C.y) - (A.y - C.y) * (B.x - C.x);
    }

    static boolean insideTriangle(Point2D A, Point2D B, Point2D C, Point2D P)
    {
        return area2(A, B, P) >= 0.0F && area2(B, C, P) >= 0.0F && area2(C, A, P) >= 0.0F;
    }

    static void triangulate(Point2D P[], Triangle tr[])
    {
        int n = P.length;
        int j = n - 1;
        int iA = 0;
        int next[] = new int[n];
        for(int i = 0; i < n; i++)
        {
            next[j] = i;
            j = i;
        }

        for(int k = 0; k < n - 2; k++)
        {
            boolean triaFound = false;
            int count;
            for(count = 0; !triaFound && ++count < n;)
            {
                int iB = next[iA];
                int iC = next[iB];
                Point2D A = P[iA];
                Point2D B = P[iB];
                Point2D C = P[iC];
                if(area2(A, B, C) >= 0.0F)
                {
                    for(j = next[iC]; j != iA && !insideTriangle(A, B, C, P[j]); j = next[j]) { }
                    if(j == iA)
                    {
                        tr[k] = new Triangle(A, B, C);
                        next[iA] = iC;
                        triaFound = true;
                    }
                }
                iA = next[iA];
            }

            if(count == n)
            {
                System.out.println("Not a simple polygon or vertex sequence not counter-clockwise.");
                System.exit(1);
            }
        }

    }

    static float distance2(Point2D P, Point2D Q)
    {
        float dx = P.x - Q.x;
        float dy = P.y - Q.y;
        return dx * dx + dy * dy;
    }
}