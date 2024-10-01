import javax.swing.*;
import java.awt.*;

public class TextEditor extends JFrame {
    public TextEditor() throws HeadlessException {
        // content panel
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        setSize(800, 800);
        setLocationRelativeTo(null);


        setVisible(true);
    }
}
