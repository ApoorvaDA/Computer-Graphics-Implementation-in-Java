import java.awt.Canvas;

@SuppressWarnings("serial")
abstract class Canvas3D extends Canvas
{  abstract Obj3D getObj(); 
   abstract void setObj(Obj3D obj);
}