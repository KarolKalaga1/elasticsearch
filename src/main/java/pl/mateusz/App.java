package pl.mateusz;

import org.openehr.am.archetype.ontology.QueryBindingItem;
import org.openehr.am.archetype.ontology.TermBindingItem;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.DummyTransportAddress;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import org.elasticsearch.index.query.SimpleQueryParser.Settings;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/*
TODO1:  Simple client app which will connect ot elasticsearch server/cluster. 
        App will be able to simple JSON document. Will be able to query on it.

TODO2:  Prepare example JSON document with the structure of some selected 
        archetype and store it

TODO3:  Prepare example on elastic search mapping a start to think about 
        mapping configuration for archetype structures.
 */
public class App 
{
    public static void main( String[] args )
    {
        try 
        { 
            Archetype
            //create node
            Node node = nodeBuilder().clusterName("Erasmus").node();
            
            //create transport client
            //Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
            Client client = node.client();
            
            XContentBuilder blood_pressure = XContentFactory.jsonBuilder()
                                            .startObject()
                                            .field("Systolic", 100)
                                            .field("Diastolic", 300)
                                            .field("Mean_Arterial_Pressure", 200)
                                            .field("Pulse_Pressure", 500)
                                            .field("Comment", "Comment")
                                            .endObject();

                        //create index
            IndexResponse response = client.prepareIndex("blood_pressure", "blood", "1")
                                     .setSource(blood_pressure)
                                     .execute()
                                     .actionGet();
            
//////            //mapping
//////            PutMappingResponse mapping = client.admin()
//////                                         .indices()
//////                                         .preparePutMapping("blood_pressure")
//////                                         .setType("blood")
//////                                         .setSource(blood_pressure)
//////                                         .execute()
//////                                         .actionGet();
            


            // on shutdown
            node.close();
            client.close();
        } 
        catch (Exception ex) 
        {
            System.out.print("ERROR! -> " + ex.getMessage());
        }
                

    }
}
