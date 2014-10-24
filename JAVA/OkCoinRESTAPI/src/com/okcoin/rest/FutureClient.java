package com.okcoin.rest;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.future.IFutureRestApi;
import com.okcoin.rest.future.impl.FutureRestApiV1;

/**
 * 期货 REST API 客户端请求
 * @author zhangchi
 *
 */
public class FutureClient {

	public static void main(String[] args) throws HttpException, IOException {

		//OKCoin唯一用户号,每个用户唯一持有一个
		String partner = "";
		
		//用户私钥
		String secret_key = "";
	    
		//请求URL 国际站https://www.okcoin.com
	    //       国内站https://www.okcoin.cn
		
		String url_prex = "";
		

		/**
		 *  get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
		 */
		IFutureRestApi futureGetV1 = new FutureRestApiV1(url_prex);

		/**
		 * post请求需发送身份认证，获取用户个人相关信息时，需要指定partner,与secretKey并与参数进行签名，
		 * 此处对构造方法传入partner与secretKey,在请求用户相关方法时则无需再传入，
		 * 发送post请求之前，程序会做自动加密，生成签名。
		 * 
		*/
		IFutureRestApi futurePostV1 = new FutureRestApiV1(url_prex, partner,secret_key);

		System.out.println("----------期货行情信息-------");
		System.out.println(futureGetV1.future_ticker("btc_usd", "this_week"));

		System.out.println("-----------期货指数信息-------");
		System.out.println(futureGetV1.future_index("btc_usd"));

		System.out.println("----------期货交易信息-------");
		System.out.println( futureGetV1.future_trades("btc_usd", "this_week"));

		System.out.println("----------期货市场深度-------");
		System.out.println(futureGetV1.future_depth("btc_usd", "this_week"));

		System.out.println("----------美元-人民币汇率-------");
		System.out.println(futureGetV1.exchange_rate());

		System.out.println("----------期货下单-------");
		String tradeResultV1 = futurePostV1.future_trade("btc_usd","this_week", "10.134", "1", "1", "0");
		JSONObject tradeJSV1 = JSONObject.parseObject(tradeResultV1);
		String tradeOrderV1 = tradeJSV1.getString("order_id");

		System.out.println(tradeResultV1);


		System.out.println("----------期货用户订单查询-------");
		System.out.println(futurePostV1.future_order_info("btc_usd", "this_week",tradeOrderV1, "1", "1", "2"));


		System.out.println("----------取消订单-------");
		System.out.println(futurePostV1.future_cancel("btc_usd", "this_week",tradeOrderV1));

		//
		System.out.println("--------期货账户信息-------");
		System.out.println( futurePostV1.future_userinfo());

		System.out.println("--------逐仓期货账户信息-------");
		System.out.println( futurePostV1.future_userinfo_4fix());
		//
		System.out.println("----------期货用户持仓查询-------");
		System.out.println(futurePostV1.future_position("btc_usd", "this_week"));

		System.out.println("----------期货用户逐仓持仓查询-------");
		System.out.println( futurePostV1.future_position_4fix("btc_usd", null));


	}
}