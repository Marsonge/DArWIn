package darwin.darwin.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.utils.Export;
import darwin.darwin.utils.Import;

/**
 * TabImportExport
 * 		Option tab for import/export
 */
public class TabImportExport extends JPanel {
	
	private static final long serialVersionUID = 426751354745529873L;
	
	WorldControler wc;
	private SidePanel self;
	MainView parent;
	
	private JPanel custPanelImport = new JPanel();
	private JPanel custPanelExport = new JPanel();
	JButton exportPngButton = new JButton("Export map to PNG");
	JButton importPngButton = new JButton("Import map from PNG");
	JButton exportJSONButton = new JButton("Export creatures to JSON");
	JButton importJSONButton = new JButton("Import creatures from JSON");
	JButton exportAllButton = new JButton("Export All (zip)");
	JButton importAllButton = new JButton("Import All (zip)");
	JButton editMapButton = new JButton("Edit map");
	
	public TabImportExport(MainView parent, SidePanel self){
		this.self = self;
		this.parent = parent;
		wc = parent.getWorldControler();
		
		this.setPreferredSize(new Dimension(280, 660));
		
		this.setLayout(new FlowLayout());
		exportJSONButton.setPreferredSize(new Dimension(200, 30));
		importJSONButton.setPreferredSize(new Dimension(200, 30));
		exportPngButton.setPreferredSize(new Dimension(200, 30));
		importPngButton.setPreferredSize(new Dimension(200, 30));
		exportAllButton.setPreferredSize(new Dimension(200, 30));
		importAllButton.setPreferredSize(new Dimension(200, 30));
		editMapButton.setPreferredSize(new Dimension(200, 30));

		custPanelExport.add(exportJSONButton);
		custPanelExport.add(exportPngButton);
		custPanelExport.add(exportAllButton);
		custPanelExport.setPreferredSize(new Dimension(290, 160));
		custPanelExport.setBorder(new TitledBorder("Export"));
		this.add(custPanelExport);

		
		custPanelImport.add(importJSONButton);
		custPanelImport.add(importPngButton);
		custPanelImport.add(importAllButton);
		custPanelImport.setPreferredSize(new Dimension(290, 160));
		custPanelImport.setBorder(new TitledBorder("Import"));
		this.add(custPanelImport);
		
		this.add(editMapButton);
		addActionListenerExportImport();
	}
	
	/**
	 * addActionListenerExportImport
	 */
	private void addActionListenerExportImport() {
		
		exportJSONButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}

			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Save files", "json");
			fileChooser.setFileFilter(filter);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			fileChooser.setSelectedFile(new File("DArWIn_export_" + sdf.format(cal.getTime()) + ".json"));

			fileChooser.setDialogTitle("Select File");

			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the export function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					Export.exportToJson(fileChooser.getSelectedFile(), wc, new ArrayList(wc.getCreatureMap().keySet()));
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "The export has failed! Error: " + e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "Export successful.");
			}
		});
		
		importJSONButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}
			
			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Save files", "json");
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("Select File");
			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the import function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					Import.importFromJson(new FileInputStream(fileChooser.getSelectedFile()),wc);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "The import has failed! Error: " + e1.getMessage());
					return;
				}
				
				self.getTabOptions().updateSeed("Imported");
			}
			

		});
		
		exportPngButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}
			
			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("Portable Network Graphics (.png)", "png");
			fileChooser.setFileFilter(filter);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			fileChooser.setSelectedFile(new File("DArWIn_export_" + sdf.format(cal.getTime()) + ".png"));

			fileChooser.setDialogTitle("Select File");

			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the export function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					wc.exportToPng(fileChooser.getSelectedFile());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "The export has failed! Error: " + e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "Export successful.");
			}
			
		});
		
		importPngButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}
			
			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("Portable Network Graphics (.png)", "png");
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("Select File");
			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the import function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					wc.importFromPng(new FileInputStream(fileChooser.getSelectedFile()));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "The import has failed! Error: " + e1.getMessage());
					return;
				}
				self.getTabOptions().updateSeed("Imported");
			}
		
		});
		
		// Global export to zip button
		exportAllButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}
			
			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP archive", "zip");
			fileChooser.setFileFilter(filter);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
			fileChooser.setSelectedFile(new File("DArWIn_export_" + sdf.format(cal.getTime()) + ".zip"));

			fileChooser.setDialogTitle("Select File");

			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the export function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					Export.exportToZip(fileChooser.getSelectedFile(), wc);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "The export has failed! Error: " + e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "Export successful.");
			}
			
		});
		
		
		// Import all from zip
		importAllButton.addActionListener(e -> {
			
			if (self.getTabOptions().getStartButton().getText() == "Pause"){
				parent.pauseTimers();
				self.getTabOptions().getStartButton().setText("Start"); 
			}
			
			JFileChooser fileChooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("ZIP archive", "zip");
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("Select File");
			int rVal = fileChooser.showOpenDialog(self);

			// When file is selected, we call the import function
			if (rVal == JFileChooser.APPROVE_OPTION) {
				try {
					wc = parent.getWorldControler();
					Import.importFromZip(fileChooser.getSelectedFile(), wc);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "The import has failed! Error: " + e1.getMessage());
					return;
				}
				self.getTabOptions().updateSeed("Imported");
			
			}

		});
		
		editMapButton.addActionListener(e -> {
			wc = parent.getWorldControler();
			wc.editMap();

		});
	}
}
