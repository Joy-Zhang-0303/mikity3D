/*
 *  $Id: ConfigData.java,v 1.2 2006/11/30 02:31:26 morimune Exp $
 *  Copyright (c) 2004 Koga Laboratory. All rights reserved.
 * 
 * 
 */
package org.mklab.mikity.internal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * 設定データを表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.2 $. 2004/11/18
 * 
 */
public class ConfigData {
  /** エージェントファイル */
  private String[] agentfile = new String[10];
  /**ウインドウの横幅 */
  private int xsize;
  /** ウインドウの縦幅 */
  private int ysize;
  /** X軸方向のずれ */
  private double xoffset;
  /** 画面中心と座標中心のオフセット　ｘ座標のずらす値 */
  private double yoffset;
  /** 画面中心と座標中心のオフセット　ｙ座標のずらす値 */
  private double zoffset;
  /** 画面中心と座標中心のオフセット　ｚ座標のずらす値 */
  private double xcenter;
  /** 各エージェントの座標系の原点の位置のｘ座標のずれ */
  private double ycenter;
  /** 各エージェントの座標系の原点の位置のｙ座標のずれ */
  private double zcenter;
  /** 各エージェントの座標系の原点の位置のｚ座標のずれ */
  private String bgcolor;
  /** 背景の色 */
  private double xeye;
  /** 視点の位置　ｘ座標 */
  private double yeye;
  /** 視点の位置　ｙ座標 */
  private double zeye;
  /** 視点の位置　ｚ座標 */
  private double xroll;
  /** objectのｘ軸周りの回転 */
  private double yroll;
  /** objectのｙ軸周りの回転 */
  private double zroll;
  /** objectのｚ軸周りの回転 */
  private String[] data = new String[10];
  
  private int parent;
  
  /** 親リンクの番号 */
  private String[] link = new String[10];

  /**
   * コンストラクター
   * 
   * @param filename ファイルネーム
   * @throws IOException ファイルを読み込めない場合
   */
  public ConfigData(String filename) throws IOException {
    final BufferedReader br = new BufferedReader(new FileReader(filename));

    String line = br.readLine();
    
    while (line != null) {
      if (!line.startsWith("#")) { //$NON-NLS-1$
        final String[] values = line.split("\\s"); //$NON-NLS-1$

        if (line.startsWith("xsize")) { //$NON-NLS-1$
          this.xsize = Integer.parseInt(values[1]);
        } else if (line.startsWith("ysize")) { //$NON-NLS-1$
          this.ysize = Integer.parseInt(values[1]);
        } else if (line.startsWith("xoffset")) { //$NON-NLS-1$
          this.xoffset = Double.parseDouble(values[1]);
        } else if (line.startsWith("yoffset")) { //$NON-NLS-1$
          this.yoffset = Double.parseDouble(values[1]);
        } else if (line.startsWith("zoffset")) { //$NON-NLS-1$
          this.zoffset = Double.parseDouble(values[1]);
        } else if (line.startsWith("xcenter")) { //$NON-NLS-1$
          this.xcenter = Double.parseDouble(values[1]);
        } else if (line.startsWith("ycenter")) { //$NON-NLS-1$
          this.ycenter = Double.parseDouble(values[1]);
        } else if (line.startsWith("zcenter")) { //$NON-NLS-1$
          this.zcenter = Double.parseDouble(values[1]);
        } else if (line.startsWith("bgcolor")) { //$NON-NLS-1$
          this.bgcolor = values[1];
        } else if (line.startsWith("xeye")) { //$NON-NLS-1$
          this.xeye = Double.parseDouble(values[1]);
        } else if (line.startsWith("yeye")) { //$NON-NLS-1$
          this.yeye = Double.parseDouble(values[1]);
        } else if (line.startsWith("zeye")) { //$NON-NLS-1$
          this.zeye = Double.parseDouble(values[1]);
        } else if (line.startsWith("xroll")) { //$NON-NLS-1$
          this.xroll = Double.parseDouble(values[1]);
        } else if (line.startsWith("yroll")) { //$NON-NLS-1$
          this.yroll = Double.parseDouble(values[1]);
        } else if (line.startsWith("zroll")) { //$NON-NLS-1$
          this.zroll = Double.parseDouble(values[1]);
        } else if (line.startsWith("data")) { //$NON-NLS-1$
          // TODO 配列に読込めるようにする
          this.data[0] = values[1];
        } else if (line.startsWith("parent")) { //$NON-NLS-1$
          this.parent = Integer.parseInt(values[1]);
        } else if (line.startsWith("link")) { //$NON-NLS-1$
          // TODO 配列に読込めるようにする
          this.link[0] = values[1];
        }
      }
      line = br.readLine();

    }
    
    br.close();
  }

  /**
   * agentfile を返します。
   * @return agentfile
   */
  public String[] getAgentfile() {
    return this.agentfile;
  }

  /**
   * agentfile を設定します。
   * @param agentfile agentfile
   */
  public void setAgentfile(String[] agentfile) {
    this.agentfile = agentfile;
  }

