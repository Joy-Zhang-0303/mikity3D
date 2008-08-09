/*
 * $Id: ColladaFileTransformer.java,v 1.2 2007/11/30 11:26:40 morimune Exp $
 * Copyright (C) 2004-2005 Koga Laboratoy. All rights reserved.
 *
 */
package org.mklab.mikity.xml.blender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Blenderデータを読み込む際、ルート要素を小文字に変換するためのファイル
 * @author SHOGO
 * @version $Revision: 1.2 $. 2007/11/30
 */
public class ColladaFileTransformer {
	
	/**
	 * 読み込みファイル(変換前)
	 */
	private File fileBefore;
	/**
	 * 読み込みファイル(変換後のものを書き込む別ファイル)
	 */
	private File fileAfter;
	/**
	 * 変換後の内容を各行ごとにまとめたリスト
	 */
	private ArrayList<String> note;
	
	/**
	 * コンストラクタ
	 * @param file 読み込みファイル
	 */
	public ColladaFileTransformer(File file){
		fileBefore = file;
		note = new ArrayList<String>();
		fileAfter = new File("registry.xml");
	}

	/**
	 * ルート要素に関する部分のみ内容を変換する。
	 * 変換する市内にかかわらず、各行ごとに内容をリストに追加する。
	 */
	private void transformFile(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileBefore));
			String line;
			while ((line = br.readLine()) != null) {
				if(line.indexOf("<COLLADA") != -1){
					note.add("<collada>");
				}else if(line.indexOf("</COLLADA") != -1){
					note.add("</collada>");
				}else{
					note.add(line);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * 変換後の内容をファイルに書き込む。
	 */
	private void writeTransformedFile(){
		try{
        	FileWriter fw = new FileWriter(fileAfter);
        	 for(int i=0; i<note.size(); i++){
        		 fw.write(note.get(i)+"\n");
             }
        	 fw.close();
        }catch(Exception e){
        }
	}
	
	/**
	 * 変換後のファイルを返す
	 * @return fileAfter　変換後のファイル
	 */
	public File getTransformedFile(){
		transformFile();
		writeTransformedFile();
		return fileAfter;
	}
}
