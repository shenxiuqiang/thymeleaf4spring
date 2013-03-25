package org.sevensource.thymeleaf4spring;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring3.dialect.SpringStandardDialect;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public abstract class DefaultAbstractThymeleafConfiguration extends ThymeleafConfigurationSupport {

	private final static Logger logger = LoggerFactory.getLogger(DefaultAbstractThymeleafConfiguration.class);
	
	private final static String LAYOUT_DIALECT_CLASS = "nz.net.ultraq.web.thymeleaf.LayoutDialect";
	private final static boolean layoutDialectAvailable = ClassUtils.isPresent(LAYOUT_DIALECT_CLASS, DefaultAbstractThymeleafConfiguration.class.getClassLoader());

	
	/**
	 * by default, a {@link SpringStandardDialect} is added. If LayoutDialect is
	 * present on the classpath, this will be added as well.
	 * 
	 * @see https://github.com/ultraq/thymeleaf-layout-dialect
	 */
	@Override
	protected Set<IDialect> getAdditionalDialects() {
		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(new SpringStandardDialect());
		
		if(layoutDialectAvailable) {
			try {
				Class<?> layoutDialectClass = ClassUtils.forName(LAYOUT_DIALECT_CLASS, this.getClass().getClassLoader());
				Object oLayoutDialect = layoutDialectClass.newInstance();
				IDialect layoutDialect = (IDialect) oLayoutDialect;
				dialects.add(layoutDialect);
			} catch (Exception e) {
				logger.error("Error creating LayoutDialect", e);
			}
		}
		
		return dialects;
	}
	
	/**
	 * @see DefaultAbstractThymeleafConfiguration#layoutTemplateResolver()
	 * @see DefaultAbstractThymeleafConfiguration#viewsTemplateResolver()
	 */
	@Override
	protected Set<ITemplateResolver> getTemplateResolvers() {
		Set<ITemplateResolver> templateResolvers = new HashSet<ITemplateResolver>(3);
		templateResolvers.add( layoutTemplateResolver() );
		templateResolvers.add( viewsTemplateResolver() );
		return templateResolvers;
	}
	
	
	/**
	 * the LayoutTemplateResolver resolves view names such as '/layouts/default'
	 * to /WEB-INF/layouts/default.html
	 * 
	 * @return
	 */
	private ITemplateResolver layoutTemplateResolver() {
		ServletContextTemplateResolver templateResolver = createServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/");
		templateResolver.setName("LayoutTemplateResolver");
		
		Set<String> resolvablePatters = new HashSet<String>(1);
		resolvablePatters.add("/layouts/*");
		templateResolver.setResolvablePatterns(resolvablePatters);
		return templateResolver;
	}

	/**
	 * The ViewsTemplateResolver resolves viewnames such as 'home' to
	 * /WEB-INF/views/home.html
	 * 
	 * @return
	 */
	private ITemplateResolver viewsTemplateResolver() {
		ServletContextTemplateResolver templateResolver = createServletContextTemplateResolver();
		templateResolver.setName("ViewTemplateResolver");
		templateResolver.setPrefix("/WEB-INF/views/");
		return templateResolver;
	}
}
