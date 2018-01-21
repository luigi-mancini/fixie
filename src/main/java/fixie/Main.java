package fixie;

import FixData.*;
import LogReader.FixDecoder;
import LogReader.FixFileReader;
import LogReader.ParsedLine;

public class Main {

    public static void main(String[] args) throws Exception
    {
        FixFileReader fr = new FixFileReader("xml/ndaq.log");

        FixDataStore ds = new FixDataStore();
        FixDecoder decoder = new FixDecoder(ds);
        ParsedLine in;
        do {
            in = fr.GetNextLine();
            if (in.returnCode_ == 0) {
                continue;
            }
            if (in.returnCode_ < 0) {
                break;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(in.header_);
            sb.append(decoder.Decode(in.tags_));

            System.out.println(sb.toString());
        } while (in.returnCode_ >= 0);

        //FixDataStore ds = new FixDataStore();
        //ds.GetField("FIX.4.2", "9");
    }
}
