package pl.michal_sobiech.engineering_thesis.photo;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Transactional
    public Photo createPhoto(byte[] bytes, String fileName) {
        Photo photo = Photo.builder()
                .bytes(bytes)
                .fileName(fileName)
                .build();
        return photoRepository.save(photo);
    }

    @Transactional
    public Photo createPhoto(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            return createPhoto(file.getBytes(), fileName);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create photo");
        }
    }

    public Optional<Photo> findById(long photoId) {
        return this.photoRepository.findById(photoId);
    }

}
