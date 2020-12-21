package Controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import View.Main;
public class MainController {
	@FXML
	private Label label1;
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
	public void QuanLy() {
		panehello.setVisible(false);
		panephatthuong.setVisible(false);
		panequanly.setVisible(true);
		panethongke.setVisible(false);
		panecaidat.setVisible(false);
	}
	public void CaiDat() {
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
	public void Logout() throws IOException {
		Main a = new Main();
		a.start(a.window);
	}
}