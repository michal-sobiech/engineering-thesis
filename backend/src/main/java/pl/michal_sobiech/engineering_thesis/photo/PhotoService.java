package pl.michal_sobiech.engineering_thesis.photo;

import java.sql.Blob;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Photo createPhoto(Blob file, String fileName) {
        Photo photo = Photo.builder()
                .file(file)
                .fileName(fileName)
                .build();
        return photoRepository.save(photo);
    }

    public Optional<Photo> findById(long photoId) {
        return this.photoRepository.findById(photoId);
    }

}
