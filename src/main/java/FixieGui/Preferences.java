package FixieGui;

import java.awt.Color;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Created by lmancini on 1/7/18.
 */
public class Preferences {

    public Preferences() {
        this.decoded_ = false;
        this.doubleSpace_ = false;
        this.noWrap_ = false;
        this.foreground_ = null;
        this.background_ = null;
        this.delimiter = null;
    }

    public boolean isDecoded_() {
        return decoded_;
    }

    public void setDecoded_(boolean decoded_) { this.decoded_ = decoded_; }

    public boolean isDoubleSpace_() {
        return doubleSpace_;
    }

    public void setDoubleSpace_(boolean doubleSpace_) { this.doubleSpace_ = doubleSpace_; }

    public boolean isNoWrap_() {
        return noWrap_;
    }

    public void setNoWrap_(boolean noWrap_) {
        this.noWrap_ = noWrap_;
    }

    public Color getForeground_() {
        return foreground_;
    }

    public void setForeground_(Color foreground_) {
        this.foreground_ = foreground_;
    }

    public Color getBackground_() {
        return background_;
    }

    public void setBackground_(Color background_) { this.background_ = background_; }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }


    public void ReadPreference()
    {
        try {
            ObjectMapper om = new ObjectMapper();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Color.class, new JacksonColor.ColorDeserializer());
            om.registerModule(module);

            Preferences p = om.readValue(new File("xml/prefs.json"), Preferences.class);
            this.setDecoded_(p.isDecoded_());
            this.setDoubleSpace_(p.isDoubleSpace_());
            this.setNoWrap_(p.isNoWrap_());
            this.setBackground_(p.getBackground_());
            this.setBackground_(p.getBackground_());
            this.setDelimiter(p.getDelimiter());
        } catch (Exception err){
            System.out.println(err);
        }
    }

    public void SavePreferences()
    {
        try {
            ObjectMapper om = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Color.class, new JacksonColor.ColorSerializer());
            om.registerModule(module);
            om.writeValue(new File("xml/prefs.json"), this);
        } catch (Exception err){
            System.out.println(err);
        }

    }

    boolean decoded_;
    boolean doubleSpace_;
    boolean noWrap_;
    Color foreground_;
    Color background_;
    String delimiter;
}
