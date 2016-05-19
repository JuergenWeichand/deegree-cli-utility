package de.weichand.deegree;


import de.weichand.deegree.Exec;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author weich_ju
 */
public class InspireTest {
    
    final String SCHEMA_URL_CP = "http://inspire.ec.europa.eu/schemas/cp/4.0/CadastralParcels.xsd";
    
    public InspireTest() {
    }
    
  

    @Test
    public void schemaOnly() throws Exception 
    {
        String[] args = { SCHEMA_URL_CP };
        Exec.main(args);
    }
    
    @Test
    public void schemaToDeegreeConfig() throws Exception 
    {
        String[] args = { SCHEMA_URL_CP, "--format=deegree" };
        Exec.main(args);
    }
    
    @Test
    public void schemaToDeegreeConfigWithSrid() throws Exception 
    {
        String[] args = { SCHEMA_URL_CP, "--format=deegree","--srid=31468" };
        Exec.main(args);
    }
}
