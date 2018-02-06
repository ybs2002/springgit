/**
 * 
 */
package spring.model.board;

import java.util.List;
import java.util.Map;

/**
 * @author user
 *
 */
public interface IBoardDAO {
	public List<BoardDTO> getList(Map<String, String> map);
	int getTotal(Map<String, String> map);
	public boolean write(BoardDTO dto);
	

}
