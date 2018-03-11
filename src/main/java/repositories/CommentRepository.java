package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	/** Level A **/
	@Query("select avg(c.descendantComments.size), sqrt(sum(c.descendantComments.size*c.descendantComments.size)/count(c.descendantComments.size)-avg(c.descendantComments.size)*avg(c.descendantComments.size)) from Comment c")
	Double[] avgSqrtRepliesPerComment();
	
	@Query("select c from Comment c join c.descendantComments re where re.id = ?1 and re member of c.descendantComments")
	Comment findCommentByReplyCommentId(int replyId);
}
