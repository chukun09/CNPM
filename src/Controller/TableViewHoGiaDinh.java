package Controller;

import java.net.*;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXTextField;
import Model.HoGiaDinh;
import Model.ConnectionDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TableViewHoGiaDinh implements Initializable {
	@FXML
	JFXTextField txt_find;
	@FXML
	TextField txtidhogiadinh, txtmahokhau, txttenchuho, txtcmndchuho, txtsothanhvien, txtdiachi;
	@FXML
	private TableView<HoGiaDinh> tablehogiadinh;
	@FXML
	private TableColumn<HoGiaDinh, Integer> IDHoGiaDinh;
	@FXML
	private TableColumn<HoGiaDinh, String> MaHoKhau;
	@FXML
	private TableColumn<HoGiaDinh, String> TenChuHo;
	@FXML
	private TableColumn<HoGiaDinh, Integer> CMNDChuHo;
	@FXML
	private TableColumn<HoGiaDinh, Integer> SoThanhVien;
	@FXML
	private TableColumn<HoGiaDinh, String> DiaChi;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public void UpdateTable() throws SQLException {
		IDHoGiaDinh.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("IDHoGiaDinh"));
		MaHoKhau.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("MaHoKhau"));
		TenChuHo.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("TenChuHo"));
		CMNDChuHo.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("CMNDChuHo"));
		SoThanhVien.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("SoThanhVien"));
		DiaChi.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("DiaChi"));
		ObservableList<HoGiaDinh> list = Update();
		tablehogiadinh.setItems(list);
	}

	public void Add() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		if (txtidhogiadinh.getText().trim().equals("") || txtmahokhau.getText().trim().equals("")
				|| txttenchuho.getText().trim().equals("") || txtcmndchuho.getText().trim().equals("")
				|| txtsothanhvien.getText().trim().equals("") || txtdiachi.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập thiếu thông tin vui lòng kiểm tra lại !");
			alert.showAndWait();
		} else {
			try {
				String sql = "Insert into HoKhau (IDHoGiaDinh, MaHoKhau, TenChuHo, CMNDChuHo, SoThanhVien, DiaChi) values ("
						+ Integer.parseInt(txtidhogiadinh.getText()) + ", N'" + txtmahokhau.getText() + "', N'"
						+ txttenchuho.getText() + "', " + Integer.parseInt(txtcmndchuho.getText()) + ", "
						+ txtsothanhvien.getText() + ", N'" + txtdiachi.getText() + "'";
				stat.executeUpdate(sql);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thông Báo");
				alert.setHeaderText(null);
				alert.setContentText("Nhập dữ liệu không hợp lệ !");
				alert.showAndWait();
				return;
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Thêm Thông Tin thành công !");
			alert.showAndWait();
			UpdateTable();
			search();
		}
	}

	public ObservableList<HoGiaDinh> Update() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		ObservableList<HoGiaDinh> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from HoKhau");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new HoGiaDinh(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) {
		index = tablehogiadinh.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		txtidhogiadinh.setText(IDHoGiaDinh.getCellData(index).toString());
		txtmahokhau.setText(MaHoKhau.getCellData(index).toString());
		txttenchuho.setText(TenChuHo.getCellData(index).toString());
		txtcmndchuho.setText(CMNDChuHo.getCellData(index).toString());
		txtsothanhvien.setText(SoThanhVien.getCellData(index).toString());
		txtdiachi.setText(DiaChi.getCellData(index).toString());
	}

	public void Edit() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String value1 = txtidhogiadinh.getText();
		String value2 = txtmahokhau.getText();
		String value3 = txttenchuho.getText();
		String value4 = txtcmndchuho.getText();
		String value5 = txtsothanhvien.getText();
		String value6 = txtdiachi.getText();
		String sql = "update HoKhau set IDHoGiaDinh= " + Integer.parseInt(value1) + ", MaHoKhau= '" + value2
				+ "', TenChuHo= N'" + value3 + "', CMNDChuHo= " + Integer.parseInt(value4) + ", SoThanhVien = "
				+ Integer.parseInt(value5) + ", DiaChi =N'" + value6 + "'" + " where IDHoGiaDinh = "
				+ Integer.parseInt(value1) + ";";
		pst = connection.prepareStatement(sql);
		pst.execute();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông Báo");
		alert.setHeaderText(null);
		alert.setContentText("Update completed !");
		alert.showAndWait();
		UpdateTable();
		search();
	}

	public void Delete() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String sql = "delete from HoKhau where IDHoGiaDinh = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, txtidhogiadinh.getText());
			pst.execute();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Delete Completed !");
			alert.showAndWait();
			UpdateTable();
			search();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void search() throws SQLException {
		IDHoGiaDinh.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("IDHoGiaDinh"));
		MaHoKhau.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("MaHoKhau"));
		TenChuHo.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("TenChuHo"));
		CMNDChuHo.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("CMNDChuHo"));
		SoThanhVien.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, Integer>("SoThanhVien"));
		DiaChi.setCellValueFactory(new PropertyValueFactory<HoGiaDinh, String>("DiaChi"));
		ObservableList<HoGiaDinh> list = Update();
		tablehogiadinh.setItems(list);
		FilteredList<HoGiaDinh> filteredData = new FilteredList<>(list, b -> true);
		txt_find.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(HoGiaDinh -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(HoGiaDinh.getIDHoGiaDinh()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (HoGiaDinh.getMaHoKhau().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (HoGiaDinh.getTenChuHo().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(HoGiaDinh.getCMNDChuHo()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(HoGiaDinh.getSoThanhVien()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (HoGiaDinh.getDiaChi().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<HoGiaDinh> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablehogiadinh.comparatorProperty());
		tablehogiadinh.setItems(sortedData);
	}

	public void Reset() throws SQLException {
		txtidhogiadinh.setText("");
		txtmahokhau.setText("");
		txttenchuho.setText("");
		txtcmndchuho.setText("");
		txtsothanhvien.setText("");
		txtdiachi.setText("");
		UpdateTable();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			UpdateTable();
			search();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
