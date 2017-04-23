/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.philb.nettytestserver;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 *
 * @author philb
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Hello");
        
        ChannelFactory cf = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()
        );
        
        ServerBootstrap sb = new ServerBootstrap(cf);
        
        sb.setPipelineFactory(() -> Channels.pipeline(new DiscardServerHandler()));
        
        sb.setOption("child.tcpNoDelay", true);
        sb.setOption("child.keepAlive", true);
    
        sb.bind(new InetSocketAddress("192.168.0.10", 5678));
    }
}
