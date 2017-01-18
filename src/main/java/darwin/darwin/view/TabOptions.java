/**
 * 
 */
package darwin.darwin.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.StringUtils;

public class TabOptions extends JPanel {

	private static final long serialVersionUID = 6519280040785280742L;

	public static final Color defaultButtonColor = new Color(220, 220, 220);
	
	public static int MIN_CREATURE = 0;
	public static int MAX_CREATURE = 100;
	public static int MIN_CRITICAL_CREATURE = 0;
	public static int MAX_CRITICAL_CREATURE = 1000;
	public static int DEFAULT_CREATURE = 50;
	public static int DEFAULT_PREFERRED_CREATURE = 400;
	public static int DEFAULT_CRITICAL_CREATURE = 550;
	public static int MIN_DEPTH = 0;
	public static int MAX_DEPTH = 100;
	public static int DEEP_WATER = 0;
	public static int OCEAN = 15;
	public static int SHALLOW_WATER = 30;
	public static int SAND = 43;
	public static int WOOD = 52;
	public static int MOUNTAIN = 75;
	public static int SNOW = 88;
	
	private JPanel custPanel = new JPanel();

	private List<JSlider> depthSliders;
	private JButton changeMap = new JButton("Change Map");
	private JButton start = new JButton("Start");
	private JButton reset = new JButton("Reset");
	JButton timeSlow2 = new JButton("<<");
	JButton timeSlow1 = new JButton("<");
	JButton timeRegular = new JButton("*");
	JButton timeFast1 = new JButton(">");
	JButton timeFast2 = new JButton(">>");
	private JLabel nbCreaturesLabel = new JLabel("Initial number of creatures");
	private JTextField nbCreaturesTextField = new JTextField(4);
	private JSlider nbCreatures = new JSlider(MIN_CREATURE, MAX_CREATURE, DEFAULT_CREATURE);
	private JLabel nbCreaturesPreferredLabel = new JLabel("Preferred number of creatures");
	private JSlider nbCreaturesPreferred = new JSlider(MIN_CRITICAL_CREATURE, MAX_CRITICAL_CREATURE,
			DEFAULT_PREFERRED_CREATURE);
	private JTextField nbCreaturesPreferredTextField = new JTextField(4);
	private JLabel nbCreaturesCriticalLabel = new JLabel("Critical number of creatures");
	private JSlider nbCreaturesCritical = new JSlider(MIN_CRITICAL_CREATURE, MAX_CRITICAL_CREATURE,
			DEFAULT_CRITICAL_CREATURE);
	private JLabel deepOceanLabel = new JLabel("Deep ocean");
	private JSlider deepOceanSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, DEEP_WATER);
	private JLabel oceanLabel = new JLabel("Ocean");
	private JSlider oceanSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, OCEAN);
	private JLabel waterLabel = new JLabel("Coast");
	private JSlider waterSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SHALLOW_WATER);
	private JLabel sandLabel = new JLabel("Sand");
	private JSlider sandSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SAND);
	private JLabel woodLabel = new JLabel("Woods");
	private JSlider woodSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, WOOD);
	private JLabel mountainsLabel = new JLabel("Mountains");
	private JSlider mountainsSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, MOUNTAIN);
	private JLabel snowLabel = new JLabel("Snow");
	private JSlider snowSlider = new JSlider(MIN_DEPTH, MAX_DEPTH, SNOW);
	private JTextField nbCreaturesCriticalTextField = new JTextField(4);
	private JTextField textSeed = new JTextField(9);
	private JLabel labelCSeed = new JLabel("Current seed:");
	private JLabel cSeed = new JLabel("");
	private JLabel labelSeed = new JLabel("Input seed for generation:");
	private JScrollPane optionsScrollable;
	
	public TabOptions(){
	
		int size = 765;
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); 
        int height = gd.getDisplayMode().getHeight();
        if(height < 900){
        	size -= 127;
        }
        
		
		this.setPreferredSize(new Dimension(280, size));
		
		custPanel.setPreferredSize(new Dimension(280, size-100));
		// Change map button
		changeMap.setBackground(defaultButtonColor);
		changeMap.setPreferredSize(new Dimension(130, 30));

		// Start button
		start.setBackground(defaultButtonColor);
		start.setPreferredSize(new Dimension(130, 30));
		
		timeSlow2.setPreferredSize(new Dimension(48, 30));
		timeSlow1.setPreferredSize(new Dimension(42, 30));
		timeRegular.setPreferredSize(new Dimension(42, 30));
		timeFast1.setPreferredSize(new Dimension(42, 30));
		timeFast2.setPreferredSize(new Dimension(48, 30));
		reset.setPreferredSize(new Dimension(150, 30));
		
		Dimension optionSliderDim = new Dimension(235, 42);
		Dimension optionLabelDim = new Dimension(235, 25);
		
		initDepthSliders();

		/** Slider **/
		nbCreatures.setMinorTickSpacing(5);
		nbCreatures.setMajorTickSpacing(20);
		nbCreatures.setPaintTicks(true);
		nbCreatures.setPaintLabels(true);
		nbCreatures.setPreferredSize(optionSliderDim);
		nbCreaturesTextField.setText(Integer.toString(DEFAULT_CREATURE));
		nbCreaturesTextField.setPreferredSize(optionLabelDim);

		/** Slider **/
		nbCreaturesCritical.setMinorTickSpacing(25);
		nbCreaturesCritical.setMajorTickSpacing(200);
		nbCreaturesCritical.setPaintTicks(true);
		nbCreaturesCritical.setPaintLabels(true);
		nbCreaturesCritical.setPreferredSize(optionSliderDim);
		nbCreaturesCriticalTextField.setPreferredSize(optionLabelDim);
		nbCreaturesCriticalTextField.setText(Integer.toString(DEFAULT_CRITICAL_CREATURE));

		/** Slider **/
		nbCreaturesPreferred.setMinorTickSpacing(25);
		nbCreaturesPreferred.setMajorTickSpacing(200);
		nbCreaturesPreferred.setPaintTicks(true);
		nbCreaturesPreferred.setPaintLabels(true);
		nbCreaturesPreferred.setPreferredSize(optionSliderDim);
		nbCreaturesPreferredTextField.setPreferredSize(optionLabelDim);
		nbCreaturesPreferredTextField.setText(Integer.toString(DEFAULT_PREFERRED_CREATURE));

		addSliderListeners();

		// Add sliders to Option tab
		this.add(nbCreaturesLabel);
		this.add(nbCreaturesTextField);
		this.add(nbCreatures);

		this.add(nbCreaturesPreferredLabel);
		this.add(nbCreaturesPreferredTextField);
		this.add(nbCreaturesPreferred);

		this.add(nbCreaturesCriticalLabel);
		this.add(nbCreaturesCriticalTextField);
		this.add(nbCreaturesCritical);
		
		Dimension d = new Dimension(100, 15);
		cSeed.setMinimumSize(d);
		cSeed.setPreferredSize(d);
		cSeed.setMaximumSize(d);
		Dimension d1 = new Dimension(125, 15);
		labelCSeed.setMinimumSize(d1);
		labelCSeed.setPreferredSize(d1);
		labelCSeed.setMaximumSize(d1);
		
		this.setLayout(new FlowLayout());
		this.add(changeMap);
		this.add(start);
		enableDepthTailoring();
		this.add(labelCSeed);
		this.add(cSeed);
		this.add(labelSeed);
		this.add(textSeed);
		
		custPanel.add(deepOceanLabel);
		custPanel.add(deepOceanSlider);

		custPanel.add(oceanLabel);
		custPanel.add(oceanSlider);

		custPanel.add(waterLabel);
		custPanel.add(waterSlider);

		custPanel.add(sandLabel);
		custPanel.add(sandSlider);

		custPanel.add(woodLabel);
		custPanel.add(woodSlider);

		custPanel.add(mountainsLabel);
		custPanel.add(mountainsSlider);

		custPanel.add(snowLabel);
		custPanel.add(snowSlider);
		
		custPanel.setBorder(new TitledBorder("Customize heights"));
		
		addScrollingPanel();
		
	}
	
	public void addScrollingPanel(){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();    
		int height = gd.getDisplayMode().getHeight();
		if (height<900){
			optionsScrollable = new JScrollPane(custPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			optionsScrollable.setPreferredSize(new Dimension(280, 438));
		
			this.add(optionsScrollable);
		} else {
			this.add(custPanel);
		}
	}
	
	/**
	 * disableLabels
	 */
	public void disableLabels() {
		//this.disable(nbCreaturesLabel);
		this.remove(labelSeed);
		this.remove(textSeed);
		this.revalidate();
		this.repaint();
	}

	/**
	 * disableSliders
	 */
	public void disableSliders() {
		
		this.remove(custPanel);
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();    
		
		int height = gd.getDisplayMode().getHeight();
		if (height<900){
			this.remove(optionsScrollable);
		}
	}

	/**
	 * enableDepthTailoring
	 */
	public void enableDepthTailoring() {
		this.add(labelSeed);
		this.add(textSeed);
		this.add(custPanel);

	}

	/**
	 * removeTimeControl 
	 */
	public void removeTimeControl() {
		this.remove(timeSlow2);
		this.remove(timeSlow1);
		this.remove(timeRegular);
		this.remove(timeFast1);
		this.remove(timeFast2);
		this.remove(reset);
	}

	/**
	 * addTimeControl
	 */
	public void addTimeControl() {
		this.add(timeSlow2);
		this.add(timeSlow1);
		this.add(timeRegular);
		this.add(timeFast1);
		this.add(timeFast2);
		this.add(reset);
	}
	
	public void updateSeed(int i) {
		this.cSeed.setText(Integer.toString(i));
	}
	
	public void updateSeed(String str) {
		this.cSeed.setText(str);
	}

	public int getSeed() {
		if (StringUtils.isNotBlank(textSeed.getText())) {
			try {
				return Integer.parseInt(this.textSeed.getText());
			} catch (NumberFormatException e) {
				textSeed.setText("");
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * getInitialNbSlider
	 * 
	 * @return
	 */
	public JSlider getInitialNbSlider() {
		return this.nbCreatures;
	}

	/**
	 * getInitialNbLabel
	 * 
	 * @return
	 */
	public JLabel getInitialNbLabel() {
		return this.nbCreaturesLabel;
	}

	public JButton getSlow2Button() {
		return timeSlow2;
	}

	public JButton getSlow1Button() {
		return timeSlow1;
	}

	public JButton getRegularButton() {
		return timeRegular;
	}

	public JButton getFast1Button() {
		return timeFast1;
	}

	public JButton getFast2Button() {
		return timeFast2;
	}

	public void enableAcceleration() {
		this.timeFast1.setEnabled(true);
		this.timeFast2.setEnabled(true);
	}

	public void disableDecceleration() {
		this.timeSlow1.setEnabled(false);
		this.timeSlow2.setEnabled(false);
	}

	public void enableDecceleration() {
		this.timeSlow1.setEnabled(true);
		this.timeSlow2.setEnabled(true);
	}

	public void disableAcceleration() {
		this.timeFast1.setEnabled(false);
		this.timeFast2.setEnabled(false);
	}

	public JButton getResetButton() {
		return this.reset;
	}
	
	/**
	 * getSoftCapSlider
	 * @return
	 */
	public JSlider getSoftCapSlider() {
		return this.nbCreaturesPreferred;
	}

	/**
	 * getHardCapSlider
	 * @return
	 */
	public JSlider getHardCapSlider() {
		return this.nbCreaturesCritical;
	}

	/**
	 * getDepthSliders
	 * @return
	 */
	public List<JSlider> getDepthSliders() {
		return this.depthSliders;
	}

	/**
	 * get the start button
	 * 
	 * @return
	 */
	public JButton getStartButton() {
		return this.start;
	}

	/**
	 * get the change map button
	 * 
	 * @return
	 */
	public JButton getChangeMapButton() {
		return this.changeMap;
	}
	
	private void initDepthSliders() {
		int width = 70;
		int height = 15;
		Dimension d = new Dimension(width, height);
		Dimension sliderDim = new Dimension(150, 42);
		this.depthSliders = new ArrayList<JSlider>();
		deepOceanSlider.setMinorTickSpacing(5);
		deepOceanSlider.setMajorTickSpacing(20);
		deepOceanSlider.setPaintTicks(true);
		deepOceanSlider.setPaintLabels(true);
		deepOceanSlider.setPreferredSize(sliderDim);
		deepOceanLabel.setMinimumSize(d);
		deepOceanLabel.setPreferredSize(d);
		deepOceanLabel.setMaximumSize(d);

		oceanSlider.setMinorTickSpacing(5);
		oceanSlider.setMajorTickSpacing(20);
		oceanSlider.setPaintTicks(true);
		oceanSlider.setPaintLabels(true);
		oceanSlider.setPreferredSize(sliderDim);
		oceanLabel.setMinimumSize(d);
		oceanLabel.setPreferredSize(d);
		oceanLabel.setMaximumSize(d);

		waterSlider.setMinorTickSpacing(5);
		waterSlider.setMajorTickSpacing(20);
		waterSlider.setPaintTicks(true);
		waterSlider.setPaintLabels(true);
		waterSlider.setPreferredSize(sliderDim);
		waterLabel.setMinimumSize(d);
		waterLabel.setPreferredSize(d);
		waterLabel.setMaximumSize(d);

		sandSlider.setMinorTickSpacing(5);
		sandSlider.setMajorTickSpacing(20);
		sandSlider.setPaintTicks(true);
		sandSlider.setPaintLabels(true);
		sandSlider.setPreferredSize(sliderDim);
		sandLabel.setMinimumSize(d);
		sandLabel.setPreferredSize(d);
		sandLabel.setMaximumSize(d);

		woodSlider.setMinorTickSpacing(5);
		woodSlider.setMajorTickSpacing(20);
		woodSlider.setPaintTicks(true);
		woodSlider.setPaintLabels(true);
		woodSlider.setPreferredSize(sliderDim);
		woodLabel.setMinimumSize(d);
		woodLabel.setPreferredSize(d);
		woodLabel.setMaximumSize(d);

		mountainsSlider.setMinorTickSpacing(5);
		mountainsSlider.setMajorTickSpacing(20);
		mountainsSlider.setPaintTicks(true);
		mountainsSlider.setPaintLabels(true);
		mountainsSlider.setPreferredSize(sliderDim);
		mountainsLabel.setMinimumSize(d);
		mountainsLabel.setPreferredSize(d);
		mountainsLabel.setMaximumSize(d);

		snowSlider.setMinorTickSpacing(5);
		snowSlider.setMajorTickSpacing(20);
		snowSlider.setPaintTicks(true);
		snowSlider.setPaintLabels(true);
		snowSlider.setPreferredSize(sliderDim);
		snowLabel.setMinimumSize(d);
		snowLabel.setPreferredSize(d);
		snowLabel.setMaximumSize(d);

		depthSliders.add(snowSlider);
		depthSliders.add(mountainsSlider);
		depthSliders.add(woodSlider);
		depthSliders.add(sandSlider);
		depthSliders.add(waterSlider);
		depthSliders.add(oceanSlider);
		depthSliders.add(deepOceanSlider);

	}

	public void addSliderListeners() {
		// nbCreatures
		nbCreatures.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				nbCreaturesTextField.setText(String.valueOf(nbCreatures.getValue()));
			}
		});
		nbCreaturesTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = nbCreaturesTextField.getText();
				nbCreatures.setValue(0);
				if (!typed.matches("\\d+") || typed.length() > 10) {
					return;
				}
				int value = Integer.parseInt(typed);
				nbCreatures.setValue(value);
			}
		});

		// Preferred
		nbCreaturesPreferred.addChangeListener(e -> {
			nbCreaturesPreferredTextField.setText(String.valueOf(nbCreaturesPreferred.getValue()));
		});
		nbCreaturesPreferredTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = nbCreaturesPreferredTextField.getText();
				nbCreaturesPreferred.setValue(0);
				if (!typed.matches("\\d+") || typed.length() > 10) {
					return;
				}
				int value = Integer.parseInt(typed);
				nbCreaturesPreferred.setValue(value);
			}
		});

		// Critical
		nbCreaturesCritical.addChangeListener(e -> {
			nbCreaturesCriticalTextField.setText(String.valueOf(nbCreaturesCritical.getValue()));
		});
		nbCreaturesCriticalTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				String typed = nbCreaturesCriticalTextField.getText();
				nbCreaturesCritical.setValue(0);
				if (!typed.matches("\\d+") || typed.length() > 10) {
					return;
				}
				int value = Integer.parseInt(typed);
				nbCreaturesCritical.setValue(value);
			}
		});
		// Deep Ocean
		deepOceanSlider.addChangeListener(e -> {
			if (deepOceanSlider.getValue() > oceanSlider.getValue()) {
				oceanSlider.setValue(deepOceanSlider.getValue());
			}
		});
		// Ocean
		oceanSlider.addChangeListener(e -> {
			if (oceanSlider.getValue() > waterSlider.getValue()) {
				waterSlider.setValue(oceanSlider.getValue());
			}
			if (oceanSlider.getValue() < deepOceanSlider.getValue()) {
				deepOceanSlider.setValue(oceanSlider.getValue());
			}
		});
		// Shallow water
		waterSlider.addChangeListener(e -> {
			if (waterSlider.getValue() > sandSlider.getValue()) {
				sandSlider.setValue(waterSlider.getValue());
			}
			if (waterSlider.getValue() < oceanSlider.getValue()) {
				oceanSlider.setValue(waterSlider.getValue());
			}
		});
		// Sand
		sandSlider.addChangeListener(e -> {
			if (sandSlider.getValue() > woodSlider.getValue()) {
				woodSlider.setValue(sandSlider.getValue());
			}
			if (sandSlider.getValue() < waterSlider.getValue()) {
				waterSlider.setValue(sandSlider.getValue());
			}
		});
		// Woods
		woodSlider.addChangeListener(e -> {
			if (woodSlider.getValue() > mountainsSlider.getValue()) {
				mountainsSlider.setValue(woodSlider.getValue());
			}
			if (woodSlider.getValue() < sandSlider.getValue()) {
				sandSlider.setValue(woodSlider.getValue());
			}
		});
		// Mountains
		mountainsSlider.addChangeListener(e -> {
			if (mountainsSlider.getValue() > snowSlider.getValue()) {
				snowSlider.setValue(mountainsSlider.getValue());
			}
			if (mountainsSlider.getValue() < woodSlider.getValue()) {
				woodSlider.setValue(mountainsSlider.getValue());
			}
		});
		// Snow
		snowSlider.addChangeListener(e -> {
			if (snowSlider.getValue() < mountainsSlider.getValue()) {
				mountainsSlider.setValue(snowSlider.getValue());
			}
		});
	}

	public void addScroll() {
		optionsScrollable = new JScrollPane(custPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
	}
	
	
}
