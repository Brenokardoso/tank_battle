/***********************************************************************
*Nome: CFase
*Descri��o: classe respons�vel pela cria��o da fase
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.util.Vector;

public abstract class CFase 
{
	/*Atributos da classe*/
	Vector vetorCamadas = null;
	Vector vetorSprites = null;
	public CGerenciadorCentral pGerenciador = null;
	
	/***********************************************************
	*Nome: CFase
	*Descri��o: Construtor da classe
	*Parametros: CGerenciador
	*Retorno: Nenhum
	************************************************************/
	public CFase(CGerenciadorCentral gerenciador)
	{
		vetorCamadas = new Vector();
		vetorSprites = new Vector();
		pGerenciador = gerenciador;
	}
	
	/***********************************************************
	*Nome: executa()
	*Descri��o: M�todo abstrato onde ser� implementada a l�gica da fase
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public abstract void executa();
	
	/***********************************************************
	*Nome: inicializa()
	*Descri��o: M�todo abstrato onde ser�o feitas as inicializa��es
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public abstract void inicializa();
	
	/***********************************************************
	*Nome: desenhaFase()
	*Descri��o: M�todo onde desenhados os componentes da fase
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void desenhaFase()
	{
		//Renderiza as camadas
		for (int iIndex=0; iIndex<(int)vetorCamadas.size(); iIndex++)
		{
			if (((CCamada)vetorCamadas.elementAt(iIndex)).bDesenhoAutomatico)
			{
				((CCamada)vetorCamadas.elementAt(iIndex)).desenhaCamada(pGerenciador.g2d);
			}
		}
		
		//Renderiza os sprites
		for (int iIndex=0; iIndex<(int)vetorSprites.size(); iIndex++)
		{
			if (((CSprite)vetorSprites.elementAt(iIndex)).bAutoDesenho)
			{
				((CSprite)vetorSprites.elementAt(iIndex)).desenhaSprite(pGerenciador.g2d);
			}
		}
	}
	
	/***********************************************************
	*Nome: atualiza()
	*Descri��o: M�todo onde s�o chamados os comandos de atualiza�ao
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void atualiza()
	{
		//Atualiza as camadas
		for (int iIndex=0; iIndex<(int)vetorCamadas.size(); iIndex++)
		{
			((CCamada)vetorCamadas.elementAt(iIndex)).deslocaCamada();
		}
		
		//Atualiza os sprites
		for (int iIndex=0; iIndex<(int)vetorSprites.size(); iIndex++)
		{
			((CSprite)vetorSprites.elementAt(iIndex)).atualizaSprite();
		}
	}
	
	/***********************************************************
	*Nome: criaCamada()
	*Descri��o: M�todo que cria uma camada
	*Parametros:CVetor2D, boolean
	*Retorno: CCamada
	************************************************************/
	public CCamada criaCamada(CVetor2D tamanhoBloco, boolean autoDesenho)
	{
		CCamada novaCamada = new CCamada(pGerenciador,tamanhoBloco);
		
		if (novaCamada!=null)
		{
			novaCamada.bDesenhoAutomatico = autoDesenho;
			vetorCamadas.addElement(novaCamada);
			return novaCamada;
		}
		
		return null;
	}
	
	/***********************************************************
	*Nome:criaCamada()
	*Descri��o: M�todo que cria uma camada
	*Parametros:CVetor2D, CVetor2D, boolean
	*Retorno: CCamada
	************************************************************/
	public CCamada criaCamada(CVetor2D tamanhoCamada, CVetor2D tamanhoBloco, boolean autoDesenho)
	{
		CCamada novaCamada = new CCamada(pGerenciador,tamanhoCamada, tamanhoBloco);
		
		if (novaCamada!=null)
		{
			novaCamada.bDesenhoAutomatico = autoDesenho;
			vetorCamadas.addElement(novaCamada);
			return novaCamada;
		}
		
		return null;
	}
	
	/***********************************************************
	*Nome:criaSprite()
	*Descri��o: M�todo que cria um sprite
	*Parametros: CGerenciador, CCamada, CVetor2D, boolean
	*Retorno:CSprite
	************************************************************/
	public CSprite criaSprite(CGerenciadorCentral pGerenciador, CCamada pCamada, CVetor2D tamanhoQuadro, boolean autoDesenho)
	{
		CSprite novoSprite = new CSprite(pGerenciador,pCamada, tamanhoQuadro);
		
		if (novoSprite!=null)
		{
			novoSprite.bAutoDesenho = autoDesenho;
			vetorSprites.addElement(novoSprite);
			return novoSprite;
		}
		
		return null;
	}
	
	/***********************************************************
	*Nome: liberaRecuros()
	*Descri��o: Libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		//Libera os sprites
		for (int iIndex=(int)vetorSprites.size()-1; iIndex>=0; iIndex--)
		{
			((CSprite)vetorSprites.elementAt(iIndex)).liberaRecursos();
			vetorSprites.removeElementAt(iIndex);
		}
		vetorSprites = null;
		
		//Libera as camadas
		for (int iIndex=(int)vetorCamadas.size()-1; iIndex>=0; iIndex--)
		{
			((CCamada)vetorCamadas.elementAt(iIndex)).liberaRecursos();
			vetorCamadas.removeElementAt(iIndex);
		}
		vetorCamadas = null;
	}
}
