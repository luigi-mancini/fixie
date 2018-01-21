package LogReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
/**
 * Created by lmancini on 12/17/17.
 */
public class FixFileReader {
    public FixFileReader(String fileName) {
        try {
            in_ = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            System.err.println(e);
        }

        String patt = "8=.*";
        pattern_ = Pattern.compile(patt);
    }

    public FixFileReader(File file) {
        try {
            in_ = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.err.println(e);
        }

        String patt = "8=.*";
        pattern_ = Pattern.compile(patt);
    }

    public ParsedLine GetNextLine() {
        String input;

        try {
            input = in_.readLine();
            if (input == null) {
                ParsedLine ret = new ParsedLine();
                ret.returnCode_ = -1;
                return ret;
            }

            return ParseLine(input);

        } catch (IOException e) {
            System.err.println(e);
            ParsedLine ret = new ParsedLine();
            ret.returnCode_ = -1;
            return ret;
        }
    }

    public List<String> ReadFile()
    {
        String input;
        List<String> strings = new ArrayList<String>();

        try {
            while ((input = in_.readLine()) != null) {
                strings.add(input);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        return strings;
    }

    public ParsedLine ParseLine(String str)
    {
        ParsedLine ret = new ParsedLine();

        Matcher m = pattern_.matcher(str);
        if (m.find()) {
            ret.header_ =  str.substring(0, m.start());
            String line = str.substring(m.start());

            ret.tags_ = line.split(Character.toString((char)1));
            ret.returnCode_ = 1;
        }
        else {
            ret.returnCode_ = 0;
            return ret;
        }
        return ret;
    }

    BufferedReader in_;
    Pattern pattern_;
}
