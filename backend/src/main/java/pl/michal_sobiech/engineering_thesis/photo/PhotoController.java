package pl.michal_sobiech.engineering_thesis.photo;

import java.util.Optional;

import org.SwaggerCodeGenExample.api.PhotosApi;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.utils.HttpUtils;

@RestController
@RequiredArgsConstructor
public class PhotoController implements PhotosApi {

    private final PhotoService photoService;

    @Override
    public ResponseEntity<Resource> getPhoto(Long photoId) {
        Optional<Photo> optionalPhoto = photoService.findById(photoId);
        if (optionalPhoto.isEmpty()) {
            return HttpUtils.createNotFoundReponse();
        }
        Photo photo = optionalPhoto.get();

        Resource resource;
        try {
            resource = new ByteArrayResource(photo.getFile().getBytes(1, (int) photo.getFile().length()));
        } catch (Exception exception) {
            return HttpUtils.createInternalServerErrorResponse();
        }

        return ResponseEntity.ok(resource);

    }
}
