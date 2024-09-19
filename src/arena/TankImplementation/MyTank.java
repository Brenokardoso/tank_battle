package TankImplementation;

import java.util.ArrayList;
import java.util.Random;

import TankSimulation.BlocoCenario;
import TankSimulation.Tank;
import TankSimulation.TankArena;
import javaengine.CSprite;
import javaengine.CVetor2D;

// rertornaTotalBalasTankInimigo() - retorna verdadeiro se o inimigo está zerado de balas
// 
// retornaBlocoTankInimigo() - retorna o bloco que está o inimigo agora
// 
// retornaAnguloCanhao() - retorna o angulo do canhao do meu tank no momento
// 
// retornaPosicaoAtual() - retorna o bloco da minha posicao atual
// 
// retornaBlocoPowerUP()  - retorna o bloco do powerUp
// 
// temParede(int linha, int coluna)  - verifica se tem parede nessa posicao
// 
// temPowerUp()  - verdadeiro se tem powerup no cenário
// 
// tankEmMovimento() - veradeiro se o tank está em movimento
// 
// temEscudo() - veradeiro se eu estou com o escudo
// 
// pararMovimento()  - pára o movimento atual
// 
// rotacionaCanhao(float angulo) - aponta o canhao para o angulo desejado
// 
// atirar()  - atira e diminui uma unidade de bala do tanque
// 
// movePara(BlocoCenario destino) - move para um bloco destino

public class MyTank extends Tank {
	public MyTank(CSprite[] sprite, TankArena arena) {
		super(sprite, arena);
	}

	int state = 0;
	float anguloAtual = retornaAnguloCanhao();
	boolean atirar;
	int linha;
	int coluna;
	BlocoCenario atualPosition = retornaPosicaoAtual();

	public void myDeathTank() {
		BlocoCenario meuBloco = retornaPosicaoAtual();
		BlocoCenario blocoInimigo = retornaBlocoTankInimigo();

		// Se tiver power-up, prioriza pegar
		if (temPowerUp()) {
			movePara(retornaBlocoPowerUP());
		}

		// Se o inimigo estiver perto e você não tem escudo, fuja
		else if (distanciaRelativa(meuBloco, blocoInimigo) < 3 && !temEscudo()) {
			BlocoCenario destinoFuga = escolheDestinoFuga(blocoInimigo);
			if (destinoFuga != null) {
				movePara(destinoFuga);
			}
		}

		// Caso sem tiros ou sem escudo, ativa escudo
		else if (qtdTiros == 0 || !temEscudo()) {
			iniciaEscudo();
		}

		// Verifica se não tem parede e atira no inimigo
		if (blocoInimigo != null && atualPosition != null) {
			int linhaAtual = atualPosition.linha;
			int colunaAtual = atualPosition.coluna;

			if (!temParede(linhaAtual, colunaAtual)) {
				rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
				atirar();
			}
		}

		// Verifica distância do inimigo e atira
		else if (distanciaRelativa(meuBloco, blocoInimigo) >= 3 && qtdTiros > 0) {
			rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
			atirar();
		}

		// Condição adicional para atirar se o inimigo estiver perto
		else if (distanciaRelativa(meuBloco, blocoInimigo) < 3 && qtdTiros > 0) {
			rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
			if (temParede(meuBloco.linha, meuBloco.coluna) || temParede(meuBloco.linha, meuBloco.coluna)){

			}
			atirar();
		}

		atualizaMovimento();
		atualizaEscudo();
	}

	// Calcula a distância relativa entre dois blocos
	private int distanciaRelativa(BlocoCenario blocoAtual, BlocoCenario blocoDestino) {
		if (blocoAtual != null && blocoDestino != null) {
			return Math.abs(blocoAtual.linha - blocoDestino.linha) + Math.abs(blocoAtual.coluna - blocoDestino.coluna);
		}
		return Integer.MAX_VALUE;
	}

	// Calcula o ângulo necessário para mirar em um bloco
	private float calculaAnguloParaBloco(BlocoCenario blocoDestino) {
		CVetor2D posicaoTank = tank[0].posicao;
		CVetor2D posicaoInimigo = blocoDestino.imagemBloco.posicao;

		double deltaX = posicaoInimigo.getX() - posicaoTank.getX();
		double deltaY = posicaoInimigo.getY() - posicaoTank.getY();

		return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
	}

	// Escolhe o melhor bloco para fugir do inimigo
	private BlocoCenario escolheDestinoFuga(BlocoCenario blocoInimigo) {
		ArrayList<BlocoCenario> vizinhos = getVizinhos(retornaPosicaoAtual());

		BlocoCenario melhorDestino = null;
		int maiorDistancia = -1;

		for (BlocoCenario vizinho : vizinhos) {
			int distancia = distanciaRelativa(vizinho, blocoInimigo);
			if (distancia > maiorDistancia && !temParede(vizinho.linha, vizinho.coluna)) {
				melhorDestino = vizinho;
				maiorDistancia = distancia;
			}
		}
		return melhorDestino;
	}

	// Obtém os blocos vizinhos
	private ArrayList<BlocoCenario> getVizinhos(BlocoCenario blocoAtual) {
		ArrayList<BlocoCenario> vizinhos = new ArrayList<>();

		int[][] direcoes = {
				{ -1, 0 }, // Norte
				{ 1, 0 }, // Sul
				{ 0, -1 }, // Oeste
				{ 0, 1 } // Leste
		};

		for (int[] direcao : direcoes) {
			int linhaVizinho = blocoAtual.linha + direcao[0];
			int colunaVizinho = blocoAtual.coluna + direcao[1];
			if (validaPosicao(linhaVizinho, colunaVizinho)) {
				BlocoCenario vizinho = matrizBlocos[linhaVizinho][colunaVizinho];
				if (vizinho.retornaCustoBloco() != Integer.MAX_VALUE) {
					vizinhos.add(vizinho);
				}
			}
		}
		return vizinhos;
	}

	// Valida se a posição é válida no cenário
	private boolean validaPosicao(int linha, int coluna) {
		return linha >= 0 && linha < matrizBlocos.length && coluna >= 0 && coluna < matrizBlocos[0].length;
	}

	@Override
	public void executa() {
		myDeathTank();
	}

}
