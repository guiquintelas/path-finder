package map;

import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import principal.Principal;
import principal.Tela;

public class TileMap {
	public static final int WIDTH = Tela.WIDTH / Tile.SIZE;
	public static final int HEIGHT = Tela.HEIGHT / Tile.SIZE;

	public static Tile[][] tilemap = new Tile[WIDTH][HEIGHT];

	public static void init() {
		if (Principal.carregarTXT) {
			try {
				int y = 0;
				for (String line : Files.readAllLines(Paths.get("C:/Users/salutar/Desktop/mapa.txt"))) {
					char[] dados = line.toCharArray();
					
					for (int x = 0; x < WIDTH; x++) {
						char atual = dados[x];
						
						tilemap[x][y] = new Tile(x, y);
						if (atual == '1') tilemap[x][y].setWall(true);
					}
					
					y++;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			for (int y = 0; y < HEIGHT; y++) {
				for (int x = 0; x < WIDTH; x++) {
					tilemap[x][y] = new Tile(x, y);
					int rand = (int) (Math.random() * 2);
					if (rand == 1)
						tilemap[x][y].setWall(true);
				}
			}
		}

		if (Principal.escreverTXT) {
			System.out.println("Escrevendo TXT mapa...");

			try {
				PrintWriter file = new PrintWriter("C:/Users/salutar/Desktop/mapa.txt", "UTF-8");

				for (int y = 0; y < HEIGHT; y++) {
					for (int x = 0; x < WIDTH; x++) {
						Tile tileA = tilemap[x][y];
						if (tileA.isWall()) {
							file.print("1");
						} else {
							file.print("0");
						}

						if (x == WIDTH - 1)
							file.println("");
					}
				}

				file.close();
				System.out.println("TXT Concluido!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void updateTiles() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				tilemap[x][y].update();
			}
		}
	}

	public static void limparTileCaminho() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (tilemap[x][y].isCaminho())
					tilemap[x][y].setCaminho(false);
			}
		}
	}

	public static void pintarTiles(Graphics2D g) {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				tilemap[x][y].pintar(g);
			}
		}
	}
	
	public static int getCountCaminhoTiles() {
		int count = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if (tilemap[x][y].isCaminho()) count ++;
			}
		}
		
		return count;
	}
}
