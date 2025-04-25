package eeit.OldProject.daniel.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"post"})
@Entity
@Table(name = "post_image", schema = "final")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageId")
    private Long imageId;

    @Lob
    @Column(name = "ImageData", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Column(name = "UploadedAt")
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "PostId")
    @JsonIgnoreProperties("images")
    private Post post;
}
