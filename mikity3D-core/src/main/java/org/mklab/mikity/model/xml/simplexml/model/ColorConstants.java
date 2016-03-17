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
package org.mklab.mikity.model.xml.simplexml.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 色彩を表すクラスです。
 * 
 * @author miki
 * @version $Revision: 1.4 $.2004/11/26
 */
public class ColorConstants {
  /** 色の名前と色オブジェクトのマップ */
  private static final Map<String, ColorModel> COLOR_MAP = new HashMap<>();

  static {
    COLOR_MAP.put("AliceBlue",  new ColorModel(240, 248, 255)); //$NON-NLS-1$
    COLOR_MAP.put("AntiqueWhite",   new ColorModel(250, 235, 215)); //$NON-NLS-1$
    COLOR_MAP.put("AntiqueWhite2",  new ColorModel(238, 223, 204)); //$NON-NLS-1$
    COLOR_MAP.put("AntiqueWhite3",  new ColorModel(205, 192, 176)); //$NON-NLS-1$
    COLOR_MAP.put("AntiqueWhite4",  new ColorModel(139, 131, 120)); //$NON-NLS-1$
    COLOR_MAP.put("BlanchedAlmond", new ColorModel(255, 235, 205)); //$NON-NLS-1$
    COLOR_MAP.put("BlueViolet", new ColorModel(138, 43, 226)); //$NON-NLS-1$
    COLOR_MAP.put("CadetBlue",  new ColorModel(95, 158, 160)); //$NON-NLS-1$
    COLOR_MAP.put("CadetBlue1", new ColorModel(152, 245, 255)); //$NON-NLS-1$
    COLOR_MAP.put("CadetBlue2", new ColorModel(142, 229, 238)); //$NON-NLS-1$
    COLOR_MAP.put("CadetBlue3", new ColorModel(122, 197, 205)); //$NON-NLS-1$
    COLOR_MAP.put("CadetBlue4", new ColorModel(83, 134, 139)); //$NON-NLS-1$
    COLOR_MAP.put("CornflowerBlue", new ColorModel(100, 149, 237)); //$NON-NLS-1$
    COLOR_MAP.put("DarkBlue",   new ColorModel(0, 0, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkCyan",   new ColorModel(0, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGoldenrod",  new ColorModel(184, 134, 11)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGoldenrod1", new ColorModel(255, 185, 15)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGoldenrod2", new ColorModel(238, 173, 14)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGoldenrod3", new ColorModel(205, 149, 12)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGoldenrod4", new ColorModel(139, 101, 8)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGray",   new ColorModel(169, 169, 169)); //$NON-NLS-1$
    COLOR_MAP.put("DarkGreen",  new ColorModel(0, 100, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkKhaki",  new ColorModel(189, 183, 107)); //$NON-NLS-1$
    COLOR_MAP.put("DarkMagenta",    new ColorModel(139, 0, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOliveGreen", new ColorModel(85, 107, 47)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOliveGreen1",    new ColorModel(202, 255, 112)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOliveGreen2",    new ColorModel(188, 238, 104)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOliveGreen3",    new ColorModel(162, 205, 90)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOliveGreen4",    new ColorModel(110, 139, 61)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrange", new ColorModel(255, 140, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrange1",    new ColorModel(255, 127, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrange2",    new ColorModel(238, 118, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrange3",    new ColorModel(205, 102, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrange4",    new ColorModel(139, 69, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrchid", new ColorModel(153, 50, 204)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrchid1",    new ColorModel(191, 62, 255)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrchid2",    new ColorModel(178, 58, 238)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrchid3",    new ColorModel(154, 50, 205)); //$NON-NLS-1$
    COLOR_MAP.put("DarkOrchid4",    new ColorModel(104, 34, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkRed",    new ColorModel(139, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSalmon", new ColorModel(233, 150, 122)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSeaGreen",   new ColorModel(143, 188, 143)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSeaGreen1",  new ColorModel(193, 255, 193)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSeaGreen2",  new ColorModel(180, 238, 180)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSeaGreen3",  new ColorModel(155, 205, 155)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSeaGreen4",  new ColorModel(105, 139, 105)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateBlue",  new ColorModel(72, 61, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateGray",  new ColorModel(47, 79, 79)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateGray1", new ColorModel(151, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateGray2", new ColorModel(141, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateGray3", new ColorModel(121, 205, 205)); //$NON-NLS-1$
    COLOR_MAP.put("DarkSlateGray4", new ColorModel(82, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DarkTurquoise",  new ColorModel(0, 206, 209)); //$NON-NLS-1$
    COLOR_MAP.put("DarkViolet", new ColorModel(148, 0, 211)); //$NON-NLS-1$
    COLOR_MAP.put("DeepPink",   new ColorModel(255, 20, 147)); //$NON-NLS-1$
    COLOR_MAP.put("DeepPink2",  new ColorModel(238, 18, 137)); //$NON-NLS-1$
    COLOR_MAP.put("DeepPink3",  new ColorModel(205, 16, 118)); //$NON-NLS-1$
    COLOR_MAP.put("DeepPink4",  new ColorModel(139, 10, 80)); //$NON-NLS-1$
    COLOR_MAP.put("DeepSkyBlue",    new ColorModel(0, 191, 255)); //$NON-NLS-1$
    COLOR_MAP.put("DeepSkyBlue2",   new ColorModel(0, 178, 238)); //$NON-NLS-1$
    COLOR_MAP.put("DeepSkyBlue3",   new ColorModel(0, 154, 205)); //$NON-NLS-1$
    COLOR_MAP.put("DeepSkyBlue4",   new ColorModel(0, 104, 139)); //$NON-NLS-1$
    COLOR_MAP.put("DimGray",    new ColorModel(105, 105, 105)); //$NON-NLS-1$
    COLOR_MAP.put("DodgerBlue", new ColorModel(30, 144, 255)); //$NON-NLS-1$
    COLOR_MAP.put("DodgerBlue2",    new ColorModel(28, 134, 238)); //$NON-NLS-1$
    COLOR_MAP.put("DodgerBlue3",    new ColorModel(24, 116, 205)); //$NON-NLS-1$
    COLOR_MAP.put("DodgerBlue4",    new ColorModel(16, 78, 139)); //$NON-NLS-1$
    COLOR_MAP.put("FloralWhite",    new ColorModel(255, 250, 240)); //$NON-NLS-1$
    COLOR_MAP.put("ForestGreen",    new ColorModel(34, 139, 34)); //$NON-NLS-1$
    COLOR_MAP.put("GhostWhite", new ColorModel(248, 248, 255)); //$NON-NLS-1$
    COLOR_MAP.put("GreenYellow",    new ColorModel(173, 255, 47)); //$NON-NLS-1$
    COLOR_MAP.put("HotPink",    new ColorModel(255, 105, 180)); //$NON-NLS-1$
    COLOR_MAP.put("HotPink1",   new ColorModel(255, 110, 180)); //$NON-NLS-1$
    COLOR_MAP.put("HotPink2",   new ColorModel(238, 106, 167)); //$NON-NLS-1$
    COLOR_MAP.put("HotPink3",   new ColorModel(205, 96, 144)); //$NON-NLS-1$
    COLOR_MAP.put("HotPink4",   new ColorModel(139, 58, 98)); //$NON-NLS-1$
    COLOR_MAP.put("IndianRed",  new ColorModel(205, 92, 92)); //$NON-NLS-1$
    COLOR_MAP.put("IndianRed1", new ColorModel(255, 106, 106)); //$NON-NLS-1$
    COLOR_MAP.put("IndianRed2", new ColorModel(238, 99, 99)); //$NON-NLS-1$
    COLOR_MAP.put("IndianRed3", new ColorModel(205, 85, 85)); //$NON-NLS-1$
    COLOR_MAP.put("IndianRed4", new ColorModel(139, 58, 58)); //$NON-NLS-1$
    COLOR_MAP.put("LavenderBlush",  new ColorModel(255, 240, 245)); //$NON-NLS-1$
    COLOR_MAP.put("LavenderBlush2", new ColorModel(238, 224, 229)); //$NON-NLS-1$
    COLOR_MAP.put("LavenderBlush3", new ColorModel(205, 193, 197)); //$NON-NLS-1$
    COLOR_MAP.put("LavenderBlush4", new ColorModel(139, 131, 134)); //$NON-NLS-1$
    COLOR_MAP.put("LawnGreen",  new ColorModel(124, 252, 0)); //$NON-NLS-1$
    COLOR_MAP.put("LemonChiffon",   new ColorModel(255, 250, 205)); //$NON-NLS-1$
    COLOR_MAP.put("LemonChiffon2",  new ColorModel(238, 233, 191)); //$NON-NLS-1$
    COLOR_MAP.put("LemonChiffon3",  new ColorModel(205, 201, 165)); //$NON-NLS-1$
    COLOR_MAP.put("LemonChiffon4",  new ColorModel(139, 137, 112)); //$NON-NLS-1$
    COLOR_MAP.put("LightBlue",  new ColorModel(173, 216, 230)); //$NON-NLS-1$
    COLOR_MAP.put("LightBlue1", new ColorModel(191, 239, 255)); //$NON-NLS-1$
    COLOR_MAP.put("LightBlue2", new ColorModel(178, 223, 238)); //$NON-NLS-1$
    COLOR_MAP.put("LightBlue3", new ColorModel(154, 192, 205)); //$NON-NLS-1$
    COLOR_MAP.put("LightBlue4", new ColorModel(104, 131, 139)); //$NON-NLS-1$
    COLOR_MAP.put("LightCoral", new ColorModel(240, 128, 128)); //$NON-NLS-1$
    COLOR_MAP.put("LightCyan",  new ColorModel(224, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("LightCyan2", new ColorModel(209, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("LightCyan3", new ColorModel(180, 205, 205)); //$NON-NLS-1$
    COLOR_MAP.put("LightCyan4", new ColorModel(122, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrod", new ColorModel(238, 221, 130)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrod1",    new ColorModel(255, 236, 139)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrod2",    new ColorModel(238, 220, 130)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrod3",    new ColorModel(205, 190, 112)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrod4",    new ColorModel(139, 129, 76)); //$NON-NLS-1$
    COLOR_MAP.put("LightGoldenrodYellow",   new ColorModel(250, 250, 210)); //$NON-NLS-1$
    COLOR_MAP.put("LightGray",  new ColorModel(211, 211, 211)); //$NON-NLS-1$
    COLOR_MAP.put("LightGreen", new ColorModel(144, 238, 144)); //$NON-NLS-1$
    COLOR_MAP.put("LightPink",  new ColorModel(255, 182, 193)); //$NON-NLS-1$
    COLOR_MAP.put("LightPink1", new ColorModel(255, 174, 185)); //$NON-NLS-1$
    COLOR_MAP.put("LightPink2", new ColorModel(238, 162, 173)); //$NON-NLS-1$
    COLOR_MAP.put("LightPink3", new ColorModel(205, 140, 149)); //$NON-NLS-1$
    COLOR_MAP.put("LightPink4", new ColorModel(139, 95, 101)); //$NON-NLS-1$
    COLOR_MAP.put("LightSalmon",    new ColorModel(255, 160, 122)); //$NON-NLS-1$
    COLOR_MAP.put("LightSalmon2",   new ColorModel(238, 149, 114)); //$NON-NLS-1$
    COLOR_MAP.put("LightSalmon3",   new ColorModel(205, 129, 98)); //$NON-NLS-1$
    COLOR_MAP.put("LightSalmon4",   new ColorModel(139, 87, 66)); //$NON-NLS-1$
    COLOR_MAP.put("LightSeaGreen",  new ColorModel(32, 178, 170)); //$NON-NLS-1$
    COLOR_MAP.put("LightSkyBlue",   new ColorModel(135, 206, 250)); //$NON-NLS-1$
    COLOR_MAP.put("LightSkyBlue1",  new ColorModel(176, 226, 255)); //$NON-NLS-1$
    COLOR_MAP.put("LightSkyBlue2",  new ColorModel(164, 211, 238)); //$NON-NLS-1$
    COLOR_MAP.put("LightSkyBlue3",  new ColorModel(141, 182, 205)); //$NON-NLS-1$
    COLOR_MAP.put("LightSkyBlue4",  new ColorModel(96, 123, 139)); //$NON-NLS-1$
    COLOR_MAP.put("LightSlateBlue", new ColorModel(132, 112, 255)); //$NON-NLS-1$
    COLOR_MAP.put("LightSlateGray", new ColorModel(119, 136, 153)); //$NON-NLS-1$
    COLOR_MAP.put("LightSteelBlue", new ColorModel(176, 196, 222)); //$NON-NLS-1$
    COLOR_MAP.put("LightSteelBlue1",    new ColorModel(202, 225, 255)); //$NON-NLS-1$
    COLOR_MAP.put("LightSteelBlue2",    new ColorModel(188, 210, 238)); //$NON-NLS-1$
    COLOR_MAP.put("LightSteelBlue3",    new ColorModel(162, 181, 205)); //$NON-NLS-1$
    COLOR_MAP.put("LightSteelBlue4",    new ColorModel(110, 123, 139)); //$NON-NLS-1$
    COLOR_MAP.put("LightYellow",    new ColorModel(255, 255, 224)); //$NON-NLS-1$
    COLOR_MAP.put("LightYellow2",   new ColorModel(238, 238, 209)); //$NON-NLS-1$
    COLOR_MAP.put("LightYellow3",   new ColorModel(205, 205, 180)); //$NON-NLS-1$
    COLOR_MAP.put("LightYellow4",   new ColorModel(139, 139, 122)); //$NON-NLS-1$
    COLOR_MAP.put("LimeGreen",  new ColorModel(50, 205, 50)); //$NON-NLS-1$
    COLOR_MAP.put("MediumAquamarine",   new ColorModel(102, 205, 170)); //$NON-NLS-1$
    COLOR_MAP.put("MediumBlue", new ColorModel(0, 0, 205)); //$NON-NLS-1$
    COLOR_MAP.put("MediumOrchid",   new ColorModel(186, 85, 211)); //$NON-NLS-1$
    COLOR_MAP.put("MediumOrchid1",  new ColorModel(224, 102, 255)); //$NON-NLS-1$
    COLOR_MAP.put("MediumOrchid2",  new ColorModel(209, 95, 238)); //$NON-NLS-1$
    COLOR_MAP.put("MediumOrchid3",  new ColorModel(180, 82, 205)); //$NON-NLS-1$
    COLOR_MAP.put("MediumOrchid4",  new ColorModel(122, 55, 139)); //$NON-NLS-1$
    COLOR_MAP.put("MediumPurple",   new ColorModel(147, 112, 219)); //$NON-NLS-1$
    COLOR_MAP.put("MediumPurple1",  new ColorModel(171, 130, 255)); //$NON-NLS-1$
    COLOR_MAP.put("MediumPurple2",  new ColorModel(159, 121, 238)); //$NON-NLS-1$
    COLOR_MAP.put("MediumPurple3",  new ColorModel(137, 104, 205)); //$NON-NLS-1$
    COLOR_MAP.put("MediumPurple4",  new ColorModel(93, 71, 139)); //$NON-NLS-1$
    COLOR_MAP.put("MediumSeaGreen", new ColorModel(60, 179, 113)); //$NON-NLS-1$
    COLOR_MAP.put("MediumSlateBlue",    new ColorModel(123, 104, 238)); //$NON-NLS-1$
    COLOR_MAP.put("MediumSpringGreen",  new ColorModel(0, 250, 154)); //$NON-NLS-1$
    COLOR_MAP.put("MediumTurquoise",    new ColorModel(72, 209, 204)); //$NON-NLS-1$
    COLOR_MAP.put("MediumVioletRed",    new ColorModel(199, 21, 133)); //$NON-NLS-1$
    COLOR_MAP.put("MidnightBlue",   new ColorModel(25, 25, 112)); //$NON-NLS-1$
    COLOR_MAP.put("MintCream",  new ColorModel(245, 255, 250)); //$NON-NLS-1$
    COLOR_MAP.put("MistyRose",  new ColorModel(255, 228, 225)); //$NON-NLS-1$
    COLOR_MAP.put("MistyRose2", new ColorModel(238, 213, 210)); //$NON-NLS-1$
    COLOR_MAP.put("MistyRose3", new ColorModel(205, 183, 181)); //$NON-NLS-1$
    COLOR_MAP.put("MistyRose4", new ColorModel(139, 125, 123)); //$NON-NLS-1$
    COLOR_MAP.put("NavajoWhite",    new ColorModel(255, 222, 173)); //$NON-NLS-1$
    COLOR_MAP.put("NavajoWhite2",   new ColorModel(238, 207, 161)); //$NON-NLS-1$
    COLOR_MAP.put("NavajoWhite3",   new ColorModel(205, 179, 139)); //$NON-NLS-1$
    COLOR_MAP.put("NavajoWhite4",   new ColorModel(139, 121, 94)); //$NON-NLS-1$
    COLOR_MAP.put("NavyBlue",   new ColorModel(0, 0, 128)); //$NON-NLS-1$
    COLOR_MAP.put("OldLace",    new ColorModel(253, 245, 230)); //$NON-NLS-1$
    COLOR_MAP.put("OliveDrab",  new ColorModel(107, 142, 35)); //$NON-NLS-1$
    COLOR_MAP.put("OliveDrab1", new ColorModel(192, 255, 62)); //$NON-NLS-1$
    COLOR_MAP.put("OliveDrab2", new ColorModel(179, 238, 58)); //$NON-NLS-1$
    COLOR_MAP.put("OliveDrab3", new ColorModel(154, 205, 50)); //$NON-NLS-1$
    COLOR_MAP.put("OliveDrab4", new ColorModel(105, 139, 34)); //$NON-NLS-1$
    COLOR_MAP.put("OrangeRed",  new ColorModel(255, 69, 0)); //$NON-NLS-1$
    COLOR_MAP.put("OrangeRed2", new ColorModel(238, 64, 0)); //$NON-NLS-1$
    COLOR_MAP.put("OrangeRed3", new ColorModel(205, 55, 0)); //$NON-NLS-1$
    COLOR_MAP.put("OrangeRed4", new ColorModel(139, 37, 0)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGoldenrod",  new ColorModel(238, 232, 170)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGreen",  new ColorModel(152, 251, 152)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGreen1", new ColorModel(154, 255, 154)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGreen2", new ColorModel(144, 238, 144)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGreen3", new ColorModel(124, 205, 124)); //$NON-NLS-1$
    COLOR_MAP.put("PaleGreen4", new ColorModel(84, 139, 84)); //$NON-NLS-1$
    COLOR_MAP.put("PaleTurquoise",  new ColorModel(175, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("PaleTurquoise1", new ColorModel(187, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("PaleTurquoise2", new ColorModel(174, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("PaleTurquoise3", new ColorModel(150, 205, 205)); //$NON-NLS-1$
    COLOR_MAP.put("PaleTurquoise4", new ColorModel(102, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("PaleVioletRed",  new ColorModel(219, 112, 147)); //$NON-NLS-1$
    COLOR_MAP.put("PaleVioletRed1", new ColorModel(255, 130, 171)); //$NON-NLS-1$
    COLOR_MAP.put("PaleVioletRed2", new ColorModel(238, 121, 159)); //$NON-NLS-1$
    COLOR_MAP.put("PaleVioletRed3", new ColorModel(205, 104, 137)); //$NON-NLS-1$
    COLOR_MAP.put("PaleVioletRed4", new ColorModel(139, 71, 93)); //$NON-NLS-1$
    COLOR_MAP.put("PapayaWhip", new ColorModel(255, 239, 213)); //$NON-NLS-1$
    COLOR_MAP.put("PeachPuff",  new ColorModel(255, 218, 185)); //$NON-NLS-1$
    COLOR_MAP.put("PeachPuff1", new ColorModel(255, 218, 185)); //$NON-NLS-1$
    COLOR_MAP.put("PeachPuff2", new ColorModel(238, 203, 173)); //$NON-NLS-1$
    COLOR_MAP.put("PeachPuff3", new ColorModel(205, 175, 149)); //$NON-NLS-1$
    COLOR_MAP.put("PeachPuff4", new ColorModel(139, 119, 101)); //$NON-NLS-1$
    COLOR_MAP.put("PowderBlue", new ColorModel(176, 224, 230)); //$NON-NLS-1$
    COLOR_MAP.put("RosyBrown",  new ColorModel(188, 143, 143)); //$NON-NLS-1$
    COLOR_MAP.put("RosyBrown1", new ColorModel(255, 193, 193)); //$NON-NLS-1$
    COLOR_MAP.put("RosyBrown2", new ColorModel(238, 180, 180)); //$NON-NLS-1$
    COLOR_MAP.put("RosyBrown3", new ColorModel(205, 155, 155)); //$NON-NLS-1$
    COLOR_MAP.put("RosyBrown4", new ColorModel(139, 105, 105)); //$NON-NLS-1$
    COLOR_MAP.put("RoyalBlue",  new ColorModel(65, 105, 225)); //$NON-NLS-1$
    COLOR_MAP.put("RoyalBlue1", new ColorModel(72, 118, 255)); //$NON-NLS-1$
    COLOR_MAP.put("RoyalBlue2", new ColorModel(67, 110, 238)); //$NON-NLS-1$
    COLOR_MAP.put("RoyalBlue3", new ColorModel(58, 95, 205)); //$NON-NLS-1$
    COLOR_MAP.put("RoyalBlue4", new ColorModel(39, 64, 139)); //$NON-NLS-1$
    COLOR_MAP.put("SaddleBrown",    new ColorModel(139, 69, 19)); //$NON-NLS-1$
    COLOR_MAP.put("SandyBrown", new ColorModel(244, 164, 96)); //$NON-NLS-1$
    COLOR_MAP.put("SeaGreen",   new ColorModel(46, 139, 87)); //$NON-NLS-1$
    COLOR_MAP.put("SeaGreen1",  new ColorModel(84, 255, 159)); //$NON-NLS-1$
    COLOR_MAP.put("SeaGreen2",  new ColorModel(78, 238, 148)); //$NON-NLS-1$
    COLOR_MAP.put("SeaGreen3",  new ColorModel(67, 205, 128)); //$NON-NLS-1$
    COLOR_MAP.put("SeaGreen4",  new ColorModel(46, 139, 87)); //$NON-NLS-1$
    COLOR_MAP.put("SkyBlue",    new ColorModel(135, 206, 235)); //$NON-NLS-1$
    COLOR_MAP.put("SkyBlue1",   new ColorModel(135, 206, 255)); //$NON-NLS-1$
    COLOR_MAP.put("SkyBlue2",   new ColorModel(126, 192, 238)); //$NON-NLS-1$
    COLOR_MAP.put("SkyBlue3",   new ColorModel(108, 166, 205)); //$NON-NLS-1$
    COLOR_MAP.put("SkyBlue4",   new ColorModel(74, 112, 139)); //$NON-NLS-1$
    COLOR_MAP.put("SlateBlue",  new ColorModel(106, 90, 205)); //$NON-NLS-1$
    COLOR_MAP.put("SlateBlue1", new ColorModel(131, 111, 255)); //$NON-NLS-1$
    COLOR_MAP.put("SlateBlue2", new ColorModel(122, 103, 238)); //$NON-NLS-1$
    COLOR_MAP.put("SlateBlue3", new ColorModel(105, 89, 205)); //$NON-NLS-1$
    COLOR_MAP.put("SlateBlue4", new ColorModel(71, 60, 139)); //$NON-NLS-1$
    COLOR_MAP.put("SlateGray",  new ColorModel(112, 128, 144)); //$NON-NLS-1$
    COLOR_MAP.put("SlateGray1", new ColorModel(198, 226, 255)); //$NON-NLS-1$
    COLOR_MAP.put("SlateGray2", new ColorModel(185, 211, 238)); //$NON-NLS-1$
    COLOR_MAP.put("SlateGray3", new ColorModel(159, 182, 205)); //$NON-NLS-1$
    COLOR_MAP.put("SlateGray4", new ColorModel(108, 123, 139)); //$NON-NLS-1$
    COLOR_MAP.put("SpringGreen",    new ColorModel(0, 255, 127)); //$NON-NLS-1$
    COLOR_MAP.put("SpringGreen1",   new ColorModel(0, 255, 127)); //$NON-NLS-1$
    COLOR_MAP.put("SpringGreen2",   new ColorModel(0, 238, 118)); //$NON-NLS-1$
    COLOR_MAP.put("SpringGreen3",   new ColorModel(0, 205, 102)); //$NON-NLS-1$
    COLOR_MAP.put("SpringGreen4",   new ColorModel(0, 139, 69)); //$NON-NLS-1$
    COLOR_MAP.put("SteelBlue",  new ColorModel(70, 130, 180)); //$NON-NLS-1$
    COLOR_MAP.put("SteelBlue1", new ColorModel(99, 184, 255)); //$NON-NLS-1$
    COLOR_MAP.put("SteelBlue2", new ColorModel(92, 172, 238)); //$NON-NLS-1$
    COLOR_MAP.put("SteelBlue3", new ColorModel(79, 148, 205)); //$NON-NLS-1$
    COLOR_MAP.put("SteelBlue4", new ColorModel(54, 100, 139)); //$NON-NLS-1$
    COLOR_MAP.put("VioletRed",  new ColorModel(208, 32, 144)); //$NON-NLS-1$
    COLOR_MAP.put("VioletRed1", new ColorModel(255, 62, 150)); //$NON-NLS-1$
    COLOR_MAP.put("VioletRed2", new ColorModel(238, 58, 140)); //$NON-NLS-1$
    COLOR_MAP.put("VioletRed3", new ColorModel(205, 50, 120)); //$NON-NLS-1$
    COLOR_MAP.put("VioletRed4", new ColorModel(139, 34, 82)); //$NON-NLS-1$
    COLOR_MAP.put("WhiteSmoke", new ColorModel(245, 245, 245)); //$NON-NLS-1$
    COLOR_MAP.put("YellowGreen",    new ColorModel(154, 205, 50)); //$NON-NLS-1$
    COLOR_MAP.put("aquamarine", new ColorModel(127, 255, 212)); //$NON-NLS-1$
    COLOR_MAP.put("aquamarine1",    new ColorModel(127, 255, 212)); //$NON-NLS-1$
    COLOR_MAP.put("aquamarine2",    new ColorModel(118, 238, 198)); //$NON-NLS-1$
    COLOR_MAP.put("aquamarine3",    new ColorModel(102, 205, 170)); //$NON-NLS-1$
    COLOR_MAP.put("aquamarine4",    new ColorModel(69, 139, 116)); //$NON-NLS-1$
    COLOR_MAP.put("azure",  new ColorModel(240, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("azure1", new ColorModel(240, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("azure2", new ColorModel(224, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("azure3", new ColorModel(193, 205, 205)); //$NON-NLS-1$
    COLOR_MAP.put("azure4", new ColorModel(131, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("beige",  new ColorModel(245, 245, 220)); //$NON-NLS-1$
    COLOR_MAP.put("bisque", new ColorModel(255, 228, 196)); //$NON-NLS-1$
    COLOR_MAP.put("bisque1",    new ColorModel(255, 228, 196)); //$NON-NLS-1$
    COLOR_MAP.put("bisque2",    new ColorModel(238, 213, 183)); //$NON-NLS-1$
    COLOR_MAP.put("bisque3",    new ColorModel(205, 183, 158)); //$NON-NLS-1$
    COLOR_MAP.put("bisque4",    new ColorModel(139, 125, 107)); //$NON-NLS-1$
    COLOR_MAP.put("black",  new ColorModel(0, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("blue",   new ColorModel(0, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("blue2",  new ColorModel(0, 0, 238)); //$NON-NLS-1$
    COLOR_MAP.put("blue3",  new ColorModel(0, 0, 205)); //$NON-NLS-1$
    COLOR_MAP.put("blue4",  new ColorModel(0, 0, 139)); //$NON-NLS-1$
    COLOR_MAP.put("brown",  new ColorModel(165, 42, 42)); //$NON-NLS-1$
    COLOR_MAP.put("brown1", new ColorModel(255, 64, 64)); //$NON-NLS-1$
    COLOR_MAP.put("brown2", new ColorModel(238, 59, 59)); //$NON-NLS-1$
    COLOR_MAP.put("brown3", new ColorModel(205, 51, 51)); //$NON-NLS-1$
    COLOR_MAP.put("brown4", new ColorModel(139, 35, 35)); //$NON-NLS-1$
    COLOR_MAP.put("burlywood",  new ColorModel(222, 184, 135)); //$NON-NLS-1$
    COLOR_MAP.put("burlywood1", new ColorModel(255, 211, 155)); //$NON-NLS-1$
    COLOR_MAP.put("burlywood2", new ColorModel(238, 197, 145)); //$NON-NLS-1$
    COLOR_MAP.put("burlywood3", new ColorModel(205, 170, 125)); //$NON-NLS-1$
    COLOR_MAP.put("burlywood4", new ColorModel(139, 115, 85)); //$NON-NLS-1$
    COLOR_MAP.put("chartreuse", new ColorModel(127, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("chartreuse1",    new ColorModel(127, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("chartreuse2",    new ColorModel(118, 238, 0)); //$NON-NLS-1$
    COLOR_MAP.put("chartreuse3",    new ColorModel(102, 205, 0)); //$NON-NLS-1$
    COLOR_MAP.put("chartreuse4",    new ColorModel(69, 139, 0)); //$NON-NLS-1$
    COLOR_MAP.put("chocolate",  new ColorModel(210, 105, 30)); //$NON-NLS-1$
    COLOR_MAP.put("chocolate1", new ColorModel(255, 127, 36)); //$NON-NLS-1$
    COLOR_MAP.put("chocolate2", new ColorModel(238, 118, 33)); //$NON-NLS-1$
    COLOR_MAP.put("chocolate3", new ColorModel(205, 102, 29)); //$NON-NLS-1$
    COLOR_MAP.put("chocolate4", new ColorModel(139, 69, 19)); //$NON-NLS-1$
    COLOR_MAP.put("coral",  new ColorModel(255, 127, 80)); //$NON-NLS-1$
    COLOR_MAP.put("coral1", new ColorModel(255, 114, 86)); //$NON-NLS-1$
    COLOR_MAP.put("coral2", new ColorModel(238, 106, 80)); //$NON-NLS-1$
    COLOR_MAP.put("coral3", new ColorModel(205, 91, 69)); //$NON-NLS-1$
    COLOR_MAP.put("coral4", new ColorModel(139, 62, 47)); //$NON-NLS-1$
    COLOR_MAP.put("cornsilk",   new ColorModel(255, 248, 220)); //$NON-NLS-1$
    COLOR_MAP.put("cornsilk1",  new ColorModel(255, 248, 220)); //$NON-NLS-1$
    COLOR_MAP.put("cornsilk2",  new ColorModel(238, 232, 205)); //$NON-NLS-1$
    COLOR_MAP.put("cornsilk3",  new ColorModel(205, 200, 177)); //$NON-NLS-1$
    COLOR_MAP.put("cornsilk4",  new ColorModel(139, 136, 120)); //$NON-NLS-1$
    COLOR_MAP.put("cyan",   new ColorModel(0, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("cyan2",  new ColorModel(0, 238, 238)); //$NON-NLS-1$
    COLOR_MAP.put("cyan3",  new ColorModel(0, 205, 205)); //$NON-NLS-1$
    COLOR_MAP.put("cyan4",  new ColorModel(0, 139, 139)); //$NON-NLS-1$
    COLOR_MAP.put("firebrick",  new ColorModel(178, 34, 34)); //$NON-NLS-1$
    COLOR_MAP.put("firebrick1", new ColorModel(255, 48, 48)); //$NON-NLS-1$
    COLOR_MAP.put("firebrick2", new ColorModel(238, 44, 44)); //$NON-NLS-1$
    COLOR_MAP.put("firebrick3", new ColorModel(205, 38, 38)); //$NON-NLS-1$
    COLOR_MAP.put("firebrick4", new ColorModel(139, 26, 26)); //$NON-NLS-1$
    COLOR_MAP.put("gainsboro",  new ColorModel(220, 220, 220)); //$NON-NLS-1$
    COLOR_MAP.put("gold",   new ColorModel(255, 215, 0)); //$NON-NLS-1$
    COLOR_MAP.put("gold1",  new ColorModel(255, 215, 0)); //$NON-NLS-1$
    COLOR_MAP.put("gold2",  new ColorModel(238, 201, 0)); //$NON-NLS-1$
    COLOR_MAP.put("gold3",  new ColorModel(205, 173, 0)); //$NON-NLS-1$
    COLOR_MAP.put("gold4",  new ColorModel(139, 117, 0)); //$NON-NLS-1$
    COLOR_MAP.put("goldenrod",  new ColorModel(218, 165, 32)); //$NON-NLS-1$
    COLOR_MAP.put("goldenrod1", new ColorModel(255, 193, 37)); //$NON-NLS-1$
    COLOR_MAP.put("goldenrod2", new ColorModel(238, 180, 34)); //$NON-NLS-1$
    COLOR_MAP.put("goldenrod3", new ColorModel(205, 155, 29)); //$NON-NLS-1$
    COLOR_MAP.put("goldenrod4", new ColorModel(139, 105, 20)); //$NON-NLS-1$
    COLOR_MAP.put("gray",   new ColorModel(190, 190, 190)); //$NON-NLS-1$
    COLOR_MAP.put("gray1",  new ColorModel(3, 3, 3)); //$NON-NLS-1$
    COLOR_MAP.put("gray10", new ColorModel(26, 26, 26)); //$NON-NLS-1$
    COLOR_MAP.put("gray11", new ColorModel(28, 28, 28)); //$NON-NLS-1$
    COLOR_MAP.put("gray12", new ColorModel(31, 31, 31)); //$NON-NLS-1$
    COLOR_MAP.put("gray13", new ColorModel(33, 33, 33)); //$NON-NLS-1$
    COLOR_MAP.put("gray14", new ColorModel(36, 36, 36)); //$NON-NLS-1$
    COLOR_MAP.put("gray15", new ColorModel(38, 38, 38)); //$NON-NLS-1$
    COLOR_MAP.put("gray16", new ColorModel(41, 41, 41)); //$NON-NLS-1$
    COLOR_MAP.put("gray17", new ColorModel(43, 43, 43)); //$NON-NLS-1$
    COLOR_MAP.put("gray18", new ColorModel(46, 46, 46)); //$NON-NLS-1$
    COLOR_MAP.put("gray19", new ColorModel(48, 48, 48)); //$NON-NLS-1$
    COLOR_MAP.put("gray2",  new ColorModel(5, 5, 5)); //$NON-NLS-1$
    COLOR_MAP.put("gray20", new ColorModel(51, 51, 51)); //$NON-NLS-1$
    COLOR_MAP.put("gray21", new ColorModel(54, 54, 54)); //$NON-NLS-1$
    COLOR_MAP.put("gray22", new ColorModel(56, 56, 56)); //$NON-NLS-1$
    COLOR_MAP.put("gray23", new ColorModel(59, 59, 59)); //$NON-NLS-1$
    COLOR_MAP.put("gray24", new ColorModel(61, 61, 61)); //$NON-NLS-1$
    COLOR_MAP.put("gray25", new ColorModel(64, 64, 64)); //$NON-NLS-1$
    COLOR_MAP.put("gray26", new ColorModel(66, 66, 66)); //$NON-NLS-1$
    COLOR_MAP.put("gray27", new ColorModel(69, 69, 69)); //$NON-NLS-1$
    COLOR_MAP.put("gray28", new ColorModel(71, 71, 71)); //$NON-NLS-1$
    COLOR_MAP.put("gray29", new ColorModel(74, 74, 74)); //$NON-NLS-1$
    COLOR_MAP.put("gray3",  new ColorModel(8, 8, 8)); //$NON-NLS-1$
    COLOR_MAP.put("gray30", new ColorModel(77, 77, 77)); //$NON-NLS-1$
    COLOR_MAP.put("gray31", new ColorModel(79, 79, 79)); //$NON-NLS-1$
    COLOR_MAP.put("gray32", new ColorModel(82, 82, 82)); //$NON-NLS-1$
    COLOR_MAP.put("gray33", new ColorModel(84, 84, 84)); //$NON-NLS-1$
    COLOR_MAP.put("gray34", new ColorModel(87, 87, 87)); //$NON-NLS-1$
    COLOR_MAP.put("gray35", new ColorModel(89, 89, 89)); //$NON-NLS-1$
    COLOR_MAP.put("gray36", new ColorModel(92, 92, 92)); //$NON-NLS-1$
    COLOR_MAP.put("gray37", new ColorModel(94, 94, 94)); //$NON-NLS-1$
    COLOR_MAP.put("gray38", new ColorModel(97, 97, 97)); //$NON-NLS-1$
    COLOR_MAP.put("gray39", new ColorModel(99, 99, 99)); //$NON-NLS-1$
    COLOR_MAP.put("gray4",  new ColorModel(10, 10, 10)); //$NON-NLS-1$
    COLOR_MAP.put("gray40", new ColorModel(102, 102, 102)); //$NON-NLS-1$
    COLOR_MAP.put("gray41", new ColorModel(105, 105, 105)); //$NON-NLS-1$
    COLOR_MAP.put("gray42", new ColorModel(107, 107, 107)); //$NON-NLS-1$
    COLOR_MAP.put("gray43", new ColorModel(110, 110, 110)); //$NON-NLS-1$
    COLOR_MAP.put("gray44", new ColorModel(112, 112, 112)); //$NON-NLS-1$
    COLOR_MAP.put("gray45", new ColorModel(115, 115, 115)); //$NON-NLS-1$
    COLOR_MAP.put("gray46", new ColorModel(117, 117, 117)); //$NON-NLS-1$
    COLOR_MAP.put("gray47", new ColorModel(120, 120, 120)); //$NON-NLS-1$
    COLOR_MAP.put("gray48", new ColorModel(122, 122, 122)); //$NON-NLS-1$
    COLOR_MAP.put("gray49", new ColorModel(125, 125, 125)); //$NON-NLS-1$
    COLOR_MAP.put("gray5",  new ColorModel(13, 13, 13)); //$NON-NLS-1$
    COLOR_MAP.put("gray50", new ColorModel(127, 127, 127)); //$NON-NLS-1$
    COLOR_MAP.put("gray51", new ColorModel(130, 130, 130)); //$NON-NLS-1$
    COLOR_MAP.put("gray52", new ColorModel(133, 133, 133)); //$NON-NLS-1$
    COLOR_MAP.put("gray53", new ColorModel(135, 135, 135)); //$NON-NLS-1$
    COLOR_MAP.put("gray54", new ColorModel(138, 138, 138)); //$NON-NLS-1$
    COLOR_MAP.put("gray55", new ColorModel(140, 140, 140)); //$NON-NLS-1$
    COLOR_MAP.put("gray56", new ColorModel(143, 143, 143)); //$NON-NLS-1$
    COLOR_MAP.put("gray57", new ColorModel(145, 145, 145)); //$NON-NLS-1$
    COLOR_MAP.put("gray58", new ColorModel(148, 148, 148)); //$NON-NLS-1$
    COLOR_MAP.put("gray59", new ColorModel(150, 150, 150)); //$NON-NLS-1$
    COLOR_MAP.put("gray6",  new ColorModel(15, 15, 15)); //$NON-NLS-1$
    COLOR_MAP.put("gray60", new ColorModel(153, 153, 153)); //$NON-NLS-1$
    COLOR_MAP.put("gray61", new ColorModel(156, 156, 156)); //$NON-NLS-1$
    COLOR_MAP.put("gray62", new ColorModel(158, 158, 158)); //$NON-NLS-1$
    COLOR_MAP.put("gray63", new ColorModel(161, 161, 161)); //$NON-NLS-1$
    COLOR_MAP.put("gray64", new ColorModel(163, 163, 163)); //$NON-NLS-1$
    COLOR_MAP.put("gray65", new ColorModel(166, 166, 166)); //$NON-NLS-1$
    COLOR_MAP.put("gray66", new ColorModel(168, 168, 168)); //$NON-NLS-1$
    COLOR_MAP.put("gray67", new ColorModel(171, 171, 171)); //$NON-NLS-1$
    COLOR_MAP.put("gray68", new ColorModel(173, 173, 173)); //$NON-NLS-1$
    COLOR_MAP.put("gray69", new ColorModel(176, 176, 176)); //$NON-NLS-1$
    COLOR_MAP.put("gray7",  new ColorModel(18, 18, 18)); //$NON-NLS-1$
    COLOR_MAP.put("gray70", new ColorModel(179, 179, 179)); //$NON-NLS-1$
    COLOR_MAP.put("gray71", new ColorModel(181, 181, 181)); //$NON-NLS-1$
    COLOR_MAP.put("gray72", new ColorModel(184, 184, 184)); //$NON-NLS-1$
    COLOR_MAP.put("gray73", new ColorModel(186, 186, 186)); //$NON-NLS-1$
    COLOR_MAP.put("gray74", new ColorModel(189, 189, 189)); //$NON-NLS-1$
    COLOR_MAP.put("gray75", new ColorModel(191, 191, 191)); //$NON-NLS-1$
    COLOR_MAP.put("gray76", new ColorModel(194, 194, 194)); //$NON-NLS-1$
    COLOR_MAP.put("gray77", new ColorModel(196, 196, 196)); //$NON-NLS-1$
    COLOR_MAP.put("gray78", new ColorModel(199, 199, 199)); //$NON-NLS-1$
    COLOR_MAP.put("gray79", new ColorModel(201, 201, 201)); //$NON-NLS-1$
    COLOR_MAP.put("gray8",  new ColorModel(20, 20, 20)); //$NON-NLS-1$
    COLOR_MAP.put("gray80", new ColorModel(204, 204, 204)); //$NON-NLS-1$
    COLOR_MAP.put("gray81", new ColorModel(207, 207, 207)); //$NON-NLS-1$
    COLOR_MAP.put("gray82", new ColorModel(209, 209, 209)); //$NON-NLS-1$
    COLOR_MAP.put("gray83", new ColorModel(212, 212, 212)); //$NON-NLS-1$
    COLOR_MAP.put("gray84", new ColorModel(214, 214, 214)); //$NON-NLS-1$
    COLOR_MAP.put("gray85", new ColorModel(217, 217, 217)); //$NON-NLS-1$
    COLOR_MAP.put("gray86", new ColorModel(219, 219, 219)); //$NON-NLS-1$
    COLOR_MAP.put("gray87", new ColorModel(222, 222, 222)); //$NON-NLS-1$
    COLOR_MAP.put("gray88", new ColorModel(224, 224, 224)); //$NON-NLS-1$
    COLOR_MAP.put("gray89", new ColorModel(227, 227, 227)); //$NON-NLS-1$
    COLOR_MAP.put("gray9",  new ColorModel(23, 23, 23)); //$NON-NLS-1$
    COLOR_MAP.put("gray90", new ColorModel(229, 229, 229)); //$NON-NLS-1$
    COLOR_MAP.put("gray91", new ColorModel(232, 232, 232)); //$NON-NLS-1$
    COLOR_MAP.put("gray92", new ColorModel(235, 235, 235)); //$NON-NLS-1$
    COLOR_MAP.put("gray93", new ColorModel(237, 237, 237)); //$NON-NLS-1$
    COLOR_MAP.put("gray94", new ColorModel(240, 240, 240)); //$NON-NLS-1$
    COLOR_MAP.put("gray95", new ColorModel(242, 242, 242)); //$NON-NLS-1$
    COLOR_MAP.put("gray96", new ColorModel(245, 245, 245)); //$NON-NLS-1$
    COLOR_MAP.put("gray97", new ColorModel(247, 247, 247)); //$NON-NLS-1$
    COLOR_MAP.put("gray98", new ColorModel(250, 250, 250)); //$NON-NLS-1$
    COLOR_MAP.put("gray99", new ColorModel(252, 252, 252)); //$NON-NLS-1$
    COLOR_MAP.put("green",  new ColorModel(0, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green2", new ColorModel(0, 238, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green3", new ColorModel(0, 205, 0)); //$NON-NLS-1$
    COLOR_MAP.put("green4", new ColorModel(0, 139, 0)); //$NON-NLS-1$
    COLOR_MAP.put("honeydew",   new ColorModel(240, 255, 240)); //$NON-NLS-1$
    COLOR_MAP.put("honeydew2",  new ColorModel(224, 238, 224)); //$NON-NLS-1$
    COLOR_MAP.put("honeydew3",  new ColorModel(193, 205, 193)); //$NON-NLS-1$
    COLOR_MAP.put("honeydew4",  new ColorModel(131, 139, 131)); //$NON-NLS-1$
    COLOR_MAP.put("ivory",  new ColorModel(255, 255, 240)); //$NON-NLS-1$
    COLOR_MAP.put("ivory2", new ColorModel(238, 238, 224)); //$NON-NLS-1$
    COLOR_MAP.put("ivory3", new ColorModel(205, 205, 193)); //$NON-NLS-1$
    COLOR_MAP.put("ivory4", new ColorModel(139, 139, 131)); //$NON-NLS-1$
    COLOR_MAP.put("khaki",  new ColorModel(240, 230, 140)); //$NON-NLS-1$
    COLOR_MAP.put("khaki1", new ColorModel(255, 246, 143)); //$NON-NLS-1$
    COLOR_MAP.put("khaki2", new ColorModel(238, 230, 133)); //$NON-NLS-1$
    COLOR_MAP.put("khaki3", new ColorModel(205, 198, 115)); //$NON-NLS-1$
    COLOR_MAP.put("khaki4", new ColorModel(139, 134, 78)); //$NON-NLS-1$
    COLOR_MAP.put("lavender",   new ColorModel(230, 230, 250)); //$NON-NLS-1$
    COLOR_MAP.put("linen",  new ColorModel(250, 240, 230)); //$NON-NLS-1$
    COLOR_MAP.put("magenta",    new ColorModel(255, 0, 255)); //$NON-NLS-1$
    COLOR_MAP.put("magenta2",   new ColorModel(238, 0, 238)); //$NON-NLS-1$
    COLOR_MAP.put("magenta3",   new ColorModel(205, 0, 205)); //$NON-NLS-1$
    COLOR_MAP.put("magenta4",   new ColorModel(139, 0, 139)); //$NON-NLS-1$
    COLOR_MAP.put("maroon", new ColorModel(176, 48, 96)); //$NON-NLS-1$
    COLOR_MAP.put("maroon1",    new ColorModel(255, 52, 179)); //$NON-NLS-1$
    COLOR_MAP.put("maroon2",    new ColorModel(238, 48, 167)); //$NON-NLS-1$
    COLOR_MAP.put("maroon3",    new ColorModel(205, 41, 144)); //$NON-NLS-1$
    COLOR_MAP.put("maroon4",    new ColorModel(139, 28, 98)); //$NON-NLS-1$
    COLOR_MAP.put("moccasin",   new ColorModel(255, 228, 181)); //$NON-NLS-1$
    COLOR_MAP.put("navy",   new ColorModel(0, 0, 128)); //$NON-NLS-1$
    COLOR_MAP.put("orange", new ColorModel(255, 165, 0)); //$NON-NLS-1$
    COLOR_MAP.put("orange2",    new ColorModel(238, 154, 0)); //$NON-NLS-1$
    COLOR_MAP.put("orange3",    new ColorModel(205, 133, 0)); //$NON-NLS-1$
    COLOR_MAP.put("orange4",    new ColorModel(139, 90, 0)); //$NON-NLS-1$
    COLOR_MAP.put("orchid", new ColorModel(218, 112, 214)); //$NON-NLS-1$
    COLOR_MAP.put("orchid1",    new ColorModel(255, 131, 250)); //$NON-NLS-1$
    COLOR_MAP.put("orchid2",    new ColorModel(238, 122, 233)); //$NON-NLS-1$
    COLOR_MAP.put("orchid3",    new ColorModel(205, 105, 201)); //$NON-NLS-1$
    COLOR_MAP.put("orchid4",    new ColorModel(139, 71, 137)); //$NON-NLS-1$
    COLOR_MAP.put("peru",   new ColorModel(205, 133, 63)); //$NON-NLS-1$
    COLOR_MAP.put("pink",   new ColorModel(255, 192, 203)); //$NON-NLS-1$
    COLOR_MAP.put("pink1",  new ColorModel(255, 181, 197)); //$NON-NLS-1$
    COLOR_MAP.put("pink2",  new ColorModel(238, 169, 184)); //$NON-NLS-1$
    COLOR_MAP.put("pink3",  new ColorModel(205, 145, 158)); //$NON-NLS-1$
    COLOR_MAP.put("pink4",  new ColorModel(139, 99, 108)); //$NON-NLS-1$
    COLOR_MAP.put("plum",   new ColorModel(221, 160, 221)); //$NON-NLS-1$
    COLOR_MAP.put("plum1",  new ColorModel(255, 187, 255)); //$NON-NLS-1$
    COLOR_MAP.put("plum2",  new ColorModel(238, 174, 238)); //$NON-NLS-1$
    COLOR_MAP.put("plum3",  new ColorModel(205, 150, 205)); //$NON-NLS-1$
    COLOR_MAP.put("plum4",  new ColorModel(139, 102, 139)); //$NON-NLS-1$
    COLOR_MAP.put("purple", new ColorModel(160, 32, 240)); //$NON-NLS-1$
    COLOR_MAP.put("purple1",    new ColorModel(155, 48, 255)); //$NON-NLS-1$
    COLOR_MAP.put("purple2",    new ColorModel(145, 44, 238)); //$NON-NLS-1$
    COLOR_MAP.put("purple3",    new ColorModel(125, 38, 205)); //$NON-NLS-1$
    COLOR_MAP.put("purple4",    new ColorModel(85, 26, 139)); //$NON-NLS-1$
    COLOR_MAP.put("red",    new ColorModel(255, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red2",   new ColorModel(238, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red3",   new ColorModel(205, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("red4",   new ColorModel(139, 0, 0)); //$NON-NLS-1$
    COLOR_MAP.put("salmon", new ColorModel(250, 128, 114)); //$NON-NLS-1$
    COLOR_MAP.put("salmon1",    new ColorModel(255, 140, 105)); //$NON-NLS-1$
    COLOR_MAP.put("salmon2",    new ColorModel(238, 130, 98)); //$NON-NLS-1$
    COLOR_MAP.put("salmon3",    new ColorModel(205, 112, 84)); //$NON-NLS-1$
    COLOR_MAP.put("salmon4",    new ColorModel(139, 76, 57)); //$NON-NLS-1$
    COLOR_MAP.put("seashell",   new ColorModel(255, 245, 238)); //$NON-NLS-1$
    COLOR_MAP.put("seashell1",  new ColorModel(255, 245, 238)); //$NON-NLS-1$
    COLOR_MAP.put("seashell2",  new ColorModel(238, 229, 222)); //$NON-NLS-1$
    COLOR_MAP.put("seashell3",  new ColorModel(205, 197, 191)); //$NON-NLS-1$
    COLOR_MAP.put("seashell4",  new ColorModel(139, 134, 130)); //$NON-NLS-1$
    COLOR_MAP.put("sienna", new ColorModel(160, 82, 45)); //$NON-NLS-1$
    COLOR_MAP.put("sienna1",    new ColorModel(255, 130, 71)); //$NON-NLS-1$
    COLOR_MAP.put("sienna2",    new ColorModel(238, 121, 66)); //$NON-NLS-1$
    COLOR_MAP.put("sienna3",    new ColorModel(205, 104, 57)); //$NON-NLS-1$
    COLOR_MAP.put("sienna4",    new ColorModel(139, 71, 38)); //$NON-NLS-1$
    COLOR_MAP.put("snow",   new ColorModel(255, 250, 250)); //$NON-NLS-1$
    COLOR_MAP.put("snow1",  new ColorModel(255, 250, 250)); //$NON-NLS-1$
    COLOR_MAP.put("snow2",  new ColorModel(238, 233, 233)); //$NON-NLS-1$
    COLOR_MAP.put("snow3",  new ColorModel(205, 201, 201)); //$NON-NLS-1$
    COLOR_MAP.put("snow4",  new ColorModel(139, 137, 137)); //$NON-NLS-1$
    COLOR_MAP.put("tan",    new ColorModel(210, 180, 140)); //$NON-NLS-1$
    COLOR_MAP.put("tan1",   new ColorModel(255, 165, 79)); //$NON-NLS-1$
    COLOR_MAP.put("tan2",   new ColorModel(238, 154, 73)); //$NON-NLS-1$
    COLOR_MAP.put("tan3",   new ColorModel(205, 133, 63)); //$NON-NLS-1$
    COLOR_MAP.put("tan4",   new ColorModel(139, 90, 43)); //$NON-NLS-1$
    COLOR_MAP.put("thistle",    new ColorModel(216, 191, 216)); //$NON-NLS-1$
    COLOR_MAP.put("thistle1",   new ColorModel(255, 225, 255)); //$NON-NLS-1$
    COLOR_MAP.put("thistle2",   new ColorModel(238, 210, 238)); //$NON-NLS-1$
    COLOR_MAP.put("thistle3",   new ColorModel(205, 181, 205)); //$NON-NLS-1$
    COLOR_MAP.put("thistle4",   new ColorModel(139, 123, 139)); //$NON-NLS-1$
    COLOR_MAP.put("tomato", new ColorModel(255, 99, 71)); //$NON-NLS-1$
    COLOR_MAP.put("tomato1",    new ColorModel(255, 99, 71)); //$NON-NLS-1$
    COLOR_MAP.put("tomato2",    new ColorModel(238, 92, 66)); //$NON-NLS-1$
    COLOR_MAP.put("tomato3",    new ColorModel(205, 79, 57)); //$NON-NLS-1$
    COLOR_MAP.put("tomato4",    new ColorModel(139, 54, 38)); //$NON-NLS-1$
    COLOR_MAP.put("turquoise",  new ColorModel(64, 224, 208)); //$NON-NLS-1$
    COLOR_MAP.put("turquoise1", new ColorModel(0, 245, 255)); //$NON-NLS-1$
    COLOR_MAP.put("turquoise2", new ColorModel(0, 229, 238)); //$NON-NLS-1$
    COLOR_MAP.put("turquoise3", new ColorModel(0, 197, 205)); //$NON-NLS-1$
    COLOR_MAP.put("turquoise4", new ColorModel(0, 134, 139)); //$NON-NLS-1$
    COLOR_MAP.put("violet", new ColorModel(238, 130, 238)); //$NON-NLS-1$
    COLOR_MAP.put("wheat",  new ColorModel(245, 222, 179)); //$NON-NLS-1$
    COLOR_MAP.put("wheat1", new ColorModel(255, 231, 186)); //$NON-NLS-1$
    COLOR_MAP.put("wheat2", new ColorModel(238, 216, 174)); //$NON-NLS-1$
    COLOR_MAP.put("wheat3", new ColorModel(205, 186, 150)); //$NON-NLS-1$
    COLOR_MAP.put("wheat4", new ColorModel(139, 126, 102)); //$NON-NLS-1$
    COLOR_MAP.put("white",  new ColorModel(255, 255, 255)); //$NON-NLS-1$
    COLOR_MAP.put("yellow", new ColorModel(255, 255, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow2",    new ColorModel(238, 238, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow3",    new ColorModel(205, 205, 0)); //$NON-NLS-1$
    COLOR_MAP.put("yellow4",    new ColorModel(139, 139, 0)); //$NON-NLS-1$
  }

  /**
   * 名前で指定した色を返します。
   * 
   * @param colorName 色の名前
   * @return 名前で指定された色
   */
  public static ColorModel getColor(String colorName) {
    if (COLOR_MAP.containsKey(colorName)) {
      return COLOR_MAP.get(colorName);
    }
    return new ColorModel(128, 128, 128);
  }
  
  /**
   * 色が登録されているか判定します。
   * @param color 色
   * @return 色が登録されていればtrue
   */
  public static boolean contain(ColorModel color) {
    return COLOR_MAP.containsValue(color);
  }
  
  /**
   * 色が登録されているか判定します。
   * @param name 色の名前
   * @return 色が登録されていればtrue
   */
  public static boolean contain(String name) {
    return COLOR_MAP.containsKey(name);
  }
  
  /**
   * 色に対応する色の名前を返します。
   * 
   * @param color 色
   * @return 色の名前
   */
  public static String getColorName(ColorModel color) {
    for (Entry<String,ColorModel> entry : COLOR_MAP.entrySet()) {
      if (entry.getValue().equals(color)) {
        return entry.getKey();
      }
    }

    return null;
  }
}
