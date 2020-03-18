
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;


public class Recommender {
	
	static Result result;
	String[] keywords = 
			{"Data Management",
			"Semantic Data", 
			"Data Modeling",
			"Big Data",
			"Data Processing",
            "Data Storage",
            "Data Querying"
			};

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
		//first we find the list of articles in the database community
		String q1 = "Match(a:Articles)-[h:has]->(t:Topic)-[o:of_keys]->(k:Keywords)\r\n" + 
				"Where k.KeywordName IN [\"Data Management\",\"Semantic Data\", \"Data Modeling\",\"Big Data\",\"Data Processing\",\"Data Storage\",\"Data Querying\"]\r\n" + 
				"return collect(distinct a.article_id) as DBCommunity";
		
		//create a path between articles and keywords for this DB community
		
		String q2 = "Match(a:Article)-[h:has]->(t:Topic)-[k:of_keys]->(k:Keywords)\r\n" + 
				"Where k.KeywordName IN [\"Data Management\",\"Semantic Data\", \"Data Modeling\",\"Big Data\",\"Data Processing\",\"Data Storage\",\"Data Querying\"]\r\n" + 
				"CREATE (a)-[r:dbkey]->(k)\r\n" + 
				"RETURN type(r)";
		
		//After pipelining the results of q1, we will get all those conferences 
		//that have papers belonging to the DB community
		
		String q3 ="";
		
		//same with journals
		
		String q4 = "";
		
		//from db conferences/journals 
		//get the articles with highest page rank (articles from the db community)
		
		String q5 = "";
		
		
		
		 runQ(q1);
	       
	    }

}
