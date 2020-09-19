// Jungbin Lee, Stacey Lee
// 05/22/2019
// TrigCrashCourse.java

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;	
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;	
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

//Frame by Jungbin
public class TrigCrashCourse
{
	public TrigCrashCourse() {}
	
	public static void main(String[]args)
	{
		TrigCrashCourse tcc = new TrigCrashCourse();
		tcc.run();
	}

	public void run()
	{
		JFrame frame = new JFrame("Trig Crash Course");
		JPanel panel = new JPanel();
		frame.setSize(1200,700);
		frame.setLocation(0,0);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		
		OverallPanel op = new OverallPanel(frame);
		frame.getContentPane().add(op);
		frame.setVisible(true);
	}
}	

//OverallPanel holds the cards(4): MainPanel, UnitCircle, Pythagorean, and Graphs
//OverallPanel by Stacey
//MainPanel by Jungbin
class OverallPanel extends JPanel 
{
	//instance of classes: the main panel and the two games
	private MainPanel mp; 
	private UnitCircle uc;
	private Pythagorean pn;
	
	private CardLayout cl;

	public OverallPanel(JFrame frame)
	{
		mp = new MainPanel(this);
		uc = new UnitCircle(this);
		pn = new Pythagorean(this);
		
		cl = new CardLayout();
		setLayout(cl);
		
		add(mp,"MainPanel");
		add(uc,"UnitCircle");
		add(pn,"Pythagorean");	
		
		cl.show(this,"MainPanel");
	 }
	 
	// It's the main page of the game which contains labels and menu
	// that will lead into different instruction panels
	class MainPanel extends JPanel
	{
		public MainPanel(OverallPanel op)
		{
			GameLabels gl = new GameLabels();
			GameItems gi = new GameItems(op);
			
			setLayout(new GridLayout(2,1));
			add(gl);
			add(gi);
		}
		
		// This class creates 3 labels and locate each of the labels 
		// using the grid layout.
		class GameLabels extends JPanel 
		{
			//three labels on the main pane;
			private JLabel label1;
			private JLabel label2;
			private JLabel label3;
				
			public GameLabels()
			{
				setBackground(Color.WHITE);
				label1 = new JLabel("Trig Crash Course");
				label2 = new JLabel("-For Algebra 2 Trig or above");
				label3 = new JLabel("You have 2 games to choose from");
					
				label1.setFont(new Font("serif",Font.BOLD,40));
				label2.setFont(new Font("serif",Font.ITALIC,15));
				label3.setFont(new Font("serif",Font.BOLD,15));
					
				setLayout(null);
					
				add(label1);
				add(label2);
				add(label3);
					
				label1.setBounds(500,40,500,80);
				label2.setBounds(650,120,300,30);
				label3.setBounds(550,220,400,20);
			}
		}
				
		// This GameItems class creates a menu, menuItems, menubar, and
		// an instance of the overall panel, which will be used when 
		// we change the panel. If the user press the menu, change the 
		// panel accordingly. 
		class GameItems extends JPanel implements ActionListener
		{
			//the menu to choose a game
			private JMenu menu;
			private JMenuBar menubar;
			private JMenuItem item1;
			private JMenuItem item2;
	
			private OverallPanel op2; //object of OverallPanel class 
			private MainPanelPic mpp; //Image on main panel
			
			public GameItems(OverallPanel op2In)
			{
				setLayout(null);
				setBackground(Color.WHITE);
				op2 = op2In;
				
				item1 = new JMenuItem("Unit Circle");
				item2 = new JMenuItem("Pythagorean theorem");

				menu = new JMenu("Choose a game HERE");
				menubar = new JMenuBar();
				menu.setBackground(Color.GREEN);
				menu.setOpaque(true);
					
				menu.add(item1);
				menu.add(item2);
				menubar.add(menu);
					
				item1.addActionListener(this);
				item2.addActionListener(this);
		
				add(menubar); 
				menubar.setBounds(600,10,160,30);
				
				MainPanelPic mpp = new MainPanelPic();
				add(mpp);
				mpp.setBounds(50,10,500,500);
				
			}
			
			class MainPanelPic extends JPanel
			{
				private String picName;
				private Image pic;
				
				public MainPanelPic()
				{
					picName = new String("MainPanelPic.jpg");
					pic = null;
					getPic();
				}
				
				public void getPic()
				{
					File mainPicFile = new File(picName);
					try
					{
						pic = ImageIO.read(mainPicFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n" + picName
							+ " cannot be found");
						e.printStackTrace();
					}
				}
				
				public void paintComponent(Graphics g)
				{
					g.drawImage(pic,0,0,400,250,this);
				}
			}
				
			public void actionPerformed(ActionEvent evt)
			{
				String value = evt.getActionCommand();
				
				if(value=="Unit Circle")
					cl.show(op2,"UnitCircle");
				else if(value=="Pythagorean theorem")
					cl.show(op2,"Pythagorean");		
			}
		}
	}
	
//Game "Unit Circle"
//By Stacey
	class UnitCircle extends JPanel
	{
		private OverallPanel opU; //instance of class
		private CardLayout clU; //CardLayout object
		private boolean spin; //whether the spinner is spinning
		private String middleChar; //in the middle of the circle; change when user got right
		private JTextArea textArea; // for computer's comments, "Comments" class in GamePanel
		
		//coordinate of where the spinner points
		private int spinX; 
		private int spinY;
		
		//instances of nested classes
		private UnitInstructionPanel uip; 
		private UnitGamePanel ugp;
		private UnitResultWinPanel urwp;
		private UnitResultLosePanel urlp;
		private UnitResultUserRecords urur;
		
		private String fileName; //the file name that stores user records
		private Scanner input;  //scanner to read file UnitUserRecord.txt
		private String userRecordsString; //The string in the text area
		private JTextArea userRecordsTxtArea; //text area to show user records
		
		public UnitCircle(OverallPanel opInU)
		{
			opU = opInU;
			spinX = 250;
			spinY = 0;
			textArea = new JTextArea("Please answer the questions "
				+ "at the bottom",3,15);
			middleChar = new String(";)");
			spin = false;
			fileName = new String("UnitUserRecord.txt");
			input = null;
			userRecordsString = new String("");
			userRecordsTxtArea = new JTextArea(userRecordsString);
			userRecordsTxtArea.setLineWrap(true);
			userRecordsTxtArea.setEditable(false);
			userRecordsTxtArea.setFont(new Font("Arial",Font.PLAIN,20));
			
			clU = new CardLayout();
			setLayout(clU);
			
			uip = new UnitInstructionPanel(this);
			add(uip,"InstructionPanel");
			
			ugp = new UnitGamePanel(this);
			add(ugp,"GamePanel");
			
			urwp = new UnitResultWinPanel();
			add(urwp, "ResultWinPanel");
			
			urlp = new UnitResultLosePanel();
			add(urlp, "ResultLosePanel");
			
			urur = new UnitResultUserRecords();
			add(urur,"ResultUserRecords");
			
		}
		
		class UnitInstructionPanel extends JPanel
		{
			private String unitCircleDirection; //"direction" for unit circle game
			private String unitCirclePlayed; // "how it's played" for unit circle game
			private UnitCircle uc; //passed in parameter of class "Unit Circle"
			
			public UnitInstructionPanel(UnitCircle ucIn)
			{
				uc = ucIn;
			
				setLayout(new GridLayout(2,1));
			
				UnitInstructionTop uit = new UnitInstructionTop();
				UnitInstructionBottom uib = new UnitInstructionBottom();
			
				add(uit);
				add(uib);
			}
			
			//the "Direction" part of the instruction page 
			class UnitInstructionTop extends JPanel
			{
				//these are for the texts on the instruction page at the top
				private Grid1 direction;
				private Grid2 labeld2;
				private Grid2E labeld2e;
				private Grid3 labeld3;
				private Grid4 labeld4;
			
				public UnitInstructionTop()
				{
					setLayout(new GridLayout(5,1));
			
					direction = new Grid1();
					labeld2 = new Grid2();
					labeld2e = new Grid2E();
					labeld3 = new Grid3();
					labeld4 = new Grid4();
				
					add(direction);
					add(labeld2);
					add(labeld2e);
					add(labeld3);
					add(labeld4);
				}
				
				class Grid1 extends JPanel
				{
					//the font for "Direction"
					private Font font1;
					
					public Grid1()
					{
						setBackground(Color.PINK);
						JLabel directionLabel = new JLabel("Direction");
						font1 = new Font("Serif",Font.ITALIC + Font.BOLD,20);
						directionLabel.setFont(font1);
						add(directionLabel);
					}
				}
					
				class Grid2 extends JPanel
				{
					//direction for stop spinning
					private JLabel label2Label;
					
					public Grid2()
					{
						setBackground(Color.PINK);
						label2Label = new JLabel("-Press\"space key\" = Stop spinning"
							);
						add(label2Label);
					}
				}
				
				class Grid2E extends JPanel
				{
					//direction for stop spinning to answer questions
					private JLabel label2eLabel;
					
					public Grid2E()
					{
						setBackground(Color.PINK);
						label2eLabel = new JLabel("(You can't fill out "
							+ "the question if you don't stop the spinne"
							+ "r first!)");
						add(label2eLabel);
					}
				}
				
				class Grid3 extends JPanel
				{
					//direction for filling out the questions
					private JLabel label3Label;
					
					public Grid3()
					{
						setBackground(Color.PINK);
						label3Label= new JLabel("-After stop spinning, write the angle "
							+ "the spinner ends up in both radian and degree mode IN "
							+ "THAT EXACT FORMAT PROVIDED");
						add(label3Label);
					}
				}
				
				class Grid4 extends JPanel
				{
					private JLabel label4Label;
					
					public Grid4()
					{
						setBackground(Color.PINK);
						label4Label = new JLabel("-The spinner is NOT exact; write your "
							+ "answers only in those 16 positions");
						add(label4Label);
					}
				}
			}
			
			//the "How it's played part of the instruction page"
			class UnitInstructionBottom extends JPanel
			{
				//these variables are for the texts in the instruction page
				private HowItsScored howItsScored;
				private LabelH1 labelh1;
				private LabelH2 labelh2;
				private LabelH3 labelh3;
		
				public UnitInstructionBottom()
				{
					setLayout(new GridLayout(5,1));
					
					howItsScored = new HowItsScored();
					labelh1 = new LabelH1();
					labelh2 = new LabelH2();
					labelh3 = new LabelH3();
			
					UnitInstructionButton buttons = new UnitInstructionButton();
			
					add(howItsScored);
					add(labelh1);
					add(labelh2);
					add(labelh3);
					add(buttons);
				}
				
				class HowItsScored extends JPanel
				{
					//title "How It's Scored"
					private JLabel howItsScoredLabel;
					
