package com.calllog.analyser;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class LocalClusterTopology {

    public static void main(String[] args) throws Exception{

        Config config = new Config();
        config.setDebug(false);


        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("call-log-reader", new CallLogReaderSpout(),2);

        builder.setBolt("call-log-creator", new CallLogCreatorBolt(),4).shuffleGrouping("call-log-reader");

        builder.setBolt("call-log-counter", new CallLogCounterBolt(),4).fieldsGrouping("call-log-creator",new Fields("call"));

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("CallLogAnalyser",config,builder.createTopology());


        try{
            Thread.sleep(1000000);
        }catch (Exception e){
            e.printStackTrace();
        }


        //cluster.killTopology("CallLogAnalyser");
        cluster.shutdown();

    }
}
