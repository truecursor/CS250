/*
	Module 3 CS250
	Author: Andrew Robert
	Date: March 16, 2026
 */

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}


class TopDestinationListFrame extends JFrame {
    private DefaultListModel listModel;
    private JList list;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel();

        // Destinations + image file names + desc
        addDestinationNameAndPicture(
                "1. Osaka, Japan - A city known for street food, bright nightlife, and massive shopping areas.",
                new ImageIcon(getClass().getResource("/resources/osaka.jpg")),
                "https://www.expedia.com/Osaka.d179897.Destination-Travel-Guides",
                "Image credit: Wikimedia Commons, Original Link: https://commons.wikimedia.org/wiki/File:Osaka_street_scene.jpg");
        addDestinationNameAndPicture(
                "2. Nara, Japan - A peaceful historic city famous for friendly deer, temples, and traditional scenery.",
                new ImageIcon(getClass().getResource("/resources/nara.jpg")),
                "https://www.expedia.com/Nara.d6139567.Destination-Travel-Guides",
                "Image credit: Wikimedia Commons, Original Link: https://commons.wikimedia.org/wiki/File:Sika_Deer_in_Nara,_Japan,_20240819_1539_4763.jpg");
        addDestinationNameAndPicture(
                "3. Seoul, South Korea - A fast paced city with modern shopping, great food, and rich culture.",
                new ImageIcon(getClass().getResource("/resources/seoul.jpg")),
                "https://www.expedia.com/Seoul.d178308.Destination-Travel-Guides",
                "Image credit: Wikimedia Commons, Original Link: https://commons.wikimedia.org/wiki/File:Seoul_at_N_Seoul_Tower.jpg");
        addDestinationNameAndPicture(
                "4. Singapore - A clean, but compact and modern destination known for amazing food, skyline views, and attractions.",
                new ImageIcon(getClass().getResource("/resources/singapore.jpg")),
                "https://www.expedia.com/Singapore.d161.Destination-Travel-Guides",
                "Image credit: Wikimedia Commons, Original Link: https://commons.wikimedia.org/wiki/File:Skylines_of_the_Central_Business_District,_Singapore_at_dusk.jpg");
        addDestinationNameAndPicture(
                "5. Hong Kong - A vibrant city with incredible night views, markets, and a unique city atmosphere.",
                new ImageIcon(getClass().getResource("/resources/hongkong.jpg")),
                "https://www.expedia.com/Hong-Kong-SAR.d77.Destination-Travel-Guides",
                "Image credit: Wikimedia Commons, Original Link: https://commons.wikimedia.org/wiki/File:Hong_Kong_Skyscrapers.jpg");

        // JList to display image credit when hovering over each destination
        list = new JList(listModel) {
            public String getToolTipText(java.awt.event.MouseEvent e) {
                int index = locationToIndex(e.getPoint());

                if (index > -1) {
                    TextAndIcon item = (TextAndIcon) getModel().getElementAt(index);
                    return item.getImageCredit(); // shows Wikimedia credit for hovered item
                }

                return null;
            }
        };
        
        // added tooltip functionality for the list
        ToolTipManager.sharedInstance().registerComponent(list);
        