					public HowItsScored()
					{
						setBackground(Color.YELLOW);
						howItsScoredLabel = new JLabel("How It's Scored");
						howItsScoredLabel.setFont(new Font("Serif",
							Font.ITALIC + Font.BOLD,20));
						add(howItsScoredLabel);
					}
				}
				
				class LabelH1 extends JPanel
				{
					//instruction for how the points works
					private JLabel labelh1Label;
					
					public LabelH1()
					{
						setBackground(Color.YELLOW);
						labelh1Label = new JLabel("-Get 1 point if got it right, lose 1 point"
						+ " if got it wrong");
						add(labelh1Label);
						
					}
				}
				
				class LabelH2 extends JPanel
				{
					//instruction for how the points works 
					private JLabel labelh2Label;
					
					public LabelH2()
					{	
						setBackground(Color.YELLOW);
						labelh2Label = new JLabel("-Earn total of 5 points will win, below"
							+ " 0 will lose");
						add(labelh2Label);
					}
				}
				
				class LabelH3 extends JPanel
				{
					//instruction for the resizing frame change coordinate problem
					private JLabel labelh3Label;
					
					public LabelH3()
					{	
						setBackground(Color.YELLOW);
						labelh3Label = new JLabel("**Resizing the frame will causes the"
							+ " spinner to change where it's pointing!");
						labelh3Label.setForeground(Color.RED);
						add(labelh3Label);
					}
				}
			
				//The "Main Page" button and "START game" button
				class UnitInstructionButton extends JPanel implements ActionListener
				{
					private JButton mainPageButton; //the button "Main Page"
					private JButton startGameButton; //the button "START game"
		
					public UnitInstructionButton()
					{
						setLayout(new GridLayout(1,3));
			
						mainPageButton = new JButton("Main Page");
						startGameButton = new JButton("START game");
						
						startGameButton.setForeground(Color.GREEN);
						startGameButton.setOpaque(true);
		
						add(mainPageButton);
						add(startGameButton);
			
						mainPageButton.addActionListener(this);
						startGameButton.addActionListener(this);
					}
		
					public void actionPerformed(ActionEvent evt)
					{
						String clicked = evt.getActionCommand();
						
						if(clicked.equals("Main Page"))
							cl.show(opU,"MainPanel");
						else if(clicked.equals("START game"))
						{
							spin = true;
							clU.show(uc,"GamePanel");
						}
					}
				} 
			}
		}
		
		//the game panel of the game "Unit Circle"
		class UnitGamePanel extends JPanel implements ActionListener
		{
			private UnitCircle uc; //instance of parent class UnitCircle
			
			//objects for classes
			private Buttons buttons; 
			private Circle circle; 
			private Questions questions; 
			private Points points; 
			private Comments comments; 
			
			private String realRadian; //the correct radian
			private String realDegree; //the correct degree
			
			private int pointsNumber; // keep track of how many times the user got it correct
			
			private Timer timer; // timer for the spinner in "UnitGamePanel"
			
			public UnitGamePanel(UnitCircle ucIn)
			{
				uc = ucIn;
				
				setBackground(Color.WHITE);

				timer = new Timer(10, this);
				timer.start();
				
				setLayout(null);
				realRadian = new String("0");
				realDegree = new String("0");
				
				pointsNumber = 0;
				
				buttons = new Buttons();
				points = new Points();
				circle = new Circle(questions);
				questions = new Questions(points);
				comments = new Comments();
				
				add(buttons);
				add(points);
				add(circle);
				add(questions);
				add(comments);
				
				buttons.setBounds(50,10,500,35);
				points.setBounds(900,10,110,50);
				circle.setBounds(330,100,500,500);
				questions.setBounds(840,500,350,150);
				comments.setBounds(870,300,280,150);
			}
			
			public void actionPerformed(ActionEvent evt)
			{
				requestFocusInWindow();
				repaint();
			}
			
			//comment from the computer to the user in a text area
			class Comments extends JPanel 
			{
				public Comments()
				{
					setBackground(Color.WHITE);
					setLayout(new GridLayout(2,1));
					
					JLabel computerLabel = new JLabel("Computer: ");
					computerLabel.setFont(new Font("Serif",Font.BOLD,25));	
					
					textArea.setEditable(false);
					textArea.setLineWrap(true);
					textArea.setFont(new Font("Serif",Font.PLAIN,20));
					
					add(computerLabel);
					add(textArea);
				}
			}
			
			//buttons for going to main/instruction page & restart
			class Buttons extends JPanel implements ActionListener
			{
				//instances of 3 buttons on the top left page
				private JButton restart; 
				private JButton main;
				private JButton instruction;
	
				public Buttons()
				{
					setLayout(new FlowLayout(FlowLayout.LEFT));
					setBackground(Color.WHITE);
					
					restart = new JButton("Restart");
					restart.setOpaque(true);
					restart.setForeground(Color.RED);
					restart.setBackground(Color.WHITE);
					
					main = new JButton("Main Page");
					instruction = new JButton("Instruction Page");
					
					add(restart);
					add(main);
					add(instruction);
					
					restart.addActionListener(this);
					main.addActionListener(this);
					instruction.addActionListener(this);
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					String gameClicked = evt.getActionCommand();
								
						if (gameClicked == "Main Page")
							cl.show(opU, "MainPanel");
						else if (gameClicked == "Restart")
						{
							clU.show(uc, "GamePanel");
							pointsNumber = 0;
							points.repaint();
							timer.start();
							spin = true;
							textArea.setText("Please answer the question at the bottom");
							points.setBackground(Color.ORANGE);
						}	
							
						else if(gameClicked == "Instruction Page")
							clU.show(uc,"InstructionPanel");	
				}
			}
			
			 //recording how many times the user got it right
			class Points extends JPanel
			{	
				public Points() 
				{
					setBackground(Color.ORANGE);
				}
				
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.setColor(Color.BLACK);
					Font pointsFont = new Font("Serif",Font.BOLD + 
						Font.ITALIC, 20);
					g.setFont(pointsFont);
					g.drawString("Points: " + pointsNumber, 10,30);
				}
				
			}
			
			//drawing the circle + the spinner 
			class Circle extends JPanel implements KeyListener
			{	
				String dot; // for the marks of the unit circle
				private Questions ques; // instance of class "Question"
				
				public Circle(Questions quesIn) 
				{			
					setBackground(Color.WHITE);
					
					ques = quesIn;
							
					dot = new String(".");
					addKeyListener(this);
				}
				
				public void paintComponent(Graphics g)
				{
					//the circle
					super.paintComponent(g);
					requestFocusInWindow();
					g.setColor(Color.YELLOW);
					g.fillOval(0,0,500,500);
					
					//angles marks on the circle
					Font dotFont = new Font("Serif",Font.BOLD,20);
					
											//degrees: 
					g.setColor(Color.BLACK);
					
					//12o'clock mark
					Font topFont = new Font("Serif",Font.BOLD,40);
					g.setFont(topFont);
					g.drawString("+",240,20); //90
					
					g.setFont(dotFont);
					g.drawString(dot,370,33); //60
					g.drawString(dot,422,73); //40
 					g.drawString(dot,462,125); //30
					g.drawString("#",490,250); //0
					g.drawString(dot,467,375); //330
					g.drawString(dot,427,427); //315
					g.drawString(dot,375,467); //300
					g.drawString("#",250,500); //270
					g.drawString(dot,120,467); //240
					g.drawString(dot,68,427); //225
					g.drawString(dot,28,375); //210
					g.drawString("#",0,250); //180
					g.drawString(dot,33,125); //150
					g.drawString(dot,73,73); //135
					g.drawString(dot,125,33); //120
					
					//the spinner
					g.drawLine(250,250, spinX, spinY);

					if (spinX == 250 && spinY == 0 && spin)
					{
						spinX = 370;
						spinY = 33;
						realRadian = "(PI)/2";
						realDegree = "90";
						
					}
					
					else if (spinX == 370 && spinY == 33 && spin)
					{
						spinX = 422;
						spinY = 73;
						realRadian = "(PI)/3";
						realDegree = "60";
						
					}
					
					else if (spinX == 422 && spinY == 73 && spin)
					{
						spinX = 462;
						spinY = 125;
						realRadian = "(PI)/4";
						realDegree = "45";
						
					}
					
					else if (spinX == 462 && spinY == 125 && spin)
					{
						spinX = 500;
						spinY = 250;
						realRadian = "(PI)/6";
						realDegree = "30";
						
					}
					
					else if (spinX == 500 && spinY == 250 && spin)
					{
						spinX = 467;
						spinY = 375;
						realRadian = "0";
						realDegree = "0";
					
					}
					
					else if (spinX == 467 && spinY == 375 && spin)
					{
						spinX = 427;
						spinY = 427;
							realRadian = "(11PI)/6";
						realDegree = "330";
						
					}
					
					else if (spinX == 427 && spinY == 427 && spin)
					{
						spinX = 380;
						spinY = 467;
						realRadian = "(7PI)/4";
						realDegree = "315";
						
					}
					
					else if (spinX == 380 && spinY == 467 && spin)
					{
						spinX = 260;
						spinY = 500;
						realRadian = "(5PI)/3";
						realDegree = "300";
					
					}
					
					else if (spinX == 260 && spinY == 500 && spin)
					{
						spinX = 120;
						spinY = 467;
						realRadian = "(3PI)/2";
						realDegree = "270";
						
					}
					
					else if (spinX == 120 && spinY == 467 && spin)
					{
						spinX = 68;
						spinY = 427;
						realRadian = "(4PI)/3";
						realDegree = "240";
						
					}
					
					else if (spinX == 68 && spinY == 427 && spin)
					{
						spinX = 28;
						spinY = 375;
						realRadian = "(5PI)/4";
						realDegree = "225";
						
					}
					
					else if (spinX == 28 && spinY == 375 && spin)
					{
						spinX = 0;
						spinY = 250;
						realRadian = "(7PI)/6";
						realDegree = "210";
					}
					
					else if (spinX == 0 && spinY == 250 && spin)
					{
						spinX = 33;
						spinY = 125;
						realRadian = "PI";
						realDegree = "180";
						
					}
					
					else if (spinX == 33 && spinY == 125 && spin)
					{
						spinX = 73;
						spinY = 73;
						realRadian = "(5PI)/6";
						realDegree = "150";
						
					}
					
					else if (spinX == 73 && spinY == 73 && spin)
					{
						spinX = 130;
						spinY = 33;
						realRadian = "(3PI)/4";
						realDegree = "135";
						
					}
					
					else if (spinX == 130 && spinY == 33 && spin)
					{
						spinX = 250;
						spinY = 0;
						realRadian = "(2PI)/3";
						realDegree = "120";
						
					}
				
					//dot in the middle
					g.setColor(Color.BLUE);
					Font middleFont = new Font("Arial",Font.BOLD,50);
					g.setFont(middleFont);
					g.drawString(middleChar,235,260);
				}
				
