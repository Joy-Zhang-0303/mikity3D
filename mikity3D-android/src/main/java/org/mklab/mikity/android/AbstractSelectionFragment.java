/*
 * Created on 2016/03/05
 * Copyright (C) 2016 Koga Laboratory. All rights reserved.
 *
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
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    
    this.mainActivity = (MainActivity)getActivity();
    this.canvasFragment = this.mainActivity.canvasFragment;
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
