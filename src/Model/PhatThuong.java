package Model;

public class PhatThuong {
	private String IDPhatThuong;
	private String ThongTinPhatThuong;
	private String NgayPhatThuong;
	
	public PhatThuong(String iDPhatThuong, String thongTinPhatThuong, String ngayPhatThuong) {
		super();
		IDPhatThuong = iDPhatThuong;
		ThongTinPhatThuong = thongTinPhatThuong;
		NgayPhatThuong = ngayPhatThuong;
	}
	public String getIDPhatThuong() {
		return IDPhatThuong;
	}
	public void setIDPhatThuong(String iDPhatThuong) {
		IDPhatThuong = iDPhatThuong;
	}
	public String getThongTinPhatThuong() {
		return ThongTinPhatThuong;
	}
	public void setThongTinPhatThuong(String thongTinPhatThuong) {
		ThongTinPhatThuong = thongTinPhatThuong;
	}
	public String getNgayPhatThuong() {
		return NgayPhatThuong;
	}
	public void setNgayPhatThuong(String ngayPhatThuong) {
		NgayPhatThuong = ngayPhatThuong;
	}
	
}