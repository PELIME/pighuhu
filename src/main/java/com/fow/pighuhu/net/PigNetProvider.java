package com.fow.pighuhu.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author ：肖承祥<pelime@qq.com>
 * @date ：Created in 2019/10/17 0017 下午 5:11
 * @Description :
 * @modified By：
 * @version: 1.0
 */
public class PigNetProvider {

    private final String DEFAULT_PROTOCOL="http";
    private String proto;
    private ChannelFuture serverChannel;

    public PigNetProvider(){
        proto=DEFAULT_PROTOCOL;
    }

    public PigNetProvider(String protocol){
        this.proto=protocol;
    }

    public void run(String host, int port, final String baseUrl) throws InterruptedException {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            if(proto.equals("http")){
                                socketChannel.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                                socketChannel.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                                socketChannel.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                                socketChannel.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                                //@TODO 添加处理消息的Handler
                                socketChannel.pipeline().addLast("http-handler",new HttpServerHandler(baseUrl));
                            }

                        }
                    });
            if(host==null){
                host="127.0.0.1";
            }
            serverChannel = bootstrap.bind(host, port).sync();
            serverChannel.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
