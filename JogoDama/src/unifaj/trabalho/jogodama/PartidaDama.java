package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PartidaDama {

	private PecaJogador[][] tabuleiro;
	private Map<Byte, Jogador> jogadores;
	private byte jogadorTurno;
	private boolean flagCaptura;
	private Map<PecaJogador, List<Captura>> pecasCaptura;
	private Captura capturaAtual;
	private int contadorMovimento;

	public PartidaDama(byte tamanho) {
		this.tabuleiro = Arrays.stream(new PecaJogador[tamanho][tamanho]).toArray(PecaJogador[][]::new);
		this.jogadores = new HashMap<>();
		this.jogadorTurno = 1;
		this.flagCaptura = false;
		this.pecasCaptura = new HashMap<>();
		this.capturaAtual = new Captura();
		this.contadorMovimento = 0;
	}

	public void iniciarJogo(Scanner sc) {
		for(byte i = 1; i <= 2; i++) {
			System.out.println("Apelido para o jogador " + i + ": ");
			final Jogador jogador = new Jogador(sc.nextLine());
			
			System.out.println("CorPeca: ");
			final String corPeca = sc.nextLine();

			final int limiteCasasJogador = i == 1 ? this.tabuleiro.length/2 - 1 : this.tabuleiro.length;

			for(int linha = (limiteCasasJogador - this.tabuleiro.length/2) + 1; linha < limiteCasasJogador; linha++) {
				for(int coluna = 0; coluna < this.tabuleiro.length; coluna++) {
					this.tabuleiro[linha][coluna] = linha % 2 == 0 && coluna % 2 == 0 ? addPeca(jogador, corPeca, linha, coluna)
							: linha % 2 != 0 && coluna % 2 != 0 ? addPeca(jogador, corPeca, linha, coluna) : null;
				}
			}
			
			this.jogadores.put(i, jogador);
		}
	}

	private PecaJogador addPeca(Jogador jogador, String corPeca, int linha, int coluna) {
		return new PecaJogador(jogador, corPeca, new int[] {linha, coluna});
	}

	public void jogar(Scanner sc) {
		for(byte linha = 0; linha < this.tabuleiro.length; linha++) {
			System.out.println("[ ");
			for(byte coluna = 0; coluna < this.tabuleiro.length; coluna++) {
				System.out.println(this.tabuleiro[linha][coluna] + ", ");

			}
			System.out.println("]\n");
		}

		this.flagCaptura = this.pecasCaptura.size() > 0;  

		Jogador jogador = this.jogadores.get(jogadorTurno);

		System.out.println("Turno Jogador " + jogador.getApelido());

		final int[] origem = selecionarOrigem(sc);
		final int[] destino = selecionarDestino(origem, sc);

		if(flagCaptura) {
			this.contadorMovimento = 0;
			flagCaptura = false;
			moverPecaCaptura(origem);
		} else {
			this.contadorMovimento++;
			moverPeca(origem, destino);
			verificarDama(destino);
			procurarCaptura();
			this.jogadorTurno = (byte) (this.jogadorTurno == 1 ? 2 : 1);
		}
	}

	public String avaliarPartida() {
		if(this.contadorMovimento >= 10) {
			return "Jogo empatado";
		}

		for(Map.Entry<Byte, Jogador> jogador: this.jogadores.entrySet()) {
			Jogador jogadorVitoria = jogador.getValue();
			if(jogadorVitoria.getPecasCapturadas() == 12) {
				return "Jogador " + jogadorVitoria.getApelido() + " Vitorioso";
			}
		}

		return "";
	}

	private int[] selecionarOrigem(Scanner sc){
		while(true) {
			System.out.println("Peça Origem: ");
			final int[] origem = validarInput(sc.nextLine());
			if(origem != null && verificarMovimentoOrigem(origem)) {
				return origem;
			}
			System.out.println("Movimento invalido");
		}
	}

	private boolean verificarMovimentoOrigem(int[] origem) {
		PecaJogador pecaJogador = this.tabuleiro[origem[0]][origem[1]]; 

		if(this.pecasCaptura.size() > 0) {
			if(!this.pecasCaptura.containsKey(pecaJogador)) {
				return false;
			}

			return true;
		}

		if(pecaJogador == null || !pecaJogador.validarPeca(this.jogadores.get(this.jogadorTurno))) {
			return false;
		}

		if(pecaJogador.getPeca().isDama()) {
			int linha = origem[0];
			int coluna = origem[1];

			if((linha + 1 > 7 || validarOrigemInvalida(linha + 1, coluna)) && (linha - 1 < 0 || validarOrigemInvalida(linha - 1, coluna))) {
				return false;
			}

			return true;
		}

		int linha = this.jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;

		if(validarOrigemInvalida(linha, origem[1])){
			return false;
		}

		return true;
	}

	// Verifica se existe alguma possibilidade de movimento da peça
	private boolean validarOrigemInvalida(int linha, int coluna) {
		return (coluna - 1 < 0 || this.tabuleiro[linha][coluna - 1] != null)
				&& (coluna + 1 > 7 || this.tabuleiro[linha][coluna + 1] != null);
	}

	private int[] selecionarDestino(int[] origem, Scanner sc){
		while(true) {
			System.out.println("Peça Destino: ");
			final int[] destino = validarInput(sc.nextLine());
			if(destino != null && verificarMovimentoDestino(origem, destino)) {
				return destino;
			}
			System.out.println("Movimento invalido");
		}
	}

	private boolean verificarMovimentoDestino(int[] origem, int[] destino) {
		PecaJogador pecaCaptura = this.tabuleiro[origem[0]][origem[1]];

		if(this.pecasCaptura.size() > 0) {
			for(Captura captura : this.pecasCaptura.get(pecaCaptura)) {
				if(Arrays.equals(captura.getLocalDestino(), destino)) {
					this.capturaAtual = captura;
					return true;
				}
			}
			return false;
		}

		if(pecaCaptura.getPeca().isDama()) {
			int linha = origem[0];

			if(validarDestinoInvalido(linha + 1, origem, destino) && validarDestinoInvalido(linha - 1, origem, destino)) {
				return false;
			}

			return true;
		}

		int linha = this.jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;
		if(validarDestinoInvalido(linha, origem, destino)) {
			return false;
		}

		return true;
	}

	// Verifica se o destino é um destino valido para o movimento da peça
	private boolean validarDestinoInvalido(int linha, int[] origem, int[] destino) {
		return this.tabuleiro[destino[0]][destino[1]] != null ||
				destino[0] != linha ||
				(destino[1] != origem[1] - 1 && destino[1] != origem[1] + 1);
	}

	private int[] validarInput(String pos) {
		if(pos.length() != 2) {
			return null;
		}

		String posConvertido = converterCoordenada(pos);

		if(posConvertido.length() != 2) {
			return null;
		}

		int[] casa = {Integer.parseInt(posConvertido.substring(0, 1)), Integer.parseInt(posConvertido.substring(1))};

		if(Arrays.stream(casa).anyMatch(c -> c < 0 || c > 7)) {
			return null;
		}

		return casa;
	}
	
	private String converterCoordenada(String coordenada){
		return ((int) coordenada.charAt(0) - 49)   + "" + ((int) coordenada.charAt(1) - 65);
	}

	private void moverPecaCaptura(int[] origem) {
		PecaJogador pecaCaptura = this.tabuleiro[origem[0]][origem[1]];
		List<Captura> capturas = this.capturaAtual.getProxCaptura();
		this.pecasCaptura = new HashMap<>();
		int[] casaPecaCapturada = this.capturaAtual.getPecaCapturada().getPos();


		this.tabuleiro[casaPecaCapturada[0]][casaPecaCapturada[1]] = null;
		this.jogadores.get(this.jogadorTurno).pecaCapturada();

		moverPeca(origem, this.capturaAtual.getLocalDestino());

		if(capturas.size() > 0) {
			this.pecasCaptura.put(pecaCaptura, capturas);
		}else {
			verificarDama(this.capturaAtual.getLocalDestino());
			buscarCaptura(this.capturaAtual.getLocalDestino());
			this.jogadorTurno = (byte) (this.jogadorTurno == 1 ? 2 : 1);
		}
	}
	
	private void moverPeca(int[] origem, int[] destino) {
		PecaJogador pecaSelecionada = this.tabuleiro[origem[0]][origem[1]];
		System.out.println(pecaSelecionada.descricao());
		this.tabuleiro[origem[0]][origem[1]] = null;
		this.tabuleiro[destino[0]][destino[1]] = pecaSelecionada;
		pecaSelecionada.setPos(destino);
	}
	
	private void verificarDama(int[] casa) {
		PecaJogador peca = this.tabuleiro[casa[0]][casa[1]];
		if(!peca.getPeca().isDama()){
			int[] pos = peca.getPos();
			if((jogadorTurno == 1 && pos[0] + 1 > 7) 
					|| (jogadorTurno == 2 && pos[0] - 1 < 0)) {
				peca.getPeca().setDama();
			}
		}
	}

	private void procurarCaptura(){
		for(byte linha = 0; linha < this.tabuleiro.length; linha++) {
			for(byte coluna = 0; coluna < this.tabuleiro.length; coluna++) {
				PecaJogador peca = this.tabuleiro[linha][coluna];
				if(peca != null && peca.validarPeca(this.jogadores.get(this.jogadorTurno))) {
					buscarCaptura(peca.getPos());
				}
			}
		}
	}

	//Verifica se a peça pode ser capturada
	private void buscarCaptura(int[] destino) {
		final int linhaBaixo = destino[0] - 1;
		final int linhaCima = destino[0] + 1;
		final int colunaEsq = destino[1] - 1;
		final int colunaDir =  destino[1] + 1;

		//Valida os cantos
		if(linhaBaixo > -1 && linhaCima < 8 && colunaEsq > -1 && colunaDir < 8) {
			PecaJogador pecaSuperiorEsq = this.tabuleiro[linhaCima][colunaEsq];
			PecaJogador pecaInferiorEsq = this.tabuleiro[linhaBaixo][colunaEsq];
			PecaJogador pecaSuperiorDir = this.tabuleiro[linhaCima][colunaDir];
			PecaJogador pecaInferiorDir = this.tabuleiro[linhaBaixo][colunaDir];

			PecaJogador pecaCapturada = this.tabuleiro[destino[0]][destino[1]];

			Jogador jogador = this.jogadores.get(this.jogadorTurno);

			boolean novaCaptura = false;

			//Diagonal Direita - Esquerda
			if(pecaInferiorDir == null && pecaSuperiorEsq != null && !pecaSuperiorEsq.validarPeca(jogador)) {
				novaCaptura = true;
				addPecaCaptura(pecaSuperiorEsq, pecaCapturada, new int[]{linhaBaixo, colunaDir});
			} else if(pecaSuperiorEsq == null && pecaInferiorDir != null && !pecaInferiorDir.validarPeca(jogador)) {
				novaCaptura = true;
				addPecaCaptura(pecaInferiorDir, pecaCapturada, new int[]{linhaCima, colunaEsq});
			}

			//Diagonal Esquerda - Direita
			if(pecaInferiorEsq == null && pecaSuperiorDir != null && !pecaSuperiorDir.validarPeca(jogador)) {
				novaCaptura = true;
				addPecaCaptura(pecaSuperiorDir, pecaCapturada, new int[]{linhaBaixo, colunaEsq});
			} else if(pecaSuperiorDir == null && pecaInferiorEsq != null && !pecaInferiorEsq.validarPeca(jogador)) {
				novaCaptura = true;
				addPecaCaptura(pecaInferiorEsq, pecaCapturada, new int[]{linhaCima, colunaDir});
			}

			if(novaCaptura) {
				buscarMelhorJogada();
			}
		}
	}

	private void addPecaCaptura(PecaJogador pecaCaptura, PecaJogador pecaCapturada, int[] casaDestinoCaptura) {
		List<Captura> destinoCaptura = this.pecasCaptura.containsKey(pecaCaptura) 
				? this.pecasCaptura.get(pecaCaptura)
						: new LinkedList<>();
		destinoCaptura.add(new Captura(pecaCapturada, casaDestinoCaptura));
		this.pecasCaptura.put(pecaCaptura, destinoCaptura);
	}

	private void buscarMelhorJogada() {
		int maiorNumeroJogadas = 0;
		for(PecaJogador pecaCaptura : this.pecasCaptura.keySet()) {
			List<Captura> capturas = this.pecasCaptura.get(pecaCaptura);
			capturas.forEach(captura -> {
				captura.setProxCaptura(calcularJogadasPossiveis(
						captura.getLocalDestino(), 
						new ArrayList<>(Arrays.asList(captura.getPecaCapturada()))));
				if(captura.getProxCaptura().size() > 0) {
					captura.buscarMelhorJogada();
				}
			});
			int profundidade = capturas.get(0).getJogadasPossiveis();
			maiorNumeroJogadas = maiorNumeroJogadas < profundidade ? profundidade : maiorNumeroJogadas;
		}
		for(Map.Entry<PecaJogador, List<Captura>> captura: this.pecasCaptura.entrySet()) {
			if(maiorNumeroJogadas > captura.getValue().get(0).getJogadasPossiveis()) {
				this.pecasCaptura.remove(captura.getKey());
			}
		}
	}

	private List<Captura> calcularJogadasPossiveis(int[] destinoCaptura, List<PecaJogador> ultimasCapturas) {	
		final int linha = destinoCaptura[0];
		final int coluna = destinoCaptura[1];

		Captura capSuperiorEsq = linha + 2 < 8 && coluna - 2 > -1 
				? new Captura(
						this.tabuleiro[linha + 1][coluna - 1],
						new int[]{linha + 2, coluna - 2}) 
						: null;
		Captura capSuperiorDir = linha + 2 < 8 && coluna + 2 < 8 
				? new Captura(
						this.tabuleiro[linha + 1][coluna + 1],
						new int[]{linha + 2, coluna + 2}) 
						: null;
		Captura capInferiorEsq = linha - 2 > -1 && coluna - 2 > -1
				? new Captura(
						this.tabuleiro[linha - 1][coluna - 1], 
						new int[]{linha - 2, coluna - 2}) 
						: null;
		Captura capInferiorDir = linha - 2 > -1 && coluna + 2 < 8
				? new Captura(
						this.tabuleiro[linha - 1][coluna + 1], 
						new int[]{linha - 2, coluna + 2}) 
						: null;

		List<Captura> capturasPossiveis = new LinkedList<>();
		List<PecaJogador> novasCapturas;

		//Casa Superior Esquerda
		if(verificarCaptura(capSuperiorEsq, ultimasCapturas)) {
			novasCapturas = new ArrayList<>(ultimasCapturas);
			novasCapturas.add(capSuperiorEsq.getPecaCapturada());
			capturasPossiveis.add(capSuperiorEsq);
			capSuperiorEsq.setProxCaptura(calcularJogadasPossiveis(capSuperiorEsq.getLocalDestino(), novasCapturas));
		}

		//Casa Superior Direita
		if(verificarCaptura(capSuperiorDir, ultimasCapturas)) {
			novasCapturas = new ArrayList<>(ultimasCapturas);
			novasCapturas.add(capSuperiorDir.getPecaCapturada());
			capturasPossiveis.add(capSuperiorDir);
			capSuperiorDir.setProxCaptura(calcularJogadasPossiveis(capSuperiorDir.getLocalDestino(), novasCapturas));
		}

		//Casa Inferior Esquerda
		if(verificarCaptura(capInferiorEsq, ultimasCapturas)) {
			novasCapturas = new ArrayList<>(ultimasCapturas);
			novasCapturas.add(capInferiorEsq.getPecaCapturada());
			capturasPossiveis.add(capInferiorEsq);
			capInferiorEsq.setProxCaptura(calcularJogadasPossiveis(capInferiorEsq.getLocalDestino(), novasCapturas));
		}

		//Casa Inferior Direita
		if(verificarCaptura(capInferiorDir, ultimasCapturas)) {
			novasCapturas = new ArrayList<>(ultimasCapturas);
			novasCapturas.add(capInferiorDir.getPecaCapturada());
			capturasPossiveis.add(capInferiorDir);
			capInferiorDir.setProxCaptura(calcularJogadasPossiveis(capInferiorDir.getLocalDestino(), novasCapturas));
		}

		return capturasPossiveis;
	}

	private boolean verificarCaptura(Captura captura, List<PecaJogador> ultimasCapturas) {
		if(captura != null) {
			PecaJogador pecaCapturada = captura.getPecaCapturada();
			int[] destinoCaptura = captura.getLocalDestino();
			return this.tabuleiro[destinoCaptura[0]][destinoCaptura[1]] == null
					&& pecaCapturada != null
					&& pecaCapturada.validarPeca(this.jogadores.get(jogadorTurno))
					&& !ultimasCapturas.contains(pecaCapturada);
		}
		return false;
	}
}
