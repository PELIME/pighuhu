package com.fow.pighuhu;

import com.fow.pighuhu.net.PigNetProvider;

/**
 * @author ：肖承祥<pelime@qq.com>
 * @date ：Created in 2019/10/18 0018 上午 9:36
 * @Description :
 * @modified By：
 * @version: 1.0
 */
public class PigWebBootstrap {
    public static void main(String[] args) throws InterruptedException {
        int port=4699;
        String baseUrl="/";
        if(args!=null&&args.length>0){
            try {
                port=Integer.parseInt(args[0]);
            }catch (NumberFormatException e){}
        }
        if(args!=null&&args.length>1){
            try {
                String tmpurl=args[1];
                tmpurl=tmpurl.replace("\\","/");
                if(!tmpurl.startsWith("/")){
                    tmpurl="/"+tmpurl;
                }
                baseUrl=tmpurl;
            }catch (Exception e){}
        }
        System.out.println("启动web容器 端口号:"+port+" 路径:"+baseUrl);
        PigNetProvider pigNetProvider=new PigNetProvider();
        pigNetProvider.run(null,port,baseUrl);
    }
}
