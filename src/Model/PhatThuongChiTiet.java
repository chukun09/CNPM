package Model;

public class PhatThuongChiTiet extends PhatThuong {
	private int IDChau;
	private String HoTen;
	private String PhanThuong;

	public PhatThuongChiTiet(String iDPhatThuong, String thongTinPhatThuong, String ngayPhatThuong, int iDChau,
			String hoTen, String phanThuong) {
		super(iDPhatThuong, thongTinPhatThuong, ngayPhatThuong);
		IDChau = iDChau;
		HoTen = hoTen;
		PhanThuong = phanThuong;
	}

	public int getIDChau() {
		return IDChau;
	}

	public void setIDChau(int iDChau) {
		IDChau = iDChau;
	}

	public String getHoTen() {
		return HoTen;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public String getPhanThuong() {
		return PhanThuong;
	}

	public void setPhanThuong(String phanThuong) {
		PhanThuong = phanThuong;
	}

}
