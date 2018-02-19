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
        JMenu edit = new JMenu("Edit");
        menubar_.add(edit);
        JMenu preferences = new JMenu("Preferences");
        menubar_.add(preferences);

        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic(KeyEvent.VK_O);
        file.add(open);

        JCheckBoxMenuItem decode = new JCheckBoxMenuItem("Decode", pref.isDecoded_());
        decode.setMnemonic(KeyEvent.VK_D);
        file.add(decode);

        JMenu find = new JMenu("Find");
        edit.add(find);

        JMenuItem mi_find = new JMenuItem("Find...");
        open.setMnemonic(KeyEvent.VK_F);
        find.add(mi_find);

        JMenuItem mi_find_next = new JMenuItem("Find Next");
        open.setMnemonic(KeyEvent.VK_N);
        find.add(mi_find_next);

        JMenuItem mi_find_prev = new JMenuItem("Find Previous");
        open.setMnemonic(KeyEvent.VK_P);
        find.add(mi_find_prev);

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

        JCheckBoxMenuItem delimiter = new JCheckBoxMenuItem(
                "Custom Delimiter",
                pref.getDelimiter() != null && !pref.getDelimiter().isEmpty());
        decode.setMnemonic(KeyEvent.VK_C);
        preferences.add(delimiter);

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

        delimiter.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (null != controller_) {
                            try {
                                controller_.SetDelimiter(e);
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

}
