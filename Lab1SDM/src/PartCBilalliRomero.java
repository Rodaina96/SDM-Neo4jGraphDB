
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;


public class PartCBilalliRomero {
	
	
	
	public PartCBilalliRomero() {
	}



	static Result result;

	public static void runQ(String q) throws Exception
	{
		 try ( NeoConnection con = new NeoConnection( "bolt://localhost:7687", "neo4j", "neo4j" ) )
	        {
	            Driver driver = con.getDriver();
	            try ( Session session = driver.session() )
		        {
					 result = session.run(q);
	            	
	            	 while ( result.hasNext() ) {
	            		  Record row = result.next();
	            		 System.out.println(row);
	                     
	            	 }
		            
		        }
	        }
	}
	


	public static void main( String... args ) throws Exception
	    {
		
		 String alg1 = "CALL algo.pageRank.stream('Articles', 'cited', {iterations:20, dampingFactor:0.85})\r\n" + 
			 		"YIELD nodeId, score\r\n" + 
			 		"RETURN algo.asNode(nodeId).sup AS page,score\r\n" + 
			 		"ORDER BY score DESC\r\n" + 
			 		"limit 10";
		 
		 String alg2 = "CALL algo.louvain.stream('Articles','cited',{direction:'outgoing'})"
		 		+ " Yield nodeId, community, communities"
		 		+ " Return algo.asNode(nodeId).sup as title, community"
		 		+ " Order by community DESC";
		 
		 runQ(alg2);
	       
	    }

}
