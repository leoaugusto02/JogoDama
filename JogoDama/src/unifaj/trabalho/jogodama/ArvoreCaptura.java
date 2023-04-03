package unifaj.trabalho.jogodama;

public class ArvoreCaptura {
	private ArvoreCaptura esquerda;
	private ArvoreCaptura direita;
	private int[] destino;
	
	public ArvoreCaptura(int[] destinoEsq, int[] destinoDir) {
		this.direita = new ArvoreCaptura(null, destinoDir);
		this.esquerda = new ArvoreCaptura(destinoEsq, null);
	}

	public ArvoreCaptura getEsquerda() {
		return esquerda;
	}

	public ArvoreCaptura getDireita() {
		return direita;
	}

	public int[] getDestino() {
		return destino;
	}
	
}
