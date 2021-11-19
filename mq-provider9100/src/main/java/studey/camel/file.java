package studey.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;

/**
 * @Author: wangxiaofeng
 * @DateTime: 2021/11/12 17:44
 * @Description: TODO
 */
public class file {
    public static void main(String[] args) throws Exception {
        ModelCamelContext camelContext = new DefaultCamelContext();

        camelContext.start();

        camelContext.addRoutes(new RouteBuilder() {
            // move:代表传输完毕后存储的目录
            // antInclude:对需要传输的文件进行过滤，类似正则表达式
            // antExclude：对不需要传输的文件进行过滤
            // idempotent:true,表示对处理后的文件将不会再次传输，默认false
            // charset:编码
            // delete:true，表示处理完文件后删除，不存在备份的
            /**
             * in.header中的key
             */
            // exchange.getIn().getHeader(Exchange.FILE_NAME)：获取文件名
            // CamelFileAbsolutePath：文件的绝对路径，header中的key
            // CamelFileLength：文件大小
            // CamelFileLastModified：文件最后修改时间

            @Override
            public void configure() throws Exception {
                // TODO Auto-generated method stub
                from("file:C://Users//15622//Desktop//11//1?charset=utf-8&delete=false&move=${date:now:yyyyMMdd}/${file:name}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                // TODO Auto-generated method stub
                                System.out.println("*******************");
                                System.out.println("文件名 ： " + exchange.getIn().getHeader(Exchange.FILE_NAME));
                                System.out.println("绝对路径 ： " + exchange.getIn().getHeader("CamelFileAbsolutePath"));
                                System.out.println("最后修改时间 ： " + exchange.getIn().getHeader("CamelFileLastModified"));
                                System.out.println("文件大小： " + exchange.getIn().getHeader("CamelFileLength"));
                            }
                        }).to("file:C://Users//15622//Desktop//11//2?charset=utf-8");
            }
        });
        synchronized (file.class) {
            file.class.wait();
        }
    }
}