				public void keyReleased(KeyEvent evt){}
				public void keyTyped(KeyEvent evt){}
				public void keyPressed(KeyEvent evt) 
				{
					requestFocusInWindow();
					int code = evt.getKeyCode();
					
					if(code == KeyEvent.VK_SPACE)
					{
						timer.stop();
						questions.requestFocusInWindow();
						spin = false;
					}
				}
			}
			
			//including the textfields
			class Questions extends JPanel implements ActionListener
			{
				private JTextField radian; //for user type in answer in radian mode
				private JLabel radianLabel; //label that says "radian: "
				private JTextField degree; //for user type in answer in degree mode
				private JLabel degreeLabel; //label that says "degree: "
			
				private EgLabel egLabel;  //example of in what form the user should type in
				public JButton submit;  //submit user's answer after they finished
				private ButtonHandler bh; //button handler class for the submit button
				
				private Points pt; //passed parameter from class "Points"
				
				public Questions(Points ptIn)
				{
					requestFocusInWindow();
					
					pt = ptIn;
					
					setBackground(Color.PINK);
					
					setLayout(new GridLayout(3,2));
					
					radian = new JTextField("format see bottom left",20);
					radianLabel = new JLabel("Radian: ");
					
					degree = new JTextField(20);
					degreeLabel = new JLabel("Degree: ");
				
					egLabel = new EgLabel();
					
					submit = new JButton("Submit");
					
					Font bolded = new Font("Arial", Font.BOLD, 14);
					radianLabel.setFont(bolded);
					degreeLabel.setFont(bolded);
					
					add(radianLabel);
					add(radian);
					add(degreeLabel);
					add(degree);
					add(egLabel);
					add(submit);
					
					bh = new ButtonHandler();
					
					submit.addActionListener(bh);
					
					radian.addActionListener(this);
					degree.addActionListener(this);
				}
				
				class EgLabel extends JPanel
				{
					public EgLabel()
					{
						setBackground(Color.PINK);
						setLayout(new GridLayout(3,1));
						
						JLabel radEg = new JLabel("radian - (11PI)/6, (PI)/4, 0, PI");
						JLabel degEg = new JLabel("degree - 0, 180, 330");
						JLabel domain = new JLabel("!Use 0 instead of 2PI!");
						
						Font egFont = new Font("Arial", Font.BOLD + Font.ITALIC, 12);
						
						radEg.setFont(egFont);
						degEg.setFont(egFont);
						domain.setFont(egFont);
						
						radEg.setForeground(Color.BLUE);
						degEg.setForeground(Color.BLUE);
						domain.setForeground(Color.RED);
											
						add(radEg);
						add(degEg);
						add(domain);
					}
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					if (!spin)
						radian.setEditable(true);
						degree.setEditable(true);
				}
				
				class ButtonHandler implements ActionListener
				{		
					public void actionPerformed(ActionEvent evt)
					{
						String clicked = evt.getActionCommand();
						
						String radianIn;
						String degreeIn;
						
						if(clicked.equals("Submit"))
						{
							//get the input from user
							radianIn = radian.getText();
							degreeIn = degree.getText();
							
							//check answer
							
							//if user didn't put anything
							if (radianIn.trim().equals("") || degreeIn.trim().equals(""))
								textArea.setText("Please complete the questions");
							 
							//if user got it right
							else if ((radianIn.trim().equalsIgnoreCase(realRadian))
								&&(degreeIn.trim().equalsIgnoreCase(realDegree)))
							{
								textArea.setText("Correct! You got 1 point!");
								pointsNumber++;
								pt.repaint();
								
								spin = true;
								timer.start();
								
								radian.setText("");
								degree.setText("");
								
								middleChar = ";D";
								points.setBackground(Color.GREEN);
								circle.repaint();
							}
							
							//if user got it wrong 
							else
							{
								textArea.setText("Incorrect. It's " + realRadian + " in radian"
									+ " and " + realDegree + " in degree");
					
								pointsNumber--;	
								
								spin = true;
								timer.start();
								radian.setText("");
								degree.setText("");	
								
								middleChar = ";)";
								points.setBackground(Color.RED);
								circle.repaint();
							}
							
							
							//win & lose
							if(pointsNumber==5)
							{
								clU.show(uc,"ResultWinPanel");
								pointsNumber = 0;
								textArea.setText("Please answer the question at the bottom");
								points.setBackground(Color.ORANGE);
							}	
								
							else if(pointsNumber<0)
							{
								clU.show(uc,"ResultLosePanel");
								pointsNumber = 0;
								textArea.setText("Please answer the question at the bottom");
								points.setBackground(Color.ORANGE);
							}
						}
					}
				}
			}
		}
		
		//This panel shows up if the user got 5 points 
		class UnitResultWinPanel extends JPanel
		{
			//instances of nested classes
			private UnitImage ui;  //ImageIO
			private UnitUsername uun; //TextField + Label
			private UnitUserComments uuc; //TextField + Label
			private UnitInfoSubmit uis; //Button
		
			private PrintWriter makesOutput; // PrintWriter for writeFile() method
			
			private JTextField textFieldName; //TextField for user's name
			private JTextField textFieldComments; //TextField for user's comment
			
			private JLabel labelComments; //label above comment field
			private JLabel labelName;  //label above name field
			
			private String usernameIn; //text from user : name
			private String userCommentsIn; //text from use: comment
			
			private JLabel remindSubmit; //remind the user to submit their info
			private Font font;
			
			//checking if both name and comments are filled out
			private boolean nameFilled;
			private boolean commentsFilled;
			
			public UnitResultWinPanel()
			{
				setLayout(null);
				JLabel winTitle = new JLabel("You win!");
				winTitle.setFont(new Font("Arial",Font.BOLD,50));
				makesOutput = null;
				usernameIn = new String("");
				userCommentsIn = new String("");
				
				nameFilled = false;
				commentsFilled = false;
				
				ui = new UnitImage();
				uun = new UnitUsername();
				uuc = new UnitUserComments();
				uis = new UnitInfoSubmit();
					
				remindSubmit = new JLabel("Your info will NOT be saved until you"
					+ " fill out BOTH fields and click \"Submit\" button");
				font = new Font("Serif",Font.PLAIN,15);
				remindSubmit.setFont(font);
				
				add(winTitle);
				add(ui);
				add(uun);
				add(uuc);
				add(uis);
				add(remindSubmit);
				
				winTitle.setBounds(500,10,675,100);
				ui.setBounds(175,150,400,200);
				uun.setBounds(600,175,400,150);
				uuc.setBounds(150,350,950,200);
				uis.setBounds(250,620,750,100);
				remindSubmit.setBounds(100,530,650,100);
				
				writeFile();
			}
			
			public void writeFile()
			{
				File file = new File(fileName);
				try
				{
					makesOutput = new PrintWriter(new FileWriter(file,true));
				}
				catch(IOException e)
				{
					System.err.print("Cannot create " + fileName
						+ " file to be written to");
					System.exit(1);
				}
			}
			
			class UnitImage extends JPanel
			{
				private Image winPicture; //the image in the win page
				private String winPictureName; // the image name
				
				public UnitImage()
				{
					winPicture = null;
					winPictureName = new String("UnitImageWin.jpg");
					getMyImage();
				}
				
				public void getMyImage()
				{
					File picFile = new File(winPictureName);
					try
					{
						winPicture = ImageIO.read(picFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n" + winPictureName
							+ " cannot be found");
						e.printStackTrace();
					}
				}
				
				public void paintComponent(Graphics g)
				{
					g.drawImage(winPicture,0,0,300,200,this);
				}
			}
			
			class UnitUsername extends JPanel
			{
				public UnitUsername()
				{
					setLayout(new GridLayout(2,1));
					
					labelName = new JLabel("Your Name");
					textFieldName = new JTextField(20);
					textFieldName.addActionListener(new UnitInfoSubmit());
					
					add(labelName);
					add(textFieldName);
				}
			}
			
			class UnitUserComments extends JPanel 
			{
				public UnitUserComments()
				{
					setLayout(new GridLayout(2,1));
					
					labelComments = new JLabel("Comments");
					textFieldComments = new JTextField(40);
					textFieldComments.addActionListener(new UnitInfoSubmit());
					
					add(labelComments);
					add(textFieldComments);
				}
			}
			
			class UnitInfoSubmit extends JPanel implements ActionListener
			{
				JButton resultSubmit; //button "Submit"; change to "Got it! Thank you" 
				//after clicked
				JButton resultMain; //button "Main Pahe"
				JButton resultRestart; //button "Restart" (play the game again)
				JButton resultUserRecords; // button that shows past user's recods
				
				private String clicked; //get the string on the button that's clicked
			
				public UnitInfoSubmit()
				{
					resultSubmit = new JButton("Submit");
					add(resultSubmit);
					resultSubmit.setBackground(Color.GREEN);
					resultSubmit.setOpaque(true);
					
					resultMain = new JButton("Main Page");
					add(resultMain);
					
					resultRestart = new JButton("Start Over");
					add(resultRestart);
					
					resultUserRecords = new JButton("Past Users' Records");
					add(resultUserRecords);
					
					clicked = "";
		
					resultSubmit.addActionListener(this);
					resultMain.addActionListener(this);
					resultRestart.addActionListener(this);
					resultUserRecords.addActionListener(this);
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					clicked = evt.getActionCommand();
					
					//if one button is clicked
					if(clicked.equals("Submit"))
					{
						//comments + check
						userCommentsIn = textFieldComments.getText();
						if(userCommentsIn.trim().equals(""))
							commentsFilled = false;	
						else
							commentsFilled = true;
					
						//username + check
						usernameIn = textFieldName.getText();
						if(usernameIn.trim().equals(""))
							nameFilled = false;
						else 
							nameFilled = true;
							
						//filled in or not
						if (nameFilled && commentsFilled)
						{
							makesOutput.println(usernameIn + ": " + userCommentsIn);
							commentsFilled = false;
							nameFilled = false;
							labelName.setForeground(Color.BLACK);
							labelComments.setForeground(Color.BLACK);
					
							resultSubmit.setText("Go it! Thank you(Choose either one --> )");
							resultSubmit.setBackground(Color.LIGHT_GRAY);
							resultSubmit.setOpaque(true);
							
							while(input.hasNext())
							{
								userRecordsString = input.nextLine();
								userRecordsTxtArea.append(userRecordsString + "\n");
							}
						}
						
						else
						{
							if((!nameFilled) && (!commentsFilled))
							{
								labelName.setForeground(Color.RED);
								labelComments.setForeground(Color.RED);
							}
							else if(!nameFilled)
							{
								labelName.setForeground(Color.RED);
								labelComments.setForeground(Color.BLACK);
							}	
							else 
							{
								labelComments.setForeground(Color.RED);
								labelName.setForeground(Color.BLACK);
							}
								
						}
						makesOutput.close();
					}
					
					else if(clicked.equals("Main Page"))
					{
						clU.show(uc, "GamePanel");
						cl.show(opU, "MainPanel");
						
						textFieldName.setText("");
						textFieldComments.setText("");
						
						usernameIn = "";
						userCommentsIn = "";
						
						labelName.setForeground(Color.BLACK);
						labelComments.setForeground(Color.BLACK);
						
						resultSubmit.setBackground(Color.GREEN);
						resultSubmit.setOpaque(true);
						
						resultSubmit.setText("Submit");
						
						File file = new File(fileName);
						try
						{
							makesOutput = new PrintWriter(new FileWriter(file,true));
						}
						catch(IOException e)
						{
							System.err.print("Cannot create " + fileName
								+ " file to be written to");
							System.exit(2);
						}
					}
					
					else if(clicked.equals("Start Over"))
					{
						clU.show(uc, "GamePanel");
						
						textFieldName.setText("");
						textFieldComments.setText("");
						
						usernameIn = "";
						userCommentsIn = "";
						
						labelName.setForeground(Color.BLACK);
						labelComments.setForeground(Color.BLACK);
						
						resultSubmit.setBackground(Color.GREEN);
						resultSubmit.setOpaque(true);
						
						resultSubmit.setText("Submit");
						
						File file = new File(fileName);
						try
						{
							makesOutput = new PrintWriter(new FileWriter(file,true));
						}
						catch(IOException e)
						{
							System.err.print("Cannot create " + fileName
								+ " file to be written to");
							System.exit(3);
						}
					}
					
					else if (clicked.equals("Past Users' Records"))
					{
						clU.show(uc,"ResultUserRecords");
					}
				}
			}
		}
		
