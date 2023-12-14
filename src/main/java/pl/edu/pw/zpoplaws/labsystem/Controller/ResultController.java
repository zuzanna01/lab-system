package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Service.ResultService;

@RestController
@RequestMapping("/result")
@AllArgsConstructor
public class ResultController {

    ResultService resultService;
    private final UserAuthenticationProvider userAuthProvider;

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
    public ResponseEntity<Page<ResultDto>> getResultsByPatient(@CookieValue(name="access_token") String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Pageable pageable = PageRequest.of(0,2);
        var id = userAuthProvider.getID(authToken);
        var list = resultService.getAllResultsByUser(id, pageable);
        return ResponseEntity.ok().headers(headers).body(list);
    }

}
