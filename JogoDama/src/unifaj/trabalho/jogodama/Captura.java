package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.List;

public class Captura {
	private PecaJogador pecaCapturada;
	private int[] localDestino;
	private List<Captura> proxCaptura;
	
	public Captura(PecaJogador pecaCapturada, int[] localDestino) {
		this.pecaCapturada = pecaCapturada;
		this.localDestino = localDestino;
		proxCaptura = new ArrayList<>();
	}

	public PecaJogador getPecaCapturada() {
		return this.pecaCapturada;
	}


	public int[] getLocalDestino() {
		return this.localDestino;
	}

	public void setProxCaptura(List<Captura> proxCaptura) {
		this.proxCaptura = proxCaptura;
	}

	public List<Captura> getProxCaptura() {
		return proxCaptura;
	}
}
