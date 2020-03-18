import java.util.ArrayList;
import java.util.List;

public class QResult {

	List<List> list ;

	public QResult() {
		this.list = new ArrayList<List>();
	}

	public List<List> getList() {
		return list;
	}

	public void setList(List<List> list) {
		this.list = list;
	}

	public void add(List<Object> y) {
		this.list.add(y);
		
	}
	
	

}
