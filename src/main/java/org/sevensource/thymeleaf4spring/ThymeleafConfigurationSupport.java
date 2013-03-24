package org.sevensource.thymeleaf4spring;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.tiles2.spring.web.configurer.ThymeleafTilesConfigurer;
import org.thymeleaf.extras.tiles2.spring.web.view.ThymeleafTilesView;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


public abstract class ThymeleafConfigurationSupport {
	
	
	/**
	 * Create a Thymleaf specific {@link org.springframework.web.servlet.view.tiles2.TilesConfigurer}<br>
	 * enables checkRefresh on the Tiles definition if {@link #isDevelopment()} is true
	 * 
	 * @return
	 * TODO upgrade to Tiles 3
	 */
	@Bean
	public ThymeleafTilesConfigurer thymeleafTilesConfigurer() {
		ThymeleafTilesConfigurer thymeleafTilesConfigurer = new ThymeleafTilesConfigurer();
		thymeleafTilesConfigurer.setCheckRefresh( isDevelopment() );
		thymeleafTilesConfigurer.setDefinitions( getTilesDefinitions() );
		
		return configureTilesConfigurer( thymeleafTilesConfigurer );
	}
	
	/**
	 * Creates a Thymeleaf {@link ViewResolver}<br>
	 * Disables cache if {@link #isDevelopment()} is true
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setCache( isCaching() );
		thymeleafViewResolver.setViewClass(ThymeleafTilesView.class);
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		return configureViewResolver( thymeleafViewResolver );
	}
	
	/**
	 * create a {@link SpringTemplateEngine}
	 * @return
	 */
	protected SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setAdditionalDialects( getAdditionalDialects() );
		templateEngine.setTemplateResolvers( getTemplateResolvers() );
		
		return configureTemplateEngine( templateEngine );
	}
	
	
	/**
	 * should caching of Templates be enabled?
	 * @return
	 */
	protected boolean isCaching() {
		return isDevelopment() ? false:true;
	}
	
	/**
	 * if true, Thymeleaf caching is disabled and Tiles definition refresh is enabled
	 * @return
	 */
	protected abstract boolean isDevelopment();
	
	/**
	 * @return an array of Tiles definitions to configure Tiles from
	 */
	protected abstract String[] getTilesDefinitions();
	
	/**
	 * Create additional Thymeleaf Dialects
	 * @return can return null
	 */
	protected abstract Set<IDialect> getAdditionalDialects();
	
	/**
	 * @return {@link ITemplateResolver}s that should be used by Thymeleaf
	 */
	protected abstract Set<ITemplateResolver> getTemplateResolvers();
	
	/**
	 * can be overridden by subclasses to further configure the {@link ThymeleafTilesConfigurer}
	 * @param tilesConfigurer
	 * @return
	 */
	protected ThymeleafTilesConfigurer configureTilesConfigurer(ThymeleafTilesConfigurer tilesConfigurer) {
		return tilesConfigurer;
	}
	
	/**
	 * can be overridden by subclasses to further configure the {@link ThymeleafViewResolver}
	 * @param viewResolver
	 * @return
	 */
	protected ThymeleafViewResolver configureViewResolver(ThymeleafViewResolver viewResolver) {
		return viewResolver;
	}
	
	/**
	 * can be overridden by subclasses to further configure the {@link SpringTemplateEngine}
	 * @param templateEngine
	 * @return
	 */
	protected SpringTemplateEngine configureTemplateEngine(SpringTemplateEngine templateEngine) {
		return templateEngine;
	}
}
