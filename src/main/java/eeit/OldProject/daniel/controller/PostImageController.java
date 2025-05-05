package eeit.OldProject.daniel.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eeit.OldProject.daniel.entity.post.PostImage;
import eeit.OldProject.daniel.service.PostImageService;

@RestController
@RequestMapping("/api/posts/{postId}/images")
public class PostImageController {
	
	@Autowired
	private PostImageService imageService;

	@GetMapping
	public List<PostImage> list(@PathVariable Long postId) {
		return imageService.findByPostId(postId);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<PostImage>> create(
			@PathVariable Long postId,
			@RequestParam("files") List<MultipartFile> files) throws IOException {
		for (MultipartFile file : files) {
			byte[] data = file.getBytes();
			imageService.save(data, postId);
		}
		return ResponseEntity.ok(imageService.findByPostId(postId));
	}

    // 刪除單張圖片
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long postId,
            @PathVariable Long imageId) {
        if(imageService.deleteImage(imageId)) {
			return ResponseEntity.noContent().build();        	
        }
		return ResponseEntity.notFound().build();
    }
}
