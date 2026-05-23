import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SlideShow extends JFrame {

	// Declare Variables
	private JPanel slidePane;
	private JPanel textPane;
	private JPanel buttonPane;
	private JPanel bottomPane;
	private CardLayout card;
	private CardLayout cardText;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblSlide;
	private JLabel lblTextArea;

	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * initialize the contents of the frame.
	 */
	private void initComponent() {
		// setting up the main objects used in the slideshow
		card = new CardLayout();
		cardText = new CardLayout();
		slidePane = new JPanel();
		textPane = new JPanel();
		buttonPane = new JPanel();
		bottomPane = new JPanel();
		btnPrev = new JButton();
		btnNext = new JButton();
		lblSlide = new JLabel();
		lblTextArea = new JLabel();

		// changed the title so it matches the new wellness travel theme
		setTitle("Top 5 Wellness Travel Destinations");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		//Setting the layouts for the panels
		slidePane.setLayout(card);
		textPane.setLayout(cardText);

		// made the text area look calmer for that wellness theme
		textPane.setBackground(new Color(193, 230, 193));

		// this bottom panel lets the text and buttons both show up correctly
		bottomPane.setLayout(new BorderLayout());
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPane.setBackground(new Color(193, 230, 193));

		// still using the same loop, just updating the content
		for (int i = 1; i <= 5; i++) {
			lblSlide = new JLabel();
			lblTextArea = new JLabel();

			lblSlide.setText(getResizeIcon(i));
			lblTextArea.setText(getTextDescription(i));
			lblTextArea.setHorizontalAlignment(SwingConstants.CENTER);

			slidePane.add(lblSlide, "card" + i);
			textPane.add(lblTextArea, "cardText" + i);
		}

		getContentPane().add(slidePane, BorderLayout.CENTER);

		bottomPane.add(textPane, BorderLayout.CENTER);

		btnPrev.setText("Previous");
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});
		buttonPane.add(btnPrev);

		btnNext.setText("Next");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});
		buttonPane.add(btnNext);

		bottomPane.add(buttonPane, BorderLayout.SOUTH);
		getContentPane().add(bottomPane, BorderLayout.SOUTH);
	}

	/**
	 * Previous button functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}

	/**
	 * Next button functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Method to get the images
	 */
	private String getResizeIcon(int i) {
		String image = "";
		
		// this loads the right image for each wellness destination
		if (i == 1) {
			image = "<html><body><img width='800' height='500' src='" 
					+ getClass().getResource("/resources/Sedona.jpg") + "'></body></html>";
		} else if (i == 2) {
			image = "<html><body><img width='800' height='500' src='" 
					+ getClass().getResource("/resources/Bali.jpg") + "'></body></html>";
		} else if (i == 3) {
			image = "<html><body><img width='800' height='500' src='" 
					+ getClass().getResource("/resources/BLI.jpg") + "'></body></html>";
		} else if (i == 4) {
			image = "<html><body><img width='800' height='500' src='" 
					+ getClass().getResource("/resources/CostaRica.jpg") + "'></body></html>";
		} else if (i == 5) {
			image = "<html><body><img width='800' height='500' src='" 
					+ getClass().getResource("/resources/Kyoto.jpg") + "'></body></html>";
		}
		
		return image;
	}

	/**
	 * Method to get the text values
	 */
	private String getTextDescription(int i) {
		String text = "";

		// changed the slide text so every slide fits the detox and wellness focus
		if (i == 1) {
			text = "<html><body><font size='5'>#1 Sedona, Arizona</font><br>"
					+ "Sedona is a great choice for travelers who want quiet scenery, relaxing spa options, and time to recharge outdoors.</body></html>";
		} else if (i == 2) {
			text = "<html><body><font size='5'>#2 Bali, Indonesia</font><br>"
					+ "Bali is known for peaceful resorts, yoga retreats, and a calm atmosphere that fits a wellness focused trip really well.</body></html>";
		} else if (i == 3) {
			text = "<html><body><font size='5'>#3 Blue Lagoon, Iceland</font><br>"
					+ "The Blue Lagoon gives visitors a chance to unwind in warm geothermal waters while enjoying a quiet and refreshing setting.</body></html>";
		} else if (i == 4) {
			text = "<html><body><font size='5'>#4 Costa Rica Retreat</font><br>"
					+ "Costa Rica works well for wellness travel as it combines nature, fresh air, and peaceful resort spaces in one trip.</body></html>";
		} else if (i == 5) {
			text = "<html><body><font size='5'>#5 Kyoto, Japan</font><br>"
					+ "Kyoto offers a more peaceful travel experience with calm gardens, quiet spaces, and a slower pace that helps people reset.</body></html>";
		}

		return text;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				SlideShow ss = new SlideShow();
				ss.setVisible(true);
			}
		});
	}
}