package com.calllog.analyser;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CallLogReaderSpout implements IRichSpout {

    //creating instances of required classes.
    private SpoutOutputCollector collector;
    private TopologyContext context;
    private boolean completed = false;

    //for generating call log at random
    private Random randomGenerator = new Random();

    //variable that limit the stream creation further in the program
    private int idx = 0;


    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

        this.context = context;
        this.collector = collector;


    }

    public void close() {

    }

    public void activate() {

    }

    public void deactivate() {

    }

    public void nextTuple() {

        if(this.idx <= 1000) {
            List<String> mobileNumbers = new ArrayList<String>();
            mobileNumbers.add("raxit");
            mobileNumbers.add("tushar");
            mobileNumbers.add("chirag");
            mobileNumbers.add("vikas");

            Integer localIdx = 0;
            while(localIdx++ < 100 && this.idx++ < 1000) {
                String fromMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                String toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));

                while(fromMobileNumber == toMobileNumber) {
                    toMobileNumber = mobileNumbers.get(randomGenerator.nextInt(4));
                }

                Integer duration = randomGenerator.nextInt(60);

                //System.out.println(fromMobileNumber+":"+toMobileNumber+":"+duration);
                this.collector.emit(new Values(fromMobileNumber, toMobileNumber, duration));
            }
        }
    }

    public void ack(Object msgId) {

    }

    public void fail(Object msgId) {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("from","to","duration"));

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
