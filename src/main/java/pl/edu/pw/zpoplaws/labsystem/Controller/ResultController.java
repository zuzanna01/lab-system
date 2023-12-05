package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Service.ResultService;

import java.util.List;

@RestController
@RequestMapping("/result")
@AllArgsConstructor
public class ResultController {

    ResultService resultService;

    @PostMapping()
    public boolean addResult(@RequestBody String file) {
        return resultService.saveToDatabase(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getResult(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.pdf");

        var body= resultService.getResult(id);
        return ResponseEntity.ok()
                .headers(headers)
                .body(body);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ResultDto>> getName(@RequestHeader("Authorization") String authToken) {
     return  null;
    }

}
