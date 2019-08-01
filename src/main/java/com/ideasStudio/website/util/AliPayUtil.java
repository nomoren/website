package com.ideasStudio.website.util;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ideasStudio.website.config.AlipayConfig;
import com.ideasStudio.website.config.SpringUtil;
import com.ideasStudio.website.entity.Order;
import com.ideasStudio.website.mapper.OrderMapper;


/**
 * 支付宝操作工具类
 * @author Administrator
 *
 */
public class AliPayUtil{

	Logger logger  = Logger.getLogger(AliPayUtil.class);

    /**
     * 支付订单生成
     * @param outTradeNo  商户订单号
     * @param orderAmount   订单金额
     * @param passbackParams 公共回传参数
     * @return 预订单信息，可直接给客户端请求
     */
    public String UnifiedOrder(String outTradeNo, String orderAmount,String body){
        try {
        	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
        	AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        	alipayRequest.setReturnUrl(AlipayConfig.return_url);
        	alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        	Map orderMap = new LinkedHashMap();
        	try {
				orderMap.put("out_trade_no",new String(outTradeNo.getBytes("ISO-8859-1"),"UTF-8"));
				orderMap.put("subject", new String(body.getBytes("utf-8"),"UTF-8"));
	        	orderMap.put("total_amount", new String(orderAmount.getBytes("ISO-8859-1"),"UTF-8"));
	        	orderMap.put("product_code", new String(AlipayConfig.product_code.getBytes("ISO-8859-1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        	
        	alipayRequest.setBizContent(JSONObject.toJSON(orderMap).toString());
            return alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
        	e.printStackTrace();
            throw new RuntimeException("订单创建异常");
        }
    }
    
    /**
     * 支付订单生成
     * @param outTradeNo  商户订单号
     * @param orderAmount   订单金额
     * @param passbackParams 公共回传参数
     * @return 预订单信息，可直接给客户端请求
     */
    public String UnifiedOrderMobile(String outTradeNo, String orderAmount,String body){
        try {
        	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
        	AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        	alipayRequest.setReturnUrl(AlipayConfig.return_url);
        	alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        	Map orderMap = new LinkedHashMap();
        	try {
				orderMap.put("out_trade_no",new String(outTradeNo.getBytes("ISO-8859-1"),"UTF-8"));
				orderMap.put("subject", new String(body.getBytes("utf-8"),"UTF-8"));
	        	orderMap.put("total_amount", new String(orderAmount.getBytes("ISO-8859-1"),"UTF-8"));
	        	orderMap.put("product_code", new String(AlipayConfig.product_code_mobile.getBytes("ISO-8859-1"),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        	
        	alipayRequest.setBizContent(JSONObject.toJSON(orderMap).toString());
            return alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
        	e.printStackTrace();
            throw new RuntimeException("订单创建异常");
        }
    }
    
    /**
     * 支付宝支付订单回调
     * @param request
     * @return 订单信息
     */
    public Map<String,Object> NotityResult(HttpServletRequest request){
        try{
            Map<String,String> params = RequestConvertUtils.convertParseUrl(request);
            String param = JSONObject.toJSON(params).toString();
            logger.error("支付异步回调参数："+params);
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,AlipayConfig.charset,AlipayConfig.sign_type);
            if (flag){		//首先判断签名是否正确
            	check(params);		//检查回调的内容是否和数据库中的内容相同，不相同则会直接抛异常
            	if("TRADE_FINISHED".equals(params.get("trade_status")) || "TRADE_SUCCESS".equals(params.get("trade_status"))){		//判断交易状态是否成功
            		String endtime = DateUtil.reformat(params.get("gmt_payment"));		//获取买家付款时间
            		String orderNo = params.get("out_trade_no");
            		Map<String,Object> result = new HashMap<String,Object>();
            		result.put("orderNo", orderNo);
            		result.put("trade_status", 1);
            		result.put("endtime",endtime);
            		result.put("total_amount", params.get("total_amount"));
            		return result;
            	}
            	logger.info("支付结果不为支付成果或者交易完毕");
            }
            logger.info("支付签名验证错误！！！");
            return null;
        }catch (Exception e){
        	logger.info("出现了异常");
        	e.printStackTrace();
        	return null;
        }
    }
    
    /**
     * 支付宝支付订单回调
     * @param request
     * @return 订单信息
     */
    public Map<String,Object> ReturnResult(HttpServletRequest request){
        try{
            Map<String,String> params = RequestConvertUtils.convertParseUrl(request);
            String param = JSONObject.toJSON(params).toString();
            logger.error("支付同步回调参数："+params);
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,AlipayConfig.charset,AlipayConfig.sign_type);
            if (flag){		//首先判断签名是否正确
            	//因为是同步，同步只会在支付成功下调用，所以不用考虑支付状态
            	String endtime = DateUtil.reformat(params.get("timestamp"));		//获取买家付款时间
        		String orderNo = params.get("out_trade_no");
        		Map<String,Object> result = new HashMap<String,Object>();
        		result.put("orderNo", orderNo);
        		result.put("trade_status", 1);
        		result.put("endtime",endtime);
        		result.put("total_amount", params.get("total_amount"));
            	return result;		//验证通过，返回参数结果map
            }else{
            	System.out.println("支付宝签名验证未通过");
            }
            return null;
        }catch (Exception e){
        	e.printStackTrace();
        	return null;
        }
    }



    /**
     * 支付交易订单查询
     * @param outTradeNo 商户订单号
     * @param tradeNo 支付宝交易号
     */
    public boolean QueryOrderStatus(String outTradeNo, String tradeNo){
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)){
            //订单查询 商户订单号和支付宝交易号不能同时为空
        }
        try{
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            Map bizContent = new HashMap();
            if (StringUtils.isNotBlank(outTradeNo)){
                bizContent.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(tradeNo)){
                bizContent.put("trade_no", tradeNo);
            }
            request.setBizContent(JSON.toJSONString(bizContent));
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
            	if ("SUCCESS".equals(response.getTradeStatus())){
                    return true;		//交易成功
                }else{
                	return false;		//交易不成功
                }
            } else {
                return false;			
            }
        }catch (Exception e){
        	throw new RuntimeException("订单查询异常");
        }
    }



    /**
     * 交易关闭
     * @param outTradeNo 商户订单号
     * @param tradeNo 支付宝交易号
     */
    public boolean CloseOrder(String outTradeNo, String tradeNo){
        if (StringUtils.isBlank(outTradeNo) && StringUtils.isBlank(tradeNo)){
            //订单查询 商户订单号和支付宝交易号不能同时为空
        }
        try{
        	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
            AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
            Map bizContent = new HashMap();
            if (StringUtils.isNotBlank(outTradeNo)){
                bizContent.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(tradeNo)){
                bizContent.put("trade_no", tradeNo);
            }
            request.setBizContent(JSON.toJSONString(bizContent));
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            throw new RuntimeException("订单关闭异常");
        }

    }



    /**
     * 交易退款
     * @param outTradeNo 商户订单号
     * @param tradeNo   支付宝订单号
     * @param refundAmount 退款金额
     * @param outRequestNo 退款流水号
     */
    public boolean AppForRefund(String outTradeNo, String tradeNo, String refundAmount, String outRequestNo){
        try{
        	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            Map bizContent = new HashMap();
            if (StringUtils.isNotBlank(outTradeNo)){
                bizContent.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(tradeNo)){
                bizContent.put("trade_no", tradeNo);
            }
            bizContent.put("refund_amount", refundAmount);

            if (StringUtils.isNotBlank(outRequestNo)){
                //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
                bizContent.put("out_request_no", outRequestNo);
            }
            request.setBizContent(JSON.toJSONString(bizContent));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                if ("10000".equals(response.getCode())){
                    return true;
                }else{
                	return false;
                }
            } else {
                return false;
            }
        }catch (Exception e){
        	throw new RuntimeException("交易退款调用失败");
        }
    }


    /**
     * 交易退款查询
     * @param outTradeNo 商户订单号
     * @param tradeNo   支付宝订单号
     * @param outRequestNo 退款流水号
     */
    public boolean AppForRefundQuery(String outTradeNo, String tradeNo, String outRequestNo){
        try{
        	AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key,AlipayConfig.request_data_type,AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            Map bizContent = new HashMap();
            if (StringUtils.isNotBlank(outTradeNo)){
                bizContent.put("out_trade_no", outTradeNo);
            }
            if (StringUtils.isNotBlank(tradeNo)){
                bizContent.put("trade_no", tradeNo);
            }
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
            bizContent.put("out_request_no", outRequestNo);
            request.setBizContent(JSON.toJSONString(bizContent));
            AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
            	if("10000".equals(response.getCode())){
            		return true;
            	}else{
            		return false;
            	}
            } else {
                return false;
            }
        }catch (Exception e){
            throw new RuntimeException("交易退款查询异常");
        }
    }
    
   
    /**
     * 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
     * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
     * 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
     * 4、验证app_id是否为该商户本身。上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     * 
     * @param params
     * @throws AlipayApiException
     */
    private void check(Map<String, String> params) throws AlipayApiException {      
        String outTradeNo = params.get("out_trade_no");
        logger.info("本次支付单号:"+outTradeNo);

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        Order order = SpringUtil.getBean(OrderMapper.class).getOrderByOrderNo(outTradeNo); // 这个方法自己实现
        if (order == null) {
        	logger.info("out_trade_no错误");
            throw new AlipayApiException("out_trade_no错误");
        }       
        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = Math.round(Double.valueOf(params.get("total_amount"))*100);    
        long orderGetPay = Math.round(order.getPay() * 100);
        logger.info("支付宝反馈金额："+total_amount);
        logger.info("数据库查询金额："+orderGetPay);
        if (total_amount != orderGetPay) {
        	logger.info("error total_amount");
            throw new AlipayApiException("error total_amount");
        }

        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        //这一步操作没有此业务，暂时不需要实现
        // 第三步可根据实际情况省略

        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(AlipayConfig.app_id)) {
        	logger.info("app_id不一致");
            throw new AlipayApiException("app_id不一致");
        }
        
    }
    public static void main(String[] args) {
    	DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
    	long total_amount = Long.valueOf(decimalFormat.format(Double.valueOf(0.01*100)));
    	 
    	System.out.println(decimalFormat.format(Double.valueOf("0.01")*100));
    	System.out.println(total_amount);
    	System.out.println(0.01*100);
    	
    	
    	 long total_amount1 = Math.round(Double.valueOf("537.70")*100);    
         long orderGetPay = Math.round(537.70 * 100);
         System.out.println("支付宝反馈金额："+total_amount1);
         System.out.println("数据库查询金额："+orderGetPay);
	}
}
