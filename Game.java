package com.mycompany.a3;
import com.mycompany.cmd.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable{
	private GameWorld gw;
	private MapView map;
	private ScoreView score;
	private UITimer time;
	private final int GAME_TICK = 100;
	private boolean pauseGame = false;
	private Button accB;
	private Button leftB;
	private Button strategyB;
	private Button pauseB;
	private Button posB;
	private Button brakeB;
	private Button rightB;
	private CheckBox soundB;
	private Accelerate acc;
	private Left left;
	private Strategy strategy;
	private Pause pause;
	private Position pos;
	private Brake brake;
	private Right right;
	private SoundCommand sound;
	
	public Game() {
		/* Create game world and views using MVC architecture
		 * 
		 */
		
		this.gw = new GameWorld();
		this.map = new MapView();
		this.score = new ScoreView();
		
		gw.addObserver(map);
		gw.addObserver(score);
		
		gw.setGameWidth(map.getMapWidth());
		gw.setGameHeight(map.getMapHeight());
		
		map.setMapPoint(new Point(map.getX(), map.getY()));
		map.setWidth(map.getWidth());
		map.setHeight(map.getHeight());
		
		System.out.println("Map Width: " + MapView.getMapPoint().getX() + "," + MapView.getMapWidth() +
						"\nMap Height: " + MapView.getMapPoint().getY() + "," + MapView.getMapHeight());
		
		this.setLayout(new BorderLayout()); //set border layout for game form
		this.addComponent(BorderLayout.CENTER, map); //add map view to center
		this.addComponent(BorderLayout.NORTH, score); //add score view to the top
		
		setCommand();
		
		gw.init();
		this.show();
		time = new UITimer(this);
		time.schedule((int)GAME_TICK, true, this);
	}
		
	public void setCommand() {

		/*Set title*/
		Toolbar myToolBar = new Toolbar();
		this.setToolbar(myToolBar);
		myToolBar.setTitle("Sili-Challenge Game");
		myToolBar.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		westContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		//Accelerate 
		acc = new Accelerate(gw);
		accB = new Button("Accelerate");
		buttonStyle(accB);
		accB.getAllStyles().setMarginTop(10);;
		accB.setCommand(acc);
		westContainer.addComponent(accB);
		this.addKeyListener('a', acc);
		myToolBar.addCommandToSideMenu(acc);
		
		//Left turn
		left = new Left(gw);
		leftB = new Button("Left");
		buttonStyle(leftB);
		leftB.setCommand(left);
		westContainer.addComponent(leftB);
		this.addKeyListener('l', left);
		
		//Change Strategies
		strategy = new Strategy(gw);
		strategyB = new Button("Change Strategies");
		buttonStyle(strategyB);
		strategyB.setCommand(strategy);
		westContainer.addComponent(strategyB);
		
		this.addComponent(BorderLayout.WEST, westContainer);
		
		Container bottomContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		bottomContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		//Collide with NPC
//		NPCCollision collision1 = new NPCCollision(gw);
//		Button npcB = new Button("Collide with NPC");
//		buttonStyle(npcB);
//		npcB.getAllStyles().setMarginLeft(5);
//		npcB.setCommand(collision1);
//		bottomContainer.addComponent(npcB);
//		
//		//Collide with Base
//		BCollision collision2 = new BCollision(gw);
//		Button bB = new Button("Collide with Base");
//		buttonStyle(bB);
//		bB.setCommand(collision2);
//		bottomContainer.addComponent(bB);
//		
//		//Collide with energy station
//		ECollision collision3 = new ECollision(gw);
//		Button eB = new Button("Collide with Energy Station");
//		buttonStyle(eB);
//		eB.setCommand(collision3);
//		bottomContainer.addComponent(eB);
//		this.addKeyListener('e', collision3);
//		
//		//Collide with Drone
//		DCollision collision4 = new DCollision(gw);
//		Button dB = new Button("Collide with Drone");
//		buttonStyle(dB);
//		dB.setCommand(collision4);
//		bottomContainer.addComponent(dB);
//		this.addKeyListener('g', collision4);
//		
//		//Clock tick
//		Clock clock = new Clock(gw);
//		Button clockB = new Button("Tick");
//		buttonStyle(clockB);
//		clockB.getAllStyles().setMarginRight(5);
//		clockB.setCommand(clock);
//		bottomContainer.addComponent(clockB);
//		this.addKeyListener('t', clock);
		
		//Pause
				pause = new Pause(this);
				pauseB = new Button("Pause");
				buttonStyle(pauseB);
				pauseB.setCommand(pause);
				bottomContainer.addComponent(pauseB);
				
				//Position
				pos = new Position(gw);
				posB = new Button("Posiion");
				buttonStyle(posB);
				posB.setCommand(pos);
				posB.setEnabled(false);
				bottomContainer.addComponent(posB);
		
		this.addComponent(BorderLayout.SOUTH, bottomContainer);
		
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.BLACK));
		
		//Brake
		brake = new Brake(gw);
		brakeB = new Button("Brake");
		buttonStyle(brakeB);
		brakeB.getAllStyles().setMarginTop(10);
		brakeB.setCommand(brake);
		eastContainer.addComponent(brakeB);
		this.addKeyListener('b', brake);
		
		//Right turn
		right = new Right(gw);
		rightB = new Button("Right");
		buttonStyle(rightB);
		rightB.setCommand(right);
		eastContainer.addComponent(rightB);
		this.addKeyListener('r', right);
		
		this.addComponent(BorderLayout.EAST, eastContainer);
		
		//Sound
		if(gw.getSound()) {
			soundB = new CheckBox("ON");
			soundB.setSelected(true);
		}
		else {
			soundB = new CheckBox("OFF");
			soundB.setSelected(false);
		}
		sound = new SoundCommand(gw);
		soundB.setCommand(sound);
		myToolBar.addComponentToSideMenu(soundB);
		
		//About
		About about = new About();
		myToolBar.addCommandToSideMenu(about);
		
		//Exit
		Exit exit = new Exit(gw);
		myToolBar.addCommandToSideMenu(exit);
		
		//Help
		Help help = new Help();
		myToolBar.addCommandToRightBar(help);
		
		
	}
	
	@Override
	public void run() {
		gw.clockTick();
		if(gw.getLive() == 0) {
			time.cancel();
			//gw.createSound('p');
			gw.gameOver('l');
		}
	}
	
	private void buttonStyle(Button button) {
		button.getAllStyles().setBgTransparency(255);
		button.getAllStyles().setBorder(Border.createBevelRaised());
		button.getAllStyles().setBorder(Border.createBevelLowered());
		button.getAllStyles().setBorder(Border.createDoubleBorder(2,ColorUtil.GRAY));
		button.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 100, 100));
		button.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		button.getPressedStyle().setBgTransparency(125);
		button.getPressedStyle().setBgColor(ColorUtil.rgb(0, 100, 100));
		button.getPressedStyle().setFgColor(ColorUtil.BLUE);
		button.getDisabledStyle().setBgTransparency(255);
		button.getDisabledStyle().setBgColor(ColorUtil.GRAY);
		button.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		button.getDisabledStyle().setStrikeThru(true);
	}
	
	public void gamePause() {
		this.pauseGame = !pauseGame;
		accB.setEnabled(!accB.isEnabled());
		strategyB.setEnabled(!strategyB.isEnabled());
		leftB.setEnabled(!leftB.isEnabled());
		rightB.setEnabled(!rightB.isEnabled());
		brakeB.setEnabled(!brakeB.isEnabled());
		boolean soundVal = soundB.isSelected();
		if(pauseGame) {
			time.cancel();
			gw.createSound('p');
			pauseB.setText("Play");
			posB.setEnabled(true);
			gw.setPause(true);
			acc.setEnabled(false);
			sound.setEnabled(false);
			
			this.removeKeyListener('a', acc);
			this.removeKeyListener('b', brake);
			this.removeKeyListener('l', left);
			this.removeKeyListener('r', right);
			
			if(soundVal) {
				soundB.setToggle(true);
			}
		}
		else {
			time.schedule((int)GAME_TICK, true, this);
			gw.createSound('m');
			pauseB.setText("Pause");
			posB.setEnabled(false);
			gw.setPause(false);
			acc.setEnabled(true);
			sound.setEnabled(true);
			this.addKeyListener('a', acc);
			this.addKeyListener('b', brake);
			this.addKeyListener('l', left);
			this.addKeyListener('r', right);
			
			if(soundVal) {
				soundB.setToggle(true);
			}
		}
	}
}
