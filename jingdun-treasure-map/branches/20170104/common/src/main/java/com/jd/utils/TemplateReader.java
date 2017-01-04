package com.jd.utils;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class TemplateReader extends FilterReader {

    private ArrayList lines     = new ArrayList();
    StringBuffer      lineBuffer = new StringBuffer();
    StringBuffer      sBuffer    = new StringBuffer();

    public TemplateReader(Reader r){
        super(r);
    }

    @Override
    public int read() throws IOException {
        int c = in.read();
        handleChar(c);
        return c;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numchars = in.read(cbuf, off, len);
        for (int i = off; i < off + numchars; i++) {
            char c = cbuf[i];
            handleChar(c);
        }
        return numchars;
    }

    private void handleChar(int c) {
        if (c == '$') {
            sBuffer.setLength(0);
            sBuffer.append((char) c);
            lines.add(lineBuffer.toString());
            lineBuffer.setLength(0);
            lineBuffer.append((char) c);
        } else if (c == '{') {
            if (sBuffer.length() > 0 && sBuffer.toString().equals("$")) {
                sBuffer.append((char) c);
            } else {
                sBuffer.setLength(0);
                sBuffer.append((char) c);
            }
            lineBuffer.append((char) c);
        } else if (c == '}') {
            if (sBuffer.length() > 0 && sBuffer.toString().equals("${")) {
                lineBuffer.append((char) c);
                lines.add(lineBuffer.toString());
                lineBuffer.setLength(0);
                sBuffer.setLength(0);
            } else {
                lineBuffer.append((char) c);
            }
        } else {
            lineBuffer.append((char) c);
        }

    }

    @Override
    public void close() throws IOException {
        super.close();
        in.close();
    }

    public ArrayList getLines() {
        lines.add(lineBuffer.toString());
        return lines;
    }

    public void setLines(ArrayList lines) {
        this.lines = lines;
    }
}
