package org.sevensource.thymeleaf4spring.tiles2;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.tiles2.spring.web.configurer.ThymeleafTilesConfigurer;


public abstract class DefaultAbstractThymeleafTilesConfiguration {
	protected final static String DEFAULT_TILES_DEFS = "/WEB-INF/layouts/tiles-defs.xml";
	
	
	/**
	 * @return an array of DefaultAbstractThymeleafTilesConfiguration definitions to
	 *         configure DefaultAbstractThymeleafTilesConfiguration from
	 */
	protected Set<String> getTilesDefinitions() {
		Set<String> definitions = new HashSet<String>();
		definitions.add(DEFAULT_TILES_DEFS);
		return definitions;
	}
	
	/**
	 * Create a Thymleaf specific {@link org.springframework.web.servlet.view.tiles2.TilesConfigurer}<br>
	 * enables checkRefresh on the DefaultAbstractThymeleafTilesConfiguration definition if {@link #isDevelopment()} is true
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafTilesConfigurer thymeleafTilesConfigurer() {
		ThymeleafTilesConfigurer thymeleafTilesConfigurer = new ThymeleafTilesConfigurer();
		thymeleafTilesConfigurer.setCheckRefresh( isDevelopment() );
		thymeleafTilesConfigurer.setDefinitions( getTilesDefinitions().toArray(new String[]{}) );
		
		return configureTilesConfigurer( thymeleafTilesConfigurer );
	}
	
	/**
	 * can be overridden by subclasses to further configure the {@link ThymeleafTilesConfigurer}
	 * @param tilesConfigurer
	 * @return
	 */
	protected ThymeleafTilesConfigurer configureTilesConfigurer(ThymeleafTilesConfigurer tilesConfigurer) {
		return tilesConfigurer;
	}
	
	protected abstract boolean isDevelopment();
}
