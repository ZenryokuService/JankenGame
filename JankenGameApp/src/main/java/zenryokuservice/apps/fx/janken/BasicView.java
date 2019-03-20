package zenryokuservice.apps.fx.janken;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BasicView extends View {

    public BasicView() {
    	// 縦のレイアウト
    	VBox layout = new VBox(300);
    	layout.setAlignment(Pos.CENTER);
    	
    	// タイトル文言
    	layout.getChildren().add(this.getTitleNode());
    	// スタートボタン
    	Button start = new Button("スタート");
    	start.setOnAction(event -> setCenter(new TestingView()));
    	layout.getChildren().add(start);
        // 画面に登録
        Group gp = new Group(layout);
        setCenter(gp);
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
//        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> System.out.println("Menu")));
//        appBar.setTitleText("Basic View");
//        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }

    /**
     * 画面のタイトルを作成します。
     * @return タイトル部分のノード
     */
    private HBox getTitleNode() {
    	HBox hbox = new HBox(20);
    	Text jan = new Text("じゃん");
    	this.settingFont(jan, Color.BLUE);
    	hbox.getChildren().add(jan);

    	Text ken = new Text("けん");
    	this.settingFont(ken, Color.YELLOW);
    	hbox.getChildren().add(ken);

    	Text game = new Text("ゲーム");
    	this.settingFont(game, Color.RED);
    	hbox.getChildren().add(game);
    	return hbox;
    }

    /**
     * テキストの設定を行います。
     * @param tx 表示するテキスト
     * @param color 設定する色
     */
    private void settingFont(Text tx, Color color) {
    	double width = MobileApplication.getInstance().getGlassPane().getWidth();
    	tx.setFont(new Font(width / 10));
    	tx.setFill(color);
    	tx.setStroke(Color.BLACK);
    	tx.setStrokeWidth(1.0);
    }
}
