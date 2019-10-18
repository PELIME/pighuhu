package com.fow.pighuhu.net;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


/**
 * @author ：肖承祥<pelime@qq.com>
 * @date ：Created in 2019/10/17 0017 下午 5:33
 * @Description :
 * @modified By：
 * @version: 1.0
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String baseUrl;
    public HttpServerHandler(String baseUrl){
        this.baseUrl=baseUrl;
    }

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String url=fullHttpRequest.uri();
        if(fullHttpRequest.decoderResult().isFailure()){
            sendError(channelHandlerContext,HttpResponseStatus.BAD_REQUEST);
            return;
        }
        sendError(channelHandlerContext,HttpResponseStatus.NOT_FOUND);

        //@TODO 添加请求处理代码 通过路由来选择过滤器链和处理方法
        /**
         * 处理注册的过滤器（实现一个过滤器链）
         * 处理url,在这里注册路由处理（模拟Spring MVC）,采用方法级别的路由控制
         *
         */
    }

    private static void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus status){
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer("Failure:  "+status.toString()
                +"\r\n",CharsetUtil.UTF_8));
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
