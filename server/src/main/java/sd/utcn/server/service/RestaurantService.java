package sd.utcn.server.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.NewRestaurantDto;
import sd.utcn.server.dto.RestaurantDto;
import sd.utcn.server.mapper.RestaurantMapper;
import sd.utcn.server.repository.AdminRepository;
import sd.utcn.server.repository.RestaurantRepository;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, AdminRepository adminRepository) {
        this.restaurantRepository = restaurantRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * Get all restaurants
     *
     * @return
     */
    public List<RestaurantDto> getAll() {
        return restaurantRepository.findAll().stream().map(RestaurantMapper::ToDto).toList();
    }

    /**
     * Get a restaurant by its id or throw an exception if the restaurant does not exist
     *
     * @param id
     * @return
     * @throws Exception
     */
    public RestaurantDto getById(Long id) throws Exception {
        var r = restaurantRepository.findById(id);
        if (r.isEmpty()) {
            log.error("Nonexistent restaurant with id {}", id);
            throw new Exception("Nonexistent restaurant");
        }
        return RestaurantMapper.ToDto(r.get());
    }

    /**
     * Gets a restaurant by id and returns a byte array representing the menu for that given restaurant
     *
     * @param dto
     * @return
     * @throws Exception
     */
    public ByteArrayOutputStream getMenuPdf(RestaurantDto dto) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();

        PdfPTable table = new PdfPTable(4);
        Stream.of("name", "description", "price", "category")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
        dto.getFoods().forEach(e -> {
            table.addCell(e.getName());
            table.addCell(e.getDescription());
            table.addCell(e.getPrice().toString());
            table.addCell(e.getCategory().toString());
        });
        document.add(table);
        document.close();
        return byteArrayOutputStream;
    }

    /**
     * Tries to add a restaurant if the input data is valid
     *
     * @param newRestaurantDto {    private String name;
     *                         private String location;
     *                         private Long adminId;}
     * @return
     * @throws Exception
     */
    @Transactional
    public RestaurantDto add(NewRestaurantDto newRestaurantDto) throws Exception {
        if (newRestaurantDto.getName().isEmpty() || newRestaurantDto.getLocation().isEmpty()) {
            log.error("Couldn't add restaurant: invalid restaurant data");
            throw new Exception("invalid restaurant data");
        }
        var admin = adminRepository.findById(newRestaurantDto.getAdminId());
        if (admin.isEmpty()) {
            log.error("Nonexistent admin");
            throw new Exception("Nonexistent admin");
        }

        var entity = RestaurantMapper.ToEntity(newRestaurantDto);
        entity.setAdmin(admin.get());
        var r = restaurantRepository.save(entity);
        admin.get().addRestaurant(r);
        return RestaurantMapper.ToDto(r);
    }
}
