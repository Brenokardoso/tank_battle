package TankSimulation;

import javaengine.CGerenciadorCentral;

public class MainSimulation {

	public static void main(String[] args) {
	
		CGerenciadorCentral gerenciador = new CGerenciadorCentral();
				
		gerenciador.gerenciadorJanela.setPosicao(0,0);		
		gerenciador.gerenciadorJanela.setTituloJanela("TANK ARENA - IA - UNICATOLICA");
		gerenciador.gerenciadorJanela.setResolucao(700,700,32);
		gerenciador.gerenciadorJanela.setTelaCheia(false);
		
		TankArena arena = new TankArena(gerenciador);
		
		gerenciador.adicionaFase(arena);
				
		//Inicia o loop principal da aplicacao
		gerenciador.inicializaLoop();
	}
}
