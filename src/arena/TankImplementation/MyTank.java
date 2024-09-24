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

		if (distanciaRelativa(meuBloco, blocoInimigo) <= 8) {
			moveParaAfastamento(blocoInimigo);
			if (!temParedeEntre(blocoInimigo)) {
				if (qtdTiros > 0) {
					rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
					movePara(posicaoLivreParaAtirar());
					atirar();
					System.out.println("Atirar");
				}
				if (qtdTiros <= 0) {
					if (temPowerUp() == true) {
						movePara(retornaBlocoPowerUP());
						if (temEscudo()) {
							atualizaEscudo();
							iniciaEscudo();
							System.out.println("Shield");
						}
					} else {
						moveParaAfastamento(escolheDestinoFuga(blocoInimigo));
					}
				}
			}
		} else {
			System.out.println("Aproxima");
			moveParaAproximacao(blocoInimigo);
			if (meuBloco == blocoInimigo) {
				moveParaAfastamento(blocoInimigo);
			}
		}

	}

	private double distanciaEntreBlocos(BlocoCenario bloco1, BlocoCenario bloco2) {
		return Math.abs(bloco1.linha - bloco2.linha) + Math.abs(bloco1.coluna - bloco2.coluna);
	}

	// Método para mover o tanque em direção ao inimigo (aproximação)
	private void moveParaAproximacao(BlocoCenario blocoInimigo) {
		// Tenta se mover em direção ao inimigo se não houver parede no caminho
		if (!temParedeEntre(blocoInimigo)) {
			movePara(blocoInimigo);
		} else {
			// Caso tenha parede, tenta encontrar o melhor caminho evitando paredes
			movePara(posicaoLivreAproximacao(blocoInimigo));
		}
	}

	// Método para mover o tanque se afastando do inimigo (afastamento)

	private void moveParaAfastamento(BlocoCenario blocoInimigo) {
		// Encontra um bloco afastado do inimigo e tenta mover para lá
		BlocoCenario blocoAfastado = posicaoLivreAfastamento(blocoInimigo);
		movePara(blocoAfastado);
	}

	// Método que encontra uma posição próxima ao inimigo evitando paredes
	// (aproximação)

	private BlocoCenario posicaoLivreAproximacao(BlocoCenario blocoInimigo) {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho) && distanciaEntreBlocos(vizinho,
					blocoInimigo) < distanciaEntreBlocos(retornaPosicaoAtual(), blocoInimigo)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); // Se não encontrar, mantém a posição atual
	}

	// Método que encontra uma posição afastada do inimigo evitando paredes
	// (afastamento)
	private BlocoCenario posicaoLivreAfastamento(BlocoCenario blocoInimigo) {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho) && distanciaEntreBlocos(vizinho,
					blocoInimigo) > distanciaEntreBlocos(retornaPosicaoAtual(), blocoInimigo)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); // Se não encontrar, mantém a posição atual
	}

	private boolean temParedeEntre(BlocoCenario blocoInimigo) {
		BlocoCenario posicaoAtual = retornaPosicaoAtual();
		int linhaAtual = posicaoAtual.linha;
		int colunaAtual = posicaoAtual.coluna;
		int linhaInimigo = blocoInimigo.linha;
		int colunaInimigo = blocoInimigo.coluna;

		while (linhaAtual != linhaInimigo || colunaAtual != colunaInimigo) {
			if (temParede(linhaAtual, colunaAtual)) {
				return true;
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
		return false;
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
		initState();
		myDeathTank();
	}

}
