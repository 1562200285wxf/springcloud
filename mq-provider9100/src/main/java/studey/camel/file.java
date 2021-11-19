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
            // move:��������Ϻ�洢��Ŀ¼
            // antInclude:����Ҫ������ļ����й��ˣ�����������ʽ
            // antExclude���Բ���Ҫ������ļ����й���
            // idempotent:true,��ʾ�Դ������ļ��������ٴδ��䣬Ĭ��false
            // charset:����
            // delete:true����ʾ�������ļ���ɾ���������ڱ��ݵ�
            /**
             * in.header�е�key
             */
            // exchange.getIn().getHeader(Exchange.FILE_NAME)����ȡ�ļ���
            // CamelFileAbsolutePath���ļ��ľ���·����header�е�key
            // CamelFileLength���ļ���С
            // CamelFileLastModified���ļ�����޸�ʱ��

            @Override
            public void configure() throws Exception {
                // TODO Auto-generated method stub
                from("file:C://Users//15622//Desktop//11//1?charset=utf-8&delete=false&move=${date:now:yyyyMMdd}/${file:name}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                // TODO Auto-generated method stub
                                System.out.println("*******************");
                                System.out.println("�ļ��� �� " + exchange.getIn().getHeader(Exchange.FILE_NAME));
                                System.out.println("����·�� �� " + exchange.getIn().getHeader("CamelFileAbsolutePath"));
                                System.out.println("����޸�ʱ�� �� " + exchange.getIn().getHeader("CamelFileLastModified"));
                                System.out.println("�ļ���С�� " + exchange.getIn().getHeader("CamelFileLength"));
                            }
                        }).to("file:C://Users//15622//Desktop//11//2?charset=utf-8");
            }
        });
        synchronized (file.class) {
            file.class.wait();
        }
    }
}