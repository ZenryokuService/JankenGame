/**
 * Copyright (c) 2019-present Math Kit JavaFX Library All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name Math Kit JavaFX Library nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package zenryokuservice.apps.fx.janken;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;

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

/**
 * JavaFXでの3Dモデルの描画(作成？)、とりあえず写経する。
 * 
 * @author takunoji
 * @see https://docs.oracle.com/javase/jp/8/javafx/graphics-tutorial/overview-3d.htm#CJAHFAHJ
 */
public class TestingView extends View {
	public TestingView() {
		double height = MobileApplication.getInstance().getScreenHeight();
		VBox gp = new VBox();
		// Rready
		Text ready = new Text();
		ready.setText("READY?");
		ready.setFont(new Font(45));
		ready.setTextAlignment(TextAlignment.CENTER);
		HBox topLine = new HBox();
		topLine.setAlignment(Pos.CENTER);
		topLine.setMinHeight(height * 0.2);
		topLine.getChildren().add(ready);
		gp.getChildren().add(topLine);

		// じゃんけんの手
		final HBox midLine = new HBox();
		midLine.setMinHeight(height * 0.34);
		final Canvas canv = new Canvas();
		midLine.getChildren().add(canv);
		gp.getChildren().add(midLine);
		// ボタン
		final GraphicsContext ctx = canv.getGraphicsContext2D();
		Button btn = new Button("バトル");
		btn.setOnAction(evt -> {
			startJanken(ctx, midLine);
		});
		HBox bottomLine = new HBox();
		bottomLine.setAlignment(Pos.CENTER);
		bottomLine.setMinHeight(height * 0.3);
		bottomLine.getChildren().add(btn);
		gp.getChildren().add(bottomLine);
		setCenter(gp);
	}

	/**
	 * じゃんけんなどのアニメーションを作成する。
	 * 
	 * @param ctx 描画コンテキスト
	 */
	private void startJanken(GraphicsContext ctx, HBox midLine) {
		Image img = new Image("leftグー.png");
		ImageView you = new ImageView();
		you.setImage(img);
		midLine.getChildren().add(you);

		midLine.getChildren().add(new Text("vs"));
		
		Image img2 = new Image("rightグー.png");
		ImageView cpu = new ImageView();
		cpu.setImage(img2);
		midLine.getChildren().add(cpu);
	}
}
