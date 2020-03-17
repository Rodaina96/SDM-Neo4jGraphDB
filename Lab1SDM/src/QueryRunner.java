import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

public class QueryRunner {
		
	 public static void main( String... args ) throws Exception
	    {
	        try ( NeoConnection con = new NeoConnection( "bolt://localhost:7687", "neo4j", "neo4j" ) )
	        {
	            Driver driver = con.getDriver();
	            try ( Session session = driver.session() )
		        {
	            	Result result = session.run("Match (a:Articles)\r\n" + 
		                    		"return a.article_id\r\n" + 
		                    		"Limit 10");
	            	
	            	 while ( result.hasNext() ) {
	            		 Map<String,Object> row= result.next();
	            		 System.out.println(result.next());
	            	 }
	            	
//	            	for (int i = 0; i < result.l; i++) {
//                        System.out.println(result.single().get(i));
//                    }
	            	
//		            Result trx = session.writeTransaction( new TransactionWork<Result>()
//		            {
//		                @Override
//		                public Result execute( Transaction tx )
//		                {
//		                    Result result = tx.execute( "Match (a:Articles)\r\n" + 
//		                    		"return a.article_id\r\n" + 
//		                    		"Limit 10" );
//		                    
//		                    for (int i = 0; i < result.single().size(); i++) {
//		                        System.out.println(result.single().get(i));
//		                    }
//		             
//		                    return result;
//		                }
//		            } );
		            
		        }
	        }
	    }

	

	
}
