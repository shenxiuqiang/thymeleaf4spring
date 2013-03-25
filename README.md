# thymeleaf4spring

Configuration support for Thymeleaf with Spring.

## Overview
thymeleaf4spring is just a very small set of configuration classes with sensible defaults to bootstrap
<a href="http://www.thymeleaf.org">Thymeleaf</a> in a <a href="http://www.springframework.org">Spring MVC</a>
environment.<br>
It 
* features a simple switch to allow for different development settings (ie. disable cache, etc.).
* detects the presence of LayoutDialect and adds it to the set of available dialects, if present 
* has optional support for configuring Thymeleaf & Spring with Tiles (see _DefaultAbstractThymeleafTilesConfiguration_)

## Getting started
1. Create a @Configuration class
```java
@Configuration
public class DefaultAbstractThymeleafConfiguration extends DefaultThymeleaf4SpringWroDialectConfiguration {
  	@Override
		protected boolean isDevelopment() {
			return true;
		}
}
```

2. @Import the class in your DispatcherServlet Config, ie.
```java
@Configuration
@EnableWebMvc
@Import({ThymeleafMVCConfiguration.class})
public class DispatcherConfig{
}
```

## Optional Tiles Support

See https://github.com/thymeleaf/thymeleaf-extras-tiles2 for functionality details.

1. Extend _DefaultAbstractThymeleafTilesConfiguration_ instead of _DefaultThymeleaf4SpringWroDialectConfiguration_

2. Create a _tiles-defs.xml_:
```xml
<tiles-definitions>
      <definition name="home" template="/layouts/mainLayout" templateType="thymeleaf">
          <put-attribute name="content" value="home :: content" type="thymeleaf" />
      </definition>

      <definition name="about" template="/about.jsp" templateType="jsp">
          <put-attribute name="footer" value="footer :: content" type="thymeleaf" />
      </definition>
</tiles-definitions>
```
