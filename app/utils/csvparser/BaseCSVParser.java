package utils.csvparser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseCSVParser<T> {

    public static CSVParser parse(Reader in) throws IOException {
        return CSVFormat.RFC4180
            .withFirstRecordAsHeader()
            .withDelimiter(';')
            .parse(in);
    }

    public List<T> read(String path) throws IOException {
        return read(new File(path));
    }

    public List<T> read(File file) throws IOException {
        return read(new FileReader(file));
    }
//    public List<T> read(MultipartFormData multipartFormData) throws IOException {
//        FilePart file = multipartFormData.getFile("file");
//
//        return read(new InputStreamReader(multipartFormData.));
//    }

    public List<T> read(Reader fileReader) throws IOException {
        Iterable<CSVRecord> records = parse(fileReader);

        List<T> retVal = StreamSupport.stream(records.spliterator(), true)
            .map(this::createObjectFromRecord)
            .collect(Collectors.toList());

        fileReader.close();
        return retVal;
    }

    protected abstract T createObjectFromRecord(CSVRecord record);

}