		class UnitResultUserRecords extends JPanel
		{
			private UnitResultUserRecordsButtons ururb; //button class object
			private JScrollPane scroll; // scroll bar for text area
			public UnitResultUserRecords()
			{
				setLayout(new BorderLayout());
				
				ururb = new UnitResultUserRecordsButtons();
				add(ururb, BorderLayout.NORTH);
				add(userRecordsTxtArea, BorderLayout.CENTER);
				
				scroll = new JScrollPane(userRecordsTxtArea);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				add(scroll);
				scanFile();
			}
			
			class UnitResultUserRecordsButtons extends JPanel implements ActionListener
			{
				private JButton userRMainPage; //button go to main page
				private JButton userRBack;  // button goes back to last page 
				private JLabel userRLabel; // label that tell user s/he's in user record page
				
				public UnitResultUserRecordsButtons()
				{
					setBackground(Color.YELLOW);
					userRMainPage = new JButton("Main Page");
					userRBack = new JButton("Go Back");
					
					userRLabel = new JLabel("\t\tUser Records");
					userRLabel.setFont(new Font("Serif",Font.BOLD,20));
					
					add(userRBack);
					add(userRMainPage);
					add(userRLabel);
					
					userRBack.addActionListener(this);
					userRMainPage.addActionListener(this);
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					String recordsClicked = evt.getActionCommand();
					
					if (recordsClicked.equals("Go Back"))
					{
						clU.show(uc,"ResultWinPanel");
					}
					else if (recordsClicked.equals("Main Page"))
					{
						cl.show(opU, "MainPanel");
					}
				}
			}
			
			public void scanFile()
			{
				File inFile = new File(fileName);
				try
				{
					input = new Scanner(inFile);
				}
				catch(FileNotFoundException e)
				{
					System.err.println("Cannot find file " + fileName);
					System.exit(3);
				}
				while(input.hasNext())
				{
					userRecordsString = input.nextLine();
					userRecordsTxtArea.append(userRecordsString + "\n");
				}
			}	
		}
		
		//This panel shows up if the user got it wrong after the point is 0
		class UnitResultLosePanel extends JPanel implements ActionListener
		{
			private JLabel loseLabel; //label tat says"Game Over"
			private JButton again; //button "Play It Again". Restart the game
			private JButton mainPage; //button "Main Page". Go to the main page
			public UnitResultLosePanel()
			{
				setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
				
				loseLabel = new JLabel("Game Over");
				loseLabel.setFont(new Font("Serif",Font.BOLD,20));
				again = new JButton("Play It Again");
				again.setBackground(Color.GREEN);
				again.setOpaque(true);
				again.setPreferredSize(new Dimension(400, 100));
				
				mainPage = new JButton("Main Page");
				mainPage.setPreferredSize(new Dimension(400, 100));
			
				add(loseLabel);
				add(again);
				add(mainPage);
			
				again.addActionListener(this);
				mainPage.addActionListener(this);				
			}
				
			public void actionPerformed(ActionEvent evt)
			{
				String clicked = evt.getActionCommand();
				
				if(clicked.equals("Play It Again"))
				{
					clU.show(uc,"GamePanel");
				}
					
				else if(clicked.equals("Main Page"))
				{
					clU.show(uc,"GamePanel");
					cl.show(opU,"MainPanel");
				}	
			}	
		}
	}
	
