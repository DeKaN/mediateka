package mediateka.server;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MainClass {

	private JFrame frame;
	private JTextField textField;
	private JButton button;
	private static boolean isStarted = false, needExit = false;
	private static ServerSocket sock = null;
	private static Synchronizer s = new Synchronizer();
	private static ArrayList<ServerThread> threads = new ArrayList<ServerThread>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainClass window = new MainClass();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		while (!needExit) {
			if (isStarted) {
				try {
					ServerThread thread = new ServerThread(sock.accept(), s);
					thread.start();
					threads.add(thread);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void removeThread(ServerThread t) {
		threads.remove(t);
	}

	/**
	 * Create the application.
	 */
	public MainClass() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				isStarted = false;
				needExit = true;
			}
		});
		frame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		frame.setBounds(100, 100, 224, 79);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label = new JLabel("Порт:");
		label.setFont(new Font("Arial", Font.PLAIN, 12));
		label.setBounds(10, 11, 39, 15);
		frame.getContentPane().add(label);

		textField = new JTextField();
		textField.setText("7568");
		textField.setBounds(54, 9, 44, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		button = new JButton("Старт");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				isStarted = !isStarted;
				try {
					if (isStarted)
						sock = new ServerSocket(Integer.parseInt(textField
								.getText()));
					else
						sock.close();
				} catch (Exception e) {
					isStarted = false;
					e.printStackTrace();
				}
				textField.setEnabled(!isStarted);
				button.setText(isStarted ? "Стоп" : "Старт");
			}
		});
		button.setBounds(108, 8, 89, 23);
		frame.getContentPane().add(button);
	}
}
