// Tools2D.java: Class to be used in other program files.

// Copied from Section 2.13 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.

// Uses: Point2D (Section 1.5) and Triangle (discussed above).

class Tools2D
{  static float area2(Point2D a, Point2D b, Point2D c)
   {  return (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
   }

   static boolean insideTriangle(Point2D a, Point2D b, Point2D c, 
      Point2D p) // ABC is assumed to be counter-clockwise
   {  return
        Tools2D.area2(a, b, p) >= 0 && 
        Tools2D.area2(b, c, p) >= 0 && 
        Tools2D.area2(c, a, p) >= 0;
   }

   static void triangulate(Point2D[] p, Triangle[] tr)
   {  // p contains all n polygon vertices in CCW order.
      // The resulting triangles will be stored in array tr.
      // This array tr must have length n - 2.
      int n = p.length, j = n - 1, iA=0, iB, iC;
      int[] next = new int[n];
      for (int i=0; i<n; i++) 
      {  next[j] = i;
         j = i;
      }
      for (int k=0; k<n-2; k++)
      {  // Find a suitable triangle, consisting of two edges
         // and an internal diagonal:
         Point2D a, b, c;
         boolean triaFound = false;
         int count = 0;
         while (!triaFound && ++count < n)
         {  iB = next[iA]; iC = next[iB];
            a = p[iA]; b = p[iB]; c = p[iC];
            if (Tools2D.area2(a, b, c) >= 0)
            {  // Edges AB and BC; diagonal AC.
               // Test to see if no other polygon vertex
               // lies within triangle ABC:
               j = next[iC];
               while (j != iA && !insideTriangle(a, b, c, p[j]))
                  j = next[j];
               if (j == iA) 
               {  // Triangle ABC contains no other vertex:
                  tr[k] = new Triangle(a, b, c);
                  next[iA] = iC; 
                  triaFound = true;
               }  
            }  
            iA = next[iA];
         }
         if (count == n)
         {  System.out.println("Not a simple polygon" +
              " or vertex sequence not counter-clockwise.");
            System.exit(1);
         }
      }
   }

   static float distance2(Point2D p, Point2D q)
   {  float dx = p.x - q.x, 
            dy = p.y - q.y;
      return dx * dx + dy * dy;
   }
}

class Triangle
{  Point2D a, b, c;
   Triangle(Point2D a, Point2D b, Point2D c)
   {  this.a = a; this.b = b; this.c = c;
   }
}

