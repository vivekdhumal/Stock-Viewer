/*
	Title : Stock Viewer Application
	Author : Vivek Vijaykant Dhumal
	Created On : 16-06-2013
*/
import java.awt.*;
import java.awt.image.BufferedImage;;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.*;

public class StockMain implements ActionListener{
	JFrame jf;
	JLabel lbl_symbol, lbl_sym, lbl_lastTrade, lbl_name,
	lbl_lastdate, lbl_lasttime, lbl_change, lbl_open, lbl_dayhigh, lbl_daylow, lbl_volume,lbl_chart;
	JTextField txt_symbol;
	JButton btn_go, btn_about;
	JPanel jp_top, jp_main, jp_image, jp;
	public StockMain() {
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception ex){}
		jf = new JFrame("Stock Viewer Application (historical data) Version 0.1");
		lbl_symbol = new JLabel("Enter Stock Symbol");
		lbl_sym = new JLabel("");
		lbl_lastTrade = new JLabel("");
		lbl_lastdate = new JLabel("");
		lbl_lasttime = new JLabel("");
		lbl_change = new JLabel("");
		lbl_open = new JLabel("");
		lbl_dayhigh = new JLabel("");
		lbl_daylow = new JLabel("");
		lbl_volume = new JLabel("");
		lbl_name = new JLabel("");
		lbl_chart = new JLabel("");
		//lbl_chart.setPreferredSize(new Dimension(200,200));
		txt_symbol = new JTextField(20);
		btn_go = new JButton("Go");
		btn_about = new JButton("About Application");
		btn_go.addActionListener(this);
		btn_about.addActionListener(this);
		jp_top = new JPanel();
		jp_image = new JPanel();
		jp_main = new JPanel(new BorderLayout());
		jp = new JPanel(new GridLayout(0,1));
		jp.add(lbl_sym);
		jp.add(lbl_name);
		jp.add(lbl_lastTrade);
		jp.add(lbl_lastdate);
		jp.add(lbl_lasttime);
		jp.add(lbl_change);
		jp.add(lbl_open);
		jp.add(lbl_dayhigh);
		jp.add(lbl_daylow);
		jp.add(lbl_volume);
		jp_top.setBorder(BorderFactory.createTitledBorder("Search"));
		jp_main.setBorder(BorderFactory.createTitledBorder("Stock Information"));
		jp_image.setBorder(BorderFactory.createTitledBorder("Stock Chart"));
		jp_top.add(lbl_symbol);
		jp_top.add(txt_symbol);
		jp_top.add(btn_go);
		jp_top.add(btn_about);
		jp_image.add(lbl_chart);
		jp_main.add(jp, BorderLayout.WEST);
		jp_main.add(jp_image, BorderLayout.CENTER);
		jp_main.setVisible(false);
		jf.setSize(780,500);
		jf.setLayout(new BorderLayout());
		jf.add(jp_top, BorderLayout.NORTH);
		jf.add(jp_main, BorderLayout.CENTER);
		jf.setLocationRelativeTo(null);
		//jf.setUndecorated(true);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_go) {
			getStockInfo(txt_symbol.getText());
		}
		if(e.getSource() == btn_about) {
			JOptionPane.showMessageDialog(jf, "Title : Stock Viewer Application\nDeveloped by : Vivek Vijaykant Dhumal\nVersion: 0.1\nCreated On : 16 Jun 2013\nDescription : This is application is only gives you\nhistorical data on stock that you have entered\nNote : All information is powered by Yahoo Finance Api", "About Application", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void getStockInfo(String symbol){
		try {
			URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=snl1d1t1c1ohgv&e=.csv");
			URL imageurl = new URL("http://chart.finance.yahoo.com/w?s="+symbol);
			BufferedImage image = ImageIO.read(imageurl);
			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			jp_main.setVisible(false);
			while((inputLine = in.readLine()) != null) {
				//System.out.print(inputLine);
				String[] yahooStockInfo = inputLine.split(",");
				if(Float.valueOf(yahooStockInfo[2]) != 0.00) {
					jp_main.setVisible(true);
					lbl_chart.setIcon(new ImageIcon(image));
					lbl_sym.setText("Symbol : "+yahooStockInfo[0]);
					lbl_name.setText("Name : "+yahooStockInfo[1]);
					lbl_lastTrade.setText("Last Trade (Price Only) : "+yahooStockInfo[2]);
					lbl_lastdate.setText("Last Trade Date : "+yahooStockInfo[3]);
					lbl_lasttime.setText("Last Trade Time : "+yahooStockInfo[4]);
					lbl_change.setText("Change : "+yahooStockInfo[5]);
					lbl_open.setText("Open : "+yahooStockInfo[6]);
					lbl_dayhigh.setText("Day's High : "+yahooStockInfo[7]);
					lbl_daylow.setText("Day's Low : "+yahooStockInfo[8]);
					lbl_volume.setText("Volume : "+yahooStockInfo[9]);
				} else {
					JOptionPane.showMessageDialog(jf, "Unable to get Stock info for : "+symbol, "System Alert", JOptionPane.ERROR_MESSAGE);
					//System.out.print("Unable get stock info for : "+symbol);
				}
			}
			in.close();
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(jf, "Unable to Connect", "System Alert", JOptionPane.ERROR_MESSAGE);
			//System.out.print("Unable get stock info for : "+symbol+" "+ex);
		}
	}
	public static void main(String [] args) {
		new StockMain();
	}
}