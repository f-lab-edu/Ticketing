package com.ticketing.server.global.factory;

import java.util.Objects;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
		Resource resource = encodedResource.getResource();
		YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
		factoryBean.setResources(resource);

		Properties properties = factoryBean.getObject();
		return new PropertiesPropertySource(Objects.requireNonNull(resource.getFilename()), Objects.requireNonNull(properties));
	}
}
