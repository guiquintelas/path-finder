package map;

import java.util.ArrayList;

import principal.Principal;

public class Finder {
	private static Tile tileInicioTemp;
	private static Tile tileInicio;
	public static Tile tileFinal;
	private static Tile tileAtual;

	public static boolean chegou = false;
	private static boolean diagonal = true;

	private static ArrayList<Tile> possivel = new ArrayList<Tile>();
	private static ArrayList<Tile> usado = new ArrayList<Tile>();

	private static int indexUsadoAtual = 0;
	private static Tile usadoAtual = null;
	private static int count = 0;

	public static void update() {
		if (tileInicioTemp == null || tileFinal == null || !Principal.inicio || chegou)
			return;
		
		if (atualizaPossivel()) {
			System.out.println("acabou");
			gerarCaminho();
			chegou = true;
			Principal.rodando = false;
			System.out.println("FINAL");
			System.out.println("Tempo decorrido: " + (System.nanoTime() - Principal.timeInicio) / 1000000.0 + "ms");
		}
		pegarNovoUsado();
		
		count++;	
		
		//tileAtual.setCaminho(true);
	}

	private static void gerarCaminho() {
		Tile tile = tileFinal;
		while(tile != tileInicio) {
			tile.isCF = true;
			Tile parent = tile.parent;
			
			tile = parent;
		}
		System.out.println(possivel.size() + " TAMANHO");
		System.out.println(TileMap.getCountCaminhoTiles() + " Count Caminhos");
		System.out.println(count + " Passos");
	}

	private static void pegarNovoUsado() {
		Tile melhorTile = null;
		for (Tile tile: possivel) {
			if (melhorTile == null || tile.pontos < melhorTile.pontos) {
				melhorTile = tile;
				
				//if (melhorTile.pontos <= tileAtual.pontos - 2) break;
			}
		}
		
		possivel.remove(melhorTile);
		usado.add(melhorTile);
		indexUsadoAtual = melhorTile.distI;
		usadoAtual= melhorTile;
	}
	
	private static boolean addPossivel(Tile tileA, int custo) {
		
		int pontos = Math.abs(tileA.x - tileFinal.x) + Math.abs(tileA.y - tileFinal.y) + indexUsadoAtual+custo;
		
		if (tileA.isCaminho() && tileA.pontos > pontos) {
			tileA.setCaminho(false);
			possivel.remove(tileA);
			pontos += 2;
		}

		if (!tileA.isWall() && !tileA.isCaminho()) {
			possivel.add(tileA);
			//if (possivel.size()> 20) possivel.remove(0);
			tileA.pontos = pontos;
			tileA.distI = indexUsadoAtual + 1;
			tileA.setCaminho(true);
			if (tileA.parent == null)tileA.parent=usadoAtual;
			
			if (tileA == tileFinal) return true;
			
		}
		
		return false;
	}

	private static boolean atualizaPossivel() {
		if (usadoAtual.x > 0) {
			Tile tileA = TileMap.tilemap[usadoAtual.x - 1][usadoAtual.y];

			if (addPossivel(tileA, 0)) return true;

		}

		if (usadoAtual.x < TileMap.WIDTH - 1) {
			Tile tileA = TileMap.tilemap[usadoAtual.x + 1][usadoAtual.y];		
			
			if (addPossivel(tileA, 0)) return true;
		}

		if (usadoAtual.y > 0) {
			Tile tileA = TileMap.tilemap[usadoAtual.x][usadoAtual.y - 1];

			if (addPossivel(tileA, 0)) return true;
		}

		if (usadoAtual.y < TileMap.HEIGHT - 1) {
			Tile tileA = TileMap.tilemap[usadoAtual.x][usadoAtual.y + 1];

			if (addPossivel(tileA, 0)) return true;
		}
		
		if(!diagonal) return false;
		
		if (usadoAtual.y < TileMap.HEIGHT - 1 && usadoAtual.x < TileMap.WIDTH - 1) {
			Tile tileA = TileMap.tilemap[usadoAtual.x + 1][usadoAtual.y + 1];

			if (addPossivel(tileA, 2)) return true;
		}
		
		if (usadoAtual.y < TileMap.HEIGHT - 1 && usadoAtual.x > 0) {
			Tile tileA = TileMap.tilemap[usadoAtual.x - 1][usadoAtual.y + 1];

			if (addPossivel(tileA, 2)) return true;
		}
		
		if (usadoAtual.y > 0 && usadoAtual.x > 0) {
			Tile tileA = TileMap.tilemap[usadoAtual.x - 1][usadoAtual.y - 1];

			if (addPossivel(tileA, 2)) return true;
		}
		
		if (usadoAtual.y > 0 && usadoAtual.x < TileMap.WIDTH - 1) {
			Tile tileA = TileMap.tilemap[usadoAtual.x + 1][usadoAtual.y - 1];

			if (addPossivel(tileA, 2)) return true;
		}
		
		return false;

	}

	public static void setTileInicial(Tile tile) {
		tileInicioTemp = tile;
		tileInicio = tile;
		tileAtual = tile;
		usado.add(tile);
		usadoAtual = tileInicioTemp;
	}

	public static void restart() {
		chegou = false;
		tileAtual = tileInicioTemp;
		TileMap.limparTileCaminho();
		Principal.timeInicio = System.nanoTime();
	}
}
