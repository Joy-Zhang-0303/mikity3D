package org.mklab.mikity.xml.blender;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Polylist要素)
 * 
 * @author Iwamoto
 * @version $Revision: 1.2 $. 2011/10/12
 */
public class Polylist {

  @XmlElement
  private String vcount;

  @XmlElement
  private String p;

  private List<Integer> vcountValue = new ArrayList<Integer>();
  /**
   * 文字列の空白の位置を記したリスト
   */
  private List<Integer> space = new ArrayList<Integer>();
  /**
   * 使用する頂点の番号を記したリスト
   */
  private List<Integer> num = new ArrayList<Integer>();
  /**
   * 使用する頂点の組み合わせをまとめた配列リスト
   */
  private List<int[]> index = new ArrayList<int[]>();

  /**
   * コンストラクタ
   */
  public Polylist() {
    this.space = new ArrayList<Integer>();
    this.num = new ArrayList<Integer>();
    this.index = new ArrayList<int[]>();
    this.vcountValue = new ArrayList<Integer>();
  }

  /**
   * 使用する頂点番号一覧を記述した文字列から使用しない値や空白を除外し、 前から3～4つずつ頂点の組み合わせをまとめる配列リストに加えていく。
   */
  @SuppressWarnings("boxing")
  private void createPolylistIndex() {
    //要チェック
    getSpaceindex(this.p, this.space); //pからthis.spaceにスペースの位置を記録
    List<Integer> vcountSpaceIndex = new ArrayList<Integer>();
    getSpaceindex(this.vcount, vcountSpaceIndex);

    getOddIndexNumberFromP(this.num, this.space);
    getNumberFromVcount(this.vcountValue, vcountSpaceIndex);
    int i = 0;
    int j = 0;
    while (i < this.num.size()) {
      int n[] = new int[this.vcountValue.get(j)];
      for (int m = 0; m < n.length; m++) {
        n[m] = this.num.get(m + i);
      }
      i += n.length;
      j++;
      this.index.add(n);
    }
  }

  private void getNumberFromVcount(List<Integer> vValue, List<Integer> vCountSpaceIndex) {
    for (int i = 0; i < vCountSpaceIndex.size() ; i++) {
      if (i == 0) {
        vValue.add(new Integer(Integer.parseInt(this.vcount.substring(0, vCountSpaceIndex.get(0).intValue()))));
      } /*else if (i == vCountSpaceIndex.size()) {
        vValue.add(new Integer(Integer.parseInt(this.vcount.substring(vCountSpaceIndex.get(vCountSpaceIndex.size() - 1).intValue() + 1))));
        //(translateSpace.get(translateSpace.size() - 1) + 1)
      } */else {
        vValue.add(new Integer(Integer.parseInt(this.vcount.substring(vCountSpaceIndex.get(i - 1).intValue() + 1, vCountSpaceIndex.get(i).intValue()))));
      }
    }
  }

  private void getOddIndexNumberFromP(List<Integer> numList, List<Integer> spaceList) {
    for (int i = 0; i < spaceList.size() + 1; i += 2) {
      if (i == 0) {
        numList.add(new Integer(Integer.parseInt(this.p.substring(0, spaceList.get(0).intValue()))));
      } else if (i == spaceList.size()) {
        numList.add(new Integer(Integer.parseInt(this.p.substring(spaceList.get(spaceList.size() - 1).intValue() + 1))));
      } else {
        numList.add(new Integer(Integer.parseInt(this.p.substring(spaceList.get(i - 1).intValue() + 1, spaceList.get(i).intValue()))));
      }
    }
  }

  @SuppressWarnings("static-method")
  private void getSpaceindex(String po, List<Integer> space2) {
    for (int j = 0; j < po.length(); j++) {
      if (po.charAt(j) == ' ') {
        space2.add(new Integer(j));
      }
    }
  }

  /**
   * 使用する頂点番号一覧を記述した文字列を返す
   * 
   * @return　p　使用する頂点番号一覧を記述した文字列
   */
  public String loadP() {
    return this.p;
  }
  
  /**
   * 使用する頂点の組み合わせをまとめた配列リストを返す
   * 
   * @return　index　使用する頂点の組み合わせをまとめた配列リスト
   */
  public List<int[]> getPolylistIndex() {
    createPolylistIndex();
    return this.index;
  }
}
