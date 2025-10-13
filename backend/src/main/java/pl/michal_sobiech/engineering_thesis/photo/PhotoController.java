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
        Resource resource = ByteUtils.createResource(photo.getBlob());
        String contentDisposition = "inline; filename=\"%s\"".formatted(photo.getFileName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
