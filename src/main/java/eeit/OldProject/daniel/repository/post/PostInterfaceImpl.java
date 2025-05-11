package eeit.OldProject.daniel.repository.post;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.ast.tree.expression.Distinct;
import org.springframework.stereotype.Repository;

import eeit.OldProject.daniel.dto.PostFilter;
import eeit.OldProject.daniel.entity.post.Post;
import eeit.OldProject.daniel.entity.post.category.PostCategoryClassifier;
import eeit.OldProject.daniel.entity.post.tag.PostTag;
import eeit.OldProject.daniel.entity.post.topic.PostTopicClassifier;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PostInterfaceImpl implements PostInterface {

	@PersistenceContext
	EntityManager entityManager;

//	public long count(JSONObject obj) {
//		return 0L;
//	}

	// select * from post where ... order by ...
	@Override
	public List<Post> searchPosts(PostFilter filter) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);

		// select * from post
		Root<Post> post = criteriaQuery.from(Post.class);

		// 加入各種判斷條件
		List<Predicate> predicates = new ArrayList<>();
		// postId = ?
		if (filter.getPostId() != null) {
			predicates.add(criteriaBuilder.equal(post.get("postId"), filter.getPostId()));
		}

		// title like %?%
		if (filter.getTitleKeyword() != null) {
			predicates.add(criteriaBuilder.like(post.get("title"), "%" + filter.getTitleKeyword() + "%"));
		}

		// content like %?%
		if (filter.getContentKeyword() != null) {
			predicates.add(criteriaBuilder.like(post.get("content"), "%" + filter.getContentKeyword() + "%"));
		}

		// createdAt >= ?
		if (filter.getStartTime() != null) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(post.get("createdAt"), filter.getStartTime()));
		}

		// created <= ?
		if (filter.getEndTime() != null) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(post.get("createdAt"), filter.getEndTime()));
		}

		// userId = ?
		if (filter.getUserId() != null) {
			predicates.add(criteriaBuilder.equal(post.get("user").get("userId"), filter.getUserId()));
		}

		List<Predicate> classifierPreds = new ArrayList<>();

		// postCategoryId in ?
		if (filter.getPostCategoryIds() != null && !filter.getPostCategoryIds().isEmpty()) {
			Join<Post, PostCategoryClassifier> catJoin = post.join("postCategoryClassifiers", JoinType.LEFT);
			classifierPreds.add(catJoin.get("postCategory").get("postCategoryId").in(filter.getPostCategoryIds()));
		}

		// postTopicId in ?
		if (filter.getPostTopicIds() != null && !filter.getPostTopicIds().isEmpty()) {
			Join<Post, PostTopicClassifier> topJoin = post.join("postTopicClassifiers", JoinType.LEFT);
			classifierPreds.add(topJoin.get("postTopic").get("postTopicId").in(filter.getPostTopicIds()));
		}

		// postTagId in ?
		if (filter.getPostTagIds() != null && !filter.getPostTagIds().isEmpty()) {
			Join<Post, PostTag> tagJoin = post.join("postTags", JoinType.LEFT);
			classifierPreds.add(tagJoin.get("tag").get("tagId").in(filter.getPostTagIds()));
		}

		// 如果有任何一組 classifier 條件，改用 OR 串起來，再加入主 predicates
		if (!classifierPreds.isEmpty()) {
			predicates.add(criteriaBuilder.or(classifierPreds.toArray(new Predicate[0])));
		}

		// 將查詢結果投影到自訂 DTO
//        criteriaQuery.select(criteriaBuilder.construct(
//                PostDto.class,
//                post.get("postId"), post.get("title"), post.get("content"), post.get("createdAt"),
//                post.get("views"), post.get("share"), post.get("user").get("userId"),
//                criteriaBuilder.nullLiteral(Long.class), criteriaBuilder.nullLiteral(Long.class), criteriaBuilder.nullLiteral(Long.class)
//            ));

		// where ...
		if (predicates != null && !predicates.isEmpty()) {
			Predicate[] array = predicates.toArray(new Predicate[0]);
			criteriaQuery = criteriaQuery.where(array)
										 .distinct(true);
		}

		if (filter.getSort() != null) {
			Path<?> path = post.get(filter.getSort());
			criteriaQuery.orderBy(
					"desc".equalsIgnoreCase(filter.getDir()) ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path));
		} else {
			Path<?> defaultPath = post.get("createdAt");
			criteriaQuery.orderBy(criteriaBuilder.desc(defaultPath));
		}

		TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);

		if (filter.getStart() != null)
			typedQuery.setFirstResult(filter.getStart());
		if (filter.getRows() != null)
			typedQuery.setMaxResults(filter.getRows());

		return typedQuery.getResultList();
	}

}