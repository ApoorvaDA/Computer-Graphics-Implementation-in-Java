import java.io.*;

class Input
{

    private File f;
    private BufferedInputStream fis;
    private int buf;
    private boolean ok;

    Input(String fileName)
    {
        ok = true;
        try
        {
            File f = new File(fileName);
            fis = new BufferedInputStream(new FileInputStream(f));
            buf = fis.read();
        }
        catch(IOException ioe)
        {
            ok = false;
        }
    }

    void close()
    {
        if(fis != null)
        {
            try
            {
                fis.close();
            }
            catch(IOException ioe)
            {
                ok = false;
            }
        }
    }

    int readInt()
    {
        boolean neg = false;
        for(; Character.isWhitespace((char)buf); nextChar()) { }
        if(buf == 45)
        {
            neg = true;
            nextChar();
        }
        if(!Character.isDigit((char)buf))
        {
            ok = false;
            return 0;
        }
        int x;
        for(x = buf - 48; nextChar() && Character.isDigit((char)buf); x = 10 * x + (buf - 48)) { }
        return neg ? -x : x;
    }

    float readFloat()
    {
        int nDec = -1;
        boolean neg = false;
        for(; Character.isWhitespace((char)buf); nextChar()) { }
        if(buf == 45)
        {
            neg = true;
            nextChar();
        }
        if(buf == 46)
        {
            nDec = 0;
            nextChar();
        }
        if(!Character.isDigit((char)buf))
        {
            ok = false;
            return 0.0F;
        }
        float x = buf - 48;
        while(nextChar() && (Character.isDigit((char)buf) || nDec == -1 && buf == 46)) 
        {
            if(buf == 46)
            {
                nDec = 0;
            } else
            {
                x = 10F * x + (float)(buf - 48);
                if(nDec >= 0)
                {
                    nDec++;
                }
            }
        }
        for(; nDec > 0; nDec--)
        {
            x = (float)((double)x * 0.10000000000000001D);
        }

        if(buf == 101 || buf == 69)
        {
            nextChar();
            int exp = readInt();
            if(!fails())
            {
                for(; exp < 0; exp++)
                {
                    x = (float)((double)x * 0.10000000000000001D);
                }

                for(; exp > 0; exp--)
                {
                    x *= 10F;
                }

            }
        }
        return neg ? -x : x;
    }

    char readChar()
    {
        char ch = (char)buf;
        nextChar();
        return ch;
    }

    boolean eof()
    {
        return !ok && buf < 0;
    }

    boolean fails()
    {
        return !ok;
    }

    void clear()
    {
        ok = true;
    }

    private boolean nextChar()
    {
        if(buf < 0)
        {
            ok = false;
        } else
        {
            try
            {
                buf = fis.read();
            }
            catch(IOException ioe)
            {
                ok = false;
            }
        }
        return ok;
    }
}
