package de.weichand.deegree;

import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import org.deegree.commons.xml.stax.IndentingXMLStreamWriter;
import org.deegree.cs.persistence.CRSManager;
import org.deegree.cs.refs.coordinatesystem.CRSRef;
import org.deegree.feature.persistence.sql.GeometryStorageParams;
import org.deegree.feature.persistence.sql.MappedAppSchema;
import org.deegree.feature.persistence.sql.config.SQLFeatureStoreConfigWriter;
import org.deegree.feature.persistence.sql.ddl.DDLCreator;
import org.deegree.feature.persistence.sql.mapper.AppSchemaMapper;
import org.deegree.feature.types.AppSchema;
import static org.deegree.feature.types.property.GeometryPropertyType.CoordinateDimension.DIM_2;
import org.deegree.gml.schema.GMLAppSchemaReader;
import org.deegree.sqldialect.SQLDialect;
import org.deegree.sqldialect.postgis.PostGISDialect;

/**
 * CLI utility
 * 
 * @author Juergen Weichand
 */
public class Exec {
    
    private static String format = "deegree";
    private static int srid = 4258;

    public static void main(String[] args) throws Exception 
    {
        
        if (args.length == 0) 
        {
            System.out.println("Usage: java -jar deegree-cli-utility.jar [options] schema_url");
            System.out.println("");
            System.out.println("options:");
            System.out.println(" --format={deegree/ddl}");
            System.out.println(" --srid=<epsg_code>");
            return;
        }
        
        String schemaUrl = "";
        for (String arg : args) 
        {
            if (arg.startsWith("--format")) 
            {
                format = arg.split("=")[1];
            }
            else if (arg.startsWith("--srid")) 
            {
                srid = Integer.valueOf(arg.split("=")[1]);
            }
            else 
            {
                schemaUrl = arg;
            }
        }
        
        String[] schemaUrls = { schemaUrl };
        GMLAppSchemaReader xsdDecoder = new GMLAppSchemaReader(null, null, schemaUrls);
        AppSchema appSchema = xsdDecoder.extractAppSchema();
        
        CRSRef storageCrs = CRSManager.getCRSRef( "EPSG:" + String.valueOf(srid) );
        GeometryStorageParams geometryParams = new GeometryStorageParams( storageCrs, String.valueOf(srid), DIM_2 );
        AppSchemaMapper mapper = new AppSchemaMapper( appSchema, false, true, geometryParams, 64, true, false );
        MappedAppSchema mappedSchema = mapper.getMappedSchema();
        SQLFeatureStoreConfigWriter configWriter = new SQLFeatureStoreConfigWriter( mappedSchema );

        if (format.equals("deegree")) 
        {
            XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter( System.out );
            xmlWriter = new IndentingXMLStreamWriter( xmlWriter );

            List<String> configUrls = Arrays.asList(schemaUrls);
            configWriter.writeConfig( xmlWriter, "JDBC", configUrls );
            xmlWriter.close();
        }
        else 
        {
            SQLDialect dialect = new PostGISDialect("2.0.0");
            String[] createStmts = DDLCreator.newInstance( mappedSchema, dialect).getDDL();
            System.out.println(Arrays.asList(createStmts));               
        }    
  }
}