  /**
   * bgcolor を返します。
   * @return bgcolor
   */
  public String getBgcolor() {
    return this.bgcolor;
  }

  /**
   * bgcolor を設定します。
   * @param bgcolor bgcolor
   */
  public void setBgcolor(String bgcolor) {
    this.bgcolor = bgcolor;
  }

  /**
   * data を返します。
   * @return data
   */
  public String[] getData() {
    return this.data;
  }

  /**
   * data を設定します。
   * @param data data
   */
  public void setData(String[] data) {
    this.data = data;
  }

  /**
   * link を返します。
   * @return link
   */
  public String[] getLink() {
    return this.link;
  }

  /**
   * link を設定します。
   * @param link link
   */
  public void setLink(String[] link) {
    this.link = link;
  }

  /**
   * parent を返します。
   * @return parent
   */
  public int getParent() {
    return this.parent;
  }

  /**
   * parent を設定します。
   * @param parent parent 
   */
  public void setParent(int parent) {
    this.parent = parent;
  }

  /**
   * xcenter を返します。
   * @return xcenter
   */
  public double getXcenter() {
    return this.xcenter;
  }

  /**
   * xcenter を設定します。
   * @param xcenter xcenter
   */
  public void setXcenter(double xcenter) {
    this.xcenter = xcenter;
  }

  /**
   * xeye を返します。
   * @return xeye
   */
  public double getXeye() {
    return this.xeye;
  }

  /**
   * xeye を設定します。
   * @param xeye xeye
   */
  public void setXeye(double xeye) {
    this.xeye = xeye;
  }

  /**
   * xoffset を返します。
   * @return xoffset
   */
  public double getXoffset() {
    return this.xoffset;
  }

  /**
   * xoffset を設定します。
   * @param xoffset xoffset
   */
  public void setXoffset(double xoffset) {
    this.xoffset = xoffset;
  }

  /**
   * xroll を返します。
   * @return xroll
   */
  public double getXroll() {
    return this.xroll;
  }

  /**
   * xroll を設定します。
   * @param xroll xroll
   */
  public void setXroll(double xroll) {
    this.xroll = xroll;
  }

  /**
   * xsize を返します。
   * @return xsize
   */
  public int getXsize() {
    return this.xsize;
  }

  /**
   * xsize を設定します。
   * @param xsize xsize
   */
  public void setXsize(int xsize) {
    this.xsize = xsize;
  }

  /**
   * ycenter を返します。
   * @return ycenter
   */
  public double getYcenter() {
    return this.ycenter;
  }

  /**
   * ycenter を設定します。
   * @param ycenter ycenter
   */
  public void setYcenter(double ycenter) {
    this.ycenter = ycenter;
  }

  /**
   * yeye を返します。
   * @return yeye
   */
  public double getYeye() {
    return this.yeye;
  }

  /**
   * yeye を設定します。
   * @param yeye yeye
   */
  public void setYeye(double yeye) {
    this.yeye = yeye;
  }

  /**
   * yoffset を返します。
   * @return yoffset
   */
  public double getYoffset() {
    return this.yoffset;
  }

  /**
   * yoffset を設定します。
   * @param yoffset yoffset
   */
  public void setYoffset(double yoffset) {
    this.yoffset = yoffset;
  }

  /**
   * yroll を返します。
   * @return yroll
   */
  public double getYroll() {
    return this.yroll;
  }

  /**
   * yroll を設定します。
   * @param yroll yroll
   */
  public void setYroll(double yroll) {
    this.yroll = yroll;
  }

  /**
   * ysize を返します。
   * @return ysize
   */
  public int getYsize() {
    return this.ysize;
  }

  /**
   * ysize を設定します。
   * @param ysize ysize
   */
  public void setYsize(int ysize) {
    this.ysize = ysize;
  }

  /**
   * zcenter を返します。
   * @return zcenter
   */
  public double getZcenter() {
    return this.zcenter;
  }

  /**
   * zcenter を設定します。
   * @param zcenter zcenter
   */
  public void setZcenter(double zcenter) {
    this.zcenter = zcenter;
  }

  /**
   * zeye を返します。
   * @return zeye
   */
  public double getZeye() {
    return this.zeye;
  }

  /**
   * zeye を設定します。
   * @param zeye zeye
   */
  public void setZeye(double zeye) {
    this.zeye = zeye;
  }

  /**
   * zoffset を返します。
   * @return zoffset
   */
  public double getZoffset() {
    return this.zoffset;
  }

  /**
   * zoffset を設定します。
   * @param zoffset zoffset
   */
  public void setZoffset(double zoffset) {
    this.zoffset = zoffset;
  }

  /**
   * zroll を返します。
   * @return zroll
   */
  public double getZroll() {
    return this.zroll;
  }

  /**
   * zroll を設定します。
   * @param zroll zroll
   */
  public void setZroll(double zroll) {
    this.zroll = zroll;
  }
}