package view;

import java.util.concurrent.Semaphore;
import controller.ThreadShow;
public class Principal {

	public static void main(String[] args) {
			
			Semaphore semaforo = new Semaphore(1);
			for (int i = 0; i < 300; i++) {
				int qtd = (int)((Math.random() * 4) + 1);
				Thread t = new ThreadShow(i, qtd, semaforo);
				t.start();
			}


	}

}
