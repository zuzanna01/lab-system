package pl.edu.pw.zpoplaws.labsystem.Controller;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.zpoplaws.labsystem.Config.UserAuthenticationProvider;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultDto;
import pl.edu.pw.zpoplaws.labsystem.Dto.ResultPatientInfo;
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
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<ResultDto> addResult( @CookieValue(name = "access_token") String token,
                                                @RequestParam("resultId") String resultId,
                                                @RequestBody String xmlFile) {
        var userId = new ObjectId(userAuthProvider.getID(token));
        var body = managementService.uploadResult(new ObjectId(resultId), xmlFile, userId );
        return  ResponseEntity.ok().body(body);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PATIENT')")
    public ResponseEntity<byte[]> getResultPdf(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.pdf");
        var body= resultService.getResultPdf(new ObjectId(id));
        return ResponseEntity.ok().headers(headers).body(body);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<ResultDto>> getWaitingResultsByLab(String labId, Pageable pageable) {
        var body = resultService.getAllWaitingResultsByLab( new ObjectId(labId), pageable);
        return  ResponseEntity.ok(body);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<ResultPatientInfo>> getResultsByPatient(@CookieValue(name="access_token") String authToken, Pageable pageable) {
        var patientId = new ObjectId(userAuthProvider.getID(authToken));
        var body = resultService.getResultsByPatient(patientId, pageable);
        return ResponseEntity.ok(body);
    }




}
