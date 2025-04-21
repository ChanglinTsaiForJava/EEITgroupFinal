package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;

	@Lob
	@Column(name = "ImageData", columnDefinition = "LONGBLOB")
	private byte[] imageData;

	@Column(name = "UploadedAt")
	private LocalDateTime uploadedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PostId")
	private Post post;
	
	
}