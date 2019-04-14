package Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import GUI.Controler;
import GUI.MainFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 Bank bank=null;
    	 
         bank = new Bank();
          try {
				FileInputStream fileIn = new FileInputStream("bank.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				bank = (Bank) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				System.out.println("Bank class not found");
				c.printStackTrace();
				return;
			}
         MainFrame mainFrame = new MainFrame(bank);
         Controler c = new Controler(bank, mainFrame);
         c.setVisible();
    }
}
