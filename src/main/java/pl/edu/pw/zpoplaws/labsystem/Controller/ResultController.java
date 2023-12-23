package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Service.ManagementService;
import pl.edu.pw.zpoplaws.labsystem.Service.ResultService;

@RestController
@RequestMapping("/result")
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final ManagementService managementService;
    private final UserAuthenticationProvider userAuthProvider;

    @PostMapping()
    public ResponseEntity<ResultDto> addResult(@CookieValue(name="access_token") String token, String resultId, @RequestBody String xmlFile) {
        var userId = new ObjectId(userAuthProvider.getID(token));
        var body = managementService.uploadResult(new ObjectId(resultId), xmlFile, userId );
        return  ResponseEntity.ok().body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getResult(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.pdf");

        var body= resultService.getResult(new ObjectId(id));
        return ResponseEntity.ok().headers(headers).body(body);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ResultDto>> getResultsByPatient(@CookieValue(name="access_token") String authToken, Pageable pageable ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        var id = userAuthProvider.getID(authToken);
        var list = resultService.getAllResultsByUser(id, pageable);
        return ResponseEntity.ok().headers(headers).body(list);
    }

}
