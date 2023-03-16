package unifaj.trabalho.jogodama;

public class Peca {
	private String cor;
	private String pos;
	private boolean dama;
	
	public Peca(String cor, String pos) {
		this.cor = cor;
		this.pos = pos;
		this.dama = false;
	}
	
	public String descricao() {
		return "cor=" + cor + "\npos=" + pos + "\ndama=" + dama;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setDama(boolean dama) {
		this.dama = dama;
	}
	
}
