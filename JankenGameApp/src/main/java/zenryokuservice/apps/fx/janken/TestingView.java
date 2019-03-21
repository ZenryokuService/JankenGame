/**
 * Copyright (c) 2019-present Math Kit JavaFX Library All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name Math Kit JavaFX Library nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package zenryokuservice.apps.fx.janken;

import java.util.Random;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.sun.prism.paint.Color;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 * JavaFXでの3Dモデルの描画(作成？)、とりあえず写経する。
 * 
 * @author takunoji
 * @see https://docs.oracle.com/javase/jp/8/javafx/graphics-tutorial/overview-3d.htm#CJAHFAHJ
 */
public class TestingView extends View {
	/** じゃんけんぽん */
	private final String[] t = new String[] {"Jan!", "Ken", "POM!"};
	/** じゃんけんぽん(アイコ) */
	private final String[] a = new String[] {"Ai!", "KoDe", "Show!"};
	/** 勝敗判定文字列 */
	private final String[] hanteStr = new String[] {"YouWin", "YouLoose", "Aiko"};
	/** テキストの色 */
	private final Color[] color = {Color.RED, Color.BLUE, Color.GREEN};
	/** 乱数用クラス */
	private final Random rdn = new  Random();
	/** じゃんけんの判定：ユーザーの勝ち */
	private static final int YOU_WIN = 0;
	/** じゃんけんの判定：CPUの勝ち */
	private static final int YOU_LOOSE = 1;
	/** じゃんけんの判定：あいこ */
	private static final int AIKO = 2;
	/** じゃんけんの勝敗判定: それ以外 */
	private static final int OTHER = 3;

	/** main timeline */
    private Timeline timeline;
    private AnimationTimer timer;
    /** タイマー確認用カウンタ */
    private Integer i = new Integer(0);
    /** 文字表示用カウンタ */ 
	private Integer c = new Integer(0);
	/** ユーザーの手 */
	private int userTe;
	/** CPUの手 */
	private int cpuTe;
	

	/** コンストラクタ */
	public TestingView() {
		double height = MobileApplication.getInstance().getScreenHeight();
		VBox gp = new VBox();
		// Rreadyの文言
		Text ready = new Text();
		ready.setText("READY?");
		ready.setFont(new Font(45));
		ready.setTextAlignment(TextAlignment.CENTER);
		// 一番上の横一列レイアウト
		HBox topLine = new HBox();
		topLine.setAlignment(Pos.CENTER);
		topLine.setMinHeight(height * 0.2);
		topLine.getChildren().add(ready);
		gp.getChildren().add(topLine);
	
		// じゃんけんの手を表示するための真ん中の横一列レイアウト
		final HBox midLine = new HBox();
		midLine.setMinHeight(height * 0.20);
		final Canvas canv = new Canvas();
		midLine.getChildren().add(canv);
		gp.getChildren().add(midLine);

		// 描画用のグラフィックスコンテキスト
		final GraphicsContext ctx = canv.getGraphicsContext2D();
		// じゃんけん開始ボタン
		Button btn = new Button("勝負！");
		btn.setAlignment(Pos.CENTER);
		HBox btnLine = new HBox();
		btnLine.setAlignment(Pos.CENTER);
		btnLine.getChildren().add(btn);
		gp.getChildren().add(btnLine);
		// ボタンを表示するための横一列レイアウト
		HBox bottomLine = new HBox();
		bottomLine.setAlignment(Pos.CENTER);
		bottomLine.setMinHeight(height * 0.20);
		// ぐー
		Button goo = new Button("Rock");
		goo.setAlignment(Pos.BASELINE_LEFT);
		bottomLine.getChildren().add(goo);
		// ぐーアクション
		goo.setOnAction(evt -> {
			midLine.getChildren().clear();
			Image img = new Image("leftグー.png");
			ImageView you = new ImageView();
			you.setImage(img);
			midLine.getChildren().add(you);
			userTe = 0;
		});
		// チョキ
		Button chi = new Button("Scissors");
		chi.setAlignment(Pos.CENTER);
		bottomLine.getChildren().add(chi);
		// チョキアクション
		chi.setOnAction(evt -> {
			midLine.getChildren().clear();
			Image img = new Image("leftチョキ.png");
			ImageView you = new ImageView();
			you.setImage(img);
			midLine.getChildren().add(you);
			userTe = 1;
		});
		// パー
		Button pa = new Button("Paper");
		pa.setAlignment(Pos.BASELINE_RIGHT);
		bottomLine.getChildren().add(pa);
		// パーアクション
		pa.setOnAction(evt -> {
			midLine.getChildren().clear();
			Image img = new Image("leftパー.png");
			ImageView you = new ImageView();
			you.setImage(img);
			midLine.getChildren().add(you);
			userTe = 2;
		});
		// 非表示に設定
		goo.setVisible(false);
		chi.setVisible(false);
		pa.setVisible(false);
		gp.getChildren().add(bottomLine);
		setCenter(gp);
		// アクション(勝負ボタン)
		btn.setOnAction(evt -> {
			// じゃんけんの手を初期化
			userTe = -1;
			cpuTe = -1;
			// 勝負ボタンの非表示
			btn.setVisible(false);
			// じゃんけんの手を表示
			goo.setVisible(true);
			chi.setVisible(true);
			pa.setVisible(true);
			// じゃんけんアニメーション
			startJanken(ctx, midLine, ready);
		});
	}

