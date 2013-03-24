package org.sevensource.thymeleaf4spring;

import java.util.Set;

import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;


public abstract class DefaultAbstractThymeleafConfigurationSupport extends
		ThymeleafConfigurationSupport {

	protected final static String DEFAULT_CHARSET = "UTF-8";
	protected final static String DEFAULT_TEMPLATE_MODE = "HTML5";
	protected final static String DEFAULT_SUFFIX = ".html";
	
	
	@Override
	protected Set<IDialect> getAdditionalDialects() {
		return null;
	}
	
	/**
	 * Helper to instantiate new {@link ServletContextTemplateResolver} and set some common options
	 */
	protected ServletContextTemplateResolver createServletContextTemplateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		
		templateResolver.setCharacterEncoding( DEFAULT_CHARSET );
		templateResolver.setSuffix( DEFAULT_SUFFIX );
		templateResolver.setTemplateMode( DEFAULT_TEMPLATE_MODE );
		
		if(isCaching()) {
			templateResolver.setCacheTTLMs( TemplateResolver.DEFAULT_CACHE_TTL_MS );
			templateResolver.setCacheable( TemplateResolver.DEFAULT_CACHEABLE );
		} else {
			templateResolver.setCacheTTLMs(0L);
			templateResolver.setCacheable( false );
		}
		
		return templateResolver;
	}
}
