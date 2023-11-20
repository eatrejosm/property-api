package mch.asset;

import mch.asset.config.DemoAppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(scanBasePackages = {
		"mch.asset",
		"mch.common.impl"
})
@EnableConfigurationProperties(DemoAppProperties.class)
public class MchAssetMgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MchAssetMgmtApplication.class, args);
	}

}
