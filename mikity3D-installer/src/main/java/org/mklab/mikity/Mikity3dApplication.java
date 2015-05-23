package org.mklab.mikity;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Mikity3Dのメインクラスです。
 * <p>
 * jarとしてデプロイしなければ利用できません。
 * 
 * @author koga
 */
public class Mikity3dApplication {

  private static String LIBRARY_DIRECTORY_NAME = "lib"; //$NON-NLS-1$

  /**
   * Mikity3Dのエントリーポイントです。
   * 
   * @param args プログラム引数
   */
  public static void main(final String[] args) {
    AccessController.doPrivileged(new PrivilegedAction<Void>() {

      @Override
      public Void run() {
        try {
          handleMain(args);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        return null;
      }
    });
  }

  static void handleMain(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
    final String mikity3dUserHome = findMikity3dUserHome();
    System.setProperty("MIKITY3D_USER_HOME", mikity3dUserHome); //$NON-NLS-1$
    final File mikity3dHome = findMikity3dHome();

    try (final URLClassLoader loader = new URLClassLoader(enumerateClasspaths(mikity3dHome))) {
      final Class<?> launcher = loader.loadClass("org.mklab.mikity.Mikity3dModeling"); //$NON-NLS-1$
      
      final Method method = launcher.getDeclaredMethod("main", String[].class); //$NON-NLS-1$
      method.invoke(null, (Object)args);
    }
  }

  /**
   * Mikity3Dのユーザホームを探します。
   * 
   * @return Mikity3Dユーザホーム。見つからなかった場合はnull
   */
  private static String findMikity3dUserHome() {
    String userHome = System.getProperty("MIKITY3D_USER_HOME"); //$NON-NLS-1$
    if (userHome != null) {
      return userHome;
    }
    
    userHome = System.getenv("MIKITY3D_USER_HOME"); //$NON-NLS-1$
    if (userHome != null) {
      return userHome;
    }

    userHome = findMikity3dHome().getAbsolutePath();
    return userHome;
  }
  
  /**
   * Mikity3dホームを探索します。
   * 
   * @return Mikity3Dホームディレクトリ
   */
  private static File findMikity3dHome() {
    final String pathOfThisClass = Mikity3dApplication.class.getName().replaceAll("\\.", "/") + ".class";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
    final URL jarUrlWithPathOfThisClass = Mikity3dApplication.class.getClassLoader().getResource(pathOfThisClass);
    if (jarUrlWithPathOfThisClass.getProtocol().equals("jar") == false) { //$NON-NLS-1$
      throw new IllegalStateException("This application can launch only from jar. Please execute \"mvn package\"."); //$NON-NLS-1$
    }

    final URL jarUrl;
    try {
      jarUrl = new URL(jarUrlWithPathOfThisClass.getFile().substring(0, jarUrlWithPathOfThisClass.getFile().lastIndexOf('!')));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    final File jarFile;
    try {
      jarFile = new File(URLDecoder.decode(jarUrl.getFile(), "UTF-8")); //$NON-NLS-1$
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    if (jarFile.exists() == false) {
      throw new IllegalStateException(MessageFormat.format("Detected Mikity3D HOME \"{0}\" not exists.", jarFile)); //$NON-NLS-1$
    }

    return jarFile.getParentFile();
  }

  private static URL[] enumerateClasspaths(File mikity3dHome) {
    final File libDir = new File(mikity3dHome, LIBRARY_DIRECTORY_NAME);
    if (libDir.exists() == false) {
      throw new IllegalStateException(MessageFormat.format("\"{0}\" not exists.", libDir.getAbsolutePath())); //$NON-NLS-1$
    }
    if (libDir.isFile()) {
      throw new IllegalStateException(MessageFormat.format("\"{0}\" is not a directory", libDir.getAbsolutePath())); //$NON-NLS-1$
    }

    final List<URL> libraries = new ArrayList<>();
    for (File file : libDir.listFiles()) {
      if (file.isDirectory()) {
        continue;
      }
      if (file.getName().endsWith(".jar")) { //$NON-NLS-1$
        try {
          libraries.add(file.toURI().toURL());
        } catch (MalformedURLException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return libraries.toArray(new URL[libraries.size()]);
  }

}
