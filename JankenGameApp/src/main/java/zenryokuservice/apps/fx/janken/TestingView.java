/**
 * Copyright (c) 2019-present Math Kit JavaFX Library All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name Math Kit JavaFX Library nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 */
package zenryokuservice.apps.fx.janken;

import com.gluonhq.charm.glisten.mvc.View;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * JavaFXでの3Dモデルの描画(作成？)、とりあえず写経する。
 * 
 * @author takunoji
 * @see https://docs.oracle.com/javase/jp/8/javafx/graphics-tutorial/overview-3d.htm#CJAHFAHJ
 */
public class TestingView extends View {
	public Translate t = new Translate();
	public Translate p = new Translate();
	public Translate ip = new Translate();
	public Rotate rx = new Rotate();
	{ rx.setAxis(Rotate.X_AXIS);}

	public Rotate ry = new Rotate();
	{ ry.setAxis(Rotate.Y_AXIS);}

	public Rotate rz = new Rotate();
	{rz.setAxis(Rotate.Z_AXIS);}

	public TestingView() {
		Sphere sp = new Sphere(30.0);
		setCenter(sp);
	}
}
