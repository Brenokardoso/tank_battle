/***********************************************************************
*Nome: UGETimeManager
*Descri��o: classe respons�vel pelo gerenciamento de tempo
*Autor: Silvano Malfatti
*Data: 06/06/08
************************************************************************/

/* Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CGerenciadorDispositivos implements KeyListener, MouseListener, MouseMotionListener
{
	//Atributos
	private final int TOTALTECLAS=256;
	private boolean[] estadoTeclas = null;
	private boolean[] estadoAnteriorTeclas = null;
	private CVetor2D posicaoMouse = null;
	
	/***********************************************************
	*Nome: CGereciadorDispositivos
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CGerenciadorDispositivos(CGerenciadorJanela pJanela)
	{
		posicaoMouse = new CVetor2D();
		estadoTeclas = new boolean[TOTALTECLAS];
		estadoAnteriorTeclas = new boolean[TOTALTECLAS];
		pJanela.addKeyListener(this);
		pJanela.addMouseMotionListener(this);
		pJanela.addMouseListener(this);
	}
	
	/***********************************************************
	*Name: gePosicaoMouse()
	*Description: retorna a posicao do mouse
	*Params: void
	*Return: CVetor2D
	************************************************************/
	public CVetor2D getPosicaoMouse()
	{
		return posicaoMouse;
	}
	
	/***********************************************************
	*Name: teclaJaPressionada()
	*Description: retorna se a tecla ja estava pressionada
	*Params: int
	*Return: boolean
	************************************************************/
	public boolean teclaJaPressionada(int iCodigoTecla)
	{
		return estadoTeclas[iCodigoTecla];
	}
	
	/***********************************************************
	*Name: teclaPressionada()
	*Description: retorna se a tecla foi pressionada agora
	*Params: int
	*Return: boolean
	************************************************************/ 
	public boolean teclaPressionada(int iCodigoTecla)
	{
		return (!(estadoAnteriorTeclas[iCodigoTecla]) && (estadoTeclas[iCodigoTecla])) ? true : false;
	}
	
	/***********************************************************
	*Name: teclaSolta()
	*Description: retorna se a tecla foi solta
	*Params: unsigned char
	*Return: bool
	************************************************************/ 
	public boolean teclaSolta(int iCodigoTecla)
	{
		return ((estadoAnteriorTeclas[iCodigoTecla]) && !(estadoTeclas[iCodigoTecla])) ? true : false;
	}
	
	/***********************************************************
	*Name: keyPressed()
	*Description: m�todo da interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/ 
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() < TOTALTECLAS)
		{
			estadoTeclas[e.getKeyCode()]=true;
		}
	}
	
	/***********************************************************
	*Name: keyRelesead()
	*Description: m�todo da interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() < TOTALTECLAS)
		{
			estadoTeclas[e.getKeyCode()] = false;
		}
	}
	
	/***********************************************************
	*Name: keyTyped()
	*Description: m�todo da interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	/***********************************************************
	*Name: mouseMoved()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseMoved(MouseEvent e)
	{
		posicaoMouse.setX(e.getX());
		posicaoMouse.setY(e.getY());
	}
	
	/***********************************************************
	*Name: mouseDragged()
	*Description: m�todo da interface MouseMotinListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseDragged(MouseEvent e)
	{
		posicaoMouse.setX(e.getX());
		posicaoMouse.setY(e.getY());
	}
	
	/***********************************************************
	*Name: mouseExited()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseExited(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseReleased()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseReleased(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseClicked()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseClicked(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseEntered()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseEntered(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseEntered()
	*Description: m�todo da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mousePressed(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: liberaRecursos()
	*Description: Libera os recursos do gerenciador
	*Params: void
	*Return: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		estadoTeclas = null;
		estadoAnteriorTeclas = null;
	}
}
