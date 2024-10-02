import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextEditor extends JFrame {
    private final JTextArea textArea;
    private JComboBox<String> fontsCombo;
    private JToggleButton blackStyle;
    private JToggleButton cursiveStyle;

    public TextEditor() {
        setTitle("Editor de Texto");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // option panel
        JPanel optionPanel = createOptionPanel();
        contentPane.add(optionPanel, BorderLayout.NORTH);

        // text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // CaretListener to update style buttons
        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                Font currentFont = textArea.getFont();
                blackStyle.setSelected(currentFont.isBold());
                cursiveStyle.setSelected(currentFont.isItalic());
            }
        });

        // default config
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Archivo");
        JMenu style = new JMenu("Estilo");

        JMenuItem newFile = new JMenuItem("Nuevo");
        JMenuItem exitFile = new JMenuItem("Salir");

        blackStyle = new JToggleButton("Negrita");
        cursiveStyle = new JToggleButton("Cursiva");

        file.add(newFile);
        file.add(exitFile);
        style.add(blackStyle);
        style.add(cursiveStyle);
        menuBar.add(file);
        menuBar.add(style);

        newFile.addActionListener(e -> textArea.setText(""));
        exitFile.addActionListener(e -> System.exit(0));

        blackStyle.addActionListener(e -> updateTextStyle());
        cursiveStyle.addActionListener(e -> updateTextStyle());

        return menuBar;
    }

    private JPanel createOptionPanel() {
        JPanel optionPanel = new JPanel();
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontsCombo = new JComboBox<>(fonts);
        optionPanel.add(fontsCombo);

        fontsCombo.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(fontsCombo.getSelectedItem().toString(), currentFont.getStyle(), currentFont.getSize()));
        });

        return optionPanel;
    }

    private void updateTextStyle() {
        Font currentFont = textArea.getFont();
        int style = Font.PLAIN;
        if (blackStyle.isSelected()) style |= Font.BOLD;
        if (cursiveStyle.isSelected()) style |= Font.ITALIC;
        textArea.setFont(new Font(currentFont.getFontName(), style, currentFont.getSize()));
    }
}