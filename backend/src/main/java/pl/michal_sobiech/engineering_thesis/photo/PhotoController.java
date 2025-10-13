package pl.michal_sobiech.engineering_thesis.photo;

import org.SwaggerCodeGenExample.api.PhotosApi;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.utils.ByteUtils;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotosApi {

    private final PhotoService photoService;

    @Override
    public ResponseEntity<Resource> getPhoto(Long photoId) {
        Photo photo = photoService.findById(photoId).orElseThrow();
        Resource resource = ByteUtils.createResource(photo.getBytes());
        String contentDisposition = "inline; filename=\"%s\"".formatted(photo.getFileName());
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition")
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
