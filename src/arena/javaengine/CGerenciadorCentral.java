/***********************************************************************
ar *Nome: CGerenciador
*Descri��o: Classe responsavel pelo gerenciamento da aplica��o
*Autor: Silvano Malfatti
*Data: 27/05/08.
**************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;

public class CGerenciadorCentral implements Runnable
{
	/*Constantes da classe*/
	private final int TEMPOPARADA = 10;
	
	/*Atributos da classe*/
	public CGerenciadorJanela gerenciadorJanela = null;
	public CGerenciadorGrafico gerenciadorGrafico = null;
	public CGerenciadorTempo gerenciadorTempo = null;
	public CGerenciadorDispositivos gerenciadorDispositivos = null;
	private boolean bExecutando = true;
	public Graphics2D g2d = null;
	public CFase faseAtual = null;
	public Vector vetorFases = null;
	
	/***********************************************************
	*Nome: CGerenciador()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CGerenciadorCentral()
	{
		vetorFases = new Vector();
		gerenciadorJanela = new CGerenciadorJanela(this);
		gerenciadorGrafico = new CGerenciadorGrafico();
		gerenciadorTempo = new CGerenciadorTempo();
		gerenciadorDispositivos = new CGerenciadorDispositivos(gerenciadorJanela);
	}
	
	/***********************************************************
	*Nome: inicializa()
	*Descri��o: faz as inicializa��es da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	private void inicializa()
	{
		CLog.inicializaLog();
		gerenciadorJanela.inicializaJanela();
		g2d = gerenciadorJanela.getBufferSecundario().createGraphics();
	}
	
	/***********************************************************
	*Nome: inicializaLoop()
	*Descri��o: inicializa a Thread com o loop do jogo
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void inicializaLoop()
	{
		inicializa();
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/***********************************************************
	*Nome: run()
	*Descri��o: m�todo sobreposto da interface Runnable
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void run()
	{
		while (bExecutando)
		{
			atualiza();
			trocaBuffers();
			pausa();
		}
		liberaRecursos();
		System.exit(0);
	}
	
	/***********************************************************
	*Nome: atualiza()
	*Descri��o: atualiza os elementos do jogo
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void atualiza()
	{
		gerenciadorTempo.atualiza();
		
		if (faseAtual!=null)
		{
			faseAtual.executa();
			faseAtual.atualiza();
			faseAtual.desenhaFase();
		}
	}
	
	/***********************************************************
	*Nome: pausa()
	*Descri��o: pausa o loop do jogo por um tempo
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	private void pausa()
	{
		try
		{
			Thread.sleep(TEMPOPARADA);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/***********************************************************
	*Nome: terminaJogo()
	*Descri��o: termina o jogo
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void terminaJogo()
	{
		bExecutando = false;
	}
	
	/***********************************************************
	*Nome: trocaBuffers()
	*Descri��o: realiza a troca dos buffers
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void trocaBuffers()
	{
		gerenciadorJanela.repaint();
	}
	
	/***********************************************************
	*Name: liberaRecursos()
	*Description: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	private void liberaRecursos()
	{
		gerenciadorJanela.liberaRecursos();
		gerenciadorGrafico.liberaRecursos();
		if (faseAtual!=null)
		{
			faseAtual.liberaRecursos();
		}
		g2d = null;
		System.gc();
	}
	
	/***********************************************************
	*Name: configuraFaseAtual()
	*Description: configura a cena atual que ser� mostrada
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void configuraFaseAtual(int indiceFase)
	{
		if (indiceFase < vetorFases.size())
		{
			if (faseAtual!=null)
			{
				faseAtual.liberaRecursos();
				faseAtual = null;
			}
			
			faseAtual = (CFase)vetorFases.elementAt(indiceFase);
			faseAtual.inicializa();
			gerenciadorTempo.reiniciaGerenciador();
		}
	}
	
	/***********************************************************
	*Name: adicionaFase()
	*descricao: adiciona uma fase ao vetor de fases
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void adicionaFase(CFase novaFase)
	{
		if (novaFase != null)
		{
			vetorFases.addElement(novaFase);
			
			if (vetorFases.size()==1)
			{
				configuraFaseAtual(0);
			}
		}
	}
}