package TankSimulation;

import java.util.*;
import javaengine.CGerenciadorTempo;
import javaengine.CSprite;
import javaengine.CTempo;
import javaengine.CVetor2D;

public abstract class Tank {

    public BlocoCenario[][] matrizBlocos = null;
    public CSprite[] tank = new CSprite[2];
    public ArrayList<BlocoCenario> caminho = new ArrayList();
    public CGerenciadorTempo gerenciadorTempo = null;
    public int qtdTiros = 30;
    public boolean atingido = false;
    public boolean escudo = false;
    public TankArena arena = null;
    public CTempo tempoTiro = null;
    public CTempo tempoAtingido = null;
    public CTempo tempoEscudo = null;
    public boolean invencivel = false;
    protected Tank inimigo = null;
    private boolean emMovimento = false;
    private boolean pararMovimento = false;
    private PriorityQueue<BlocoCenario> filaPrioridade = null;
    private HashSet<BlocoCenario> caminhosVisitados = null;

    public Tank(CSprite[] sprite, TankArena arena) {
        this.arena = arena;
        this.matrizBlocos = arena.matrizBlocos;
        this.tank = sprite;
        this.gerenciadorTempo = arena.pGerenciador.gerenciadorTempo;
        filaPrioridade = new PriorityQueue();
        caminhosVisitados = new HashSet();
        tempoTiro = new CTempo(500);
        tempoTiro.inicia(gerenciadorTempo);
        tempoAtingido = new CTempo(0);
        tempoAtingido.inicia(gerenciadorTempo);
        tempoEscudo = new CTempo(20000);
        tempoEscudo.inicia(gerenciadorTempo);
    }

    public void configuraPosicaoInicialTanque(CVetor2D posicao) {
        tank[0].posicao.setXY(posicao.getX(), posicao.getY());
        tank[1].posicao.setXY(posicao.getX(), posicao.getY());
    }

    public boolean tankAtingido() {
        return atingido;
    }

    public abstract void executa();

    public BlocoCenario retornaBlocoTankInimigo() {
        int linha = (int) (inimigo.tank[0].posicao.getY() / TankArena.TAMANHO_QUADRO);
        int coluna = (int) (inimigo.tank[0].posicao.getX() / TankArena.TAMANHO_QUADRO);
        if (linha >= matrizBlocos.length || coluna >= matrizBlocos.length) {
            return null;
        }
        return matrizBlocos[linha][coluna];
    }

    public int rertornaTotalBalasTankInimigo() {
        return inimigo.qtdTiros;
    }

    public float retornaAnguloCanhao() {
        return tank[1].fAngle;
    }

    public BlocoCenario retornaPosicaoAtual() {
        int linha = (int) (tank[0].posicao.getY() / TankArena.TAMANHO_QUADRO);
        int coluna = (int) (tank[0].posicao.getX() / TankArena.TAMANHO_QUADRO);
        if (linha >= matrizBlocos.length || coluna >= matrizBlocos.length) {
            return null;
        }
        return matrizBlocos[linha][coluna];
    }

    public BlocoCenario retornaBlocoPowerUP() {
        if (arena.powerUpSelecionado != null) {
            int linha = (int) (arena.powerUpSelecionado.posicao.getY() / TankArena.TAMANHO_QUADRO);
            int coluna = (int) (arena.powerUpSelecionado.posicao.getX() / TankArena.TAMANHO_QUADRO);
            if (linha >= matrizBlocos.length || coluna >= matrizBlocos.length) {
                return null;
            }
            return matrizBlocos[linha][coluna];
        }
        return null;
    }

    public boolean temParede(int linha, int coluna) {
        return matrizBlocos[linha][coluna].retornaCustoBloco() > 0;
    }

    public boolean temPowerUp() {
        return arena.powerUpSelecionado != null && arena.powerUpSelecionado.bVisivel;
    }

    public boolean tankEmMovimento() {
        return emMovimento;
    }

    public boolean temEscudo() {
        return escudo;
    }

    public void pararMovimento() {
        pararMovimento = true;
    }

    public void rotacionaCanhao(float angulo) {
        tank[1].fAngle = angulo;
    }

    public void atirar() {
        if (tempoTiro.fimIntervalo() && qtdTiros > 0) {
            this.arena.criaTiro(tank[0], tank[1]);
            tempoTiro.reinicia();
            qtdTiros--;
        }
    }

