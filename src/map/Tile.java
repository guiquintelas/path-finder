package map;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
	public int x;
	public int y;
	public static final int SIZE = 16;
	
	private boolean wall = false;
	private boolean isInicio = false;
	private boolean isFinal = false;
	private boolean isCaminho = false;
	public boolean isCF = false;
	public boolean inicioAtual = false;
	
	public int pontos = 0;
	public int distI = 0;
	
	public Tile parent = null;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public int getX() {
		return x * SIZE;
	}
	
	public int getY() {
		return y * SIZE;
	}
	
	public void pintar(Graphics2D g) {
		
		if (isCaminho) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(getX(), getY(), SIZE, SIZE);
		}
		
		if (wall) {
			g.setColor(Color.BLACK);
			g.fillRect(getX(), getY(), SIZE, SIZE);
		}	
		
		if (isInicio) {
			g.setColor(Color.YELLOW);
			g.fillRect(getX(), getY(), SIZE, SIZE);
		}
		
		if (inicioAtual) {
			g.setColor(Color.CYAN);
			g.fillRect(getX(), getY(), SIZE, SIZE);
		}
		
		if (isFinal || isCF) {
			g.setColor(Color.RED);
			g.fillRect(getX(), getY(), SIZE, SIZE);
		}		
		
		g.setColor(Color.GRAY);
		g.drawLine(getX(), getY() + SIZE -1, getX() + SIZE - 1, getY() + SIZE  -1);
		g.drawLine(getX() + SIZE -1, getY(), getX() + SIZE -1, getY() + SIZE - 1);
		
		g.setColor(Color.BLACK);
	    if (pontos != 0) g.drawString(pontos + "", getX() +2, getY() +12);

	}

	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		if (isFinal || isInicio) return;
		this.wall = wall;
	}

	public boolean isInicio() {
		return isInicio;
	}

	public void setInicio(boolean isInicio) {
		this.isInicio = isInicio;
		System.out.println("Inicio em " + x/Tile.SIZE + " , " + y/Tile.SIZE);
		Finder.setTileInicial(this);
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean setFinal(boolean isFinal) {
		if (isInicio) return false;
		this.isFinal = isFinal;
		System.out.println("Final em " + x/Tile.SIZE + " , " + y/Tile.SIZE);
		Finder.tileFinal = this;
		return true;
	}
	
	public boolean isCaminho() {	
		return isCaminho;

	}
	
	public void setCaminho(boolean caminho) {
		this.isCaminho = caminho;
				
	}
	
	public boolean semSaida() {
		int bloqueios = 0;
		
		if (x > 0) {
			Tile tileA = TileMap.tilemap[x - 1][y];
			
			if (tileA.isWall() || tileA.isCaminho()) {
				bloqueios++;
			}
	
		} else {
			bloqueios++;
		}
		
		if (x < TileMap.WIDTH -1) {
			Tile tileA = TileMap.tilemap[x + 1][y];
			
			if (tileA.isWall() || tileA.isCaminho()) {
				bloqueios++;
			}
		}else {
			bloqueios++;
		}
		
		if (y > 0) {
			Tile tileA = TileMap.tilemap[x][y - 1];
			
			if (tileA.isWall() || tileA.isCaminho()) {
				bloqueios++;
			}
		}else {
			bloqueios++;
		}
		
		if (y < TileMap.HEIGHT - 1) {
			Tile tileA = TileMap.tilemap[x][y + 1];
			if (tileA.isWall() || tileA.isCaminho()) {
				bloqueios++;
			}
		}else {
			bloqueios++;
		}
		
		return bloqueios == 4;
	}
	
}
