/**
 * Copyright (C) 2015 MKLab.org (Koga Laboratory)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.mikity.model.xml.simplexml.blender;

import java.util.ArrayList;
import java.util.List;

import org.mklab.mikity.model.xml.simplexml.model.GroupModel;
import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.TranslationModel;
import org.mklab.mikity.util.Matrix4;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;


/**
 * Blenderから出力したCOLLADAデータを読み込むためのクラス(Node要素)です。
 * 
 * @author SHOGO
 * @version $Revision: 1.6 $. 2007/11/30
 */
public class Node {

  @Attribute
  private String name;
  @Attribute
  private String type;
  @Attribute
  private String id;
  @Element
  private String matrix;
  @Element
  private InstanceGeometry instanceGeometry;
  @ElementList
  private List<Node> nodes;
  @Element
  private String translate;
  @ElementList
  private List<String> rotate;

  private List<Matrix4> matrixNodes;

  private String geometryURL;

  private GroupModel group;

  /** 列ベクトルリスト */
  private List<String> vectors;
  /** 変換行列 */
  private Matrix4 matrix4f;

  /** translateの番号記録用 */
  private float rotateNum[][] = { {(float)0.0, (float)0.0, (float)0.0, (float)0.0}, {(float)0.0, (float)0.0, (float)0.0, (float)0.0}, {(float)0.0, (float)0.0, (float)0.0, (float)0.0}};
  /** 平行移動の行列 */
  private double translation[][] = { {1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};
  /** x軸周りの回転行列 */
  private double rotationX[][] = { {1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};
  /** y回軸周りの転行列 */
  private double rotationY[][] = { {1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};
  /** z回軸周りの転行列 */
  private double rotationZ[][] = { {1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};

  /** 変換行列記録用 */
  private double matrixXY[][] = { {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}};
  private double matrixXYZ[][] = { {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}};
  private double matrixAll[][] = { {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}};

  /**
   * 新しく生成された<code>Node</code>オブジェクトを初期化します。
   */
  public Node() {
    this.instanceGeometry = new InstanceGeometry();
    this.nodes = new ArrayList<>();
    this.vectors = new ArrayList<>();
    this.matrix4f = new Matrix4();
    this.matrixNodes = new ArrayList<>();
    this.group = new GroupModel();
  }

  /**
   * ノードの名前を返します。
   * 
   * @return　ノードの名前
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return geometry URL
   */
  public String getGeometryURL() {
    return this.geometryURL;
  }

  /**
   * 変換行列を返します。
   * 
   * @return 変換行列
   */
  public Matrix4 getTransformMatrix() {
    return this.matrix4f;
  }

  /**
   * 変換行列の要素をまとめた文字列から余分なものを省いて，各要素の値を抽出して変換行列を生成します。
   */
  @SuppressWarnings("boxing")
  public void createTransformMatrix() {
    if (this.translate == null) {
      if (this.name.indexOf("-N") != -1) { //$NON-NLS-1$
        this.name = this.name.substring(0, this.name.indexOf("-N")); //$NON-NLS-1$
      }
      if (this.name.indexOf("-J") != -1) { //$NON-NLS-1$
        this.name = this.name.substring(0, this.name.indexOf("-J")); //$NON-NLS-1$
      }
      
      List<Integer> tab = new ArrayList<>();
      for (int i = 0; i < this.matrix.length(); i++) {
        if (this.matrix.indexOf("\n\t\t\t\t", i) == i) { //$NON-NLS-1$
          tab.add(i);
        }
      }
      
      for (int i = 0; i < tab.size() - 1; i++) {
        if (i == tab.size() - 2) {
          this.vectors.add(this.matrix.substring(tab.get(i) + 6, tab.get(tab.size() - 1) - 1));
        } else {
          this.vectors.add(this.matrix.substring(tab.get(i) + 6, tab.get(i + 1) - 1));
        }
      }
      
      for (int i = 0; i < this.vectors.size(); i++) {
        final List<Integer> space = new ArrayList<>();
        for (int j = 0; j < this.vectors.get(i).length(); j++) {
          if (this.vectors.get(i).indexOf(" ", j) == j) { //$NON-NLS-1$
            space.add(j);
          }
        }
        final float[] elements = new float[4];
        elements[0] = Float.parseFloat(this.vectors.get(i).substring(0, space.get(0)));
        elements[1] = Float.parseFloat(this.vectors.get(i).substring(space.get(0) + 1, space.get(1)));
        elements[2] = Float.parseFloat(this.vectors.get(i).substring(space.get(1) + 1, space.get(2)));
        elements[3] = Float.parseFloat(this.vectors.get(i).substring(space.get(2) + 1));
        this.matrix4f.setRow(i, elements);
      }
      
      if (this.nodes.size() != 0) {
        for (int i = 0; i < this.nodes.size(); i++) {
          this.nodes.get(i).createTransformMatrix();
          this.matrixNodes.add(this.nodes.get(i).getTransformMatrix());
        }
      }
      setURL();
      //ここからver1．4.1用
    } else /*if(this.version == "1.4.1")*/{
      if (this.name == null) {
        this.name = this.id;
      }

      /**
       * translate
       */
      final String[] trans = this.translate.split(" "); //$NON-NLS-1$
      final float[] n = new float[trans.length];
      for (int l = 0; l < trans.length; l++) {
        n[l] = Float.parseFloat(trans[l]);
      }

      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (i == j) {
            this.translation[i][j] = (float)1.0;
          } else if (i != 3 && j == 3) {
            this.translation[i][j] = n[i];//num.get(i);
          } else {
            this.translation[i][j] = (float)0.0;
          }

        }
      }

      /**
       * rotate
       */
      for (int i = 0; i < this.rotate.size(); i++) {
        final List<Integer> space = new ArrayList<>();
        for (int j = 0; j < this.rotate.get(i).length(); j++) {
          if (this.rotate.get(i).charAt(j) == ' ') {
            space.add(j);
          }
        }
        final int[] spaceNum = new int[space.size()];
        for (int k = 0; k < spaceNum.length; k++) {
          spaceNum[k] = space.get(k);
        }

        for (int j = 0; j < spaceNum.length + 1; j++) {
          if (j == 0) {
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(0, spaceNum[0]));
          } else if (j == spaceNum.length) {
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(spaceNum[spaceNum.length - 1] + 1));
          } else {
            this.rotateNum[i][j] = Float.parseFloat(this.rotate.get(i).substring(spaceNum[j - 1] + 1, spaceNum[j]));
          }
        }
      }

      //rotateの変換行列の作成
      for (int i = 0; i < 3; i++) {
        setRotationMatrix(this.rotateNum, i);//this.rotateNumber.get(i),i);
      }
      //変換行列の作成
      createMatrix4f();

      if (this.nodes.size() != 0) {
        for (int i = 0; i < this.nodes.size(); i++) {
          this.nodes.get(i).createTransformMatrix();
          this.matrixNodes.add(this.nodes.get(i).getTransformMatrix());
        }
      }
      setURL();
    }

  }

