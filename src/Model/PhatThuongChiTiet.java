package Model;

public class PhatThuongChiTiet extends PhatThuong {
	private int IDChau;
	private String HoTen;
	private Integer PhanThuong;

	public PhatThuongChiTiet(String iDPhatThuong, String thongTinPhatThuong, String ngayPhatThuong, int iDChau,
			String hoTen, Integer phanThuong) {
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

	public Integer getPhanThuong() {
		return PhanThuong;
	}

	public void setPhanThuong(int phanThuong) {
		PhanThuong = phanThuong;
	}

}
