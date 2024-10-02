import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TextEditor extends JFrame {
    private JTextArea textArea;
    private JComboBox<String> fontComboBox;
    private JToggleButton boldButton;
    private JToggleButton italicButton;
    private JSpinner fontSizeSpinner;
    private JSlider fontSizeSlider;
    private static final int INITIAL_FONT_SIZE = 12;

    public TextEditor() {
        setupFrame();
        setupComponents();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Text Editor");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void setupComponents() {
        // create menu bar
        setJMenuBar(createMenuBar());

        // create option panel
        add(createOptionPanel(), BorderLayout.NORTH);

        // create text area
        textArea = new JTextArea();
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, INITIAL_FONT_SIZE));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // add caret listener
        textArea.addCaretListener(e -> updateStyleButtons());
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createStyleMenu());
        return menuBar;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem newItem = new JMenuItem("Nuevo");
        JMenuItem exitItem = new JMenuItem("Salir");

        newItem.addActionListener(e -> textArea.setText(""));
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(exitItem);
        return fileMenu;
    }

    private JMenu createStyleMenu() {
        JMenu styleMenu = new JMenu("Estilo");
        boldButton = new JToggleButton("Negrita");
        italicButton = new JToggleButton("Cursiva");

        boldButton.addActionListener(e -> updateTextStyle());
        italicButton.addActionListener(e -> updateTextStyle());

        styleMenu.add(boldButton);
        styleMenu.add(italicButton);
        return styleMenu;
    }

    private JPanel createOptionPanel() {
        JPanel optionPanel = new JPanel();
        optionPanel.add(createFontComboBox());
        optionPanel.add(createFontSizeSpinner());
        optionPanel.add(createFontSizeSlider());
        return optionPanel;
    }

    private JComboBox<String> createFontComboBox() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.addActionListener(e -> updateFont());
        return fontComboBox;
    }

    private JSpinner createFontSizeSpinner() {
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(INITIAL_FONT_SIZE, 1, 100, 1));
        fontSizeSpinner.addChangeListener(e -> updateFontSize((int) fontSizeSpinner.getValue()));
        return fontSizeSpinner;
    }

    private JSlider createFontSizeSlider() {
        fontSizeSlider = new JSlider(JSlider.HORIZONTAL, 12, 24, INITIAL_FONT_SIZE);
        fontSizeSlider.setMajorTickSpacing(2);
        fontSizeSlider.setPaintLabels(true);
        fontSizeSlider.addChangeListener(e -> updateFontSize(fontSizeSlider.getValue()));
        return fontSizeSlider;
    }

    private void updateFont() {
        Font currentFont = textArea.getFont();
        String newFontName = (String) fontComboBox.getSelectedItem();
        textArea.setFont(new Font(newFontName, currentFont.getStyle(), currentFont.getSize()));
    }

    private void updateFontSize(int size) {
        Font currentFont = textArea.getFont();
        textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), size));
        fontSizeSpinner.setValue(size);
        fontSizeSlider.setValue(size);
    }

    private void updateTextStyle() {
        int style = Font.PLAIN;
        if (boldButton.isSelected()) style |= Font.BOLD;
        if (italicButton.isSelected()) style |= Font.ITALIC;
        textArea.setFont(new Font(textArea.getFont().getFontName(), style, textArea.getFont().getSize()));
    }

    private void updateStyleButtons() {
        Font currentFont = textArea.getFont();
        boldButton.setSelected(currentFont.isBold());
        italicButton.setSelected(currentFont.isItalic());
    }
}