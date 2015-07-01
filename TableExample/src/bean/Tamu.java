package bean;

import java.util.Calendar;

public class Tamu {
    public enum JenisKelamin {
	L, P;
	@Override
	public String toString() {
	    return name().substring(0, 1);
	};
    }

    public enum StatusKawin {
	LAJANG, MENIKAH, CERAI, JANDA, DUDA;
	@Override
	public String toString() {
	    return name().toLowerCase();
	};
    }

    private String id;
    private String nama;
    private JenisKelamin jenisKelamin;
    private int tahunLahir, umur;
    private String asal;
    private StatusKawin statusKawin;
    private String pekerjaan;
    private String noHp;

    public String getAsal() {
	return asal;
    }

    public String getId() {
	return id;
    }

    public JenisKelamin getJenisKelamin() {
	return jenisKelamin;
    }

    public String getNama() {
	return nama;
    }

    public String getNoHp() {
	return noHp;
    }

    public String getPekerjaan() {
	return pekerjaan;
    }

    public StatusKawin getStatusKawin() {
	return statusKawin;
    }

    public int getTahunLahir() {
	return tahunLahir;
    }

    public int getUmur() {
	return umur;
    }

    public void setAsal(String asal) {
	this.asal = asal;
    }

    public void setId(String id) {
	this.id = id;
    }

    public void setJenisKelamin(JenisKelamin jenisKelamin) {
	this.jenisKelamin = jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
	if (jenisKelamin.equals("L")) {
	    this.jenisKelamin = JenisKelamin.L;
	} else if (jenisKelamin.equals("P"))
	    this.jenisKelamin = JenisKelamin.P;
	else
	    throw new IllegalArgumentException();
    }

    public void setNama(String nama) {
	this.nama = nama;
    }

    public void setNoHp(String noHp) {
	if (noHp.matches("08\\d{8,10}") || noHp == null || noHp.equals(""))
	    this.noHp = noHp;
	else
	    throw new IllegalArgumentException();
    }

    public void setPekerjaan(String pekerjaan) {
	this.pekerjaan = pekerjaan;
    }

    public void setStatusKawin(StatusKawin statusKawin) {
	this.statusKawin = statusKawin;
    }

    public void setStatusKawin(String statusKawin) {
	this.statusKawin = StatusKawin.valueOf(statusKawin.toUpperCase());
    }

    public void setTahunLahir(int tahunLahir) {
	this.tahunLahir = tahunLahir;
	umur = Calendar.getInstance().get(Calendar.YEAR) - tahunLahir;
    }

    public void setUmur(int umur) {
	this.umur = umur;
	tahunLahir = Calendar.getInstance().get(Calendar.YEAR) - umur;
    }

    @Override
    public String toString() {
	return String
		.format("{id:'%s', nama:'%s', jenisKelamin:'%s', umur:%d, asal:'%s', statusKawin:'%s',noHp:'%s',pekerjaan:'%s'}%n",
			id, nama, jenisKelamin, umur, asal, statusKawin, noHp,
			pekerjaan);
    }
}
