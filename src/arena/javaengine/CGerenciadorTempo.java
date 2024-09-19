/***********************************************************************
*Nome: CGerenciadorTempo                                           [
*Descri��o: classe respons�vel pelo gerenciamento de tempo
*Autor: Silvano Malfatti
*Data: 06/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

public class CGerenciadorTempo 
{
	/*Atributos da classe*/
	private long tempoQuadroAnterior;
	private long tempoQuadroAtual;
	private long acumuladorTempo;
	private long	contadorQuadros;
	private long	lFPS;
	private final int MININTERVAL = 5;

	/***********************************************************
	*Nome: CGerenciadorTempo()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CGerenciadorTempo()
	{
		tempoQuadroAnterior = System.currentTimeMillis();
		tempoQuadroAtual = 0;
		acumuladorTempo = 0;
		contadorQuadros = 0;
		lFPS = 0;
	}
	
	/***********************************************************
	*Nome: getTempoQuadroAnterior()
	*Descri��o: Devolve o tempo que o ultimo frame levou
	*Parametros: Nenhum
	*Retorno: long
	************************************************************/
	public long getTempoQuadroAnterior()
	{
		return tempoQuadroAnterior;
	}
	
	/***********************************************************
	*Name: getTempoQuadroAtual()
	*Description: retorna o tempo de atualiza��o do frame
	*Params: Nenhum
	*Return: int
	************************************************************/
	long getTempoQuadroAtual()
	{
		return tempoQuadroAtual;
	}
	
	/***********************************************************
	*Name: getAcumuladorTempo()
	*Description: retorna o valor do acumulador de tempo no momento
	*Params: Nenhum
	*Return: int
	************************************************************/
	public long getAcumuladorTempo()
	{
		return acumuladorTempo;
	}
	
	/***********************************************************
	*Name: liberaRecursos()
	*Description: libera recursos
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		
	}
	
	/***********************************************************
	*Name: atualiza()
	*Description: atualiza os tempos e valores
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void atualiza()
	{
		tempoQuadroAtual = 0;

		long tempoSistema=0;

		do
		{
			tempoSistema = System.currentTimeMillis();
			tempoQuadroAtual = (tempoSistema > tempoQuadroAnterior) ? (tempoSistema - tempoQuadroAnterior) : 0;
			tempoQuadroAnterior = (tempoSistema >= tempoQuadroAnterior) ? tempoQuadroAnterior : tempoSistema;
		}
		while(! (tempoQuadroAtual >= MININTERVAL));

		acumuladorTempo += tempoQuadroAtual;
		contadorQuadros++;

		if (acumuladorTempo >= 1000)
		{
			lFPS = contadorQuadros;
			contadorQuadros = 0;
			acumuladorTempo   = 0;
		}

		tempoQuadroAnterior = tempoSistema;
	}
	
	/***********************************************************
	*Name:reiniciaGerenciador()
	*Description: reinicializa o tempo do gerente
	*Params: Nenhum
	*Return: Nenhum
	************************************************************/
	public void reiniciaGerenciador()
	{
		tempoQuadroAnterior = System.currentTimeMillis();
	}
}
