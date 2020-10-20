package hubin.train.jvm.week01;

import java.lang.reflect.Method;

public class XClassLoaderTest {

    public static void main(String[] args) {
        String xClassPath = "./Hello.xlass";
        try {
            Class xClass = new XClassLoader(xClassPath).findClass("Hello");
            Method method = xClass.getDeclaredMethod("hello");
            method.invoke(xClass.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
