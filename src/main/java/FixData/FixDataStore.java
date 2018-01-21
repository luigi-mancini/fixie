package FixData;

import java.util.*;

/**
 * Created by lmancini on 12/27/17.
 */
public class FixDataStore {

    class FixRevision {

        public FixRevision() {
           fields_ = new HashMap();
            enums_ = new HashMap();
        }

        Map<String, FixField> fields_;
        Map<String, Map<String, FixEnum>> enums_;
    }

    public FixField GetField(String version, String tag) throws Exception
    {
        if (!dataStore_.containsKey(version)) {
            LoadVersion(version);
        }
        FixRevision rev = dataStore_.get(version);
        return rev.fields_.get(tag);
    }

    public FixEnum GetEnum(String version, String tag, String value) throws Exception
    {
        if (!dataStore_.containsKey(version)) {
            LoadVersion(version);
        }
        FixRevision rev = dataStore_.get(version);
        Map<String, FixEnum> enumMap = rev.enums_.get(tag);
        if (enumMap == null)
            return null;
        return enumMap.get(value);
    }

    private void LoadVersion(String version) throws Exception
    {
        StringBuilder sbf =  new StringBuilder();
        sbf.append("xml/fix_repository/");
        sbf.append(version);
        sbf.append("/Base/Fields.xml");

        StringBuilder sbe =  new StringBuilder();
        sbe.append("xml/fix_repository/");
        sbe.append(version);
        sbe.append("/Base/Enums.xml");

        FixRevision rev = new FixRevision();
        new FieldParser(sbf.toString(), rev.fields_);
        new EnumParser(sbe.toString(), rev.enums_);

        dataStore_.put(version, rev);
    }

    Map<String, FixRevision> dataStore_ = new HashMap();
}
