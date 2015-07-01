package bean;

public class Kamar {
    private String noKamar;
    private boolean kosong;

    public String getNoKamar() {
	return noKamar;
    }

    public boolean isKosong() {
	return kosong;
    }

    public void setKosong(boolean kosong) {
	this.kosong = kosong;
    }

    public void setNoKamar(String noKamar) {
	if(!noKamar.matches("\\d{3}"))throw new IllegalArgumentException();
	this.noKamar = noKamar;
    }
}
