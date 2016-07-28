package utils.csvparser.sysuser;

import org.apache.commons.csv.CSVRecord;
import utils.csvparser.BaseCSVParser;

public class SysUserCSVParser extends BaseCSVParser<SysUserCSV> {
    public static final SysUserCSVParser parser = new SysUserCSVParser();

    @Override
    public SysUserCSV createObjectFromRecord(CSVRecord record) {
        SysUserCSV sysUserCSV = new SysUserCSV();

        sysUserCSV.email = record.get(SysUserCSVHeader.EMAIL);
        sysUserCSV.fullName = record.get(SysUserCSVHeader.FULLNAME);
        sysUserCSV.phone = record.get(SysUserCSVHeader.PHONE);
        sysUserCSV.languageCode = record.get(SysUserCSVHeader.LANGUAGE_CODE);
        sysUserCSV.webUser = Boolean.parseBoolean(record.get(SysUserCSVHeader.WEBUSER));

        return sysUserCSV;
    }
}