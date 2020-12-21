package Controller;

import java.net.*;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXTextField;
import Model.PhatThuongChiTiet;
import Model.ConnectionDatabase;
import Model.PhatThuong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TableViewPhatThuongChiTiet implements Initializable {
	@FXML
	JFXTextField txt_find;
	@FXML
	TextField txtidphatthuong, txtidchau, txthoten, txtthongtinphatthuong, txtphanthuong, txtngayphatthuong;
	@FXML
	private TableView<PhatThuongChiTiet> tablechitietphatthuong;
	@FXML
	private TableColumn<PhatThuongChiTiet, String> IDPhatThuong;
	@FXML
	private TableColumn<PhatThuongChiTiet, Integer> IDChau;
	@FXML
	private TableColumn<PhatThuongChiTiet, String> HoTen;
	@FXML
	private TableColumn<PhatThuongChiTiet, String> ThongTinPhatThuong;
	@FXML
	private TableColumn<PhatThuongChiTiet, String> PhanThuong;
	@FXML
	private TableColumn<PhatThuongChiTiet, String> NgayPhatThuong;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public void UpdateTable() throws SQLException {
		IDPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("IDPhatThuong"));
		IDChau.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, Integer>("IDChau"));
		HoTen.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("HoTen"));
		ThongTinPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("ThongTinPhatThuong"));
		PhanThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("PhanThuong"));
		NgayPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("NgayPhatThuong"));
		ObservableList<PhatThuongChiTiet> list = Update();
		tablechitietphatthuong.setItems(list);
	}

	public void Add() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		if (txtidphatthuong.getText().trim().equals("") || txtidchau.getText().trim().equals("")
				|| txthoten.getText().trim().equals("") || txtthongtinphatthuong.getText().trim().equals("")
				|| txtphanthuong.getText().trim().equals("") || txtngayphatthuong.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập thiếu thông tin vui lòng kiểm tra lại !");
			alert.showAndWait();
		} else {
			try {
				String sql = "Insert into PhatThuong_Dip (IDPhatThuong, IDChau, HoTen, ThongTinPhatThuong, PhanThuong, NgayPhatThuong) values (N'"
						+ txtidphatthuong.getText() + ", " + Integer.parseInt(txtidchau.getText()) + ", N'"
						+ txthoten.getText() + "', N'" + txtthongtinphatthuong.getText() + "', N'"
						+ txtphanthuong.getText() + "', N'" + txtngayphatthuong.getText() + "'";
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

	public ObservableList<PhatThuongChiTiet> Update() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		ObservableList<PhatThuongChiTiet> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from PhatThuong_Dip");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new PhatThuongChiTiet(rs.getString(1), rs.getString(5), rs.getString(7), rs.getInt(2),rs.getString(4),rs.getString(6)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) {
		index = tablechitietphatthuong.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		txtidphatthuong.setText(IDPhatThuong.getCellData(index).toString());
		txtidchau.setText(IDChau.getCellData(index).toString());
		txthoten.setText(HoTen.getCellData(index).toString());
		txtthongtinphatthuong.setText(ThongTinPhatThuong.getCellData(index).toString());
		txtphanthuong.setText(PhanThuong.getCellData(index).toString());
		txtngayphatthuong.setText(NgayPhatThuong.getCellData(index).toString());
	}

	public void Edit() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String value1 = txtidphatthuong.getText();
		String value2 = txtidchau.getText();
		String value3 = txthoten.getText();
		String value4 = txtthongtinphatthuong.getText();
		String value5 = txtphanthuong.getText();
		String value6 = txtngayphatthuong.getText();
		String sql = "update PhatThuong_Dip set IDPhatThuong= N'" + value1 + "', IDChau= " +Integer.parseInt(value2) 
				+ ", HoTen= N'" + value3 + "', ThongTinPhatThuong= N'" + value4 + "', PhanThuong = N'"
				+ value5 + "', NgayPhatThuong =N'" + value6 + "'" + "' where IDPhatThuong = "
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
		String sql = "delete from PhatThuong_Dip where IDPhatThuong = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, txtidphatthuong.getText());
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
		IDPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("IDPhatThuong"));
		IDChau.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, Integer>("IDChau"));
		HoTen.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("HoTen"));
		ThongTinPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("ThongTinPhatThuong"));
		PhanThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("PhanThuong"));
		NgayPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuongChiTiet, String>("NgayPhatThuong"));
		ObservableList<PhatThuongChiTiet> list = Update();
		tablechitietphatthuong.setItems(list);
		FilteredList<PhatThuongChiTiet> filteredData = new FilteredList<>(list, b -> true);
		txt_find.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(PhatThuongChiTiet -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (PhatThuongChiTiet.getIDPhatThuong().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(PhatThuongChiTiet.getIDChau()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuongChiTiet.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuongChiTiet.getThongTinPhatThuong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuongChiTiet.getPhanThuong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuongChiTiet.getNgayPhatThuong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<PhatThuongChiTiet> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablechitietphatthuong.comparatorProperty());
		tablechitietphatthuong.setItems(sortedData);
	}

	public void Reset() throws SQLException {
		txtidphatthuong.setText("");
		txtidchau.setText("");
		txthoten.setText("");
		txtthongtinphatthuong.setText("");
		txtphanthuong.setText("");
		txtngayphatthuong.setText("");
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
