import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class TextEditor extends JFrame {
    private JTextArea textArea = new JTextArea();
    private JComboBox<String> fontComboBox;
    private JToggleButton boldButton;
    private JToggleButton italicButton;
    private JSpinner fontSizeSpinner;
    private JSlider fontSizeSlider;
    private static final int INITIAL_FONT_SIZE = 12;
    private final JLabel infoText = new JLabel("Líneas: 0 Carácteres: 0 Palabras: 0 Fuente: 0");
    // variables de informacion
    private final String currentFont = Font.SANS_SERIF;
    private int currentLines;
    private int currentChars;
    private int currectWords;

    public TextEditor() {
        setupFrame();
        setupComponents();
        setVisible(true);
    }

    /**
     * hacemos un setup del JFrame global
     */
    private void setupFrame() {
        setTitle("Text Editor");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /**
     * seteamos en el JFrame todos los componentes que vayamos creando
     */
    private void setupComponents() {
        // create menu bar
        setJMenuBar(createMenuBar());

        // create option panel
        add(createOptionPanel(), BorderLayout.NORTH);

        // create info panel
        add(createInfoPanel(), BorderLayout.SOUTH);

        // create text area
        textArea = new JTextArea();
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, INITIAL_FONT_SIZE));

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                currentLines = textArea.getLineCount();
                currentChars = knowCharsNumber();
                currectWords = knowWordsNumber();
                infoText.setText("Líneas: " + currentLines + " Carácteres: " + currentChars + " Palabras: " + currectWords + " Fuente: " + currentFont);
            }
        });

        textArea.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    /**
     * función para crear la barra entera con los botones de las opciones tanto de "archivo" como de "estilos"
     *
     * @return JMenuBar barra de navigación con todas las opciones
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createStyleMenu());
        return menuBar;
    }

    /**
     * función para crear los botones con las opciones de las acciones del menú "archivo"
     *
     * @return JMenu con el típico contenido de la opción "file"
     */
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

    /**
     * función para crear los botones con las opciones de los estilos de la fuente
     *
     * @return JMenu con las opciones de los estilos de fuente
     */
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

    /**
     * función para crear el panel de opciones añadiendo el combobox, spinner y slider
     *
     * @return JPanel contiene ComboBox, Spinner y Slider
     */
    private JPanel createOptionPanel() {
        JPanel optionPanel = new JPanel();
        optionPanel.add(createFontComboBox());
        optionPanel.add(createFontSizeSpinner());
        optionPanel.add(createFontSizeSlider());
        return optionPanel;
    }

    /**
     * función para crear el combobox obteniendo todas las fuentes disponibles en el sistema
     *
     * @return JComboBox combobox con su contenido
     */
    private JComboBox<String> createFontComboBox() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fonts);
        fontComboBox.setSelectedItem(Font.SANS_SERIF);
        fontComboBox.addActionListener(e -> updateFont());
        return fontComboBox;
    }

    /**
     * función para crear el spinner, dándole un valor mínimo de 12 y máximo de 72 con un valor inicial predeterminado de 12 (INITIAL_FONT_SIZE) y un stepsize de 2
     *
     * @return JSpinner spinner con su contenido
     */
    private JSpinner createFontSizeSpinner() {
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(INITIAL_FONT_SIZE, 12, 72, 2));
        fontSizeSpinner.addChangeListener(e -> updateFontSize((int) fontSizeSpinner.getValue()));
        return fontSizeSpinner;
    }

    /**
     * función para crear el slider, dándole un valor mínimo de 12 y máximo de 72 con un valor inicial predeterminado de 12 (INITIAL_FONT_SIZE)
     *
     * @return JSlider slider con su contenido
     */
    private JSlider createFontSizeSlider() {
        fontSizeSlider = new JSlider(JSlider.HORIZONTAL, 12, 72, INITIAL_FONT_SIZE);
        fontSizeSlider.setMajorTickSpacing(10);
        fontSizeSlider.setPaintLabels(true);
        fontSizeSlider.addChangeListener(e -> updateFontSize(fontSizeSlider.getValue()));
        return fontSizeSlider;
    }

    /**
     * función para actualizar la fuente seleccionada en el comboBox
     */
    private void updateFont() {
        Font currentFont = textArea.getFont();
        String newFontName = (String) fontComboBox.getSelectedItem();
        textArea.setFont(new Font(newFontName, currentFont.getStyle(), currentFont.getSize()));
        infoText.setText("Líneas: " + currentLines + " Carácteres: " + currentChars + " Palabras: " + currectWords + " Fuente: " + newFontName);
    }

    /**
     * función para actualizar el tamaño de la letra enviando el nuevo tamaño por parametro como número entero
     *
     * @param size el nuevo valor del tamaño de la fuente
     */
    private void updateFontSize(int size) {
        Font currentFont = textArea.getFont();
        textArea.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), size));
        fontSizeSpinner.setValue(size);
        fontSizeSlider.setValue(size);
    }

    /**
     * función para actualizar el estilo de la letra, añadiendo | quitando negrita y cursiva
     */
    private void updateTextStyle() {
        int style = Font.PLAIN;
        if (boldButton.isSelected()) style |= Font.BOLD;
        if (italicButton.isSelected()) style |= Font.ITALIC;
        textArea.setFont(new Font(textArea.getFont().getFontName(), style, textArea.getFont().getSize()));
    }

    /**
     * función para initializar el panel con información del editor de texto la información que devuelve infoPanel:
     * 1. El número de líneas
     *
     * @return JPanel toda la información del editor
     */
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.add(infoText);
        return infoPanel;
    }

    /**
     * función para obtener el número de caracteres a partir del texto del text area
     *
     * @return int número de caracteres
     */
    private int knowCharsNumber() {
        return textArea.getText().split("").length;
    }

    /**
     * función para obtener el número de palabras a partir del texto del text area
     *
     * @return int número de palabras
     */
    private int knowWordsNumber() {
        return textArea.getText().split("\\s+").length;
    }
}