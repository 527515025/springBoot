package cn.abel.user.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志-复制并替代com.alibaba.dubbo.rpc.protocol.rest.support.LoggingFilter
 *
 * @author ye
 * @date 2018/11/04 16:34
 */
public class RestInterceptor implements WriterInterceptor, ReaderInterceptor {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RestInterceptor.class);

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        byte[] buffer = IOUtils.toByteArray(context.getInputStream());
        logger.info("The contents of request body is: \n" + new String(buffer, "UTF-8") + "\n");
        context.setInputStream(new ByteArrayInputStream(buffer));
        return context.proceed();
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        RestInterceptor.OutputStreamWrapper wrapper =
                new RestInterceptor.OutputStreamWrapper(context.getOutputStream());
        context.setOutputStream(wrapper);
        context.proceed();
        logger.info("The contents of response body is: \n" + new String(wrapper.getBytes(), "UTF-8") + "\n");
    }

    protected static class OutputStreamWrapper extends OutputStream {

        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private final OutputStream output;

        private OutputStreamWrapper(OutputStream output) {
            this.output = output;
        }

        @Override
        public void write(int i) throws IOException {
            buffer.write(i);
            output.write(i);
        }

        @Override
        public void write(byte[] b) throws IOException {
            buffer.write(b);
            output.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            buffer.write(b, off, len);
            output.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            output.flush();
        }

        @Override
        public void close() throws IOException {
            output.close();
        }

        public byte[] getBytes() {
            return buffer.toByteArray();
        }
    }

}
