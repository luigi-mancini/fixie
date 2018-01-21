package FixieGui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by lmancini on 12/30/17.
 */
public class App {
    private JPanel MainPanel;
    private NoWrapTextPane textArea_;
    private JFrame frame_;
    private JMenuBar menubar_;
    private FixieController controller_;

    public App() {
    }

    public void SetText(String text, Preferences pref) {
        textArea_.setForeground(pref.getForeground_());
        textArea_.setBackground(pref.getBackground_());
        textArea_.setNoWrap_(pref.isNoWrap_());
        textArea_.setText(text);
        textArea_.setCaretPosition(0);
    }

    public void InitGui(Preferences pref) {
        frame_ = new JFrame("Fixie");

        frame_.setContentPane(MainPanel);
        frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menubar_ = new JMenuBar();
        JMenu file = new JMenu("File");
        menubar_.add(file);
        JMenu preferences = new JMenu("Preferences");
        menubar_.add(preferences);

        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        file.add(open);

        JCheckBoxMenuItem decode = new JCheckBoxMenuItem("Decode", pref.isDecoded_());
        decode.setMnemonic(KeyEvent.VK_D);
        file.add(decode);

        JCheckBoxMenuItem doubleSpace = new JCheckBoxMenuItem("Double Space", pref.isDoubleSpace_());
        decode.setMnemonic(KeyEvent.VK_D);
        preferences.add(doubleSpace);

        JCheckBoxMenuItem noWrap = new JCheckBoxMenuItem("No Wrap", pref.isNoWrap_());
        decode.setMnemonic(KeyEvent.VK_W);
        preferences.add(noWrap);

        JMenuItem foreground = new JMenuItem("Foreground Color");
        decode.setMnemonic(KeyEvent.VK_F);
        preferences.add(foreground);

        JMenuItem background = new JMenuItem("Background Color");
        decode.setMnemonic(KeyEvent.VK_B);
        preferences.add(background);

        open.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            controller_.Openfile(e);
                        }
                    }
                });

        decode.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.DecodeFile(e);
                            } catch (Exception err) {
                                System.err.println(err);
                            }
                        }
                    }
                });

        doubleSpace.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.SetDoubleSpace(e);
                            } catch (Exception err) {
                                System.err.println(err);
                            }
                        }
                    }
                });

        noWrap.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.SetNoWrap(e);
                            } catch (Exception err) {
                                System.err.println(err);
                            }
                        }
                    }
                });

        foreground.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.SetForeground(e);
                            } catch (Exception err) {
                                System.err.println(err);
                            }
                        }
                    }
                });

        background.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.SetBackground(e);
                            } catch (Exception err) {
                                System.err.println(err);
                            }
                        }
                    }
                });

        frame_.setJMenuBar(menubar_);
        frame_.pack();
        frame_.setVisible(true);

    }

    public void setController(FixieController controller) {
        this.controller_ = controller;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        MainPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(800, 600), null, 0, false));
        textArea_ = new NoWrapTextPane();
        scrollPane1.setViewportView(textArea_);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }
}
