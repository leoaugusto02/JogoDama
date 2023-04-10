package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.List;

public class Captura {
	private PecaJogador pecaCapturada;
	private int[] localDestino;
	private int profundidade;
	private List<Captura> proxCaptura;

	public Captura() {
	}

	public Captura(PecaJogador pecaCapturada, int[] localDestino) {
		this.pecaCapturada = pecaCapturada;
		this.localDestino = localDestino;
		this.proxCaptura = new ArrayList<>();
		this.profundidade = 0;
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
		return this.proxCaptura;
	}

	public int getProfundidade() {
		return this.profundidade;
	}

	public void buscarMelhorJogada() {
		proxCaptura.forEach(captura -> {
			captura.profundidade += 1 + captura.aumentarProfundidade();
		});
		
		int maiorProfundidade = proxCaptura.stream()
				.mapToInt(captura -> captura.profundidade)
				.max()
				.orElse(0);
		
		proxCaptura.removeIf(captura -> captura.profundidade < maiorProfundidade);
	}

	private int aumentarProfundidade() {
		if(!this.proxCaptura.isEmpty()) {
			this.buscarMelhorJogada();
		}
		return this.profundidade;
	}
}