    public void movePara(BlocoCenario destino) {
        if (emMovimento) {
            return;
        }
        limpaAnterior();
        filaPrioridade.clear();
        caminhosVisitados.clear();
        BlocoCenario inicio = retornaPosicaoAtual();
        if (inicio == null) {
            return;
        }
        inicio.custoMovimento = 0;
        inicio.custoEstimadoParaDestino = distanciaRelativa(inicio, destino);
        filaPrioridade.add(inicio);
        while (!filaPrioridade.isEmpty()) {
            BlocoCenario blocoAtual = filaPrioridade.poll();
            if (blocoAtual.equals(destino)) {
                caminho = reconstruirCaminho(blocoAtual);
                emMovimento = true;
                return;
            }
            caminhosVisitados.add(blocoAtual);
            for (BlocoCenario vizinho : getVizinhos(blocoAtual)) {
                if (caminhosVisitados.contains(vizinho)) {
                    continue;
                }
                int novoCustoMovimento = blocoAtual.custoMovimento + vizinho.retornaCustoBloco();
                if (!filaPrioridade.contains(vizinho)) {
                    vizinho.anterior = blocoAtual;
                    vizinho.custoMovimento = novoCustoMovimento;
                    vizinho.custoEstimadoParaDestino = novoCustoMovimento + distanciaRelativa(vizinho, destino);
                    filaPrioridade.add(vizinho);
                }
            }
        }
    }

    protected void configuraInimigo(Tank inimigo) {
        this.inimigo = inimigo;
    }

    protected void atualizaMovimento() {
        if (tank[0].movimentoEncerrado()) {
            if (pararMovimento) {
                pararMovimento = false;
                caminho.clear();
                emMovimento = false;
                return;
            }
            if (caminho.size() > 1) {
                caminho.remove(0);
                calculaRotacaoTank(tank[0].posicao, caminho.get(0).imagemBloco.posicao);
                tank[0].iniciaMovimento(700, caminho.get(0).imagemBloco.posicao);
            } else if (caminho.size() == 1) {
                caminho.remove(0);
                emMovimento = false;
            }
        }
    }

    protected void atualizaTempos() {
        tempoTiro.atualiza();
        tempoAtingido.atualiza();
    }

    protected void atualizaAtingido() {
        if (!tempoAtingido.fimIntervalo()) {
            tank[0].bVisivel = !tank[0].bVisivel;
            tank[1].bVisivel = !tank[1].bVisivel;
        } else {
            tank[0].bVisivel = true;
            tank[1].bVisivel = true;
            atingido = false;
        }
    }

    protected void atingido() {
        atingido = true;
        tempoAtingido.reinicia(2000);
    }

    protected void atualizaPosicaoSprites() {
        tank[1].posicao.setXY(tank[0].posicao.getX(), tank[0].posicao.getY());
    }

    private void calculaRotacaoTank(CVetor2D inicio, CVetor2D destino) {
        if (inicio.getX() > destino.getX()) {
            tank[0].fAngle = 180;
        } else if (inicio.getX() < destino.getX()) {
            tank[0].fAngle = 0;
        } else if (inicio.getY() > destino.getY()) {
            tank[0].fAngle = 270;
        } else {
            tank[0].fAngle = 90;
        }
    }

    private void limpaAnterior() {
        for (int linha = 0; linha < matrizBlocos.length; linha++) {
            for (int coluna = 0; coluna < matrizBlocos.length; coluna++) {
                matrizBlocos[linha][coluna].anterior = null;
            }
        }
    }

    private ArrayList<BlocoCenario> reconstruirCaminho(BlocoCenario blocoDestino) {
        ArrayList<BlocoCenario> caminho = new ArrayList();
        BlocoCenario blocoAtual = blocoDestino;
        while (blocoAtual != null) {
            caminho.add(blocoAtual);
            blocoAtual = blocoAtual.anterior;
        }
        Collections.reverse(caminho);
        return caminho;
    }

    private int distanciaRelativa(BlocoCenario blocoAtual, BlocoCenario blocoDestino) {
        if (blocoAtual != null && blocoDestino != null) {
            return Math.abs(blocoAtual.linha - blocoDestino.linha) + Math.abs(blocoAtual.coluna - blocoDestino.coluna);
        }
        return Integer.MAX_VALUE;
    }

    private ArrayList<BlocoCenario> getVizinhos(BlocoCenario blocoAtual) {
        ArrayList<BlocoCenario> vizinhos = new ArrayList();

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

    private boolean validaPosicao(int linha, int coluna) {
        return linha >= 0 && linha < matrizBlocos.length && coluna >= 0 && coluna < matrizBlocos[0].length;
    }

    public void iniciaEscudo() {
        tempoEscudo.reinicia(20000);
        arena.spriteEscudo.bVisivel = true;
        escudo = true;
    }

    public void atualizaEscudo() {
        if (!escudo) {
            return;
        }

        tempoEscudo.atualiza();
        if (tempoEscudo.fimIntervalo()) {
            arena.spriteEscudo.bVisivel = false;
            escudo = false;
            return;
        }

        arena.spriteEscudo.posicao = tank[0].posicao;
        arena.spriteEscudo.fAngle += 20;
    }
}