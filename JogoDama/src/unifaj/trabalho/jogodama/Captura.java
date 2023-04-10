package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.List;

public class Captura {
	private PecaJogador pecaCapturada;
	private int[] localDestino;
	private int jogadasPossiveis;
	private List<Captura> proxCaptura;

	public Captura() {
	}

	public Captura(PecaJogador pecaCapturada, int[] localDestino) {
		this.pecaCapturada = pecaCapturada;
		this.localDestino = localDestino;
		this.proxCaptura = new ArrayList<>();
		this.jogadasPossiveis = 1;
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

	public int getJogadasPossiveis() {
		return this.jogadasPossiveis;
	}

	public void buscarMelhorJogada() {
		proxCaptura.forEach(captura -> {
			this.jogadasPossiveis = 1 + captura.incrementarNovaJogada();
		});
		
		int maiorNumeroJogadas = proxCaptura.stream()
				.mapToInt(captura -> captura.jogadasPossiveis)
				.max()
				.orElse(0);
		
		proxCaptura.removeIf(captura -> captura.jogadasPossiveis < maiorNumeroJogadas);
	}

	private int incrementarNovaJogada() {
		if(!this.proxCaptura.isEmpty()) {
			this.buscarMelhorJogada();
		}
		return this.jogadasPossiveis;
	}
}