  private void createMatrix4f() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          this.matrixXY[i][j] += this.rotationX[i][k] * this.rotationY[k][j];
        }
      }
    }
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          this.matrixXYZ[i][j] += this.matrixXY[i][k] * this.rotationZ[k][j];
        }
      }
    }
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          this.matrixAll[i][j] += this.matrixXYZ[i][k] * this.translation[k][j];
        }
      }
    }
    setMatrix4f();
  }

  private void setMatrix4f() {
    float[] elements = new float[4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        elements[j] = (float)this.matrixAll[i][j];
      }
      this.matrix4f.setRow(i, elements);
    }
  }

  private void setRotationMatrix(float[][] rotate, int k) {
    if (k == 0) {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (i == j && i < 2) {
            this.rotationZ[i][j] = Math.cos(rotate[k][3]);
          } else if (i == 0 && j == 1) {
            this.rotationZ[i][j] = -1.0 * Math.sin(rotate[k][3]);
          } else if (i == 1 && j == 0) {
            this.rotationZ[i][j] = Math.sin(rotate[k][3]);
          } else if (i == j && i > 1) {
            this.rotationZ[i][j] = 1.0;
          } else {
            this.rotationZ[i][j] = 0.0;
          }
        }
      }
    } else if (k == 1) {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (i == j && i == 0) {
            this.rotationY[i][j] = Math.cos(rotate[k][3]);
          } else if (i == j && i == 2) {
            this.rotationY[i][j] = Math.cos(rotate[k][3]);
          } else if (i == 0 && j == 1) {
            this.rotationY[i][j] = -1.0 * Math.sin(rotate[k][3]);
          } else if (i == 1 && j == 0) {
            this.rotationY[i][j] = Math.sin(rotate[k][3]);
          } else if (i == j && ((i == 1) || i == 3)) {
            this.rotationY[i][j] = 1.0;
          } else {
            this.rotationY[i][j] = 0.0;
          }
        }
      }
    } else if (k == 2) {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          if (i == j && i > 0 && i < 3) {
            this.rotationX[i][j] = Math.cos(rotate[k][3]);
          } else if (i == 1 && j == 2) {
            this.rotationX[i][j] = -1.0 * Math.sin(rotate[k][3]);
          } else if (i == 2 && j == 1) {
            this.rotationX[i][j] = Math.sin(rotate[k][3]);
          } else if (i == j && (i > 1 || i == 0)) {
            this.rotationX[i][j] = 1.0;
          } else {
            this.rotationX[i][j] = (float)0.0;
          }
        }
      }
    }
  }

  /**
   * @return type
   */
  public String getType() {
    return this.type;
  }

  private void setURL() {
    this.geometryURL = this.instanceGeometry.getGeometryURL();
    if (this.geometryURL != null) {
      if (this.geometryURL.indexOf("-G") != -1) { //$NON-NLS-1$
        this.geometryURL = this.geometryURL.substring(1, this.geometryURL.indexOf("-G")); //$NON-NLS-1$
      } else if (this.geometryURL.indexOf("-m") != -1) { //$NON-NLS-1$
        this.geometryURL = this.geometryURL.substring(1, this.geometryURL.indexOf("-m")); //$NON-NLS-1$
      } else {
        this.geometryURL = this.geometryURL.substring(1);
      }
    }
  }

  /**
   * 
   */
  public void createScene() {
    for (final Node node : this.nodes) {
      node.createGroup();
      node.createScene();
    }

    for (final Node node : this.nodes) {
      if (node != null) {
        this.group.add(node.getGroup());
      }
    }

  }

  /**
   * 
   */
  public void createGroup() {
    if (this.type != null) {
      if (this.type.equals("JOINT")) { //$NON-NLS-1$
        this.group.setName(this.name);
        final TranslationModel location = new TranslationModel(this.matrix4f.getElement(0, 3), this.matrix4f.getElement(1, 3), this.matrix4f.getElement(2, 3));
        this.group.setTranslation(location);
        
        AnimationModel[] animations = new AnimationModel[3];
        animations[0] = new AnimationModel();
        animations[0].setTarget("translationX"); //$NON-NLS-1$
        animations[1] = new AnimationModel();
        animations[1].setTarget("translationY"); //$NON-NLS-1$
        animations[2] = new AnimationModel();
        animations[2].setTarget("translationZ"); //$NON-NLS-1$
        this.group.add(animations[0]);
        this.group.add(animations[1]);
        this.group.add(animations[2]);
      }
    }
  }

  /**
   * @return group
   */
  public GroupModel getGroup() {
    return this.group;
  }
}