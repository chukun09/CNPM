package Model;

public class KiemKe {
	private int IDKiemKe;
	private int QuyTruoc;
	private int SoTienChiTieu;
	private int QuySau = QuyTruoc - SoTienChiTieu;
	private String NgayThayDoi;
	private String NoiDung;

	public KiemKe(int iDKiemKe, int quyTruoc, int soTienChiTieu, int quySau, String ngayThayDoi, String noiDung) {
		super();
		IDKiemKe = iDKiemKe;
		QuyTruoc = quyTruoc;
		SoTienChiTieu = soTienChiTieu;
		QuySau = quyTruoc - soTienChiTieu;
		NgayThayDoi = ngayThayDoi;
		NoiDung = noiDung;
	}
	
	public int getIDKiemKe() {
		return IDKiemKe;
	}

	public void setIDKiemKe(int iDKiemKe) {
		IDKiemKe = iDKiemKe;
	}

	public int getQuyTruoc() {
		return QuyTruoc;
	}

	public void setQuyTruoc(int quyTruoc) {
		QuyTruoc = quyTruoc;
	}

	public int getSoTienChiTieu() {
		return SoTienChiTieu;
	}

	public void setSoTienChiTieu(int soTienChiTieu) {
		SoTienChiTieu = soTienChiTieu;
	}

	public int getQuySau() {
		return QuySau;
	}

	public void setQuySau(int quySau) {
		QuySau = quySau;
	}

	public String getNgayThayDoi() {
		return NgayThayDoi;
	}

	public void setNgayThayDoi(String ngayThayDoi) {
		NgayThayDoi = ngayThayDoi;
	}

	public String getNoiDung() {
		return NoiDung;
	}

	public void setNoiDung(String noiDung) {
		NoiDung = noiDung;
	}

}
