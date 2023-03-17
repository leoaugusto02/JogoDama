package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tabuleiro {
	 private List<String> casasVazias;
	 private List<String> casasDisponiveis;
	 
	 public Tabuleiro() {
		 final String[] colunasImpar = {"A", "C", "E", "G"};
		 final String[] colunasPar = {"B", "D", "F", "H"};
		 this.casasDisponiveis = new ArrayList<>();
		 for(byte linha = 0; linha <= 8; linha++) {
			 String[] colunas = linha % 2 == 0 ? colunasPar: colunasImpar;
	    	 for(String coluna : colunas) {
	    		 this.casasDisponiveis.add(linha + coluna);
	    	 }
		 }
		 this.casasVazias = new LinkedList<>();
	 }
	 
	 public boolean validarMovimento(String destino){
		return false; 
	 }
	 
	 public void addCasaVazia(String casa) {
		 
	 }
	 
	 public void removerCasaVazia(String casa) {
		 
	 }
}
