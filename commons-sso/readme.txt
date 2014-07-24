本配置和客户端filter 基于cas-server-3.4.11

1 casserver 配置
   a.生成证书   keytool -genkey -alias cas_server -keystore cas_server.keystore -keyalg RSA -validity 3666
   b.配置tomcat server.xml
   
   		<Connector port="443" protocol="org.apache.coyote.http11.Http11Protocol" SSLEnabled="true"
               maxThreads="150" scheme="https" secure="true" 
			   keystorePass="changeit" 
			   keystoreFile="D:/cas_server.keystore"
               clientAuth="false" sslProtocol="TLS" />
    c.修改cas-server-3.4.11  deployerConfigContext.xml 文件
    
    			I 添加 数据源和passwordEncoder配置
    			
    					<bean id="casDataSource" class="org.apache.commons.dbcp.BasicDataSource"
						destroy-method="close">
						<property name="driverClassName" value="oracle.jdbc.driver.OracleDriver"></property>
						<property name="url"
							value="jdbc:oracle:thin:@192.168.1.220:1521:hexizheng"></property>
						<property name="username" value="hexizheng"></property>
						<property name="password" value="hexizheng"></property>
						<property name="initialSize" value="5" />
						<property name="maxActive" value="5" />
						<property name="maxIdle" value="5" />
						<property name="maxWait" value="1000" />
					</bean>
					
					<bean id="md5PasswordEncoder" class="org.jasig.cas.authentication.handler.Md5PasswordEncoder" />
				II 注释简单验密bean配置数据库验证bean
				 
				   	<!--<bean class="org.jasig.cas.authentication.handler.support.SimpleTestUsernamePasswordAuthenticationHandler" />-->
				
					<bean id="SearchModeSearchDatabaseAuthenticationHandler"
						class="org.jasig.cas.adaptors.jdbc.SearchModeSearchDatabaseAuthenticationHandler">
						<property name="tableUsers">
							<value>customer_info</value>
						</property>
						<property name="fieldUser">
							<value>customer_no</value>
						</property>
						<property name="fieldPassword">
							<value>version</value>
						</property>
						<property  name="passwordEncoder"  ref="md5PasswordEncoder"/>
						<property name="dataSource" ref="casDataSource" />
					</bean>
					
2. cas 客户应用程序配置

		配置 web.xml 添加过滤器(应用根据情况修改过滤规则)
			<filter>
				<filter-name>Auto Sign Filter</filter-name>
				<filter-class>com.jscn.commons.sso.filter.AutoSigninFilter</filter-class>
			</filter>
			<filter-mapping>
				<filter-name>Auto Sign Filter</filter-name>
				<url-pattern>*.htm</url-pattern>
			</filter-mapping>
			<filter-mapping>
				<filter-name>Auto Sign Filter</filter-name>
				<url-pattern>/sso/postticket.valid</url-pattern>
			</filter-mapping>
			
			<filter>
				<filter-name>Session Filter</filter-name>
				<filter-class>com.jscn.commons.sso.filter.SessionFilter</filter-class>
			</filter>
			<filter-mapping>
				<filter-name>Session Filter</filter-name>
				<url-pattern>/manage/*</url-pattern>
			</filter-mapping>
			
		
		


					