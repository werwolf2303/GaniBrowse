package com.browser.vpn.ui;

import javax.swing.*;
import java.awt.*;

public class Dialog extends JDialog
{
static Dialog theDialog;

JPanel pnPanel0;
JLabel taArea0;
public Dialog() 
{
   super();
   this.setTitle("Information");
   pnPanel0 = new JPanel();
   GridBagLayout gbPanel0 = new GridBagLayout();
   GridBagConstraints gbcPanel0 = new GridBagConstraints();
   pnPanel0.setLayout( gbPanel0 );
   taArea0 = new JLabel();
   taArea0.setText("<html><center><h1>Information</h1><br><br><h3>WerwolfVPN</h3><br><br><img src='https://avatars.githubusercontent.com/u/66752411?v=4'><br><br><h2>Entwickler</h2><br><br><h3>Werwolf2303</h3><br><h3>Gianluca.B</h3><br><h3>2021-2026&copy;</h3></center></html>");
   gbcPanel0.gridx = 0;
   gbcPanel0.gridy = 0;
   gbcPanel0.gridwidth = 20;
   gbcPanel0.gridheight = 20;
   gbcPanel0.fill = GridBagConstraints.BOTH;
   gbcPanel0.weightx = 1;
   gbcPanel0.weighty = 1;
   gbcPanel0.anchor = GridBagConstraints.NORTH;
   gbPanel0.setConstraints( taArea0, gbcPanel0 );
   pnPanel0.add( taArea0 );

   setDefaultCloseOperation( DISPOSE_ON_CLOSE );

   setContentPane( pnPanel0 );
   pack();
   setVisible( true );
   Thread t = new Thread();
   t.start();
}
public void run() {
   while(true) {
      System.out.println(this.getSize());
   }
}
} 
