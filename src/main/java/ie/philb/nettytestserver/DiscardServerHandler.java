/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.philb.nettytestserver;

import java.util.Date;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author philb
 */
public class DiscardServerHandler extends SimpleChannelHandler {

    private Logger logger = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent evt) {
        logger.info("Message received, reading");

        StringBuilder sb = new StringBuilder();

        ChannelBuffer buf = (ChannelBuffer) evt.getMessage();
        while (buf.readable()) {
            sb.append((char) buf.readByte());
        }

        logger.info("Got message " + sb);
        ChannelBuffer outBuffer = ChannelBuffers.buffer(1000);
        String msg = "Hello, the time is " + new Date();
        outBuffer.writeBytes(msg.getBytes());

        Channel ch = evt.getChannel();
        ChannelFuture f = ch.write(outBuffer);

        f.addListener((ChannelFuture future) -> {
            Channel ch1 = future.getChannel();
            ch1.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent evt) {
        Channel c = evt.getChannel();
        c.close();
    }
}
