package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import Model.CacChau;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Model.ConnectionDatabase;
import Model.KiemKe;

public class ControllerChart extends Application implements Initializable {
	@FXML
	private BarChart<?, ?> barchart;
	@FXML
	private CategoryAxis caxis;
	@FXML
	private NumberAxis naxis;
	@FXML
	JFXComboBox<String> combobox1, combobox2, combobox3;
	ObservableList<CacChau> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loaddata();
		combobox1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				check();
			}
		});
	}

	public void loaddata() {
		String[] s = { "Bảng Các Cháu", "Bảng Phát Thưởng", "Bảng Quỹ" };
		combobox1.getItems().addAll(s);
	}

	public void Submit() throws SQLException {
		barchart.getData().clear();
		if (combobox2.getValue() != null && combobox3.getValue() != null) {
			caxis.setLabel(combobox2.getValue());
			naxis.setLabel(combobox3.getValue());
		}
		if (combobox2.getValue().equals("Thành Tích")) {
			Hocsinhgioi();
		}
		if (combobox2.getValue().equals("Giới Tính")) {
			GioiTinh();
		}
		if (combobox2.getValue().equals("Dịp Phát Thưởng")) {
			PhatThuong();
		}
		if (combobox2.getValue().equals("Số tiền ban đầu") && combobox3.getValue().equals("Số tiền hiện còn")) {
			Quy1();
		}
		if (combobox2.getValue().equals("Số tiền phát thưởng")) {
			combobox3.getItems().clear();
			combobox3.getItems().add("Số Lượng");
			combobox3.setValue("Số Lượng");
			TienChi();
		}
		if (combobox2.getValue().equals("Tên")) {
			combobox3.getItems().clear();
			combobox3.getItems().add("Tổng Giá Trị Phần Thưởng");
			combobox3.setValue("Tổng Giá Trị Phần Thưởng");
			MoiChau();
		}
	}

	private void check() {
		String s1[] = { "Thành Tích", "Giới Tính", "Tên" };
		String s2[] = { "Số tiền phát thưởng", "Số tiền hiện còn", "Số tiền ban đầu" };
		if (combobox1.getValue().equals("Bảng Các Cháu")) {
			combobox2.getItems().clear();
			combobox2.getItems().addAll(s1);
			combobox3.getItems().clear();
			combobox3.getItems().add("Số Lượng");
			combobox3.setValue("Số Lượng");
		}
		if (combobox1.getValue().equals("Bảng Quỹ")) {
			combobox3.getItems().clear();
			combobox3.getItems().addAll(s2);
			combobox2.getItems().clear();
			combobox2.getItems().addAll(s2);
		}
		if (combobox1.getValue().equals("Bảng Phát Thưởng")) {
			combobox2.getItems().clear();
			combobox2.getItems().add("Dịp Phát Thưởng");
			combobox2.setValue("Dịp Phát Thưởng");
			combobox3.getItems().clear();
			combobox3.getItems().add("Số Phần Quà");
			combobox3.setValue("Số Phần Quà");
		}
	}

	public void Quy1() throws SQLException {
		TableViewQuy a = new TableViewQuy();
		ObservableList<KiemKe> list1 = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		XYChart.Series set2 = new XYChart.Series<>();
		for (KiemKe i : list1) {
			set1.getData().add(new XYChart.Data(i.getNgayThayDoi(), i.getQuyTruoc()));
			set2.getData().add(new XYChart.Data(i.getNgayThayDoi(), i.getQuySau()));
		}
		barchart.getData().addAll(set1, set2);
	}

	public void Hocsinhgioi() throws SQLException {
		TableViewCacChau a = new TableViewCacChau();
		list = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		for (CacChau i : list) {
			set1.getData().add(new XYChart.Data(i.getThanhTich(), Counths(i.getThanhTich())));
		}
		barchart.getData().addAll(set1);
	}

	public void GioiTinh() throws SQLException {
		TableViewCacChau a = new TableViewCacChau();
		list = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		for (CacChau i : list) {
			set1.getData().add(new XYChart.Data(i.getGioiTinh(), Countgioitinh(i.getGioiTinh())));
		}
		barchart.getData().addAll(set1);
	}

	public void PhatThuong() throws SQLException {
		TableViewPhatThuong a = new TableViewPhatThuong();
		ObservableList<Model.PhatThuong> list1 = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		for (Model.PhatThuong i : list1) {
			set1.getData().add(new XYChart.Data(i.getThongTinPhatThuong(), Countphatthuong(i.getIDPhatThuong())));
		}
		barchart.getData().addAll(set1);
	}

	public void TienChi() throws SQLException {
		TableViewPhatThuong a = new TableViewPhatThuong();
		ObservableList<Model.PhatThuong> list1 = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		for (Model.PhatThuong i : list1) {
			set1.getData().add(new XYChart.Data(i.getThongTinPhatThuong(), Counttien(i.getIDPhatThuong())));
		}
		barchart.getData().addAll(set1);
	}

	public void MoiChau() throws SQLException {
		TableViewPhatThuongChiTiet a = new TableViewPhatThuongChiTiet();
		ObservableList<Model.PhatThuongChiTiet> list1 = a.Update();
		XYChart.Series set1 = new XYChart.Series<>();
		for (Model.PhatThuongChiTiet i : list1) {
			set1.getData().add(new XYChart.Data(i.getHoTen(), Countgiatri(i.getHoTen())));
		}
		barchart.getData().addAll(set1);
	}

	public int Countgiatri(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("Select SUM(PhanThuong) from PhatThuong_Dip where HoTen = N'" + s + "'");
		rs.next();
		return rs.getInt(1);
	}

	public int Countphatthuong(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat
				.executeQuery("Select count(IDPhatThuong) AS total from PhatThuong_Dip where IDPhatThuong = N'" + s + "'");
		rs.next();
		return rs.getInt("total");
	}

	public int Counths(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("Select count(*) AS total from CacChau where ThanhTich = N'" + s + "'");
		rs.next();
		return rs.getInt("total");
	}

	public int Countgioitinh(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("Select count(*) AS total from CacChau where GioiTinh = N'" + s + "'");
		rs.next();
		return rs.getInt("total");
	}

	public int Counttien(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat
				.executeQuery("Select SUM(PhanThuong) AS total from PhatThuong_Dip where IDPhatThuong = N'" + s + "'");
		rs.next();
		return rs.getInt("total");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/View/Thongke.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
