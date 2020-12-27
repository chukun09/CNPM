package Controller;

import java.net.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.xml.bind.NotIdentifiableEvent;

import com.jfoenix.controls.JFXTextField;

import Model.CacChau;
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

public class TableViewCacChau implements Initializable {
	@FXML
	JFXTextField txt_find;
	@FXML
	TextField txtidchau, txtidhogiadinh, txthoten, txtgioitinh, txtthanhtich;
	@FXML
	DatePicker txtngaysinh;
	@FXML
	private TableView<CacChau> tablecacchau;
	@FXML
	private TableColumn<CacChau, Integer> IDChau;
	@FXML
	private TableColumn<CacChau, Integer> IDHoGiaDinh;
	@FXML
	private TableColumn<CacChau, String> HoTen;
	@FXML
	private TableColumn<CacChau, String> GioiTinh;
	@FXML
	private TableColumn<CacChau, String> NgaySinh;
	@FXML
	private TableColumn<CacChau, String> ThanhTich;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	public void UpdateTable() throws SQLException {
		IDChau.setCellValueFactory(new PropertyValueFactory<CacChau, Integer>("IDChau"));
		IDHoGiaDinh.setCellValueFactory(new PropertyValueFactory<CacChau, Integer>("IDHoGiaDinh"));
		HoTen.setCellValueFactory(new PropertyValueFactory<CacChau, String>("HoTen"));
		GioiTinh.setCellValueFactory(new PropertyValueFactory<CacChau, String>("GioiTinh"));
		NgaySinh.setCellValueFactory(new PropertyValueFactory<CacChau, String>("NgaySinh"));
		ThanhTich.setCellValueFactory(new PropertyValueFactory<CacChau, String>("ThanhTich"));
		ObservableList<CacChau> list = Update();
		tablecacchau.setItems(list);
	}
	public void Add() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		if (txtidchau.getText().trim().equals("") || txtidhogiadinh.getText().trim().equals("")
				|| txthoten.getText().trim().equals("") || txtgioitinh.getText().trim().equals("")
				|| txtngaysinh.getValue().toString().trim().equals("") || txtthanhtich.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập thiếu thông tin vui lòng kiểm tra lại !");
			alert.showAndWait();
		} else {
			try {
				String sql = "Insert into CacChau (IDChau, IDHoGiaDinh, HoTen, GioiTinh, NgaySinh, ThanhTich) values ("
						+ Integer.parseInt(txtidchau.getText()) + ", " + Integer.parseInt(txtidhogiadinh.getText())
						+ ", N'" + txthoten.getText() + "', N'" + txtgioitinh.getText() + "', N'"
						+ txtngaysinh.getValue().toString() + "', N'" + txtthanhtich.getText() + "')";
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

	public ObservableList<CacChau> Update() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		ObservableList<CacChau> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from CacChau");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new CacChau(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) {
		index = tablecacchau.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		txtidchau.setText(IDChau.getCellData(index).toString());
		txtidhogiadinh.setText(IDHoGiaDinh.getCellData(index).toString());
		txthoten.setText(HoTen.getCellData(index).toString());
		txtgioitinh.setText(GioiTinh.getCellData(index).toString());
		txtngaysinh.setValue(LocalDate.parse(NgaySinh.getCellData(index).toString()));
		txtthanhtich.setText(ThanhTich.getCellData(index).toString());
	}

	public void Edit() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String value1 = txtidchau.getText();
		String value2 = txtidhogiadinh.getText();
		String value3 = txthoten.getText();
		String value4 = txtgioitinh.getText();
		String value5 = txtngaysinh.getValue().toString();
		String value6 = txtthanhtich.getText();
		String sql = "update CacChau set IDChau= " + Integer.parseInt(value1) + ", IDHoGiaDinh= " + value2
				+ ", HoTen= N'" + value3 + "',GioiTinh= N'" + value4 + "', NgaySinh = '" + value5 + "', ThanhTich =N'"
				+ value6 + "'" + " where IDChau = " + Integer.parseInt(value1) + ";";
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
		String sql = "delete from CacChau where IDChau = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, txtidchau.getText());
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
		IDChau.setCellValueFactory(new PropertyValueFactory<CacChau, Integer>("IDChau"));
		IDHoGiaDinh.setCellValueFactory(new PropertyValueFactory<CacChau, Integer>("IDHoGiaDinh"));
		HoTen.setCellValueFactory(new PropertyValueFactory<CacChau, String>("HoTen"));
		GioiTinh.setCellValueFactory(new PropertyValueFactory<CacChau, String>("GioiTinh"));
		NgaySinh.setCellValueFactory(new PropertyValueFactory<CacChau, String>("NgaySinh"));
		ThanhTich.setCellValueFactory(new PropertyValueFactory<CacChau, String>("ThanhTich"));
		ObservableList<CacChau> list = Update();
		tablecacchau.setItems(list);
		FilteredList<CacChau> filteredData = new FilteredList<>(list, b -> true);
		txt_find.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(CacChau -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(CacChau.getIDChau()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(CacChau.getIDHoGiaDinh()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (CacChau.getHoTen().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (CacChau.getGioiTinh().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (CacChau.getNgaySinh().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (CacChau.getThanhTich().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<CacChau> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablecacchau.comparatorProperty());
		tablecacchau.setItems(sortedData);
	}

	public void Reset() throws SQLException {
		txtidchau.setText("");
		txtidhogiadinh.setText("");
		txthoten.setText("");
		txtgioitinh.setText("");
		txtngaysinh.setValue(null);;
		txtthanhtich.setText("");
		UpdateTable();
		search();
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
