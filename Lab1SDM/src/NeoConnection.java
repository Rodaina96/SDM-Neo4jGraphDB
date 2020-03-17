import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import static org.neo4j.driver.Values.parameters;

public class NeoConnection implements AutoCloseable
{
    private final Driver driver;

    public NeoConnection( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }
    
    

    public Driver getDriver() {
		return driver;
	}


	@Override
    public void close() throws Exception
    {
        driver.close();
    }

}