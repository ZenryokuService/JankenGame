/**
 * Copyright (c) 2019-present Math Kit JavaFX Library All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name Math Kit JavaFX Library nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package zenryokuservice.apps.fx.janken.util;

import java.util.Random;

import javafx.scene.text.Text;

/**
 * @author takunoji
 *
 * 2019/03/23
 */
public class Judgement {
	///// 定数 /////
	/** じゃんけんぽん */
	private final String[] t = new String[] {"Jan!", "Ken", "POM!"};
	/** じゃんけんぽん(アイコ) */
	private final String[] a = new String[] {"Ai!", "KoDe", "Show!"};
	/** 勝敗判定文字列 */
	private final String[] hanteStr = new String[] {"YouWin", "YouLoose", "Aiko"};
	/** じゃんけんの判定：ユーザーの勝ち */
	private static final int YOU_WIN = 0;
	/** じゃんけんの判定：CPUの勝ち */
	private static final int YOU_LOOSE = 1;
	/** じゃんけんの判定：あいこ */
	private static final int AIKO = 2;
	/** じゃんけんの勝敗判定: それ以外 */
	private static final int OTHER = 3;
	///// Utility /////
	/** 乱数用クラス */
	private final Random rdn = new  Random();
	/// じゃんけん判定用変数 /// 
	/** ユーザーの手 */
	private int userTe;
	/** CPUの手 */
	private int cpuTe;
	/// じゃんけんの判定フラグ/// 
	/** 早出し */
	private boolean isFirstOut;
	/** 遅だし */
	private boolean isLate;
	/** 勝負済み */
	private boolean isFinish;

	/**
	 * ユーザーの手を取得。
	 * @return ユーザーの手
	 */
	public int getUserTe() {
		return userTe;
	}

	/**
	 * ユーザーの手を設定。
	 * @param userTe
	 */
	public void setUserTe(int userTe) {
		this.userTe = userTe;
	}

	/**
	 * CPUの手を取得。
	 * @return CPUの手
	 */
	public int getCpuTe() {
		return rdn.nextInt(2);
	}

	/**
	 * CPUの手を設定。
	 * @param cpuTe
	 */
	public void setCpuTe(int cpuTe) {
		this.cpuTe = cpuTe;
	}

	
	public boolean isFirstOut() {
		return isFirstOut;
	}

	public void setFirstOut(boolean isFirstOut) {
		this.isFirstOut = isFirstOut;
	}

	/**
	 * 遅だしフラグを取得する。
	 * @return 遅だしフラグ
	 */
	public boolean isLate() {
		return isLate;
	}

	/**
	 * 遅だしフラグを設定する。
	 * @param isLate
	 */
	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}

	/**
	 * 決着済みフラグを取得する。
	 * @return isFinish
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * 決着済みフラグを設定する。
	 * @param isFinish
	 */
	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	/**
	 * 判定文字列を取得する
	 * @param 判定結果
	 * @return 判定結果の文字列
	 */
	public String getHanteStr(int hantei) {
		return hanteStr[hantei];
	}

	/**
	 * 画面の上部表示する「じゃんけんぽん」を取得する。
	 * @param count じゃんけんカウンター
	 * @return 対象の文字列「じゃん」など
	 */
	public String getJanKenPon(int count) {
		return t[count];
	}

	/**
	 * じゃんけんの勝敗判定を行う。
	 * @return 0: ユーザーの勝利 1: CPUの勝利　2: あいこ
	 */
	public int shobu(Text ready) {
		if (userTe == -1) {
			isLate = true;
			ready.setText("おそだし！");
			isFinish = true;
			return YOU_LOOSE;
		}
		if (isFirstOut) {
			ready.setText("はやだし！");
			return YOU_LOOSE;
		}
		int hante = userTe + cpuTe;
		if (hante == 0 || hante == 4 || hante == 8) {
			hante = AIKO;
		} else if (hante == 2 || hante == 3 || hante == 7) {
			hante = YOU_WIN;
		} else {
			hante = YOU_LOOSE;
		}
		if (isFirstOut == false && isLate == false && isFinish) {
			ready.setText(hanteStr[hante]);
		}
		return hante;
	}
}
