package ui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	// GUI, reflected in at runtime by SwingBuilder
	private BuildResult result;

	// Twitter
	private Twitter twitter;

	public MainFrame()
	{
		this.result = SwingJavaBuilder.build(this);
	}

	public void login() throws TwitterException
	{
		// login to twitter
		final String username = ((JTextField)this.result.get("usernameField")).getText().trim();
		final String password = ((JTextField)this.result.get("passwordField")).getText().trim();

		this.twitter = new Twitter(username, password);
//		System.setProperty("http.proxyHost", "proxy-us.intel.com");
//		System.setProperty("http.proxyPort", "911");
//		twitter.setHttpProxy("proxy-us.intel.com", 911);

		// swap the layout
		this.changeCard("home");

		// populate the list
		final JList list = ((JList)this.result.get("list"));
		list.setCellRenderer(new StatusCellRenderer());
		
		list.setListData(this.twitter.getFriendsTimeline().toArray());
		
		list.repaint();
	}

	private void changeCard(String layout)
	{
		((CardLayout)this.result.get("cards")).show(((JPanel)this.result.get("deck")), layout);
	}

	public static void main(String[] args)
	{
		// TODO report this, and remove
		SwingJavaBuilder.getConfig().addType(JPasswordField.class);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					SwingJavaBuilder.getConfig().addResourceBundle("ui/MainFrame");
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					new MainFrame().setVisible(true);
				}
				catch (ClassNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (UnsupportedLookAndFeelException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
