package Controller;

import java.io.IOException;
import View.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainController {
	@FXML
	private Label label1, labeluser;
	@FXML
	private JFXButton btnphatthuong, btnquanly, btncaidat, btnthongke;
	@FXML
	private Pane panephatthuong, panequanly, panecaidat, panethongke, panehello;
	@FXML
	private TableViewCacChau tableyoung;
	@FXML
	private TableViewQuy viewquy;
	@FXML
	private TableViewPhatThuongChiTiet viewchitiet;
	@FXML
	private TableViewPhatThuong viewphatthuong;
	@FXML
	private TableViewHoGiaDinh viewhogiadinh;
	@FXML
	JFXComboBox<String> combo1, combo2;

	public void PhatThuong() {
		panehello.setVisible(false);
		panephatthuong.setVisible(true);
		panequanly.setVisible(false);
		panethongke.setVisible(false);
		panecaidat.setVisible(false);
	}

	public void setname(String s) {
		label1.setText(s);
	}

	public void settext(String s) {
		labeluser.setText(s);
	}

	public void QuanLy() {
		panehello.setVisible(false);
		panephatthuong.setVisible(false);
		panequanly.setVisible(true);
		panethongke.setVisible(false);
		panecaidat.setVisible(false);
	}

	public void CaiDat() {
		loaddata();
		panehello.setVisible(false);
		panephatthuong.setVisible(false);
		panequanly.setVisible(false);
		panethongke.setVisible(false);
		panecaidat.setVisible(true);
	}

	public void ThongKe() {
		panehello.setVisible(false);
		panephatthuong.setVisible(false);
		panequanly.setVisible(false);
		panethongke.setVisible(true);
		panecaidat.setVisible(false);
	}

	public void loaddata() {
		String[] s1 = { "Vietnamese", "Chinese", "English", "French" };
		String[] s2 = { "Blue", "Red", "White", "Green" };
		combo1.getItems().clear();
		combo2.getItems().clear();
		combo1.getItems().addAll(s1);
		combo1.setValue("Vietnamese");
		combo2.getItems().addAll(s2);
		combo2.setValue("White");
	}

	public void Logout() throws IOException {
		Main a = new Main();
		a.start(a.window);
	}
}