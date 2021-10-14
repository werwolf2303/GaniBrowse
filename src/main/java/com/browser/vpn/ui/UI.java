package com.browser.vpn.ui;

import com.browser.vpn.api.Connect;
import com.browser.vpn.api.Disconnect;
import com.browser.vpn.api.GetServers;
import com.opencsv.exceptions.CsvException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI extends JFrame
{
static UI theUI;

private JPanel pnPanel1;
private JList lsList0;
private JButton btBut3;
private JLabel tfText2;
private JButton btBut5;
private JFrame frame = this;
public void initital()
{
   try 
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   }
   catch ( ClassNotFoundException e )
   {
   }
   catch ( InstantiationException e )
   {
   }
   catch ( IllegalAccessException e )
   {
   }
   catch ( UnsupportedLookAndFeelException e )
   {
   }
   theUI = new UI();
}
public UI() 
{
   super( "WerwolfVPN " + "1.0" + " - " + "ALPHA" );
   JMenuBar menuBar = new JMenuBar();
   JMenu menu = new JMenu("About");
   JMenuItem item = new JMenuItem("Info");
   JMenuItem add = new JMenuItem("Add Connection");
   JMenuItem delete = new JMenuItem("Delete Connection");
   delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         try {
            new DeleteDialog();
         } catch (IOException ioException) {
            ioException.printStackTrace();
         } catch (CsvException csvException) {
            csvException.printStackTrace();
         }
      }
   });
   menuBar.add(menu);
   menu.add(add);
   menu.add(item);
   menu.add(delete);
   add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
         try {
            new Add();
         } catch (IOException ex) {
            ex.printStackTrace();
         } catch (CsvException ex) {
            ex.printStackTrace();
         }
      }
   });
   item.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         new Dialog();
      }
   });
   this.setJMenuBar(menuBar);
   pnPanel1 = new JPanel();
   GridBagLayout gbPanel1 = new GridBagLayout();
   GridBagConstraints gbcPanel1 = new GridBagConstraints();
   tfText2 = new JLabel();
   pnPanel1.setLayout( gbPanel1 );
   tfText2.setText("<html><h3>WerwolfVPN</h3><br><a>Click on a VPN Server in this list then on connect</a></html>");
   GetServers servers = new GetServers();
   String[] conv = new String[]{};
   try {
      conv = servers.getAdded().split("\\[");
   } catch (IOException ioException) {
      ioException.printStackTrace();
   }
   lsList0 = new JList( conv );
   gbcPanel1.gridx = 0;
   gbcPanel1.gridy = 8;
   gbcPanel1.gridwidth = 20;
   gbcPanel1.gridheight = 9;
   gbcPanel1.fill = GridBagConstraints.BOTH;
   gbcPanel1.weightx = 1;
   gbcPanel1.weighty = 1;
   gbcPanel1.anchor = GridBagConstraints.NORTH;
   gbPanel1.setConstraints( lsList0, gbcPanel1 );
   pnPanel1.add( lsList0 );
   btBut3 = new JButton( "Connect"  );
   btBut3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         String[] command = {"rasdial"};
         String out = "";
         try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            final Process proc = builder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
               if(out=="") {
                  out = s;
               }else {
                  out = out + "\n" + s;
               }
            }
         } catch (IOException ea) {
            ea.printStackTrace();
         }
         String[] trenn = out.split("\n");
         try {
            String test = trenn[2];
         }catch (ArrayIndexOutOfBoundsException aiobee) {
            //Not connected
            Connect connections = new Connect();
            String toconnect = (String)lsList0.getSelectedValue();
            connections.connect(toconnect);
            frame.setTitle("WerwolfVPN " + "1.0" + " - " + "ALPHA" + " -        " + "Connected");
         }
      }
   });
   gbcPanel1.gridx = 3;
   gbcPanel1.gridy = 18;
   gbcPanel1.gridwidth = 6;
   gbcPanel1.gridheight = 2;
   gbcPanel1.fill = GridBagConstraints.BOTH;
   gbcPanel1.weightx = 1;
   gbcPanel1.weighty = 0;
   gbcPanel1.anchor = GridBagConstraints.NORTH;
   gbPanel1.setConstraints( btBut3, gbcPanel1 );
   pnPanel1.add( btBut3 );
   tfText2.setBackground( new Color( 240,240,240 ) );
   gbcPanel1.gridx = 0;
   gbcPanel1.gridy = 0;
   gbcPanel1.gridwidth = 20;
   gbcPanel1.gridheight = 8;
   gbcPanel1.fill = GridBagConstraints.BOTH;
   gbcPanel1.weightx = 1;
   gbcPanel1.weighty = 0;
   gbcPanel1.anchor = GridBagConstraints.NORTH;
   gbPanel1.setConstraints( tfText2, gbcPanel1 );
   pnPanel1.add( tfText2 );
   String[] command = {"rasdial"};
   String out = "";
   try {
      ProcessBuilder builder = new ProcessBuilder(command);
      builder.redirectErrorStream(true);
      final Process proc = builder.start();
      BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String s = null;
      while ((s = in.readLine()) != null) {
         if(out=="") {
            out = s;
         }else {
            out = out + "\n" + s;
         }
      }
   } catch (IOException ea) {
      ea.printStackTrace();
   }
   String[] trenn = out.split("\n");
   try {
      String test = trenn[2];
      this.setTitle("WerwolfVPN " + "1.0" + " - " + "ALPHA" + " -        " + "Connected");
   }catch (ArrayIndexOutOfBoundsException aiobee) {
      //Not connected
   }
   btBut5 = new JButton( "Disconnect"  );
   btBut5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         String[] command = {"rasdial"};
         String out = "";
         try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            final Process proc = builder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
               if(out=="") {
                  out = s;
               }else {
                  out = out + "\n" + s;
               }
            }
         } catch (IOException ea) {
            ea.printStackTrace();
         }
         String[] trenn = out.split("\n");
         try {
            String test = trenn[2];
            Disconnect disconnect = new Disconnect();
            disconnect.disconnect(trenn[1]);
            frame.setTitle("WerwolfVPN " + "1.0" + " - " + "ALPHA" + " -        " + "Disconnected");
         }catch (ArrayIndexOutOfBoundsException aiobee) {
            //Not connected
         }
      }
   });
   gbcPanel1.gridx = 11;
   Thread t2 = new Thread(this::addThread);
   t2.start();
   gbcPanel1.gridy = 18;
   gbcPanel1.gridwidth = 6;
   gbcPanel1.gridheight = 2;
   gbcPanel1.fill = GridBagConstraints.BOTH;
   gbcPanel1.weightx = 1;
   gbcPanel1.weighty = 0;
   gbcPanel1.anchor = GridBagConstraints.NORTH;
   gbPanel1.setConstraints( btBut5, gbcPanel1 );
   pnPanel1.add( btBut5 );
   this.setResizable(false);
   this.addWindowListener(new WindowListener() {
      public void windowOpened(WindowEvent e) {

      }

      public void windowClosing(WindowEvent e) {

      }

      public void windowClosed(WindowEvent e) {

      }

      public void windowIconified(WindowEvent e) {

      }

      public void windowDeiconified(WindowEvent e) {

      }

      public void windowActivated(WindowEvent e) {

      }

      public void windowDeactivated(WindowEvent e) {

      }
   });
   this.setMinimumSize(new Dimension(703, 433));
   setContentPane( pnPanel1 );
   pack();
   setVisible( true );
}
public void addThread() {
   while(true) {
      File f = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Local/GaniBrowse/" + "Act.txt");
      if(f.exists()) {
         GetServers servers = new GetServers();
         String[] conv = new String[]{};
         try {
            conv = servers.getAdded().split("\\[");
         } catch (IOException ioException) {
            ioException.printStackTrace();
         }
         lsList0.setListData(conv);
         f.delete();
      }
   }
}
} 
