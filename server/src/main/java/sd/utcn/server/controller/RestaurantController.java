package sd.utcn.server.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sd.utcn.server.dto.NewRestaurantDto;
import sd.utcn.server.dto.RestaurantDto;
import sd.utcn.server.service.RestaurantService;
import sd.utcn.server.viewmodels.NewRestaurantViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Log4j2
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAll(Authentication authentication) {
        log.info("Getting all restaurants");
        return new ResponseEntity<>(restaurantService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getById(Authentication authentication, @PathVariable("id") Long id) {
        try {
            log.info("Trying to get restaurant with id {}", id);
            var dto = restaurantService.getById(id);
            var output = restaurantService.getMenuPdf(dto);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE));
            ContentDisposition disposition = ContentDisposition
                    .inline()
                    .filename(dto.getName() + ".pdf")
                    .build();
            headers.setContentDisposition(disposition);
            return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> add(Authentication authentication, @RequestBody NewRestaurantViewModel newRestaurantViewModel) {
        try {
            log.info("Creating a new restaurant for the owner with id {}", authentication.getName());
            var dto = new NewRestaurantDto(newRestaurantViewModel.getName(), newRestaurantViewModel.getLocation(), Long.parseLong(authentication.getName()));
            var res = restaurantService.add(dto);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
