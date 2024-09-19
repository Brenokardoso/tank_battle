/***********************************************************************
*Nome: CAnimacao
*Descricao: classe responsaggggggggpppgggpppppppgggggggggggvel pelo armazenamento de uma animacao
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

public class CAnimacao 
{
	/*Atributos da classe*/
	int iQuadroAtual;
	CQuadro[] vetorQuadros=null;
	int iFPS;
	boolean bRepeticao;
	CTempo tempo = null;
	
	/***********************************************************
	*Nome: CAnimacao()
	*Descri��o: Construtor da classe
	*Parametros: CQuadro[]
	*Retorno: Nenhum
	************************************************************/
	public CAnimacao(CQuadro[] pQuadros)
	{
		iFPS = 1;
		iQuadroAtual=0;
		vetorQuadros = pQuadros;
		bRepeticao = true;
	}
	
	/***********************************************************
	*Nome: finalizada()
	*Descri��o: verifica se a anima��o acabou ou n�o
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	public boolean finalizada()
	{
		if (bRepeticao)
		{
			return false;
		}
		
		return (iQuadroAtual >= vetorQuadros.length);
	}
	
	/***********************************************************
	*Nome: getQuadroAtual()
	*Descri��o: devolve o quadro atual da anima��o
	*Parametros: Nenhum
	*Retorno: Quadro
	************************************************************/
	public CQuadro getQuadroAtual()
	{
		if (iQuadroAtual >= 0 && iQuadroAtual < vetorQuadros.length)
		{
			return vetorQuadros[iQuadroAtual];
		}
		
		return vetorQuadros[vetorQuadros.length-1];
	}
	
	/***********************************************************
	*Nome: reinicia()
	*Descri��o: reinicia a anima��o
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void reinicia()
	{
		tempo.reinicia((iFPS>0) ? 1000/iFPS : 1);
		iQuadroAtual = 0;
	}
	
	/***********************************************************
	*Nome: setIntervaloQuadro()
	*Descri��o: configura o tempo de intervalo entre os quadros
	*Parametros: int
	*Retorno: Nenhum
	************************************************************/
	public void setIntervaloQuadro(int interval, CGerenciadorTempo timeManager)
	{
		if (tempo==null)
		{
			tempo = new CTempo();
			tempo.gerenciadorTempo = timeManager;
		}
		iFPS = interval;
		tempo.reinicia((iFPS>0) ? 1000/iFPS : 1);
	}
	
	/***********************************************************
	*Nome: atualiza()
	*Descri��o: atualiza os quadros da anima��o com base no tempo
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void atualiza()
	{
		tempo.atualiza();
		
		if (tempo.fimIntervalo())
		{
			if(bRepeticao)
			{
				iQuadroAtual++;
				iQuadroAtual %= vetorQuadros.length;
			}
			else
			{
				iQuadroAtual += (iQuadroAtual < vetorQuadros.length)? 1: 0;
			}
			tempo.reinicia();
		}
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		tempo = null;
	}
}
