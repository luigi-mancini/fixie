package LogReader;

import FixData.FixDataStore;
import FixData.FixField;
import FixData.FixEnum;


import java.util.Map;

/**
 * Created by lmancini on 12/26/17.
 */
public class FixDecoder {

    public FixDecoder(FixDataStore dataStore_) {
        this.dataStore_ = dataStore_;
    }

    public String Decode(String[] in) throws Exception
    {
        StringBuilder sb = new StringBuilder();

        String version = GetVersion(in);
        if (version == null) {
            return "";
        }

        for (int i = 0; i < in.length; ++i) {

            String[] tagValue = in[i].split("=");
            if (tagValue.length != 2) {
                // Tag, Value pair was not in Tag=Value form
                sb.append(in[i]);
                if (i != (in.length - 1)) {
                    // Don't print delimiter on last field
                    sb.append(", ");
                }
                continue;
            }

            FixField field = dataStore_.GetField(version, tagValue[0]);

            if (field == null) {
                // Field not found. Could be exchange specific tag.
                // Just print the field and continue
                sb.append(in[i]);
                if (i != (in.length - 1)) {
                    // Don't print delimter on last field
                    sb.append(", ");
                }
                continue;
            }

            sb.append(field.getName());
            sb.append("(");
            sb.append(field.getTag());
            sb.append(")=");

            FixEnum fe = dataStore_.GetEnum(version, tagValue[0], tagValue[1]);
            if (null == fe) {
                sb.append(tagValue[1]);
            } else {
                sb.append(fe.getName());
                sb.append("(");
                sb.append(tagValue[1]);
                sb.append(")");
            }

            if (i != (in.length - 1)) {
                // Don't print delimiter on last field
                sb.append(", ");
            }
        }

        return sb.toString();

    }

    private String GetVersion(String[] in) {
        if (in.length < 1) {
            return null;
        }

        String[] tagValue = in[0].split("=");
        if (tagValue.length == 2 || 0 == tagValue[0].compareTo("8")) {

            if (0 == tagValue[1].compareTo("FIXT.1.1")) {
                // If FIX Transport is specified assume that
                // the exchange is using the latest version
                return "FIX.5.0SP2";
            }

            return tagValue[1];
        }

        return null;
    }

    FixDataStore dataStore_;
}
