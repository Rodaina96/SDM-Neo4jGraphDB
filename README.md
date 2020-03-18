# SDM-Neo4jGraphDB

## Ledia Isaj
## Rodaina Mohamed

The original data was obtained here: https://dblp.org/xml/release/ (xml format)
Using https://github.com/ThomHurks/dblp-to-csv/blob/master/XMLToCSV.py (XMLToCSV.py) we converted the xml file to CSV files for each node and relationship.
After that we needed to process the data to make it consistent, fit our schema and purposes, and ready to load into neo4j. (since this is for academic purposes and the main focus is not real research, 
we have used this data combined with autogenerated and/or transformed data to fit our purposes.)

The final data you can find in google drive https://drive.google.com/file/d/1WvcWbSbNyyxRfc-7x2bOLc0PyFpclpPp/view .
In import/GraphDB folder is the data used for initial bulk load.
In import is the data used in exercise A.3 for evolving the graph.


In order to load the data into neo4j, we benefited from neo4j bulk import, which for first load is very useful and fast (compared to other approaches).
Below you can find the code for the initial load:

bin\neo4j-admin import --id-type=INTEGER  
--nodes:Authors import/GraphDB/authors.csv 
--nodes:Articles import/GraphDB/articles.csv 
--nodes:Journals import/GraphDB/journals.csv 
--nodes:Proceedings import/GraphDB/proceedins.csv 
--nodes:Workshops import/GraphDB/workshops.csv  
--nodes:Conferences import/GraphDB/conferences.csv 
--nodes:Topic import/GraphDB/topics.csv 
--nodes:Keywords import/GraphDB/keywords.csv 
--relationships:responsible_for import/GraphDB/Article_Author.csv 
--relationships:write import/GraphDB/author_authored_by.csv 
--relationships:published import/GraphDB/article-journal.csv 
--relationships:published_p import/GraphDB/article-proceeding.csv 
--relationships:is_w import/GraphDB/proceedings_workshops.csv 
--relationships:is_c import/GraphDB/proceedings_conferences.csv 
--relationships:has import/GraphDB/articles_topics.csv 
--relationships:of_keys import/GraphDB/topics_keywords.csv 
--relationships:cited import/GraphDB/citation.csv 
--relationships:reviewed import/GraphDB/reviewer1.csv
--relationships:reviewed import/GraphDB/reviewer2.csv 
--relationships:reviewed import/GraphDB/reviewer3.csv 
--ignore-missing-nodes=true
