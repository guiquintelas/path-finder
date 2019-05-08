package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import map.Finder;
import map.Tile;
import map.TileMap;

@SuppressWarnings("serial")
public class Tela extends JPanel {
	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;
	
	private static final BufferedImage imgPP = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private static Graphics2D g;
	
	public Tela(JFrame janela) {
		
		g = (Graphics2D)imgPP.getGraphics();
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingH);
		
		Dimension din = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(din);
		setMinimumSize(din);
		setMaximumSize(din);
		janela.setContentPane(this);
		
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				
			}
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (Principal.numTile == 0) {
						TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setInicio(true);
						Principal.numTile++;
					} else if (Principal.numTile == 1) {
						if (TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setFinal(true)) 
							Principal.numTile++;;
					}
					TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setWall(true);
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setWall(false);
				}
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mouseEntered(MouseEvent e) {
				
			}
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent arg0) {
				
			}
			
			public void mouseDragged(MouseEvent e) {
				if (e.getX() >= WIDTH || e.getX() < 0) return;
				if (e.getY() >= HEIGHT || e.getY() < 0) return;

				if (SwingUtilities.isLeftMouseButton(e)) {
					TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setWall(true);;
				}
				
				if (SwingUtilities.isRightMouseButton(e)) {
					TileMap.tilemap[(int)Math.floor(e.getX()/Tile.SIZE)][(int)Math.floor(e.getY()/Tile.SIZE)].setWall(false);;
				}
			}
		});
		
		janela.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {

			}
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !Principal.inicio) {
					Principal.inicio = true;
					System.out.println("INICIO");
					Principal.timeInicio = System.nanoTime();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_R) {
					Finder.restart();
				}
			}
		});
		
		System.out.println("Tela init");
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		TileMap.pintarTiles(this.g);
		
		
		
		g.drawImage(imgPP, 0, 0, this);
		
		repaint();
	}

}
