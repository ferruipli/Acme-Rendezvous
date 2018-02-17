package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	/** Level A **/
	@Query("")
	Double[] avgSqrtRepliesPerComment();
}
