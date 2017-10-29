package mainPack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Hider extends javax.swing.JFrame{

	//initializing JLabel
	private JLabel appName;
	
	//initializing JTextField
	private JTextField txtBrowse;
	
	//initializing JPanels
	private JPanel basePanel, btnPanel;
	
	//initializing JList
	private JList<String> hiddenFileList;
	
	//List model
	private DefaultListModel<String> defaultListModel = new DefaultListModel<String>();
	
	//initializing JButtons
	private JButton hide, unhide, showHidden, browse;
	
	//JFileChooser
	private JFileChooser fileOpener;
	
	//JScrollPane
	private JScrollPane scrollPane;
	
	//File
	private File file;
	
	//Constructor
	public Hider(){
		makeGui();
	}
	
	public void makeGui(){
		this.setName("hiderApp");
		//Set title for the frame
		this.setTitle("Hider");
		//Set size of the frame
		this.setSize(1070,550);
		//Set layout
		this.setLayout(new BorderLayout());
		//Set the closing operation
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		//JLable
		appName = new JLabel("HIDER",JLabel.CENTER);
		appName.setBackground(Color.BLACK);
		appName.setForeground(Color.black);
		appName.setFont(new java.awt.Font("Serif", 1, 48));
		
		//JTextField
		txtBrowse = new JTextField();
		txtBrowse.setBackground(new java.awt.Color(206, 230, 195));

		//BasePanel
		basePanel = new JPanel();
		basePanel.setLayout(new GridLayout(3,1,1,1));
		basePanel.setBackground(Color.GRAY);
		basePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		
		//JFileChooser
		fileOpener = new JFileChooser();
		fileOpener.setVisible(true);
		fileOpener.setMultiSelectionEnabled(true);
		
		//JList
		hiddenFileList = new JList<String>(defaultListModel);
		hiddenFileList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hidden Files", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Serif", 1, 11)));
		hiddenFileList.setModel(defaultListModel);
		hiddenFileList.setOpaque(false);
		
		//JScrollPane
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(hiddenFileList);
		
		//JButtons
		browse = new JButton("Browse");
		browse.setBackground(new java.awt.Color(229, 55, 134));
		browse.setSelected(true);
		
		hide = new JButton("Hide");
		hide.setBackground(new java.awt.Color(167, 135, 250));
		
		unhide = new JButton("Unhide");
		showHidden = new JButton("Show");
		
		basePanel.add(appName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 170, 50));
		basePanel.add(txtBrowse, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 390, 54));
		basePanel.add(browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 110, 50));
		basePanel.add(hide, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 100, 45));
		basePanel.add(unhide, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 100, 45));
		basePanel.add(showHidden, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 100, 45));
		basePanel.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 380, 330));
		
		//Set Controls to frame
		this.getContentPane().add(basePanel);
		this.setVisible(true);
		
		//Action
		browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == browse){
					fileOpener.showOpenDialog(null);
		            if(JFileChooser.APPROVE_OPTION == 0){
		                file = fileOpener.getSelectedFile();
                        String selectedFile = file.getName();
                        String path = file.getParent();
                        txtBrowse.setText(path+"\\"+'"'+selectedFile+'"');
		            }

		            }else if(JFileChooser.CANCEL_OPTION == 1){
		            	fileOpener.setVisible(false);
		                txtBrowse.setText("");		                
		            }
		        }
		});
		
		hide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
		            Process p = Runtime.getRuntime().exec("cmd /c attrib +h +s "+txtBrowse.getText());
		            p.waitFor();
		            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		            String line;
		            while((line = br.readLine()) != null){
		                System.out.println(line);
		            }
		        } catch (IOException ex) {
		            Logger.getLogger(Hider.class.getName()).log(Level.SEVERE, null, ex);
		        } catch (InterruptedException ex) {
		            Logger.getLogger(Hider.class.getName()).log(Level.SEVERE, null, ex);
		        }
		        JOptionPane.showMessageDialog(null, "Document hided successfully");
			}
		});
		
		unhide.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedValue = hiddenFileList.getSelectedValue();
	            try {
	            Process p = Runtime.getRuntime().exec("cmd /c attrib -h -s "+selectedValue);
	            p.waitFor();
	            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while((line = br.readLine()) != null){
	                System.out.println(line);
	            }
	        } catch (IOException ex) {
	            Logger.getLogger(Hider.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (InterruptedException ex) {
	            Logger.getLogger(Hider.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     
	        JOptionPane.showMessageDialog(null, "Document unhided successfully");
	        defaultListModel.removeElementAt(hiddenFileList.getSelectedIndex());
			}
		});
		
		showHidden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileOpener.showOpenDialog(null);
				File files = fileOpener.getSelectedFile();
		        if(files.isDirectory()){
		            final File[] hiddenFiles = new File(files.getPath()).listFiles(File::isHidden);
		            for(File f:hiddenFiles){
		              String path = f.getParent();
		              String filename = f.getName();
		              String absolutepath = path+"\\"+'"'+filename+'"';
		              defaultListModel.addElement(absolutepath);
		              
		        }
		        }else{
		            final File[] hiddenFiles = new File(files.getParent()).listFiles(File::isHidden);
		        for(File f:hiddenFiles){
		              String path = f.getParent();
		              String filename = f.getName();
		              String absolutepath = path+"\\"+'"'+filename+'"';
		              defaultListModel.addElement(absolutepath);
		        }
		        }
			}
		});
		
	}
	        

	// Main Method
	public static void main(String[] args) {
		
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hider.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		try{
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					new Hider().setVisible(true);
				}
			});
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

}
