package Controller;

import java.net.*;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXTextField;
import Model.KiemKe;
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

public class TableViewQuy implements Initializable {
	@FXML
	JFXTextField txt_find;
	@FXML
	TextField txtidkiemke, txtquytruoc, txtsotienchitieu, txtquysau, txtngaythaydoi, txtnoidung;
	@FXML
	private TableView<KiemKe> tablequy;
	@FXML
	private TableColumn<KiemKe, Integer> IDKiemKe;
	@FXML
	private TableColumn<KiemKe, Integer> QuyTruoc;
	@FXML
	private TableColumn<KiemKe, Integer> SoTienChiTieu;
	@FXML
	private TableColumn<KiemKe, Integer> QuySau;
	@FXML
	private TableColumn<KiemKe, String> NgayThayDoi;
	@FXML
	private TableColumn<KiemKe, String> NoiDung;
	Connection connection = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	public void UpdateTable() throws SQLException {
		IDKiemKe.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("IDKiemKe"));
		QuyTruoc.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("QuyTruoc"));
		SoTienChiTieu.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("SoTienChiTieu"));
		QuySau.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("QuySau"));
		NgayThayDoi.setCellValueFactory(new PropertyValueFactory<KiemKe, String>("NgayThayDoi"));
		NoiDung.setCellValueFactory(new PropertyValueFactory<KiemKe, String>("NoiDung"));
		ObservableList<KiemKe> list = Update();
		tablequy.setItems(list);
	}

	public void Add() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		if (txtidkiemke.getText().trim().equals("") || txtquytruoc.getText().trim().equals("")
				|| txtsotienchitieu.getText().trim().equals("") || txtquysau.getText().trim().equals("")
				|| txtngaythaydoi.getText().trim().equals("") || txtnoidung.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thông Báo");
			alert.setHeaderText(null);
			alert.setContentText("Nhập thiếu thông tin vui lòng kiểm tra lại !");
			alert.showAndWait();
		} else {
			try {
				String sql = "Insert into KiemKe (IDKiemKe, QuyTruoc, SoTienChiTieu, QuySau, NgayThayDoi) values ("
						+ Integer.parseInt(txtidkiemke.getText()) + ", " + Integer.parseInt(txtquytruoc.getText())
						+ ", " + Integer.parseInt(txtsotienchitieu.getText()) + ", "
						+ Integer.parseInt(txtquysau.getText()) + ", N'" + txtngaythaydoi.getText() + "')";
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

	public ObservableList<KiemKe> Update() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		ObservableList<KiemKe> list = FXCollections.observableArrayList();
		try {
			PreparedStatement ps1 = connection.prepareStatement("Select * from KiemKe");
			rs = ps1.executeQuery();
			while (rs.next()) {
				list.add(new KiemKe(rs.getInt(1), rs.getInt(2), Counttien(rs.getString(7)), rs.getInt(4),
						rs.getString(5), rs.getString(6)));
			}
		} catch (Exception e) {
		}
		return list;
	}

	public int Counttien(String s) throws SQLException {
		Connection connection = ConnectionDatabase.ConnectionData("cnpm1");
		Statement stat = connection.createStatement();
		ResultSet rs = stat
				.executeQuery("Select SUM(GiaTri) AS total from PhatThuong_Dip where IDPhatThuong = N'" + s + "'");
		rs.next();
		return rs.getInt("total");
	}

	int index = -1;

	@FXML
	void getSelected(MouseEvent event) {
		index = tablequy.getSelectionModel().getSelectedIndex();
		if (index <= -1) {
			return;
		}
		txtidkiemke.setText(IDKiemKe.getCellData(index).toString());
		txtquytruoc.setText(QuyTruoc.getCellData(index).toString());
		txtsotienchitieu.setText(SoTienChiTieu.getCellData(index).toString());
		txtquysau.setText(QuySau.getCellData(index).toString());
		txtngaythaydoi.setText(NgayThayDoi.getCellData(index).toString());
		txtnoidung.setText(NoiDung.getCellData(index).toString());
	}

	public void Edit() throws SQLException {
		connection = ConnectionDatabase.ConnectionData("cnpm1");
		String value1 = txtidkiemke.getText();
		String value2 = txtquytruoc.getText();
		String value3 = txtsotienchitieu.getText();
		String value4 = txtquysau.getText();
		String value5 = txtngaythaydoi.getText();
		String value6 = txtnoidung.getText();
		String sql = "update KiemKe set IDKiemKe= " + Integer.parseInt(value1) + ", QuyTruoc= "
				+ Integer.parseInt(value2) + ", SoTienChiTieu= " + Integer.parseInt(value3) + ", QuySau= "
				+ Integer.parseInt(value4) + ", NgayThayDoi = '" + Date.valueOf(value5) + "', NoiDung =N'" + value6
				+ "' " + " where IDKiemKe = " + Integer.parseInt(value1) + ";";
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
		String sql = "delete from KiemKe where IDKiemKe = ?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, txtidkiemke.getText());
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
		IDKiemKe.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("IDKiemKe"));
		QuyTruoc.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("QuyTruoc"));
		SoTienChiTieu.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("SoTienChiTieu"));
		QuySau.setCellValueFactory(new PropertyValueFactory<KiemKe, Integer>("QuySau"));
		NgayThayDoi.setCellValueFactory(new PropertyValueFactory<KiemKe, String>("NgayThayDoi"));
		NoiDung.setCellValueFactory(new PropertyValueFactory<KiemKe, String>("NoiDung"));
		ObservableList<KiemKe> list = Update();
		tablequy.setItems(list);
		FilteredList<KiemKe> filteredData = new FilteredList<>(list, b -> true);
		txt_find.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(KiemKe -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(KiemKe.getIDKiemKe()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(KiemKe.getQuyTruoc()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(KiemKe.getSoTienChiTieu()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(KiemKe.getQuySau()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (KiemKe.getNgayThayDoi().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (KiemKe.getNoiDung().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<KiemKe> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tablequy.comparatorProperty());
		tablequy.setItems(sortedData);
	}

	public void Reset() throws SQLException {
		txtidkiemke.setText("");
		txtquytruoc.setText("");
		txtsotienchitieu.setText("");
		txtquysau.setText("");
		txtngaythaydoi.setText("");
		txtnoidung.setText("");
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
