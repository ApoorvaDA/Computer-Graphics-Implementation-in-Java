Problem Statement:

--------------------------------

1.3 Draw a set of concentric pairs of squares, each consisting of a
square with horizontal and vertical edges and one rotated through 45°.
Except for the outermost square, the vertices of each square are the
midpoints of the edges of its immediately surrounding square, as Figure
1.12 shows. It is required that all lines are exactly straight, and that
vertices of smaller squares lie exactly on the edges of larger ones.

![](media/image1.png){width="6.531944444444444in"
height="3.7555555555555555in"}

**Figure 1.12. Concentric squares **

1.4 Write a program that draws a pattern of hexagons, as shown in Figure
1.13. The vertices of a (regular) hexagon lie on its so-called
circumscribed circle. The user must be able to specify the radius of
this circle by clicking a point near the upper-left corner of the
drawing rectangle. Then the distance between that point and that corner
is to be used as the radius of the circle just mentioned. There must be
as many hexagons of the specified size as possible and the margins on
the left and the right must be equal. The same applies to the upper and
lower margins, as Figure 1.13 shows.

![](media/image2.png){width="6.531944444444444in"
height="3.7555555555555555in"}

**Figure 1.13. Hexagons **

1.5 Write a class *Lines* containing a static method *dashedLine* to
draw dashed lines, in such a way that we can write

Lines.dashedLine(g, xA, yA, xB, yB, dashLength);

where *g* is a variable of type *Graphics*, *xA*, *yA*, *xB*, *yB* are
the device coordinates of the endpoints A and B, and *dashLength* is the
desired length (in device coordinates) of a single dash. There should be
a dash, not a gap, at each endpoint of a dashed line. Figure 1.14 shows
eight dashed lines drawn in this way, with *dashLength* = 20.

![](media/image3.png){width="6.531944444444444in"
height="3.446527777777778in"}

**Figure 1.14. Dashed lines **