//Game "Pythagorean"
//By Tony
	class Pythagorean extends JPanel
	{
		private OverallPanel op; 
		private PythGamePanel pgp;
		private PythHardGamePanel phgp;
		private Pythagorean p;
		private PythResultUserRecords prup;
		private CardLayout cards; // card layout for all the panels above
		private int score; // It will be the number of stars
		private Scanner input; // Scanner for reading from the text file
		private JTextArea userRecord; // Text area that shows past user record
		private String userString; // string value for reading from the text file
		
		// Create two instances of instruction panel and game panel
		// and add it to card layout.
		public Pythagorean(OverallPanel opInPytha)
		{
			input = null;
			
			p = this;
			score = 3;
			op = opInPytha;
			cards = new CardLayout();
			
			userString = new String("");
			userRecord = new JTextArea(userString);
			userRecord.setLineWrap(true);
			userRecord.setEditable(false);
			userRecord.setFont(new Font("Arial",Font.PLAIN,20));
			
			phgp = new PythHardGamePanel(this);
			PythInstructionPanel poc = new PythInstructionPanel(this);
			pgp = new PythGamePanel(this);
			PythWinResultPanel pwrp = new PythWinResultPanel(this);
			prup = new PythResultUserRecords(this);
			PythLosePanel plp = new PythLosePanel(this);
			
			
			setLayout(cards);
			add(poc,"instruction panel");
			add(phgp,"hard game panel");
			add(pgp,"game panel");
			add(pwrp,"winning result panel");
			add(prup,"Past user record");
			add(plp,"losing result panel");
			
		}
		
		// this class creates two different parts inside the instruction
		// panel using Grid layout
		class PythInstructionPanel extends JPanel
		{		
			private Pythagorean pyta; // instance of Pythagorean class
			
			public PythInstructionPanel(Pythagorean pytaIn)
			{
				pyta = pytaIn;
				
				setLayout(new GridLayout(2,1));
			
				PythInstructionTop pit = new PythInstructionTop();
				PythInstructionBottom pib = new PythInstructionBottom();
			
				add(pit);
				add(pib);
			}
			
			// This class makes 3 different labels which explains about 
			// the direction of this game.
			class PythInstructionTop extends JPanel
			{
				//these are for the texts in the instruction page
				private Direction d;
				private Label1 l1;
				private Label2 l2;
			
				public PythInstructionTop()
				{
					d = new Direction();
					l1 = new Label1();
					l2 = new Label2();
					
				//	setBackground(Color.PINK);
					setLayout(new GridLayout(3,1));
			
					add(d);
					add(l1);
					add(l2);
				}
				
				class Direction extends JPanel
				{
					// These are texts for the direction
					private JLabel direction;
					private Font direcFont;
					
					public Direction()
					{
						setBackground(Color.PINK);
						direction = new JLabel("Direction");
						direcFont = new Font("Serif",Font.ITALIC + Font.BOLD,20);
						direction.setFont(direcFont);
						
						add(direction);
					}
				}
				class Label1 extends JPanel
				{
					private JLabel labeld1; // label for first direction
					
					public Label1()
					{
						setBackground(Color.PINK);
						labeld1 = new JLabel("-Click on 3 cards that are valid numbers for Pythagorean theorem");
						add(labeld1);
					}
				}
				class Label2 extends JPanel
				{
					private JLabel labeld2; // second lable for direction
					
					public Label2()
					{
						setBackground(Color.PINK);
						labeld2 = new JLabel("-You can choose level: easy or hard");
						add(labeld2);
					}
				}
			}

			// This class makes a label for how the gmae is being played
			// locate it using grid layout
			class PythInstructionBottom extends JPanel
			{
				//these variables are for the texts in the instruction page
				private HowItsPlayed hip;
				private Labelh1 h1;
				private Labelh2 h2;
				private Labelh3 h3;
				private Labelh4 h4;
				private Labelh5 h5;
				private PythInstructionButton buttons;
		
		
				public PythInstructionBottom()
				{
					setLayout(new GridLayout(7,1));
			
					hip = new HowItsPlayed();
					h1 = new Labelh1();
					h2 = new Labelh2();
					h3 = new Labelh3();
					h4 = new Labelh4();
					h5 = new Labelh5();
					buttons = new PythInstructionButton();
			
					add(hip);
					add(h1);
					add(h2);
					add(h3);
					add(h4);
					add(h5);
					add(buttons);
				}
				
				class HowItsPlayed extends JPanel
				{
					// label for how it's played
					private JLabel howItsPlayed; 
					private Font howItsFont;
					
					public HowItsPlayed()
					{
						setBackground(Color.YELLOW);
						howItsPlayed = new JLabel("How It's Played");
						howItsFont = new Font("Serif",Font.ITALIC + Font.BOLD,20);
						howItsPlayed.setFont(howItsFont);
						
						add(howItsPlayed);
					}
				}
				class Labelh1 extends JPanel
				{
					// label for how it's played: 1st one
					private JLabel labelh1;
					
					public Labelh1()
					{
						setBackground(Color.YELLOW);
						labelh1 = new JLabel("-You start with 3 stars");
						add(labelh1);
					}
				}
				class Labelh2 extends JPanel
				{
					private JLabel labelh2; // label for second how to play
					
					public Labelh2()
					{
						setBackground(Color.YELLOW);
						labelh2 = new JLabel("-Get 1 star if the cards matches the theorem");
						add(labelh2);
					}
				}
				class Labelh3 extends JPanel
				{
					private JLabel labelh3; // label for third how to play
					
					public Labelh3()
					{
						setBackground(Color.YELLOW);
						labelh3 = new JLabel("-Lose 1 star if the card matches are incorrect");
						add(labelh3);
					}
				}
				class Labelh4 extends JPanel
				{
					private JLabel labelh4; // Fourth label for how to play
					
					public Labelh4()
					{
						setBackground(Color.YELLOW);
						labelh4 = new JLabel("-Reach 5 starts to win the game in given 10 min");
						add(labelh4);
					}
				}
				class Labelh5 extends JPanel
				{
					private JLabel labelh5; // Fifth label for how to play
					
					public Labelh5()
					{
						setBackground(Color.YELLOW);
						labelh5 = new JLabel("-If you get 0 star or exceed the given time, you lose"); 
						add(labelh5);
					}
				}
		
				// Create two buttons: main and start button, which will
				// lead into different panels. main --> main panel,
				// start --> game panel
				class PythInstructionButton extends JPanel implements ActionListener
				{
					private JButton mainPageButton; //the button "Main Page"
					private JButton startGameButton; //the button "START game"
				//	private GameTimer gt;
		
					public PythInstructionButton()
					{
					//	gt = new GameTimer();
						setLayout(new GridLayout(1,3));
			
						mainPageButton = new JButton("Main Page");
						startGameButton = new JButton("START game");
		
						add(mainPageButton);
						add(startGameButton);
						
						startGameButton.setForeground(Color.GREEN);
						startGameButton.setBackground(Color.WHITE);
						startGameButton.setOpaque(true);
			
						mainPageButton.addActionListener(this);
						startGameButton.addActionListener(this);
					}
					
		
					public void actionPerformed(ActionEvent evt)
					{
						
						String buttonName = evt.getActionCommand();
			
						if(buttonName.equals("Main Page"))
						{
							cards.show(p,"instruction panel");
							cl.show(op,"MainPanel");
						}
						else if(buttonName.equals("START game"))
						{
							pgp.startGame();
							cards.show(pyta,"game panel");
						}
					}
				}
			}
		}
		
		class PythGamePanel extends JPanel
		{
			
			private Pythagorean pyth;// Pythagorean instance 
			// These are subclasses of PythGamePanel
			private SouthPanel sp; 
			private CenterPanel cp; 
			
			public PythGamePanel(Pythagorean pythIn)
			{
				pyth = pythIn;
				
				setLayout(new BorderLayout());
				
				sp = new SouthPanel();
				cp = new CenterPanel();
				
				add(sp, BorderLayout.SOUTH);
				add(cp, BorderLayout.CENTER);
			}
			
			public void startGame()
			{
				sp.startGame();
				cp.startGame();
			}
			
			// Set the timer to 10 minand make a radio button in order to set 
			// the difficulty for the game. 
			class SouthPanel extends JPanel
			{
				private GameTimer gt; // class GameTimer
				
				public SouthPanel()
				{
					setLayout(new GridLayout(2,1));
					
					gt = new GameTimer();
					GameDifficulty gd = new GameDifficulty();
					GameButtons gb = new GameButtons();
					
					add(gt);
					add(gd);
					add(gb);
				}
				
				public void startGame()
				{
					gt.restartTimer();
				}
			
				// Set the timer and make the timer to show up so that 
				//  the user can see. 
				class GameTimer extends JPanel implements ActionListener
				{
					private JLabel remainTime; // label that shows the remaining time
					private Timer timer; // Timer for decreasing every second and changing the label
					private int minute; // minute. which decreaes by 1 in every 60 sec
					private int second; // second that decreaes every second
					
					public GameTimer()
					{
						minute = 9;
						second = 59;
						timer = new Timer(1000,this);
						remainTime = new JLabel("Remaining time = 9:59");
						
						remainTime.setFont(new Font("serif",Font.BOLD,15));
						add(remainTime);						
					}
					
					public void restartTimer()
					{
						minute = 9;
						second = 59;
						remainTime.setText("Remaining time = " + minute + ":" + second);
						repaint();

						timer.stop();
						timer.start();
					}
					
					
					
					public void actionPerformed(ActionEvent evt)
					{
						second --;
						if(second < 0)
						{
							minute--;
							second = 59;
						}
						remainTime.setText("Remaining time = " + minute + ":" + second);
						
						if(minute==0 && second==0)
						{
							timer.stop();
							cards.show(pyth,"losing result panel");
						}
						
						repaint();
					}
					
					public void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						requestFocusInWindow();
					}
				}
				
				// Create two different radio button with the button 
				// gruop: easy and hard to change the game's difficulty
				class GameDifficulty extends JPanel implements ActionListener
				{
					private JRadioButton easy; // radiobutton for easy
					private JRadioButton hard; // radio button for hard
					private ButtonGroup buttonGroup; // button gruop that holds two radio buttons
					
					public GameDifficulty()
					{
						easy = new JRadioButton("Easy");
						hard = new JRadioButton("Hard");
						buttonGroup = new ButtonGroup();
						
						buttonGroup.add(easy);
						buttonGroup.add(hard);
						
						setLayout(new FlowLayout(10));
						add(easy);
						add(hard);
						
						easy.setSelected(true);
						
						easy.addActionListener(this);
						hard.addActionListener(this);
					}
					
					public void actionPerformed(ActionEvent evt)
					{
						String value = evt.getActionCommand();
						if(value.equals("Easy"))
						{
							pgp.startGame();
						//	cards.show(p,"game panel");

							repaint();
							cards.show(p,"game panel");
							easy.setSelected(true);
							hard.setSelected(false);
						}
						else if(value.equals("Hard"))
						{
							phgp.startGame();
						//	cards.show(p,"hard game panel");
							
							repaint();
							cards.show(p,"hard game panel");
							easy.setSelected(true);
							hard.setSelected(false);
						}
							
					}
				}
				
				// This class creates a button, and if the user press
				// the buttn, it will change the panel to main panel.
				class GameButtons extends JPanel implements ActionListener
				{
					private JButton mainMenu; // button for main page
					private JButton restart; // button for restarting the game
					private JButton instruction; // button for going back to instruction panel
					
					private CenterPanel cp;
					private GameTimer gt;
					
					public GameButtons()
					{
						gt = new GameTimer();
						cp = new CenterPanel();
						
						mainMenu = new JButton("Main menu");
						restart = new JButton("Restart");
						instruction = new JButton("Instruction Page");
						
						setLayout(new FlowLayout());
						add(mainMenu);
						add(restart);
						add(instruction);
						
						mainMenu.addActionListener(this);
						restart.addActionListener(this);
						instruction.addActionListener(this);
					}
					
					public void actionPerformed(ActionEvent evt)
					{
						String press = evt.getActionCommand();
						
						if(press.equals("Main menu"))
						{
							cards.show(pyth,"instruction panel");
							cl.show(op,"MainPanel");
						}
						else if (press.equals("Restart"))
						{
							//gt.restartTimer();
							pgp.startGame();
							cards.show(pyth,"game panel");
						}
						else if (press.equals("Instruction Page"))
						{
							pgp.startGame();
							cards.show(pyth,"instruction panel");
						}
							
					}
				}
			}
			
		// This will be a pythagorean theorem game. 
			class CenterPanel extends JPanel implements ActionListener,MouseListener
			{
				private String [] imageName; // string array of image name
				private Image [] images; // image array
				private Timer time; // timer for flipping back every card after 4 sec
				private Image backgroundImage; // background image	
				private Image starImage; // star image
				private Timer flippingTime; // timer for when the user gets the pair wrong, 
											// it stays for 1 second and flips back. 
				
				
				private int xPos; // x position of the mouse pressed. 
				private int yPos; // y position of the mouse pressed 
				private int numOfCards; // number of cards
				private int numOfCols; // numebr of colums
				private int horSize; // horizontal size the card
				private int verSize; // vertical size of the card
				private int clickCounter; // int value to keep track of number of clicked(3 max)
				private int [] imageValue; // squared value of each card image
				private int firstIndex = 0; // fisrt index when uesr press the card
				private	int secondIndex = 0; // second index when user pressed the card after first
				private	int thirdIndex = 0; // third index when the user press the card after second
				private int [] openCount; // int array for checking whether it's flipped opened or not. if yes, assign as 1. 
				private int timeCount; // value in order to make it not possible to click the card before time stops. 
				private int flipTimeCount;// value needed to make it impossible to click the card before flippingTime stops. 
				
				private FlipCards [] fc; // array of a class object(one class for each card)
				
				public CenterPanel()
				{
					flipTimeCount = 0;
					timeCount = 0;
					firstIndex = 0;
					secondIndex = 0;
					thirdIndex = 0;
					
					openCount = new int[18];
					imageValue = new int[]{9,5,676,36,49,8,169,100,25,64,45,25,16,144,4,
											100,3,576};
					clickCounter = 0;					
					numOfCards = 18;
					numOfCols = 6;
					horSize = 116;
					verSize = 133;
					fc = new FlipCards[numOfCards];
							
					flippingTime = new Timer(1000,this);
					time = new Timer(4000,this);
					images = new Image[18];
								
					imageName = new String[]{"Picture1.jpg","Picture2.jpg",
								"Picture3.jpg","Picture4.jpg",
								"Picture5.jpg","Picture6.jpg", "Picture7.jpg",
								"Picture8.jpg", "Picture9.jpg",
								"Picture10.jpg","Picture11.jpg" ,"Picture9.jpg", 
								"Picture13.jpg", "Picture14.jpg", "Picture15.jpg",
								"Picture8.jpg", "Picture17.jpg","Picture18.jpg"};
					try
					{
						for(int i=0; i<imageName.length; i++)
						{
							File pictFile = new File(imageName[i]);
							images[i] = ImageIO.read(pictFile);
						}
					}
					catch(IOException e)
					{
						System.err.println("\n\n can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					try
					{
						File backFile  = new File("BacksideImage.jpg");
						backgroundImage = ImageIO.read(backFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					try
					{
						File topFile = new File("StarImage.jpg");
						starImage = ImageIO.read(topFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					for(int i=0; i<fc.length; i++)
					{
						if(i>=0 && i<6)
							fc[i] = new FlipCards(i,250 + i%numOfCols*horSize,100);
						else if(i>=6 && i<12)
							fc[i] = new FlipCards(i,250 + i%numOfCols*horSize,233);
						else if(i>=12 && i<18)
							fc[i] = new FlipCards(i,250 + i%numOfCols*horSize,369);
					}
					
					time.addActionListener(this);
					showAll();
					//time.start();
					
					addMouseListener(this);
				}
				
				public void startGame()
				{
					score = 3;
					clickCounter = 0;
					firstIndex = 0;
					secondIndex = 0;
					thirdIndex = 0;
					showAll();
					repaint();
					time.stop();
					time.start();
					for(int i=0; i<numOfCards; i++)
						openCount[i] = 0;
					timeCount = 0;
					flipTimeCount = 0;
				}
				
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.setColor(Color.WHITE);
					g.fillRect(250,100,700,400);
					
					g.setColor(Color.BLACK);
					g.drawLine(366,100,366,500);
					g.drawLine(482,100,482,500);
					g.drawLine(598,100,598,500);
					g.drawLine(714,100,714,500);
					g.drawLine(830,100,830,500);
					g.drawLine(482,100,482,500);
					g.drawLine(250,233,950,233);
					g.drawLine(250,369,950,369);
					
					if(score <= 0)
						cards.show(pyth,"losing result panel");
					
					if(openCount[firstIndex] != 1 && openCount[secondIndex] != 1
						&& openCount[thirdIndex] != 1)
					{
						if(score <= 0)
							g.drawImage(starImage,0,0,0,0,0,0,0,0,this);
						
						else if(score == 1)
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
						else if(score == 2)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
						}
						else if(score == 3)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,633,80,0,0,50,50,this);
						}
						else if(score == 4)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
						}
						else if(score == 5)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
							g.drawImage(starImage,682,30,732,80,0,0,50,50,this);
						}
					}
					else
					{
						if(score <= 0)
							g.drawImage(starImage,0,0,0,0,0,0,0,0,this);
						
						else if(score == 1)
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
						else if(score == 2)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
						}
						else if(score == 3)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,633,80,0,0,50,50,this);
						}
						else if(score == 4)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
						}
						else if(score == 5)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
							g.drawImage(starImage,682,30,732,80,0,0,50,50,this);
						}
					}
					
					
					int count = 0;
					if(score == 5)
						cards.show(p,"winning result panel");
					for(int i=0; i<numOfCards; i++)
					{
						if(openCount[i] == 1)
							count++;
						if(count == 18)
						cards.show(p,"winning result panel");
					}
						
					
					
					for(int i=0; i<fc.length; i++)
						fc[i].draw(g);
				}
				
				public void actionPerformed(ActionEvent evt)
				{					
					if(evt.getSource() == time)
					{
						hideAll();
						repaint();
						time.stop();
						timeCount = 10;
					}	
					else if(evt.getSource() == flippingTime)
					{	
						if(openCount[firstIndex] != 1 && openCount[secondIndex] != 1
							&& openCount[thirdIndex] != 1)
						{
							fc[firstIndex].hide();
							fc[secondIndex].hide();
							fc[thirdIndex].hide();
							score --;
						}
						
					//	score --;
						if(score <= 0)
							score = 0;
							
						
						clickCounter = 0;
						firstIndex = 0;
						secondIndex = 0;
						thirdIndex = 0;
						repaint();
						
						flippingTime.stop();
						flipTimeCount = 0;
					}
				}
				
				public void showAll()
				{
					for(int i=0; i<fc.length; i++)
						fc[i].show();
				}
				
				public void hideAll()
				{
					for(int i=0; i<fc.length; i++)
						fc[i].hide();
				}
				
				public void mousePressed(MouseEvent evt)
				{	
					xPos = evt.getX();
					yPos = evt.getY();
							
					if(timeCount == 10 && flipTimeCount == 0)
					{
						for(int i=0; i<fc.length; i++)
						{
							if(fc[i].cardSelected(xPos,yPos))
							{
								if(clickCounter == 0)
								{
									firstIndex = i;
								}
								else if(clickCounter == 1)
								{
									secondIndex = i;
									if(firstIndex == secondIndex)
									{
										secondIndex = 0;
										clickCounter = 0;
									}
								}
								else if(clickCounter == 2)
								{
									thirdIndex = i;
									if(secondIndex == thirdIndex || firstIndex == thirdIndex)
									{
										thirdIndex = 0;
										clickCounter = 1;
									}
								}
								
								clickCounter++;
								fc[i].show();
														
								if(openCount[firstIndex] == 1)
								{
									clickCounter = 0;
									firstIndex = 0;
									secondIndex = 0;
									thirdIndex = 0;
								}
								
								else if(openCount[secondIndex] == 1)
								{
									clickCounter = 1;
									secondIndex = 0;
									thirdIndex = 0;
								}
								
								else if(openCount[thirdIndex] == 1)
								{
									clickCounter = 2;
									thirdIndex = 0;
								}
								
								if(clickCounter==3)
								{
									if(fc[i].cardCheck(firstIndex,secondIndex,thirdIndex))
									{
										//fc[i].show();
										repaint();
										score ++;
																				
										if(score >= 5)
											score = 5;
										clickCounter = 0;
										firstIndex = 0;
										secondIndex = 0;
										thirdIndex = 0;
									}
									else
									{
										//score --;
										if(score <= 0)
											score = 0;
										repaint();
										flippingTime.start();
										flipTimeCount = 1;
									}
								}
							}
						}
						repaint();	
					}
				}
				
				public void mouseReleased(MouseEvent evt) {}
				public void mouseClicked(MouseEvent evt) {}
				public void mouseEntered(MouseEvent evt) {}
				public void mouseExited(MouseEvent evt) {}
				
				class FlipCards
				{
					private Image image; // according image after getting the image index and position as a parameter 
					private int x; // x position of the image
					private int y; // y position of the image
					private boolean shouldReveal; // boolean that checks whether to draw front side or backside. 
					
					public FlipCards(int imageIndex, int xVal, int yVal)
					{
						image = images[imageIndex];
						x = xVal;
						y = yVal;
						shouldReveal = false;
					}
					
					public void draw(Graphics g)
					{
						if(shouldReveal)
							g.drawImage(image,x+10,y+10,horSize-20,verSize-20,null);
						else 
							g.drawImage(backgroundImage,x,y,horSize,verSize,null);
					}
					
					public boolean cardSelected(int xClicked, int yClicked)
					{
						boolean condition = false;
						if(xClicked>x && xClicked < x+horSize && yClicked>y && yClicked < y+verSize)
							condition = true;
						return condition;
					}
					
					public void show()
					{
						shouldReveal = true;
					}
					
					public void hide()
					{
						shouldReveal = false;
					}
					
					
					public boolean cardCheck(int index1, int index2, int index3)
					{
						int max = index1;
						int sum = 0;
						
						if(imageValue[max]<imageValue[index2])
							max = index2;
						if(imageValue[max]<imageValue[index3])
							max = index3;
										
						if(index1!= max)
							sum += imageValue[index1];
						if(index2 != max)
							sum += imageValue[index2];
						if(index3 != max)
							sum += imageValue[index3];
										
						if(sum == imageValue[max])
						{
							openCount[index1] = 1;
							openCount[index2] = 1;
							openCount[index3] = 1;
						}
						return sum == imageValue[max];
					}
					
				}
			}
		}
		
		class PythHardGamePanel extends JPanel
		{
			private Pythagorean pyth; // instance of the Pythagorean
			private SouthPanel2 sp2; // instacne for SouthPanel
			private CenterPanel2 cp2; // instance for CenterPanel
			
			public PythHardGamePanel(Pythagorean pythIn)
			{
				pyth = pythIn;
				
				setLayout(new BorderLayout());
				
				sp2 = new SouthPanel2();
				cp2 = new CenterPanel2();
				
				add(sp2, BorderLayout.SOUTH);
				add(cp2, BorderLayout.CENTER);
				
				phgp = this;
				
			//	restartTimer();
				startGame();
			}
			
			public void startGame()
			{
				sp2.startGame2();
				cp2.startGame2();
			}
			
			// Set the timer to 10 minand make a radio button in order to set 
			// the difficulty for the game. 
			class SouthPanel2 extends JPanel
			{
				private GameTimer2 gt2; // Instance for GameTimer2 class
				private JRadioButton easy; // radio button for easy mode
				private JRadioButton hard; // radio button for hard mode
				
				public SouthPanel2()
				{
					setLayout(new GridLayout(2,1));
					
					gt2 = new GameTimer2();
					GameButtons2 gb2 = new GameButtons2();
					GameDifficulty2 gd2 = new GameDifficulty2();
					
					add(gt2);
					add(gd2);
					add(gb2);
				}
				
				public void startGame2()
				{
					gt2.restartTimer2();
				}
			
				// Set the timer and make the timer to show up so that 
				//  the user can see. 
				class GameTimer2 extends JPanel implements ActionListener
				{
					private JLabel remainTime; // label that shows how much time u have
					private Timer timer; // Timer that decreases every second(10min)
					private int minute; // decreaes 1 in every 60 sec
					private int second; // decreaes every sec
					
					public GameTimer2()
					{
						minute = 9;
						second = 59;
						timer = new Timer(1000,this);
						remainTime = new JLabel("Remaining time = 9:59");
						
						remainTime.setFont(new Font("serif",Font.BOLD,15));
						add(remainTime);						
					}
					
					public void restartTimer2()
					{
						minute = 9;
						second = 59;
						remainTime.setText("Remaining time = " + minute + ":" + second);
						repaint();

						timer.stop();
						timer.start();
					}
					
					public Timer getTimer()
					{
						return timer;
					}
					
					public void setTimer(Timer gameTimer)
					{
						timer =  gameTimer;
					}
					
					
					public void actionPerformed(ActionEvent evt)
					{
						second --;
						if(second < 0)
						{
							minute--;
							second = 59;
						}
						remainTime.setText("Remaining time = " + minute + ":" + second);
						
						if(minute==0 && second==0)
							timer.stop();
						
						repaint();
					}
					
					public void paintComponent(Graphics g)
					{
						super.paintComponent(g);
						requestFocusInWindow();
					}
				}
				
				class GameDifficulty2 extends JPanel implements ActionListener
				{
					private ButtonGroup buttonGroup; // button group for radio button easy and hard
					
					public GameDifficulty2()
					{
						easy = new JRadioButton("Easy");
						hard = new JRadioButton("Hard");
						buttonGroup = new ButtonGroup();
						
						buttonGroup.add(easy);
						buttonGroup.add(hard);
						
						setLayout(new FlowLayout(10));
						add(easy);
						add(hard);
						
						hard.setSelected(true);
						
						easy.addActionListener(this);
						hard.addActionListener(this);
					}
					
					public void actionPerformed(ActionEvent evt)
					{
						String value = evt.getActionCommand();
						if(value.equals("Easy"))
						{
							pgp.startGame();
						//	cards.show(p,"game panel");
							
							easy.repaint();
							hard.repaint();
							repaint();
							cards.show(p,"game panel");
							easy.setSelected(false);
							hard.setSelected(true);
						}
						else if(value.equals("Hard"))
						{
							phgp.startGame();
						//	cards.show(p,"hard game panel");
							
							easy.repaint();
							hard.repaint();
							repaint();
							cards.show(p,"hard game panel");
							hard.setSelected(true);
							easy.setSelected(false);
						}
							
					}
				}

				// This class creates a button, and if the user press
				// the buttn, it will change the panel to main panel.
				class GameButtons2 extends JPanel implements ActionListener
				{
					private JButton mainMenu; // button for main menu
					private JButton restart; // button for restart
					private JButton instruction; // button for instruction
					
					private CenterPanel2 cp2;
					private GameTimer2 gt2;
					
					public GameButtons2()
					{
						gt2 = new GameTimer2();
						cp2 = new CenterPanel2();
						
						mainMenu = new JButton("Main menu");
						restart = new JButton("Restart");
						instruction = new JButton("Instruction Page");
						
						setLayout(new FlowLayout());
						add(mainMenu);
						add(restart);
						add(instruction);
						
						mainMenu.addActionListener(this);
						restart.addActionListener(this);
						instruction.addActionListener(this);
					}
					
					public void actionPerformed(ActionEvent evt)
					{
						String press = evt.getActionCommand();
						
						if(press.equals("Main menu"))
						{
							cards.show(pyth,"instruction panel");
							//startGame2();
							//cards.show(pyth,"hard game panel");
							easy.setSelected(true);
							cl.show(op,"MainPanel");
						}
						else if (press.equals("Restart"))
						{
						//	phgp.restartTimer();
							phgp.startGame();
							cards.show(p,"hard game panel");
						}
						else if (press.equals("Instruction Page"))
						{
							startGame2();
							easy.setSelected(true);
							cards.show(pyth,"hard game panel");
							cards.show(pyth,"instruction panel");
						}
							
					}
				}
				
				
			}
			
		// This will be a pythagorean theorem game. 
			class CenterPanel2 extends JPanel implements ActionListener,MouseListener
			{
				private String [] imageName; // string array of image name
				private Image [] images; // image array
				private Timer time; // timer for flipping back every card after 4 sec
				private Image backgroundImage; // background image	
				private Image starImage; // star image
				private Timer flippingTime; // timer for when the user gets the pair wrong, 
											// it stays for 1 second and flips back. 
				
				private int xPos; // x position of the mouse pressed. 
				private int yPos; // y position of the mouse pressed 
				private int numOfCards; // number of cards
				private int numOfCols; // numebr of colums
				private int horSize; // horizontal size the card
				private int verSize; // vertical size of the card
				private int clickCounter; // int value to keep track of number of clicked(3 max)
				private int [] imageValue; // squared value of each card image
				private int firstIndex = 0; // fisrt index when uesr press the card
				private	int secondIndex = 0; // second index when user pressed the card after first
				private	int thirdIndex = 0; // third index when the user press the card after second
				private int [] openCount; // int array for checking whether it's flipped opened or not. if yes, assign as 1. 
				private int timeCount; // value in order to make it not possible to click the card before time stops. 
				private int flipTimeCount;// value needed to make it impossible to click the card before flippingTime stops. 
				
				private FlipCards2 [] fc; // array of a class object(one class for each card)
				
							
				public CenterPanel2()
				{
					flipTimeCount = 0;
					timeCount = 0;
					firstIndex = 0;
					secondIndex = 0;
					thirdIndex = 0;
					
					openCount = new int[21];
					imageValue = new int[]{9,676,36,5,8,49,9,100,64,169,25
											,45,4,400,576,625,25,100,225,16,144};
					clickCounter = 0;
					
					numOfCards = 21;
					numOfCols = 7;
					horSize = 116;
					verSize = 133;
					fc = new FlipCards2[numOfCards];
							
					flippingTime = new Timer(1000,this);
					time = new Timer(3000,this);
					images = new Image[21];
								
					imageName = new String[]{"Picture1.jpg","Picture3.jpg",
								"Picture4.jpg","Picture2.jpg",
								"Picture6.jpg","Picture5.jpg", "Picture1.jpg",
								"Picture8.jpg", "Picture10.jpg",
								"Picture7.jpg","Picture9.jpg" ,"Picture11.jpg", 
								"Picture15.jpg", "Picture20.jpg", "Picture18.jpg",
								"Picture21.jpg", "Picture9.jpg","Picture8.jpg",
								"Picture19.jpg","Picture13.jpg","Picture14.jpg"};
					try
					{
						for(int i=0; i<imageName.length; i++)
						{
							File pictFile = new File(imageName[i]);
							images[i] = ImageIO.read(pictFile);
						}
					}
					catch(IOException e)
					{
						System.err.println("\n\n can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					try
					{
						File backFile  = new File("BacksideImage.jpg");
						backgroundImage = ImageIO.read(backFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n backside can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					try
					{
						File topFile = new File("StarImage.jpg");
						starImage = ImageIO.read(topFile);
					}
					catch(IOException e)
					{
						System.err.println("\n\n star can't be found "
							+ ".\n\n");
						e.printStackTrace();
					}
					
					for(int i=0; i<fc.length; i++)
					{
						if(i>=0 && i<7)
							fc[i] = new FlipCards2(i,150 + i%numOfCols*horSize,100);
						else if(i>=7 && i<14)
							fc[i] = new FlipCards2(i,150 + i%numOfCols*horSize,233);
						else if(i>=14 && i<21)
							fc[i] = new FlipCards2(i,150 + i%numOfCols*horSize,369);
					}
					
					time.addActionListener(this);
					showAll();
					//time.start();
					
					addMouseListener(this);
				}
				
				public void startGame2()
				{
					score = 3;
					clickCounter = 0;
					firstIndex = 0;
					secondIndex = 0;
					thirdIndex = 0;
					showAll();
					repaint();

					time.start();
					for(int i=0; i<numOfCards; i++)
						openCount[i] = 0;
					timeCount = 0;
					flipTimeCount = 0;
				}
				
				public void paintComponent(Graphics g)
				{
					super.paintComponent(g);
					g.setColor(Color.WHITE);
					g.fillRect(150,100,806,400);
					
					g.setColor(Color.BLACK);
					g.drawLine(266,100,266,500);
					g.drawLine(382,100,382,500);
					g.drawLine(498,100,498,500);
					g.drawLine(614,100,614,500);
					g.drawLine(730,100,730,500);
					g.drawLine(846,100,846,500);
					g.drawLine(150,233,956,233);
					g.drawLine(150,369,956,369);
					
					if(score <= 0)
						cards.show(pyth,"losing result panel");
					
					if(openCount[firstIndex] != 1 && openCount[secondIndex] != 1
						&& openCount[thirdIndex] != 1)
					{
						if(score <= 0)
							g.drawImage(starImage,0,0,0,0,0,0,0,0,this);
						
						else if(score == 1)
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
						else if(score == 2)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
						}
						else if(score == 3)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,633,80,0,0,50,50,this);
						}
						else if(score == 4)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
						}
						else if(score == 5)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
							g.drawImage(starImage,682,30,732,80,0,0,50,50,this);
						}
					}
					else
					{
						if(score <= 0)
							g.drawImage(starImage,0,0,0,0,0,0,0,0,this);
						
						else if(score == 1)
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
						else if(score == 2)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
						}
						else if(score == 3)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,633,80,0,0,50,50,this);
						}
						else if(score == 4)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
						}
						else if(score == 5)
						{
							g.drawImage(starImage,482,30,532,80,0,0,50,50,this);
							g.drawImage(starImage,532,30,582,80,0,0,50,50,this);
							g.drawImage(starImage,582,30,632,80,0,0,50,50,this);
							g.drawImage(starImage,632,30,682,80,0,0,50,50,this);
							g.drawImage(starImage,682,30,732,80,0,0,50,50,this);
						}
					}
					
					
					int count = 0;
					if(score == 5)
						cards.show(p,"winning result panel");
					for(int i=0; i<numOfCards; i++)
					{
						if(openCount[i] == 1)
							count++;
						if(count == 21)
						cards.show(p,"winning result panel");
					}
					
					for(int i=0; i<fc.length; i++)
						fc[i].draw(g);
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					if(evt.getSource() == time)
					{
						hideAll();
						repaint();
						time.stop();
						timeCount = 10;
					}	
					
					
					else if(evt.getSource() == flippingTime)
					{	
						if(openCount[firstIndex] != 1 && openCount[secondIndex] != 1
							&& openCount[thirdIndex] != 1)
						{
							fc[firstIndex].hide();
							fc[secondIndex].hide();
							fc[thirdIndex].hide();
							score --;
						}
						
						if(score <= 0)
							score = 0;
							
						repaint();
						clickCounter = 0;
						firstIndex = 0;
						secondIndex = 0;
						thirdIndex = 0;
						
						flippingTime.stop();
						flipTimeCount = 0;
					}
				}
				
				public void showAll()
				{
					for(int i=0; i<fc.length; i++)
						fc[i].show();
				}
				
				public void hideAll()
				{
					for(int i=0; i<fc.length; i++)
						fc[i].hide();
				}
				
				public void mousePressed(MouseEvent evt)
				{	
					xPos = evt.getX();
					yPos = evt.getY();
							
					if(timeCount == 10 && flipTimeCount == 0)
					{
						for(int i=0; i<fc.length; i++)
						{
							if(fc[i].cardSelected(xPos,yPos))
							{
								if(clickCounter == 0)
								{
									firstIndex = i;
								}
								else if(clickCounter == 1)
								{
									secondIndex = i;
									if(firstIndex == secondIndex)
									{
										secondIndex = 0;
										clickCounter = 0;
									}
								}
								else if(clickCounter == 2)
								{
									thirdIndex = i;
									if(secondIndex == thirdIndex  || firstIndex == thirdIndex)
									{
										thirdIndex = 0;
										clickCounter = 1;
									}
								}
								
								clickCounter++;
								fc[i].show();
														
								if(openCount[firstIndex] == 1)
								{
									clickCounter = 0;
									firstIndex = 0;
									secondIndex = 0;
									thirdIndex = 0;
								}
								
								else if(openCount[secondIndex] == 1)
								{
									clickCounter = 1;
									secondIndex = 0;
									thirdIndex = 0;
								}
								
								else if(openCount[thirdIndex] == 1)
								{
									clickCounter = 2;
									thirdIndex = 0;
								}
								
								if(clickCounter==3)
								{
									if(fc[i].cardCheck(firstIndex,secondIndex,thirdIndex))
									{
										//fc[i].show();
										repaint();
										score ++;
																				
										if(score >= 5)
											score = 5;
										clickCounter = 0;
										firstIndex = 0;
										secondIndex = 0;
										thirdIndex = 0;
									}
									else
									{
										//score --;
										if(score <= 0)
											score = 0;
										repaint();
										flippingTime.start();
										flipTimeCount = 1;
									}
								}
							}
							
						}
						repaint();	
					}
				}
				
				public void mouseReleased(MouseEvent evt) {}
				public void mouseClicked(MouseEvent evt) {}
				public void mouseEntered(MouseEvent evt) {}
				public void mouseExited(MouseEvent evt) {}
				
				class FlipCards2
				{
					private Image image; // according image after getting the image index and position as a parameter 
					private int x; // x position of the image
					private int y; // y position of the image
					private boolean shouldReveal; // boolean that checks whether to draw front side or backside. 
					
					public FlipCards2(int imageIndex, int xVal, int yVal)
					{
						image = images[imageIndex];
						x = xVal;
						y = yVal;
						shouldReveal = false;
					}
					
					public void draw(Graphics g)
					{
						if(shouldReveal)
							g.drawImage(image,x+10,y+10,horSize-20,verSize-20,null);
						else 
							g.drawImage(backgroundImage,x,y,horSize,verSize,null);
					}
					
					public boolean cardSelected(int xClicked, int yClicked)
					{
						boolean condition = false;
						if(xClicked>x && xClicked < x+horSize && yClicked>y && yClicked < y+verSize)
							condition = true;
						return condition;
					}
					
					public void show()
					{
						shouldReveal = true;
					}
					
					public void hide()
					{
						shouldReveal = false;
					}
					
					public boolean cardCheck(int index1, int index2, int index3)
					{
						int max = index1;
						int sum = 0;
						
						
						if(imageValue[max]<imageValue[index2])
							max = index2;
						if(imageValue[max]<imageValue[index3])
							max = index3;
										
						if(index1!= max)
							sum += imageValue[index1];
						if(index2 != max)
							sum += imageValue[index2];
						if(index3 != max)
							sum += imageValue[index3];
										
						if(sum == imageValue[max])
						{
							openCount[index1] = 1;
							openCount[index2] = 1;
							openCount[index3] = 1;
						}
						return sum == imageValue[max];
					}
					
				}
			}
		}
		
		class PythWinResultPanel extends JPanel implements ActionListener
		{
			private Pythagorean pyth; // instance for Pythagorean class
			private JLabel win; // label that shows You win!
			private Font winFont; // font for win label
			private JLabel yourName; // label that says Your name
			private JLabel comment; // label that says comment
			private JTextField name; // text field for user to enter their name
			private JTextArea comments; // text Area for user to enter some comments
			private JButton submitButton; // button for saving the user inputs to the text file
											// and checks for whether they entered everything
			
			private JButton pastUserRecord; // button that go to a past user record panel
			private JButton mainButton; // button that goes to main button
			private JLabel reminder; // First line that reminds user to fill both name and comment
			private JLabel reminder2; // second line that reminds to press submit button
			
			private String userName; // string for getText() from name t.f
			private String userComment; // string for getText() from comments t.a
			
			private PrintWriter pw; // PrintWriter that writes to different file
			private File storeFile; // file that printwriter will write to
			private String fileName; // file name of the storeFile. 
			
			private String imageName; // name of the image
			private Image congrats; // congratulations image
			private Font reminderFont; // font for the reminder labels
		
			public PythWinResultPanel(Pythagorean pythIn)
			{	
				reminder = new JLabel("Please fill out both name and comment.");
				reminder2 = new JLabel("Then, press submit button below to save it.");
				
									
				imageName = new String("PythWinningImage.jpg");
				congrats = null;
				
				pyth = pythIn;
				
				setLayout(null);
				win = new JLabel("You win!");
				winFont = new Font("Serif",Font.BOLD,100);
				win.setFont(winFont);
				win.setBounds(200,50,600,100);
				add(win);
				
				yourName = new JLabel("Your name: ");
				comment = new JLabel("Comment: ");
				name = new JTextField("",10);
				comments = new JTextArea("",20,5);
				
				yourName.setBounds(100,250,100,25);
				name.setBounds(300,250,200,30);
				comments.setBounds(100,400,400,100);
				comment.setBounds(100,350,100,25);
				
				reminderFont = new Font("Serif",Font.BOLD + Font.ITALIC,20);
				reminder.setBounds(100,500,500,50);
				reminder2.setBounds(100,520,500,50);
				reminder.setFont(reminderFont);
				reminder2.setFont(reminderFont);
				add(reminder);
				add(reminder2);
				
				add(yourName);
				add(comment);
				add(name);
				add(comments);
				
				name.addActionListener(this);
				
				submitButton = new JButton("Submit");
				pastUserRecord = new JButton("Past user record");
				mainButton = new JButton("Main page");
				
				submitButton.setBounds(100,600,200,50);
				pastUserRecord.setBounds(350,600,200,50);
				mainButton.setBounds(600,600,200,50);
				
				add(submitButton);
				add(pastUserRecord);
				add(mainButton);
				submitButton.addActionListener(this);
				pastUserRecord.addActionListener(this);
				mainButton.addActionListener(this);
				
				createFile();
				getImage();
			}
			
			public void getImage()
			{
				File picFile = new File(imageName);
				try
				{
					congrats = ImageIO.read(picFile);
				}
				catch(IOException e)
				{
					System.err.println("\n\n" + imageName
						+ " cannot be found");
					System.exit(1);
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);

				g.drawImage(congrats,700,100,400,400,this);
			}
			
			public void createFile()
			{
				fileName = new String("PythUserRecord.txt");
				storeFile = new File(fileName);
				
				try
				{
					pw = new PrintWriter(new FileWriter(storeFile,true));
				}
				catch(IOException e)
				{
					System.err.println("Cannot create " + fileName + "file to be written to");
					System.exit(1);
				}
			}
			
			public void actionPerformed(ActionEvent evt)
			{
				String whatPressed = evt.getActionCommand();
				boolean nameTyped = false;
				boolean commentTyped = false;
				
				userName = name.getText();
				userComment = comments.getText();
				
				if(whatPressed.equals("Submit"))
				{
					if(userName.trim().equals(""))
						nameTyped = false;	
					else
						nameTyped = true;
					
					if(userComment.trim().equals(""))
						commentTyped = false;
					else
						commentTyped = true;
			
					if(nameTyped == true && commentTyped == true)
					{
						pw.println("Name: " + userName);
						pw.println("Comments: " + userComment);
						pw.close();
					}
					
					else
					{
						if(nameTyped == false)
							name.setForeground(Color.RED);
						
						if(commentTyped == false)
							comments.setForeground(Color.RED);
					}
				}
				else if(whatPressed.equals("Past user record"))
				{
					cards.show(pyth,"Past user record");
				}
				
				else if(whatPressed.equals("Main page"))
				{
					name.setText("");
					comments.setText("");
					
					pgp.startGame();
					cards.show(pyth,"instruction panel");
					cl.show(op,"MainPanel");
				}
			}
			
		}
		
		class PythResultUserRecords extends JPanel
		{
			private PythUserRecordsButtons purb; //button class object
			private JScrollPane scroll; // scroll bar for text area
			
			public PythResultUserRecords(Pythagorean pythIn)
			{
				setLayout(new BorderLayout());
				
				purb = new PythUserRecordsButtons();
				add(purb, BorderLayout.NORTH);
				add(userRecord, BorderLayout.CENTER);
				
				scroll = new JScrollPane(userRecord);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				add(scroll);
				scanFile();
			}
			
			class PythUserRecordsButtons extends JPanel implements ActionListener
			{
				private JButton userRMainPage; //button go to main page
				private JButton userRBack;  // button goes back to last page 
				private JLabel userRLabel; // label that tell user s/he's in user record page
				
				public PythUserRecordsButtons()
				{
					setBackground(Color.YELLOW);
					userRMainPage = new JButton("Main Page");
					userRBack = new JButton("Go Back");
					
					userRLabel = new JLabel("\t\tUser Records");
					userRLabel.setFont(new Font("Serif",Font.BOLD,20));
					
					add(userRBack);
					add(userRMainPage);
					add(userRLabel);
					
					userRBack.addActionListener(this);
					userRMainPage.addActionListener(this);
				}
				
				public void actionPerformed(ActionEvent evt)
				{
					String recordsClicked = evt.getActionCommand();
					
					if (recordsClicked.equals("Go Back"))
					{
						cards.show(p,"winning result panel");
					}
					else if (recordsClicked.equals("Main Page"))
					{
						cl.show(op,"MainPanel");
					}
				}
			}
			
			public void scanFile()
			{
				String fileName = "PythUserRecord.txt";
				File inFile = new File(fileName);
				try
				{
					input = new Scanner(inFile);
				}
				catch(FileNotFoundException e)
				{
					System.err.println("Cannot find file " + fileName);
					System.exit(3);
				}
				while(input.hasNext())
				{
					userString = input.nextLine();
					userRecord.append(userString + "\n");
				}
			}	
		}
		
		
		class PythLosePanel extends JPanel implements ActionListener
		{
			private Pythagorean pyth; // instance of Pythagorean
			private JLabel gameOver; // label that shows Game Over!
			private JButton restart; // button that restarts the game if pressed
			private JButton mainPage; // button that goes to main page if pressed
			private Font gameOverFont; // font for game over label
			private JLabel clickSee; // description on what to do to see the correct matches for the game
			private JButton click; // button that shows the correct matches if pressed
			private Font font; // font for clickSee
			private int num; // if user press the button, it becomes 1 and shows all the correct matches. 
				
			
			public PythLosePanel(Pythagorean pytaLoseIn)
			{
				pyth = pytaLoseIn;
				
				num = 0;
				gameOver = new JLabel("Game Over!");
				restart = new JButton("Restart");
				mainPage = new JButton("Main page");
				gameOverFont = new Font("serif",Font.BOLD,100);
				font = new Font("Serif",Font.BOLD, 15);
				clickSee = new JLabel("Click to see the correct matches");
				click = new JButton("Click here");
				
				gameOver.setFont(gameOverFont);
				
				setLayout(null);
				gameOver.setBounds(200,50,600,100);
				restart.setBounds(300,250,200,30);
				mainPage.setBounds(600,250,200,30);
				clickSee.setBounds(150,350,250,30);
				click.setBounds(150,400,100,100);
				
				add(gameOver);
				add(restart);
				add(mainPage);
				add(clickSee);
				add(click);
				
				clickSee.setFont(font);
				click.addActionListener(this);
				restart.addActionListener(this);
				mainPage.addActionListener(this);
			}
			
			public void actionPerformed(ActionEvent evt)
			{
				String thisPressed = evt.getActionCommand();
				if(thisPressed.equals("Restart"))
				{
					pgp.startGame();
					cards.show(pyth,"game panel");
				}
				
				else if(thisPressed.equals("Main page"))
				{
					pgp.startGame();
					//cards.show(pyth,"game panel");
					cards.show(pyth,"instruction panel");
					cl.show(op,"MainPanel");
				}
				
				if(thisPressed.equals("Click here"))
				{
					num = 1;
					repaint();
				}
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				
				if(num == 1)
				{
					g.drawString("1: 3,4,5",300,450);
					g.drawString("2: 5,12,13",500,450);
					g.drawString("3: root3, root5, root8",300,500);
					g.drawString("4: root45,2,7",500,500);
					g.drawString("5: 6,8,10",300,550);
					g.drawString("6: 10,24,26",500,550);
					g.setColor(Color.RED);
					g.drawString("For hard mode: 15,20,25",300,600);
				}
			}
		}
		
	}
}