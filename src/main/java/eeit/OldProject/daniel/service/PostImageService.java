package eeit.OldProject.daniel.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.Post;
import eeit.OldProject.daniel.entity.PostImage;
import eeit.OldProject.daniel.repository.PostImageRepository;

@Service
@Transactional
public class PostImageService {

	@Autowired
	private PostImageRepository imageRepo;

	public List<PostImage> findByPostId(Long postId) {
		return imageRepo.findByPostPostId(postId);
	}

	public PostImage save(byte[] data, Long postId) {
		PostImage img = new PostImage();
		img.setImageData(data);
		img.setUploadedAt(LocalDateTime.now());
		img.setPost(new Post() {
			{
				setPostId(postId);
			}
		});
		return imageRepo.save(img);
	}

	public boolean deleteImage(Long imageId) {
		if (imageId != null) {
			if (imageRepo.existsById(imageId)) {
				imageRepo.deleteById(imageId);
				return true;
			}
		}
		return false;
	}
}
