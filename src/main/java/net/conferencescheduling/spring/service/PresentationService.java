package net.conferencescheduling.spring.service;

import com.opencsv.bean.*;
import net.conferencescheduling.spring.model.dto.ConstraintDto;
import net.conferencescheduling.spring.model.dto.PaperDto;
import net.conferencescheduling.spring.model.dto.PresentationDto;
import net.conferencescheduling.spring.model.entity.Paper;
import net.conferencescheduling.spring.model.entity.Presentation;
import net.conferencescheduling.spring.repository.PresentationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

@Service
public class PresentationService {

    private final PresentationRepository presentationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConstraintService constraintService;

    @Autowired
    private PaperService paperService;

    @Autowired
    public PresentationService(PresentationRepository presentationRepository) {
        this.presentationRepository = presentationRepository;
    }

    public List<PresentationDto> getAllPresentations() {
        final String csv_location = "presentation.csv";
        try {
            FileWriter writer = new
                    FileWriter(csv_location);
            List<ConstraintDto> constraints = constraintService.getAllConstraints();

            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(ConstraintDto.class);

            String[] columns = new String[]
                    {"dayNo", "parallelSessionCount", "sessionCount", "startTime",
                            "endTime", "sessionDuration", "presentationDuration"};
            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<ConstraintDto> builder =
                    new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter =
                    builder.withMappingStrategy(mappingStrategy).build();

            beanWriter.write(constraints);

            //TODO CALL PYTHON SCRIPT and write to new csv

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO ------------------------------------------------------------------------

        final String papers_location = "papers.csv";
        try {
            FileWriter writer = new
                    FileWriter(papers_location);
            List<PaperDto> papers = paperService.getAllPapers().stream().map(paper -> modelMapper.map(paper, PaperDto.class)).toList();

            ColumnPositionMappingStrategy mapping =
                    new ColumnPositionMappingStrategy();
            mapping.setType(PaperDto.class);

            StatefulBeanToCsvBuilder<ConstraintDto> builder =
                    new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter =
                    builder.build();

            beanWriter.write(papers);

            //TODO CALL PYTHON SCRIPT and write to new csv

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



//        Map<String, String> columnMappings = Map.of(
//                "dayNo", "dayNo",
//                "sessionNo", "sessionNo",
//                "start_time", "start_time",
//                "end_time", "end_time",
//                "roomNo", "roomNo"
//        );

//        HeaderColumnNameTranslateMappingStrategy mappingStrategy =
//                new HeaderColumnNameTranslateMappingStrategy();
//        mappingStrategy.setColumnMapping(columnMappings);
//        mappingStrategy.setType(PresentationDto.class);


        CsvToBean csvReader = null;
        try {
            csvReader = new CsvToBeanBuilder(new FileReader("result.csv"))
                    .withType(PresentationDto.class)
                    //.withMappingStrategy(mappingStrategy)
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<PresentationDto> results = csvReader.parse();
        System.out.println(results);

        //Stream<PresentationDto> stream = csvReader.stream().map(presentation -> modelMapper.map(presentation, PresentationDto.class));

        //presentationRepository.save(stream);
        return results;
        //return presentationRepository.findAll();
    }

    public Presentation createPresentation(Presentation presentation) {

        return presentationRepository.save(presentation);
    }

    public Presentation updatePresentation(Long id, Presentation presentationRequest) {
        Presentation presentation = presentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No valid presentation!") );

        presentation.setRoomNo(presentationRequest.getRoomNo());
        presentation.setSessionNo(presentationRequest.getSessionNo());
        presentation.setDayNo(presentationRequest.getDayNo());
        presentation.setStart_time(presentationRequest.getStart_time());
        presentation.setEnd_time(presentationRequest.getEnd_time());
        presentation.setPaper(presentationRequest.getPaper());
        return presentationRepository.save(presentation);
    }

    public Presentation getPresentationById(Long id) {
        Optional<Presentation> result = presentationRepository.findById(id);
        if(result.isPresent()) {
            return result.get();
        }else {
            throw new RuntimeException("No valid presentation!");
        }

    }

}