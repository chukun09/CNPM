package Model;

public class CacChau {
	private int IDChau;
	private int IDHoGiaDinh;
	private String HoTen;
	private String GioiTinh;
	private String NgaySinh;
	private String ThanhTich;
	public CacChau(int iDChau, int iDHoGiaDinh, String hoTen, String gioiTinh, String ngaySinh, String thanhTich) {
		super();
		IDChau = iDChau;
		IDHoGiaDinh = iDHoGiaDinh;
		HoTen = hoTen;
		GioiTinh = gioiTinh;
		NgaySinh = ngaySinh;
		ThanhTich = thanhTich;
	}
	public int getIDChau() {
		return IDChau;
	}
	public void setIDChau(int iDChau) {
		IDChau = iDChau;
	}
	public int getIDHoGiaDinh() {
		return IDHoGiaDinh;
	}
	public void setIDHoGiaDinh(int iDHoGiaDinh) {
		IDHoGiaDinh = iDHoGiaDinh;
	}
	public String getHoTen() {
		return HoTen;
	}
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	public String getGioiTinh() {
		return GioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		GioiTinh = gioiTinh;
	}
	public String getNgaySinh() {
		return NgaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		NgaySinh = ngaySinh;
	}
	public String getThanhTich() {
		return ThanhTich;
	}
	public void setThanhTich(String thanhTich) {
		ThanhTich = thanhTich;
	}
	
}