	/**
	 * じゃんけんなどのアニメーションを作成する。
	 * 
	 * @param ctx 描画コンテキスト
	 */
	private void startJanken(GraphicsContext ctx, HBox midLine, Text ready) {
		Image img = new Image("leftグー.png");
		ImageView you = new ImageView();
		you.setImage(img);
		midLine.getChildren().add(you);

		midLine.getChildren().add(new Text(" vs "));
		
		Image img2 = new Image("rightグー.png");
		ImageView cpu = new ImageView();
		cpu.setImage(img2);
		RotateTransition rotateTransition = 
				 new RotateTransition(Duration.millis(500), you);
		rotateTransition.setAxis(new Point3D(0, 10, 0));
		rotateTransition.setByAngle(360f);
		rotateTransition.setInterpolator(Interpolator.TANGENT(Duration.millis(100), 10000.0));
		rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
		rotateTransition.play();

		RotateTransition rotateTransition2 = 
				 new RotateTransition(Duration.millis(500), cpu);
		rotateTransition2.setAxis(new Point3D(0, 10, 0));
		rotateTransition2.setByAngle(360f);
		rotateTransition2.setInterpolator(Interpolator.TANGENT(Duration.millis(100), 10000.0));
		rotateTransition2.setCycleCount(RotateTransition.INDEFINITE);
		rotateTransition2.play();

		midLine.getChildren().add(cpu);
		midLine.setAlignment(Pos.CENTER);

		// カウンタ初期化
		i = 0;
		c = 0;

		// タイマー処理
		timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		timer = new AnimationTimer() {
			@Override
			public void handle(long time) {
				if (i % 50 == 0 && c < 3) {
					ready.setText(t[c]);
					c++;
				}
				if (c >= 3) {
					timer.stop();
					rotateTransition.stop();
					rotateTransition2.stop();
					midLine.getChildren().add(getCpuNoTe());
					shobu(ready);
				}
				i++;
			}
		};
		timeline.play();
		timer.start();
	}

	/**
	 * CPUのじゃんけんの手を返却します。
	 * @return ImageView じゃんけんの手
	 */
	private ImageView getCpuNoTe() {
		Image[] imgHako = {new Image("rightグー.png"), new Image("rightチョキ.png"), new Image("rightパー.png")};
		ImageView cpu = new ImageView();
		// 0-2までの乱数
		int te = rdn.nextInt(2);
		cpu.setImage(imgHako[te]);
		cpuTe = te;
		return cpu;
	}

	/**
	 * じゃんけんの勝敗判定を行う。
	 * @return 0: ユーザーの勝利 1: CPUの勝利　2: あいこ
	 */
	private int shobu(Text ready) {
		int aikoHante = cpuTe == userTe ? AIKO : OTHER;
		if (aikoHante == AIKO) {
			return AIKO;
		}
		int hante = userTe + cpuTe;
		if (hante == 0 || hante == 4 || hante == 8) {
			hante = AIKO;
		} else if (hante == 2 || hante == 3 || hante == 7) {
			hante = YOU_WIN;
		} else {
			hante = YOU_LOOSE;
		}
		System.out.println("*** 判定 ***");
		System.out.println("user: " + userTe + " / cpu: " + cpuTe);
		System.out.println(hanteStr[hante]);
		ready.setText(hanteStr[hante]);
		return hante;
	}
}
