package eeit.OldProject.daniel.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.topic.PostTopic;
import eeit.OldProject.daniel.entity.post.topic.PostTopicClassifier;
import eeit.OldProject.daniel.repository.post.topic.PostTopicClassifierRepository;
import eeit.OldProject.daniel.repository.post.topic.PostTopicRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PostTopicService {

	@Autowired
	private PostTopicRepository topicRepo;
	@Autowired
	private PostTopicClassifierRepository classifierRepo;

	// 取得所有主題
	public List<PostTopic> findAllTopics() {
		return topicRepo.findAll();
	}

	// 新增主題
	public PostTopic createTopic(PostTopic topic) {
		return topicRepo.save(topic);
	}

	// 指派貼文多個主題
	public List<PostTopicClassifier> assignTopics(Long postId, List<Long> topicIds) {
		List<PostTopicClassifier> classifiers = topicIds.stream()
				.map(topicId -> PostTopicClassifier.builder().post(new Post() {
					{
						setPostId(postId);
					}
				}).postTopic(new PostTopic() {
					{
						setPostTopicId(topicId);
					}
				}).build()).collect(Collectors.toList());
		return classifierRepo.saveAll(classifiers);
	}

	// 指派貼文至主題
	public PostTopicClassifier assignTopic(Long postId, Long topicId) {
		PostTopicClassifier pt = PostTopicClassifier.builder().post(new Post() {
			{
				setPostId(postId);
			}
		}).postTopic(new PostTopic() {
			{
				setPostTopicId(topicId);
			}
		}).build();
		return classifierRepo.save(pt);
	}

	// 移除貼文與主題對應
	public void removeAssignment(Long classifierId) {
		classifierRepo.deleteById(classifierId);
	}
	
	// 同步貼文主題
	public void syncPostTopics(Post post, List<Long> newTopicIds) {
	    Map<Long, PostTopicClassifier> oldMap = post.getPostTopicClassifiers().stream()
	        .collect(Collectors.toMap(
	            c -> c.getPostTopic().getPostTopicId(),
	            Function.identity()
	        ));

	    Set<Long> toAdd = new HashSet<>(newTopicIds);
	    toAdd.removeAll(oldMap.keySet());

	    Set<Long> toRemove = new HashSet<>(oldMap.keySet());
	    toRemove.removeAll(newTopicIds);

	    // 刪除不再屬於的主題
	    toRemove.forEach(topicId ->
	        post.removeTopic(oldMap.get(topicId))
	    );

	    // 新增新的主題
	    toAdd.forEach(topicId -> {
	        PostTopic topic = topicRepo.findById(topicId)
	            .orElseThrow(() -> new EntityNotFoundException("Topic not found: " + topicId));
	        post.addTopic(topic);
	    });
	}
}