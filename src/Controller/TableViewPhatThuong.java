package Controller;

import java.net.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXTextField;
import Model.PhatThuong;
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

public class TableViewPhatThuong implements Initializable {
	@FXML
	JFXTextField txt_find;
	@FXML
	TextField txtidphatthuong, txtthongtinphatthuong;
	@FXML
	DatePicker txtngayphatthuong;
	@FXML
	private TableView<PhatThuong> tablephatthuong;
	@FXML
	private TableColumn<PhatThuong, String> IDPhatThuong;
	@FXML
	private TableColumn<PhatThuong, String> ThongTinPhatThuong;
	@FXML
	private TableColumn<PhatThuong, String> NgayPhatThuong;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public void UpdateTable() throws SQLException {
		IDPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("IDPhatThuong"));
		ThongTinPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("ThongTinPhatThuong"));
		NgayPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("NgayPhatThuong"));
		ObservableList<PhatThuong> list = Update();
		tablephatthuong.setItems(list);
	}

	public void Add() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		if (txtidphatthuong.getText().trim().equals("") || txtthongtinphatthuong.getText().trim().equals("")
				|| txtngayphatthuong.getValue().toString().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập thiếu thông tin vui lòng kiểm tra lại !");
			alert.showAndWait();
		} else {
			try {
				String sql = "Insert into PhatThuong (IDPhatThuong, ThongTinPhatThuong, NgayPhatThuong) values (N'"
						+ txtidphatthuong.getText() + "', N'" + txtthongtinphatthuong.getText() + "', N'"
						+ txtngayphatthuong.getValue().toString() + "')";
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

	public ObservableList<PhatThuong> Update() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		ObservableList<PhatThuong> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from PhatThuong");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new PhatThuong(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) {
		index = tablephatthuong.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		txtidphatthuong.setText(IDPhatThuong.getCellData(index).toString());
		txtthongtinphatthuong.setText(ThongTinPhatThuong.getCellData(index).toString());
		txtngayphatthuong.setValue(LocalDate.parse(NgayPhatThuong.getCellData(index).toString()));
	}

	public void Edit() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String value1 = txtidphatthuong.getText();
		String value5 = txtthongtinphatthuong.getText();
		String value6 = txtngayphatthuong.getValue().toString();
		String sql = "update PhatThuong set IDPhatThuong= N'" + value1 + "', ThongTinPhatThuong = N'" + value5
				+ "', NgayPhatThuong ='" + value6 + "'" + " where IDPhatThuong = N'" + value1 + "';";
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
		String sql = "delete from PhatThuong where IDPhatThuong = ?";
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
		IDPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("IDPhatThuong"));
		ThongTinPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("ThongTinPhatThuong"));
		NgayPhatThuong.setCellValueFactory(new PropertyValueFactory<PhatThuong, String>("NgayPhatThuong"));
		ObservableList<PhatThuong> list = Update();
		tablephatthuong.setItems(list);
		FilteredList<PhatThuong> filteredData = new FilteredList<>(list, b -> true);
		txt_find.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(PhatThuong -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (PhatThuong.getIDPhatThuong().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuong.getThongTinPhatThuong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (PhatThuong.getNgayPhatThuong().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<PhatThuong> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablephatthuong.comparatorProperty());
		tablephatthuong.setItems(sortedData);
	}

	public void Reset() throws SQLException {
		txtidphatthuong.setText("");
		txtthongtinphatthuong.setText("");
		txtngayphatthuong.setValue(null);
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
