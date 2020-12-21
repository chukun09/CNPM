package Model;

public class HoGiaDinh {
	private int IDHoGiaDinh;
	private String MaHoKhau;
	private String TenChuHo;
	private int CMNDChuHo;
	private int SoThanhVien;
	private String DiaChi;
	public HoGiaDinh(int iDHoGiaDinh, String maHoKhau, String tenChuHo, int cMNDChuHo, int soThanhVien, String diaChi) {
		super();
		IDHoGiaDinh = iDHoGiaDinh;
		MaHoKhau = maHoKhau;
		TenChuHo = tenChuHo;
		CMNDChuHo = cMNDChuHo;
		SoThanhVien = soThanhVien;
		DiaChi = diaChi;
	}
	public int getIDHoGiaDinh() {
		return IDHoGiaDinh;
	}
	public void setIDHoGiaDinh(int iDHoGiaDinh) {
		IDHoGiaDinh = iDHoGiaDinh;
	}
	public String getMaHoKhau() {
		return MaHoKhau;
	}
	public void setMaHoKhau(String maHoKhau) {
		MaHoKhau = maHoKhau;
	}
	public String getTenChuHo() {
		return TenChuHo;
	}
	public void setTenChuHo(String tenChuHo) {
		TenChuHo = tenChuHo;
	}
	public int getCMNDChuHo() {
		return CMNDChuHo;
	}
	public void setCMNDChuHo(int cMNDChuHo) {
		CMNDChuHo = cMNDChuHo;
	}
	public int getSoThanhVien() {
		return SoThanhVien;
	}
	public void setSoThanhVien(int soThanhVien) {
		SoThanhVien = soThanhVien;
	}
	public String getDiaChi() {
		return DiaChi;
	}
	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}
	
	
}