package TankImplementation;

import java.util.ArrayList;

import TankSimulation.BlocoCenario;
import TankSimulation.Tank;
import TankSimulation.TankArena;
import javaengine.CSprite;
import javaengine.CVetor2D;

public class EnemyTank extends Tank {

	public EnemyTank(CSprite[] sprite, TankArena arena) {
		super(sprite, arena);
	}

	float anguloAtual = retornaAnguloCanhao();
	BlocoCenario atualPosition = retornaPosicaoAtual();
	boolean state;

	boolean initState() {
		if (state == false) {
			atualizaMovimento();
			atualizaTempos();
			atualizaAtingido();
			atualizaEscudo();
			state = true;
		}
		return state;
	}

	public void myDeathTank() {
		BlocoCenario meuBloco = retornaPosicaoAtual();
		BlocoCenario blocoInimigo = retornaBlocoTankInimigo();

		if (distanciaRelativa(meuBloco, blocoInimigo) <= 6) {
			if (!temParedeEntre(blocoInimigo)) {
				if (qtdTiros > 0) {
					rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
					atirar();

				} else {
					if (temPowerUp() == true) {
						movePara(retornaBlocoPowerUP());

					}
				}
			}
		} else {
			if (temPowerUp() == true) {
				if (temEscudo() == true) {
					movePara(retornaBlocoPowerUP());
					iniciaEscudo();

				}
				pararMovimento();
				movePara(retornaBlocoPowerUP());

			}
			if (qtdTiros > 0) {
				movePara(posicaoLivreParaAtirar());

			} else {
				movePara(escolheDestinoFuga(blocoInimigo));
			}

		}

	}

	private boolean temParedeEntre(BlocoCenario blocoInimigo) {
		BlocoCenario posicaoAtual = retornaPosicaoAtual();
		int linhaAtual = posicaoAtual.linha;
		int colunaAtual = posicaoAtual.coluna;
		int linhaInimigo = blocoInimigo.linha;
		int colunaInimigo = blocoInimigo.coluna;

		// Verifica ao longo da linha reta se há parede entre o tanque e o inimigo
		while (linhaAtual != linhaInimigo || colunaAtual != colunaInimigo) {
			if (temParede(linhaAtual, colunaAtual)) {
				return true; // Há uma parede no caminho
			}
			if (linhaAtual < linhaInimigo)
				linhaAtual++;
			else if (linhaAtual > linhaInimigo)
				linhaAtual--;

			if (colunaAtual < colunaInimigo)
				colunaAtual++;
			else if (colunaAtual > colunaInimigo)
				colunaAtual--;
		}
		return false; // Nenhuma parede no caminho
	}

	// Método para encontrar uma posição livre onde o tanque possa atirar
	private BlocoCenario posicaoLivreParaAtirar() {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); // Se não encontrar, mantém a posição atual
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