        // double click a destination to open its travel link (CONTROLS)
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedDestinationLink();
                }
            }
        });
        
        // press Enter key to open selected destination (CONTROLS)
        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    openSelectedDestinationLink();
                }
            }
        });
        
        // press ESC to clear the current selection
        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    list.clearSelection();
                    setTitle("Top 5 Destination List"); // reset title
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(list);

        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(2);
        list.setCellRenderer(renderer);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // update window title based on the selected destination
        list.addListSelectionListener(e -> {
            TextAndIcon selected = (TextAndIcon) list.getSelectedValue();
            if (selected != null) {
                setTitle("Top 5 Destination List - " + selected.getText());
            }
        });
        
        // auto selects the first destination when the app starts
        list.setSelectedIndex(0);

        // header area for extra customization
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Top 5 Travel Destinations", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        topPanel.add(headerLabel, BorderLayout.CENTER);

        // note to guide the user
        JLabel instructionLabel = new JLabel("Select a destination, then click the button below to open a travel package link.", SwingConstants.CENTER);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        topPanel.add(instructionLabel, BorderLayout.SOUTH);

        // button so user can open a travel package page for the selected destination
        JButton packageButton = new JButton("Open Selected Travel Package");
        packageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openSelectedDestinationLink();
            }
        });

        // label to match the assignment req
        JLabel nameLabel = new JLabel("Created by Andrew Robert", SwingConstants.CENTER);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 12, 10));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        // info button to show controls and usage instructions
        JButton infoButton = new JButton("?");
        infoButton.setFocusPainted(false);
        infoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Controls:\n\n" +
                "- Click: Select destination\n" +
                "- Double-click: Open travel link\n" +
                "- ESC: Deselect\n" +
                "- Hover: View image credit",
                "Help / Controls",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        bottomPanel.add(packageButton, BorderLayout.CENTER);

        // panel to hold centered name label and info button
        JPanel bottomRow = new JPanel(new BorderLayout());

        // empty spacer to balance the info button on the right
        JLabel leftSpacer = new JLabel();
        leftSpacer.setPreferredSize(infoButton.getPreferredSize());

        // keeps name centered visually
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        namePanel.add(nameLabel);

        bottomRow.add(leftSpacer, BorderLayout.WEST);
        bottomRow.add(namePanel, BorderLayout.CENTER);
        bottomRow.add(infoButton, BorderLayout.EAST);

        bottomPanel.add(bottomRow, BorderLayout.SOUTH);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addDestinationNameAndPicture(String text, Icon icon, String packageLink, String imageCredit) {
        TextAndIcon tai = new TextAndIcon(text, icon, packageLink, imageCredit);
        listModel.addElement(tai);
    }

    // helper method to open the selected destination link in browser
    private void openSelectedDestinationLink() {
        TextAndIcon selectedDestination = (TextAndIcon) list.getSelectedValue();

        if (selectedDestination == null) {
            JOptionPane.showMessageDialog(this, "Please select a destination first.");
            return;
        }

        try {
            Desktop.getDesktop().browse(new URI(selectedDestination.getPackageLink()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to open the travel package link.");
        }
    }
}

//updated class to include travel package link and image credit for each destination
class TextAndIcon {
    private String text;
    private Icon icon;
    private String packageLink;
    private String imageCredit;		// Stores Wikimedia image credit to display on hover

    // updated constructor to include image credit along with text, icon, and link
    public TextAndIcon(String text, Icon icon, String packageLink, String imageCredit) {
        this.text = text;
        this.icon = icon;
        this.packageLink = packageLink;
        this.imageCredit = imageCredit;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getPackageLink() {
        return packageLink;
    }
    
	// returns the image credit for tooltip display
    public String getImageCredit() {
        return imageCredit;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void setPackageLink(String packageLink) {
        this.packageLink = packageLink;
    }

    public void setImageCredit(String imageCredit) {
        this.imageCredit = imageCredit;
    }
}


class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer {
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
        setHorizontalTextPosition(JLabel.RIGHT);
        setVerticalTextPosition(JLabel.CENTER);
        setIconTextGap(15);
    }

    public Component getListCellRendererComponent(JList list, Object value,
    int index, boolean isSelected, boolean hasFocus) {
        // The object from the combo box model MUST be a TextAndIcon.
        TextAndIcon tai = (TextAndIcon) value;

        // Sets text and icon on 'this' JLabel.
        setText(tai.getText());
        // scales images to a consistent size
        ImageIcon icon = (ImageIcon) tai.getIcon();
        Image img = icon.getImage().getScaledInstance(500, 250, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(img));

        if (isSelected) {
            setBackground(new Color(30, 30, 30)); // darker colored selection
            setForeground(Color.WHITE);
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(new Font("SansSerif", Font.PLAIN, 16));

        return this;
    }

    // The following methods are overridden to be empty for performance
    // reasons. If you want to understand better why, please read:
    //
    // http://java.sun.com/javase/6/docs/api/javax/swing/DefaultListCellRenderer.html#override

    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}
