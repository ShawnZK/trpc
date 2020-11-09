package com.sigmoid.arch.trpc.springboot;

import com.sigmoid.arch.trpc.rpc.server.ServerBean;
import com.sigmoid.arch.trpc.rpc.server.ServerBeanMapping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExposeAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Expose expose = bean.getClass().getAnnotation(Expose.class);
        String clazzName = expose.clazzName();
        if (!clazzName.equals(StringUtils.EMPTY)) {
            ServerBeanMapping.add(new ServerBean(clazzName, bean));
        }
        return bean;
    }

}
