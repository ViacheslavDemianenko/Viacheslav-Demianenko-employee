package bg.sirma.demianenko.dao;

import bg.sirma.demianenko.exception.DetailRuntimeException;
import bg.sirma.demianenko.model.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataDao {
    private final ResourceLoader resourceLoader;
    private final String relativePath;

    public DataDao(ResourceLoader resourceLoader, @Value("${csv.relative.path}") String relativePath) {
        this.resourceLoader = resourceLoader;
        this.relativePath = relativePath;
    }

    private final static String COLUMN_SEPARATOR = ",";

    public List<Data> getDataList(){
        List<Data> dataList = new ArrayList<>();
        int questionNumber = 0;
        try(BufferedReader bufferedReader = new BufferedReader
                (new InputStreamReader(resourceLoader.getResource(relativePath).getInputStream()))) {
            while(bufferedReader.ready()){
                var currentData = new Data();
                var row = bufferedReader.readLine().trim();
                var columnValues = row.split(COLUMN_SEPARATOR);

                currentData.setEmployeeId(Long.parseLong(columnValues[0].trim()));
                currentData.setProjectId(Long.parseLong(columnValues[1].trim()));

                currentData.setDateFrom(LocalDate.parse(columnValues[2].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                if(columnValues[3].trim().equals("NULL")){
                    currentData.setDateTo(LocalDate.now());
                } else {
                    currentData.setDateTo(LocalDate.parse(columnValues[3].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                dataList.add(currentData);
            }
        } catch (IOException ioe) {
            throw new DetailRuntimeException(MessageFormat.format("Can't download CSV-file: {0}", ioe));
        }

        return dataList;
    }
}
