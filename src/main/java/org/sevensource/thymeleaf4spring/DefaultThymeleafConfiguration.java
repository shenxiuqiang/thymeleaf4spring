package org.sevensource.thymeleaf4spring;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.tiles2.dialect.TilesDialect;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public abstract class DefaultThymeleafConfiguration extends DefaultAbstractThymeleafConfigurationSupport {

	protected final static String DEFAULT_TILES_DEFS = "/WEB-INF/layouts/tiles-defs.xml";
	

	@Override
	protected String[] getTilesDefinitions() {
		return new String[] {DEFAULT_TILES_DEFS};
	}

	@Override
	protected Set<IDialect> getAdditionalDialects() {
		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(new TilesDialect());
		return dialects;
	}

	@Override
	protected Set<ITemplateResolver> getTemplateResolvers() {
		Set<ITemplateResolver> templateResolvers = new HashSet<ITemplateResolver>(3);
		templateResolvers.add( layoutTemplateResolver() );
		templateResolvers.add( viewsTemplateResolver() );
		return templateResolvers;
	}
	
	private ITemplateResolver layoutTemplateResolver() {
		ServletContextTemplateResolver templateResolver = createServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setName("LayoutTemplateResolver");
		
		Set<String> resolvablePatters = new HashSet<String>(1);
		resolvablePatters.add("/layouts/*");
		templateResolver.setResolvablePatterns(resolvablePatters);
		return templateResolver;
	}
	
	private ITemplateResolver viewsTemplateResolver() {
		ServletContextTemplateResolver templateResolver = createServletContextTemplateResolver();
		templateResolver.setName("ViewTemplateResolver");
		templateResolver.setPrefix("/WEB-INF/views/");
		return templateResolver;
	}
}
