package net.conferencescheduling.spring.model.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;


@Data
public class PaperDto {

    @CsvBindByPosition(position = 0)
    private String author;
    @CsvBindByPosition(position = 1)
    private String presenter;
    @CsvBindByPosition(position = 2)
    private String title;
    @CsvBindByPosition(position = 3)
    private String keyword;

}
