package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	/** Level A **/
	@Query("select avg(c.repliedComments.size), sqrt(sum(c.repliedComments.size*c.repliedComments.size)/count(c.repliedComments.size)-avg(c.repliedComments.size)*avg(c.repliedComments.size)) from Comment c")
	Double[] avgSqrtRepliesPerComment();
}
