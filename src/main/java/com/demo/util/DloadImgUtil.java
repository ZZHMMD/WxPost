package com.demo.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DloadImgUtil {
  /**
   * �������������ж��ļ���չ��
   *
   * @param contentType ��������
   * @return
   */
  public static String getFileexpandedName(String contentType) {
    String fileEndWitsh = "";
    if ("image/jpeg".equals(contentType))
      fileEndWitsh = ".jpg";
    else if ("audio/mpeg".equals(contentType))
      fileEndWitsh = ".mp3";
    else if ("audio/amr".equals(contentType))
      fileEndWitsh = ".amr";
    else if ("video/mp4".equals(contentType))
      fileEndWitsh = ".mp4";
    else if ("video/mpeg4".equals(contentType))
      fileEndWitsh = ".mp4";
    return fileEndWitsh;
  }
  /**
   * ��ȡý���ļ�
   * @param accessToken �ӿڷ���ƾ֤
   * @param mediaId ý���ļ�id
   * @param savePath �ļ��ڱ��ط������ϵĴ洢·��
   * */
  public static String downloadMedia(String accessToken, String mediaId, String savePath) {

    String filePath = null;
    String fileName = null;
    // ƴ�������ַ
    String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
    try {
      URL url = new URL(requestUrl);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setDoInput(true);
      conn.setRequestMethod("GET");

      if (!savePath.endsWith("/")) {
        savePath += "/";
      }
      // �����������ͻ�ȡ��չ��
      String fileExt = DloadImgUtil .getFileexpandedName(conn.getHeaderField("Content-Type"));
      // ��mediaId��Ϊ�ļ���
      filePath = savePath + mediaId + fileExt;
      fileName = mediaId +fileExt;
      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
      FileOutputStream fos = new FileOutputStream(new File(filePath));
      byte[] buf = new byte[8096];
      int size = 0;
      while ((size = bis.read(buf)) != -1)
        fos.write(buf, 0, size);
      fos.close();
      bis.close();

      conn.disconnect();
      String info = String.format("����ý���ļ��ɹ���filePath=" + filePath);
      System.out.println(info);
    } catch (Exception e) {
      filePath = null;
      String error = String.format("����ý���ļ�ʧ�ܣ�%s", e);
      System.out.println(error);
    }
    return fileName;
  }
}