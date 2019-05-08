package principal;

import javax.swing.JFrame;

import map.Finder;
import map.TileMap;

public class Principal implements Runnable {
	
	public static JFrame janela;
	public static boolean rodando = true;
	public Tela tela;
	public long fps = 180;
	
	public static long timeInicio = 0;
	
	public static int numTile = 0;
	
	public static boolean inicio = false;
	public static boolean carregarTXT = false;
	public static boolean escreverTXT = false;
	
	public Principal() {
		janela = new JFrame("Path Finder v0.0");
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		tela = new Tela(janela);
		
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	
		
		TileMap.init();
		
		new Thread(this).start();
		
	}

	public static void main(String[] args) {
		new Principal();
	}
	
	public void update() {
		//TileMap.updateTiles();
		Finder.update();
	}

	@Override
	public void run() {
		long targetTime = 1000000000/fps;
		
		System.out.println("Thread iniciado");
		
		long totalTime = 0;
		long timer = 0;
		int tick = 0;
		
		while(rodando) {
			
			long time = System.nanoTime(); 
			
			update();
			tick++;
			
			long timeElapse = System.nanoTime() - time;
			
			
			long waitTime = targetTime;
			
			if (timeElapse < targetTime) {
				waitTime -= timeElapse;
			}
			//System.out.println(totalTime);
			totalTime += timeElapse;
			if (totalTime >= 100000000) {
				System.out.println("FPS: " +  tick);
				tick = 0;
				totalTime = 0;
			}
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Thread finalizado");
	}

}
