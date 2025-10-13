package pl.michal_sobiech.engineering_thesis.photo;

import java.sql.Blob;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Transactional
    public Photo createPhoto(Blob blob, String fileName) {
        Photo photo = Photo.builder()
                .blob(blob)
                .fileName(fileName)
                .build();
        return photoRepository.save(photo);
    }

    @Transactional
    public Photo createPhoto(MultipartFile file) {
        try {
            Blob blob = new SerialBlob(file.getBytes());
            String fileName = file.getOriginalFilename();
            return createPhoto(blob, fileName);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to create photo");
        }
    }

    public Optional<Photo> findById(long photoId) {
        return this.photoRepository.findById(photoId);
    }

}
