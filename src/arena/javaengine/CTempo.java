/***********************************************************************
*Nome: CTempo
*Descri��o: classe respons�vel pela controle de um intervalo de tempo
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

public class CTempo 
{
	/*Atributos da classe*/
	public int finalIntervalo;
	public int tempoAtual; 
	public CGerenciadorTempo gerenciadorTempo=null;
	
	/***********************************************************
	*Nome:CTimer
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CTempo()
	{
		tempoAtual = 0;
		finalIntervalo = 1000;
		gerenciadorTempo = null;
	}
	
	/***********************************************************
	*Nome:CTempo()
	*Descri��o: Construtor da classe
	*Parametros: Int
	*Retorno: Nenhum
	************************************************************/
	public CTempo(int intervalo)
	{
		tempoAtual = 0;
		finalIntervalo = intervalo;
		gerenciadorTempo = null;
	}
	
	/***********************************************************
	*Name:atualiza()
	*Description: Atualiza os intervalos de tempo
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void atualiza()
	{
		if (gerenciadorTempo!=null)
		{
			tempoAtual += (int)gerenciadorTempo.getTempoQuadroAtual();
		}
	}
	
	/***********************************************************
	*Name:fimIntervalo()
	*Description: Retorna se o tempo acabou ou nao
	*Params: Nenhum
	*Return: bool
	************************************************************/
	public boolean fimIntervalo()
	{
		if (tempoAtual >= finalIntervalo)
			return true;

		return false;
	}
	
	/***********************************************************
	*Name: reinicia()
	*Description: Reinicializa os intervalos de tempo
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void reinicia()
	{
		tempoAtual %= finalIntervalo;
	}
	
	/***********************************************************
	*Name: reinicia()
	*Description: Reinicializa os intervalos de tempo com um novo final
	*Params: undigned int
	*Return: Nenhum
	************************************************************/
	public void reinicia(int novoTempo)
	{
		tempoAtual = 0;
		finalIntervalo = novoTempo;
	}
	
	/***********************************************************
	*Name: getTempoAtual()
	*Description: Retorna o tempo passado desde a inicializa��o do timer em ms
	*Params: Nenhum
	*Return: unsigned int
	************************************************************/
	public int getTempoAtual()
	{
		return tempoAtual;
	}
	
	/***********************************************************
	*Name: inicia()
	*Description: Inicia o timer
	*Params: CGerenciador
	*Return: bool
	************************************************************/
	public boolean inicia(CGerenciadorTempo pGerenciador)
	{
		if (pGerenciador!=null)
		{
			gerenciadorTempo = pGerenciador;
			return true;
		}

		return false;
	}
	
	
	
	/***********************************************************
	*Name: liberaRecursos()
	*Description: libera recursos
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		gerenciadorTempo = null;
	}
}
