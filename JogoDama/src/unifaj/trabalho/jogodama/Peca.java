package unifaj.trabalho.jogodama;

public class Peca {
	private String cor;
	private boolean dama;
	
	public Peca(String cor) {
		this.cor = cor;
		this.dama = false;
	}
	
	public String descricao() {
		return "cor= " + cor + "\ndama= " + dama;
	}


	public void setDama(boolean dama) {
		this.dama = dama;
	}
	
}
