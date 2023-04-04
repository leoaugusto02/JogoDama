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
	 private Map<PecaJogador, List<int[]>> pecasCaptura;
	 
	 public PartidaDama(byte tamanho) {
		 this.tabuleiro = Arrays.stream(new PecaJogador[tamanho][tamanho]).toArray(PecaJogador[][]::new);
		 this.jogadores = new HashMap<>();
		 this.jogadorTurno = 1;
		 this.flagCaptura = false;
		 this.pecasCaptura = new HashMap<>();
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
		 
		 System.out.println("Turno Jogador " + this.jogadorTurno);
		 
		 final int[] origem = selecionarOrigem(sc);
		 final int[] destino = selecionarDestino(origem, sc);
		 
		 if(flagCaptura) {
			 flagCaptura = false;
			 moverPecaCaptura(origem, destino);
			 buscarMelhorJogada();
		 } else {
			 moverPeca(origem, destino);
			 buscarCaptura(destino);
		 }
		 
		 this.jogadorTurno = (byte) (this.jogadorTurno == 1 ? 2 : 1);
	 }
	 
	 private void buscarMelhorJogada() {
		 List<Integer> jogadas = new ArrayList<>();
		 for(PecaJogador pecaCaptura : pecasCaptura.keySet()) {
			 int[] destinoCaptura = pecaCaptura.getPos();
			 int jogadas = calcularJogada(destinoCaptura);
		 }
		 
	 }
	 
	 private int calcularJogada(int[] destinoCaptura) {
		 final int linhaBaixo = destinoCaptura[0] - 1;
		 final int linhaCima = destinoCaptura[0] + 1;
		 final int colunaEsq = destinoCaptura[1] - 1;
		 final int colunaDir =  destinoCaptura[1] + 1;
		 int pecasCapturadas = 0;
		 
		 
		 PecaJogador pecaSuperiorEsq = this.tabuleiro[linhaCima][colunaEsq];
		 PecaJogador pecaInferiorEsq = this.tabuleiro[linhaBaixo][colunaEsq];
		 PecaJogador pecaSuperiorDir = this.tabuleiro[linhaCima][colunaDir];
		 PecaJogador pecaInferiorDir = this.tabuleiro[linhaBaixo][colunaDir];
		 
		 Jogador jogador = this.jogadores.get(this.jogadorTurno);
		 
		 //Diagonal Esquerda - Direita
		 if(pecaInferiorDir == null && pecaSuperiorEsq != null && !pecaSuperiorEsq.validarPeca(jogador)) {
			 addPecaCaptura(pecaSuperiorEsq, new int[]{linhaBaixo, colunaDir});
		 } else if(pecaSuperiorEsq == null && pecaInferiorDir != null && !pecaInferiorDir.validarPeca(jogador)) {
			 addPecaCaptura(pecaInferiorDir, new int[]{linhaCima, colunaEsq});
		 }
		 
		//Diagonal Direita - Esquerda
		 if(pecaInferiorEsq == null && pecaSuperiorDir != null && !pecaSuperiorDir.validarPeca(jogador)) {
			 addPecaCaptura(pecaSuperiorDir, new int[]{linhaBaixo, colunaEsq});
		 } else if(pecaSuperiorDir == null && pecaInferiorEsq != null && !pecaInferiorEsq.validarPeca(jogador)) {
			 addPecaCaptura(pecaInferiorEsq, new int[]{linhaCima, colunaDir});
		 }
		 
		 return 1;
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
				 
				 Jogador jogador = this.jogadores.get(this.jogadorTurno);
				 
				 //Diagonal Esquerda - Direita
				 if(pecaInferiorDir == null && pecaSuperiorEsq != null && !pecaSuperiorEsq.validarPeca(jogador)) {
					 addPecaCaptura(pecaSuperiorEsq, new int[]{linhaBaixo, colunaDir});
				 } else if(pecaSuperiorEsq == null && pecaInferiorDir != null && !pecaInferiorDir.validarPeca(jogador)) {
					 addPecaCaptura(pecaInferiorDir, new int[]{linhaCima, colunaEsq});
				 }
				 
				//Diagonal Direita - Esquerda
				 if(pecaInferiorEsq == null && pecaSuperiorDir != null && !pecaSuperiorDir.validarPeca(jogador)) {
					 addPecaCaptura(pecaSuperiorDir, new int[]{linhaBaixo, colunaEsq});
				 } else if(pecaSuperiorDir == null && pecaInferiorEsq != null && !pecaInferiorEsq.validarPeca(jogador)) {
					 addPecaCaptura(pecaInferiorEsq, new int[]{linhaCima, colunaDir});
				 } 
			 }
		 }
		 
	 private void addPecaCaptura(PecaJogador pecaCaptura, int[] casaDestinoCaptura) {
			 List<int[]> destinoCaptura = this.pecasCaptura.containsKey(pecaCaptura) 
					 ? this.pecasCaptura.get(pecaCaptura)
					 : new LinkedList<>();
			 destinoCaptura.add(casaDestinoCaptura);
			 this.pecasCaptura.put(pecaCaptura, destinoCaptura);
	 }
	 
	 private void moverPecaCaptura(int[] origem, int[] destino) {
		 int pecaCapturadaLinha = destino[0] > origem[0] ? destino[0] - 1 : origem[0] - 1;
		 int pecaCapturadaColuna = destino[1] > origem[1] ? destino[1] - 1 : origem[1] - 1;
		 
		 this.tabuleiro[pecaCapturadaLinha][pecaCapturadaColuna] = null;
		 this.jogadores.get(this.jogadorTurno).pecaCapturada();
		 
		 moverPeca(origem, destino);
	 }
	 
	 private void moverPeca(int[] origem, int[] destino) {
		 PecaJogador pecaSelecionada = this.tabuleiro[origem[0]][origem[1]];
		 System.out.println(pecaSelecionada.descricao());
		 this.tabuleiro[origem[0]][origem[1]] = null;
		 
		 this.tabuleiro[destino[0]][destino[1]] = pecaSelecionada;
		 pecaSelecionada.setPos(destino);
	 }
	 
	 private int[] selecionarOrigem(Scanner sc){
		 while(true) {
			 System.out.println("Peça Origem: ");
			 final String pecaOrigem = converterCoordenada(sc.nextLine());
			 final int[] origem = {Integer.parseInt(pecaOrigem.substring(0, 1)), Integer.parseInt(pecaOrigem.substring(1))};
			 if(verificarMovimentoOrigem(origem)) {
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
		 
		 int linha = this.jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;
		 if(pecaJogador == null ||
				 !pecaJogador.validarPeca(this.jogadores.get(this.jogadorTurno)) ||
				 (origem[1] - 1 > -1 && this.tabuleiro[linha][origem[1] - 1] != null) ||
				 (origem[1] + 1 < 8 && this.tabuleiro[linha][origem[1] + 1] != null)) {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 private int[] selecionarDestino(int[] origem, Scanner sc){
		 while(true) {
			 System.out.println("Peça Destino: ");
			 final String pecaDestino = converterCoordenada(sc.nextLine());
			 final int[] destino = {Integer.parseInt(pecaDestino.substring(0, 1)), Integer.parseInt(pecaDestino.substring(1))};
			 if(verificarMovimentoDestino(origem, destino)) {
				 return destino;
			 }
			 System.out.println("Movimento invalido");
		 }
	 }
	 
	 private boolean verificarMovimentoDestino(int[] origem, int[] destino) {
		 if(this.pecasCaptura.size() > 0) {
			 List<int[]> destinoCaptura = this.pecasCaptura.get(this.tabuleiro[origem[0]][origem[1]]);
			 if(!Arrays.equals(destinoCaptura.get(0), destino)) { 
				 return false;
			 }
			 destinoCaptura.remove(0);
			 return true;
		 }
		 
		 int linha = this.jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;
		 if(this.tabuleiro[destino[0]][destino[1]] != null ||
				 destino[0] != linha ||
				 (destino[1] != origem[1] - 1 && destino[1] != origem[1] + 1)) {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 private String converterCoordenada(String coordenada){
		 return Integer.parseInt(coordenada.substring(0, 1)) - 1 + "" + ((int) coordenada.charAt(1) - 64 - 1);
	 }
	 

}
