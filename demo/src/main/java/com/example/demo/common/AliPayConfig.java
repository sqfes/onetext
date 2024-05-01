package com.example.demo.common;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
//读取yml文件中alipay 开头的配置
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    private String appId;//支付宝的appid
    private String appPrivateKey;//应用私钥
    private String alipayPublicKey;//支付宝公钥
    private String notifyUrl;//支付宝回调地址
    private String retureUrl;//支付宝同步回调地址
    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.alipayPublicKey;
        config.notifyUrl = this.notifyUrl;
        Factory.setOptions(config);
        System.out.println("=======支付宝SDK初始化成功=======");
    }

}
