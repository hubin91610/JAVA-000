package hubin.train.jvm.week01;

import java.io.*;

public class XClassLoader extends ClassLoader {

    public String xClassPath;

    public XClassLoader(String xClassPath) {
        this.xClassPath = xClassPath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] xClassBates = getClassByte(xClassPath);
        if (xClassBates == null) {
            throw  new ClassNotFoundException();
        }
        for (int i = 0; i < xClassBates.length; i++) {
            xClassBates[i] = (byte)(255 - xClassBates[i]);
        }
        return super.defineClass(name,xClassBates,0 ,xClassBates.length);
    }

    private byte[] getClassByte (String path) {
        File file = new File(path);
        BufferedInputStream ins = null;
        ByteArrayOutputStream outs = null;
        try {
            if(!file.exists()) {
                return  null;
            }
            ins = new BufferedInputStream(new FileInputStream(file));
            outs = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = ins.read(buf)) != -1) {
                    outs.write(buf,0,len);
            }
            return outs.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outs != null) {
                try {
                    outs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
