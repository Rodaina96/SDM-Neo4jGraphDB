
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;


public class PartBBilalliRomero {
	
	
	
	public PartBBilalliRomero() {
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
		 String q1 = "MATCH (a:Authors)-[w:write]->(ar:Articles)<-[c:cited]-(ar2:Articles)\r\n" + 
				"WITH a.author as Author, ar.article_id as Article_id,\r\n" + 
				"count(c) as nCitation\r\n" + 
				"ORDER BY Author, nCitation DESC\r\n" + 
				"WITH Author, collect(nCitation) as citation\r\n" + 
				"UNWIND range(1, size(citation)) as i\r\n" + 
				"WITH Author,\r\n" + 
				"CASE WHEN citation[i] <= (i)\r\n" + 
				"THEN citation[i]\r\n" + 
				"ELSE (i)\r\n" + 
				"END as article_index\r\n" + 
				"RETURN Author, max(article_index) as hIndex";
		 
		 String q2 = "Match (c:Conferences)<-[is:is_c]-(p:Proceedings)-[pb:published_p]->(a:Articles)<-[ct:cited]-(a2:Articles)\r\n" + 
		 		"With c.title as Conference, a.sup as Article, count(ct) as nbCitations\r\n" + 
		 		"Order By nbCitations DESC\r\n" + 
		 		"WITH Conference, collect(Article) as articles\r\n" + 
		 		"return Conference, articles[0] AS Top1 ,articles[1] as Top2,articles[3] as Top3";
		
		 String q3 = "MATCH (a:Authors)-[:write]->(:Articles)<-[:published_p]-(:Proceedings)-[ic:is_c]->(c:Conferences)\r\n" + 
		 		"WITH c.title as Conference, a.author as Author,\r\n" + 
		 		"count(distinct ic.year) as editions\r\n" + 
		 		"where editions>=4\r\n" + 
		 		"RETURN Conference, collect(Author) as ListAuthors\r\n" ;
		 
		 String q4 = "MATCH (ar2:Articles)-[c:cited]->(ar:Articles)-[p:published]->(j:Journals)\r\n" + 
		 		"WITH j.JournalName as JournalName, ar.article_id as article, p.volume as volume,count(ar2.article_id) as citation\r\n" + 
		 		"WHERE toFloat(volume) in [(toFloat(volume)-1), (toFloat(volume)-2)]\r\n" + 
		 		"RETURN JournalName,  sum(citation)/count(article)  as ImpactFactor\r\n" + 
		 		"ORDER BY ImpactFactor DESC";
		 
		
		 runQ(q1);
	       
	    }

}
