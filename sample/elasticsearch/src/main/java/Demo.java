import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Demo {

    public static void main(String[] args){
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "limix-dev").put("client.transport.sniff", true).build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            while (true){
                client.admin().indices()
                        .prepareRefresh()
                        .get();
                System.out.println("I am here");
            }
            // client.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("I am here result");
        }
    }
}
