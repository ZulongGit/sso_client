package org.beetl.json.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


public class NoLockStringWriter extends Writer {

    private StringBuilder buf;


    public NoLockStringWriter() {
        buf = ContextLocal.get().getWriterBuffer();
     
    }

    public NoLockStringWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        buf = new StringBuilder(initialSize);
    }

   
    public void write(int c) {
        buf.append((char) c);
    }

   
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        buf.append(cbuf, off, len);
    }

    /**
     * Write a string.
     */
    public void write(String str) {
        buf.append(str);
    }

    public void write(String str, int off, int len)  {
        buf.append(str.substring(off, off + len));
    }

    public NoLockStringWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
    }

    public NoLockStringWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    public NoLockStringWriter append(char c) {
        write(c);
        return this;
    }

 
    public String toString() {
        return buf.toString();
    }

   

    public void flush() {
    }

  
    public void close() throws IOException {
    }

}
