package FixieGui;

import FixData.FixDataStore;
import LogReader.FixDecoder;
import LogReader.FixFileReader;
import LogReader.ParsedLine;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lmancini on 1/1/18.
 */
public class FixieController {
    private App view_;

    FixFileReader reader_;
    List<String> rawText_;
    FixDataStore dataStore_;
    Preferences preferences_;

    public FixieController(Preferences pref) {
        this.dataStore_ = new FixDataStore();
        this.preferences_ = pref;
    }


    public void Openfile(ActionEvent event)
    {

        JFileChooser fc = new JFileChooser();

        Component component = (Component) event.getSource();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
        int returnVal = fc.showOpenDialog(frame);

        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }

        reader_ = new FixFileReader(file);
        rawText_ = reader_.ReadFile();

        try {
            displayText();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void DecodeFile(ActionEvent event) throws Exception
    {
        AbstractButton aButton = (AbstractButton) event.getSource();
        boolean selected = aButton.getModel().isSelected();

        preferences_.setDecoded_(selected);
        preferences_.SavePreferences();

        displayText();
    }

    public void SetDoubleSpace(ActionEvent event) throws Exception
    {
        AbstractButton aButton = (AbstractButton) event.getSource();
        boolean selected = aButton.getModel().isSelected();

        preferences_.setDoubleSpace_(selected);
        preferences_.SavePreferences();

        displayText();
    }

    public void SetNoWrap(ActionEvent event) throws Exception
    {
        AbstractButton aButton = (AbstractButton) event.getSource();
        boolean selected = aButton.getModel().isSelected();

        preferences_.setNoWrap_(selected);
        preferences_.SavePreferences();

        displayText();
    }


    public void SetForeground(ActionEvent event) throws Exception
    {
        Color newColor = JColorChooser.showDialog(
                null,
                "Foreground Color",
                preferences_.getForeground_());

        preferences_.setForeground_(newColor);
        preferences_.SavePreferences();

        displayText();
    }

    public void SetBackground(ActionEvent event) throws Exception
    {
        Color newColor = JColorChooser.showDialog(
            null,
            "Foreground Color",
            preferences_.getBackground_());

        preferences_.setBackground_(newColor);
        preferences_.SavePreferences();

        displayText();
    }

    public void SetDelimiter(ActionEvent event) throws Exception
    {
        if (preferences_.getDelimiter() != null
            && !preferences_.getDelimiter().isEmpty())
        {
            preferences_.setDelimiter("");
        }
        else {
            String delimiter = JOptionPane.showInputDialog(
                    null,
                    "Enter Delimiter");

            preferences_.setDelimiter(delimiter);
            preferences_.SavePreferences();
        }
        displayText();
    }


    private void displayText() throws Exception
    {
        if (preferences_.isDecoded_()) {
            displayDecoded();
        }
        else {
            displayRaw();
        }
    }
    private void displayRaw()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rawText_.size(); ++i) {
            sb.append(rawText_.get(i));
            sb.append("\n");

            if (preferences_.isDoubleSpace_()) {
                sb.append("\n");
            }
        }
        view_.SetText(sb.toString(), preferences_);
    }

    private void displayDecoded() throws Exception
    {
        if (null == rawText_ || null == reader_) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        FixDecoder decoder = new FixDecoder(dataStore_);

        for (int i = 0; i < rawText_.size(); ++i)
        {
            ParsedLine pl = reader_.ParseLine(rawText_.get(i), preferences_.getDelimiter());

            if (pl.returnCode_ == 0) {
                continue;
            }
            if (pl.returnCode_ < 0) {
                break;
            }

            sb.append(pl.header_);
            sb.append(decoder.Decode(pl.tags_));
            sb.append("\n");

            if (preferences_.isDoubleSpace_()) {
                sb.append("\n");
            }
        }
        view_.SetText(sb.toString(), preferences_);
    }

    public void setView(App view) {
        this.view_ = view;
        view_.SetText("", preferences_);
    }
}
