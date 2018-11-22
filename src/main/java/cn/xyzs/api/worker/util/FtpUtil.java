package cn.xyzs.api.worker.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.MalformedURLException;

public class FtpUtil {

  public static FTPClient ftpClient = null;
  private static String hostname;
  private static Integer port = 21;
  private static String username;
  private static String password;
  static {
    hostname = PropertiesUtil.getSourcingValueBykey("host");
    if (PropertiesUtil.getSourcingValueBykey("port")!=null){
      port = Integer.valueOf(PropertiesUtil.getSourcingValueBykey("port"));
    }
    username = PropertiesUtil.getSourcingValueBykey("username");
    DEStool stool = new DEStool("xyzs123456");
    try {
		password = stool.decrypt(PropertiesUtil.getSourcingValueBykey("password"));
	} catch (Exception e) {
		e.printStackTrace();
	}
  }

  /**
   * 初始化ftp服务器
   */
  public static void initFtpClient() {
    ftpClient = new FTPClient();
    ftpClient.setControlEncoding("utf-8");
    try {
      System.out.println("connecting...ftp服务器:" + hostname + ":" + port);
      ftpClient.connect(hostname, port); // 连接ftp服务器
      ftpClient.login(username, password); // 登录ftp服务器
      int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
      if (!FTPReply.isPositiveCompletion(replyCode)) {
        System.out.println("connect failed...ftp服务器:" + hostname + ":" + port);
      }
      System.out.println("connect successfu...ftp服务器:" + hostname + ":" + port);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 上传文件
   *
   * @param pathname ftp服务保存地址
   * @param fileName 上传到ftp的文件名
   * @param originfilename 待上传文件的名称（绝对地址） *
   * @return
   */
  /*
   * public boolean uploadFile(String pathname, String fileName, String originfilename) { boolean
   * flag = false; InputStream inputStream = null; try { System.out.println("开始上传文件"); inputStream =
   * new FileInputStream(new File(originfilename)); initFtpClient();
   * ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE); CreateDirecroty(pathname);
   * ftpClient.makeDirectory(pathname); ftpClient.changeWorkingDirectory(pathname);
   * ftpClient.storeFile(fileName, inputStream); inputStream.close(); ftpClient.logout(); flag =
   * true; System.out.println("上传文件成功"); } catch (Exception e) { System.out.println("上传文件失败");
   * e.printStackTrace(); } finally { if (ftpClient.isConnected()) { try { ftpClient.disconnect(); }
   * catch (IOException e) { e.printStackTrace(); } } if (null != inputStream) { try {
   * inputStream.close(); } catch (IOException e) { e.printStackTrace(); } } } return true; }
   */

  /**
   * 上传文件
   *
   * @param pathname ftp服务保存地址
   * @param fileName 上传到ftp的文件名
   * @param inputStream 输入文件流
   * @return
   */
  public static boolean uploadFile(String pathname, String fileName, InputStream inputStream) {
    boolean flag = false;
    try {
      System.out.println("开始上传文件");
      initFtpClient();
      ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
      CreateDirecroty(pathname);
      // ftpClient.makeDirectory(pathname);
      // ftpClient.changeWorkingDirectory(pathname);
      ftpClient.storeFile(fileName, inputStream);
      inputStream.close();
      ftpClient.logout();
      flag = true;
      System.out.println("上传文件成功");
    } catch (Exception e) {
      System.out.println("上传文件失败");
      e.printStackTrace();
    } finally {
      if (ftpClient.isConnected()) {
        try {
          ftpClient.disconnect();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != inputStream) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return true;
  }

  // 改变目录路径
  public static boolean changeWorkingDirectory(String directory) {
    boolean flag = true;
    try {
      flag = ftpClient.changeWorkingDirectory(directory);
      if (flag) {
        System.out.println("进入文件夹" + directory + " 成功！");
      } else {
        System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return flag;
  }

  // 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
  public static boolean CreateDirecroty(String remote) throws IOException {
    boolean success = true;
    String directory = remote + "/";
    // 如果远程目录不存在，则递归创建远程服务器目录
    if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
      int start = 0;
      int end = 0;
      if (directory.startsWith("/")) {
        start = 1;
      } else {
        start = 0;
      }
      end = directory.indexOf("/", start);
      String path = "";
      String paths = "";
      while (true) {
        String subDirectory =
                new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
        path = path + "/" + subDirectory;
        if (!existFile(path)) {
          if (makeDirectory(subDirectory)) {
            changeWorkingDirectory(subDirectory);
          } else {
            System.out.println("创建目录[" + subDirectory + "]失败");
            changeWorkingDirectory(subDirectory);
          }
        } else {
          changeWorkingDirectory(subDirectory);
        }

        paths = paths + "/" + subDirectory;
        start = end + 1;
        end = directory.indexOf("/", start);
        // 检查所有目录是否创建完毕
        if (end <= start) {
          break;
        }
      }
    }
    return success;
  }

  // 判断ftp服务器文件是否存在
  public static boolean existFile(String path) throws IOException {
    boolean flag = false;
    FTPFile[] ftpFileArr = ftpClient.listFiles(path);
    if (null!= ftpFileArr&&ftpFileArr.length > 0) {
      flag = true;
    }
    return flag;
  }

  // 创建目录
  public static boolean makeDirectory(String dir) {
    boolean flag = true;
    try {
      flag = ftpClient.makeDirectory(dir);
      if (flag) {
        System.out.println("创建文件夹" + dir + " 成功！");

      } else {
        System.out.println("创建文件夹" + dir + " 失败！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  /**
   * * 下载文件 *
   *
   * @param pathname FTP服务器文件目录 *
   * @param filename 文件名称 *
   * @param localpath 下载后的文件路径 *
   * @return
   */
  public static boolean downloadFile(String pathname, String filename, String localpath, String hostname,
                              Integer port, String username, String password) {
    boolean flag = false;
    OutputStream os = null;
    try {
      System.out.println("开始下载文件");
      initFtpClient();
      // 切换FTP目录
      ftpClient.changeWorkingDirectory(pathname);
      FTPFile[] ftpFiles = ftpClient.listFiles();
      for (FTPFile file : ftpFiles) {
        if (filename.equalsIgnoreCase(file.getName())) {
          File localFile = new File(localpath + "/" + file.getName());
          os = new FileOutputStream(localFile);
          ftpClient.retrieveFile(file.getName(), os);
          os.close();
        }
      }
      ftpClient.logout();
      flag = true;
      System.out.println("下载文件成功");
    } catch (Exception e) {
      System.out.println("下载文件失败");
      e.printStackTrace();
    } finally {
      if (ftpClient.isConnected()) {
        try {
          ftpClient.disconnect();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != os) {
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return flag;
  }

  /**
   * * 删除文件 *
   *
   * @param pathname FTP服务器保存目录 *
   * @param filename 要删除的文件名称 *
   * @return
   */
  public boolean deleteFile(String pathname, String filename, String hostname, Integer port,
                            String username, String password) {
    boolean flag = false;
    try {
      System.out.println("开始删除文件");
      initFtpClient();
      // 切换FTP目录
      ftpClient.changeWorkingDirectory(pathname);
      ftpClient.dele(filename);
      ftpClient.logout();
      flag = true;
      System.out.println("删除文件成功");
    } catch (Exception e) {
      System.out.println("删除文件失败");
      e.printStackTrace();
    } finally {
      if (ftpClient.isConnected()) {
        try {
          ftpClient.disconnect();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return flag;
  }

  public File getFileFromBytes(byte[] b, String outputFile) {
    BufferedOutputStream stream = null;
    File file = null;
    try {
      file = new File(outputFile);
      FileOutputStream fstream = new FileOutputStream(file);
      stream = new BufferedOutputStream(fstream);
      stream.write(b);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return file;
  }

  public static byte[] getBytesFromFile(File f) {
    if (f == null) {
      return null;
    }
    try {
      FileInputStream stream = new FileInputStream(f);
      ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
      byte[] b = new byte[1000];
      int n;
      while ((n = stream.read(b)) != -1)
        out.write(b, 0, n);
      stream.close();
      out.close();
      return out.toByteArray();
    } catch (IOException e) {
    }
    return null;
  }
}
