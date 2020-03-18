
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Session;
import org.neo4j.driver.Value;
import org.neo4j.driver.Result;

public class Recommender {

	String[] keywords = { "Data management", "Indexing", "Data modeling", "Big Data", "Data processing", "Data storage",
			"Data querying" };

	public static Result runQ(String q) throws Exception {
		Result result;
		QResult qr = new QResult();
//		 try ( NeoConnection con = new NeoConnection( "bolt://localhost:7687", "neo4j", "neo4j" ) )
//	        {
		NeoConnection con = new NeoConnection("bolt://localhost:7687", "neo4j", "neo4j");
		Driver driver = con.getDriver();
		Session session = driver.session();
		result = session.run(q);
//	            try ( Session session = driver.session() )
//		        {
//	            	result = session.run(q);
//	            	
//	            	 while ( result.hasNext() ) {
//	       	   		  Record row = result.next();
//	       	   		  Value x = row.get(title);
//	       	   		  List<Object> y = x.asList();
//	       	   		  qr.add(y);
//
//	       	   	 }
//		            
//		        }
//	        }
		return result;
	}

	public static void main(String... args) throws Exception {
		QueryRunner runer = new QueryRunner();
		// first we create a new node to have the DB Community in our graph
		String createNode = "CREATE (community:Community{name: \"Database\"})";
		// runer.runQ(q2);

		// we find the list of articles in the database community
		String q1 = "Match(a:Articles)-[h:has]->(t:Topic)-[o:of_keys]->(k:Keywords)\r\n"
				+ "Where k.KeywordName IN [\"Data management\",\r\n" + "			\"Indexing\", \r\n"
				+ "			\"Data modeling\",\r\n" + "			\"Big Data\",\r\n"
				+ "			\"Data processing\",\r\n" + "            \"Data storage\",\r\n"
				+ "            \"Data querying\"]\r\n" + "return collect(distinct a.article_id) as DBCommunity";

		Result r = runQ(q1);
		Value dbArticles = r.next().get("DBCommunity");

		// create an edge between articles and keywords for this DB community

		String q2 = "Match(a:Articles)-[h:has]->(t:Topic)-[o:of_keys]->(k:Keywords)\r\n"
				+ "Where k.KeywordName IN [\"Data management\",\r\n" + "			\"Indexing\", \r\n"
				+ "			\"Data modeling\",\r\n" + "			\"Big Data\",\r\n"
				+ "			\"Data processing\",\r\n" + "            \"Data storage\",\r\n"
				+ "            \"Data querying\"]\r\n" + "CREATE (a)-[r:dbkey]->(k)\r\n" + "RETURN type(r)";

		// runer.runQ(q2);

		// create an edge between articles and DB community node
		String edgeAC = "Match (a:Articles)\r\n" + "		 where a.article_id IN" + dbArticles
				+ "		 CREATE (a)-[r:ainComm]->(c:Community)";

		// runer.runQ(edgeAC);

		// After getting the results of q1, we will get all those conferences
		// that have 90% papers belonging to the DB community

		String ar_conf = "Match (ar:Articles)<-[pb:published_p]-(p:Proceedings)-[is:is_c]->(c1:Conferences)\r\n"
				+ "Return c1.confID as confID, count(distinct ar.article_id) as totalArConf Order by c1.confID";

		Result aConf = runQ(ar_conf);

		HashMap<Integer, Integer> total = new HashMap();

		while (aConf.hasNext()) {
			Record tot = aConf.next();
//			System.out.println(tot);
			total.put(tot.get("confID").asInt(), tot.get("totalArConf").asInt());
		}

		String db_ar_conf = "MATCH (cm:Community)<-[acm:ainComm]-(ar2:Articles)<-[:published_p]-(:Proceedings)-[:is_c]->(c:Conferences) \r\n"
				+ "with c,count(distinct ar2.article_id) As ndDBArticles\r\n"
				+ "RETURN c.confID as confID, ndDBArticles Order by c.confID";

		Result db_aConf = runQ(db_ar_conf);
		HashMap<Integer, Integer> dbCount = new HashMap();

		while (db_aConf.hasNext()) {
			Record tot = db_aConf.next();
//			System.out.println(tot);
			//System.out.println(tot.get("confID")+"   "+tot.get("ndDBArticles"));
			dbCount.put(tot.get("confID").asInt(),
					tot.get("ndDBArticles") == null ? 0 : tot.get("ndDBArticles").asInt());
		}
		String dbConfs = "";
		for (Integer key : total.keySet()) {
			int db=dbCount.get(key)==null?0:dbCount.get(key);
			float ratio = (float)db/total.get(key);
			if (ratio >= 0.5)
					{
						dbConfs+=","+key;
					}
			
		}
		dbConfs = dbConfs.substring(1, dbConfs.length()-1);
		//System.out.println(dbConfs);
		// create edge from conf to db community node

		String edgeCC = "Match (c:Conferences)\r\n" + 
				"where c.confID IN ["+dbConfs+"]\r\n" + 
				"CREATE (c)-[r:cinComm]->(cm:Community)";

		 runer.runQ(edgeCC);

		// same for journals find dbJounals and create edge

//		Result r1 = runQ(conf_article);
//		while(r1.hasNext()) {
//			  Record row = result.next();
////   	   		  Value x = row.get(title);
////   	   		  List<Object> y = x.asList();
////   	   		  qr.add(y);
//
//		}

	}

}
