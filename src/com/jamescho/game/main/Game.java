package com.jamescho.game.main;

import java.awt.Color;//�ṩ������ɫ�ռ����
import java.awt.Dimension;//��װ��������������Ŀ�Ⱥ͸߶�
import java.awt.Graphics;//����ͼ�������ĵĳ�����࣬����Ӧ�ó�����������Ѿ��ڸ����豸��ʵ�֣��Լ�����ͼ���Ͻ��л��ơ�
import java.awt.Image;//ͼ��ͼ���������ĳ��ࡣ

import javax.swing.JPanel;//һ������������

import com.jamescho.framework.util.InputHandler;
import com.jamescho.game.state.LoadState;
import com.jamescho.game.state.State;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {//�������Ĺ�����
	private int gameWidth;//��Ϸ���
	private int gameHeight;//��Ϸ�߶�
	private Image gameImage;//ͼƬ

	private Thread gameThread;//�̶߳���
	private volatile boolean running;//�Ƿ�������
	private volatile State currentState;//��ǰ��Ϸ״̬

	private InputHandler inputHandler;

	public Game(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		setPreferredSize(new Dimension(gameWidth, gameHeight));//���ô��������ѡ��С
		setBackground(Color.BLACK);//���ô�����ı���ɫ��
		setFocusable(true);//Component �Ƿ���Ի�ý���
		requestFocus();//����� Component ��ȡ���뽹�㣬���Ҵ� Component �Ķ������ȳ�Ϊ��ý���� Window
	}

	public void setCurrentState(State newState) {
		System.gc();//��������������
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);//���뵱ǰ״̬���¼�����
	}

	@Override
	public void addNotify() {//���֪ͨ
		super.addNotify();
		initInput();
		setCurrentState(new LoadState());
		initGame();
	}

	private void initGame() {//��ʼ����Ϸ
		running = true;
		gameThread = new Thread(this, "Game Thread");//�������߳���"Game Thread"
		gameThread.start();//�����߳�
	}

	@Override
	public void run() {
		long updateDurationMillis = 0;//���³���ʱ�� 
		long sleepDurationMillis = 0;//˯�߳���ʱ��
		while (running) {
			long beforeUpdateRender = System.nanoTime();//������׼ȷ�Ŀ���ϵͳ��ʱ���ĵ�ǰֵ���Ժ�΢��Ϊ��λ
			long deltaMillis = updateDurationMillis + sleepDurationMillis;

			updateAndRender(deltaMillis);//ִ�и��²��Ҽ���

			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;//���³�������
			sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);//ȡ����˯�߳���ʱ��

			try {
				Thread.sleep(sleepDurationMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	private void updateAndRender(long deltaMillis) {//���²��Ҽ���
		currentState.update(deltaMillis / 1000f);
		prepareGameImage();//׼��һ����ʼ����
		currentState.render(gameImage.getGraphics());//���أ�����һ�������Ʊ���ͼ��off-screen image��ʹ�õ�ͼ�������ġ�
		renderGameImage(getGraphics());//������Ϸ���棬getGraphics���ش������ͼ�������ģ����������������������
	}

	private void prepareGameImage() {
		if (gameImage == null) {
			gameImage = createImage(gameWidth, gameHeight);//����һ������˫����ġ�������Ļ����Ƶ�ͼ��
		}
		Graphics g = gameImage.getGraphics();
		g.clearRect(0, 0, gameWidth, gameHeight);//ͨ��ʹ�õ�ǰ��ͼ����ı���ɫ������������ָ���ľ��Ρ�
	}

	public void exit() {
		running = false;
	}

	private void renderGameImage(Graphics g) {//������ϷͼƬ
		if (gameImage != null) {
			g.drawImage(gameImage, 0, 0, null);//����ָ��ͼ���е�ǰ���õ�ͼ��
		}
		g.dispose();//�ͷŴ�ͼ�ε��������Լ���ʹ�õ�����ϵͳ��Դ��
	}

	private void initInput() {
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);//���ָ���İ������������Խ��շ��Դ�����İ����¼���
		addMouseListener(inputHandler);//���ָ����������������Խ��շ��Դ����������¼���
	}
}