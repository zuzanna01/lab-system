package pl.edu.pw.zpoplaws.labsystem.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static pl.edu.pw.zpoplaws.labsystem.Utils.ResourceUtils.*;

@SpringBootTest
@Import(ResultServiceImpl.class)
@EnableAutoConfiguration
class ResultServiceImplTest {

    @Autowired
    ResultService resultService;

    @Test
    public void convertToPdf(){
       // String xml = getResourceAsString("laboratoryResults\\result3.xml");
        var result = resultService.getTest();
            System.out.print(result);

    }
    
}