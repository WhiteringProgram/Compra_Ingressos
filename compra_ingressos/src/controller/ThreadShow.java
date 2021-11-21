package controller;

import java.util.concurrent.Semaphore;

public class ThreadShow extends Thread {
	private static int totalDeIngressos = 100;
	private int num, qtd;
	private Semaphore s;
	private boolean isFree;

	public ThreadShow(int num, int qtd, Semaphore s) {
		this.num = num;
		this.qtd = qtd;
		this.s = s;
	}

	@Override
	public void run() {
		entrar();
		if (isFree) {
			tempoCompra();
			if (isFree) {
				try {
					s.acquire();
					compraIngresso();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					s.release();
				}
			}
		}
	}

	private void tempoCompra() {
		int tempo = (int) ((Math.random() * 2001) + 1000);
		try {
			sleep(tempo);
			if (tempo >= 2500) {
				System.err.println("Compra #"+num+" - Session TimeOut");
				isFree = false;
			} else {
				System.out.println("Comprador #" + num + " solicitou a compra");
				isFree = true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void entrar() {
		int t = (int) ((Math.random() * 1951) + 50);
		try {
			sleep(t);
			if (t >= 1000) {
				System.err.println("Login #"+num+" TimeOut");
				isFree = false;
			} else {
				System.out.println("O Comprador #" + num + " está logado no sistema");
				isFree = true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void compraIngresso() {
		if (totalDeIngressos - qtd >= 0) {
			totalDeIngressos -= qtd; //totalDeIngressos = totalIngressos - qtd; 
			System.out.println("Comprador #" + num + " comprou seus " + qtd
					+ " ingressos");
			System.out.println("Sobraram "+totalDeIngressos+" ingressos");
		} else {
			System.err.println("O Comprador #" + num + " não comprou seus " + qtd
					+ " ingressos devido à indisponibilidade no estoque");
		}
	}
}

