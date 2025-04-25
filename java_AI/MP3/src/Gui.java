import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Gui extends JFrame{
    private PerceptronService perceptronService;

    public Gui(PerceptronService perceptronService) {
        this.perceptronService = perceptronService;
        startGui();
    }

    public void startGui() {
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textField = new JTextArea();
        panel.add(textField, BorderLayout.CENTER);

        JFrame jFrame = this;
        JButton actionButton = new JButton("Check language!");
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                if (Objects.equals(inputText, ""))
                    JOptionPane.showMessageDialog(jFrame, "No input provided!");
                else {
                    String result = perceptronService.getResult(inputText);
                    if (result.contains(" "))
                        JOptionPane.showMessageDialog(jFrame, result + ". Try with longer input!");
                    else
                        JOptionPane.showMessageDialog(jFrame, result);

                }

            }
        });
        panel.add(actionButton, BorderLayout.NORTH);
        this.add(panel);
    }
}
