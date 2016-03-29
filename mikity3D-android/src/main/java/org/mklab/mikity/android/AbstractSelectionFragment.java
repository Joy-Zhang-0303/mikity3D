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
package org.mklab.mikity.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.mklab.mikity.model.xml.simplexml.model.AnimationModel;
import org.mklab.mikity.model.xml.simplexml.model.GroupModel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;


/**
 * 通常のファイルやサンプルのファイルを選択するフラグメントを表す抽象クラスです。
 * @author koga
 * @version $Revision$, 2016/03/05
 */
public abstract class AbstractSelectionFragment extends Fragment {
  /** メインアクティビティ。 */
  protected MainActivity mainActivity;
  /** キャンバスフラグメント。 */
  protected CanvasFragment canvasFragment;
  
  /** モデルファイル名。 */
  protected String modelFileName = "..."; //$NON-NLS-1$
  /** モデルファイルのパス。 */
  protected TextView modelFileNameView;
  /** ソースファイルのパス。 */
  protected Map<String, TextView> sourceFileNameViews = new HashMap<String, TextView>();
  /** ソースファイル名。 */
  protected Map<String, String> sourceFileNames = new HashMap<String, String>();
  /** ソースファイルを選択するためのボタン。 */
  protected List<Button> sourceSelectButtons = new ArrayList<Button>();
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onAttach(Activity activity) {
      super.onAttach(activity);
      if (activity instanceof MainActivity) {
          this.mainActivity = (MainActivity)activity;
          this.canvasFragment = this.mainActivity.canvasFragment;
      }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
  }

  /**
   * 全ての含まれるアニメーソンを返します。
   * 
   * @param groups グループ
   * 
   * @return 全ての含まれるアニメーソン
   */
  private List<AnimationModel> getAllAnimation(List<GroupModel> groups) {
    final List<AnimationModel> allAnimations = new ArrayList<AnimationModel>();
  
    for (final GroupModel group : groups) {
      final AnimationModel[] animations = group.getAnimations();
      for (final AnimationModel animation : animations) {
        if (animation.exists()) {
          allAnimations.add(animation);
        }
      }
  
      allAnimations.addAll(getAllAnimation(group.getGroups()));
    }
  
    return allAnimations;
  }

  /**
   * グループ以下に含まれるアニメーションのソースIDを返します。
   * 
   * @param ｇroups グループ
   * @return グループ以下に含まれるアニメーションのソースIDを返します。
   */
  protected Set<String> getAllSourceIds(final List<GroupModel> ｇroups) {
    final List<AnimationModel> allAnimations = getAllAnimation(ｇroups);
  
    final Set<String> ids = new TreeSet<String>();
    for (final AnimationModel animation : allAnimations) {
      ids.add(animation.getSource().getId());
    }
    return ids;
  }

  /**
   * メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showMessageInDialog(String message) {
    final MessageDialogFragment dialog = new MessageDialogFragment();
    final Bundle arguments = new Bundle();
    arguments.putString("message", message); //$NON-NLS-1$
    dialog.setArguments(arguments);
    
    dialog.show(AbstractSelectionFragment.this.mainActivity.getSupportFragmentManager(), "alertDialogFragment"); //$NON-NLS-1$
  }

  /**
   * リセットします。
   */
  public void reset() {
    this.modelFileName = "..."; //$NON-NLS-1$
    this.modelFileNameView = null;
    this.sourceFileNames.clear();
    this.sourceFileNameViews.clear();
    this.sourceSelectButtons.clear();
  }
}
