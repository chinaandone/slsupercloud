package com.clever.common.partnerApi.sms;

import com.clever.common.domain.SendRecord;
import com.clever.common.domain.Table;
import com.clever.common.domain.TablePhone;
import com.clever.common.eum.TemplateSMSType;
import com.clever.common.service.SendRecordManageService;
import com.clever.common.util.CommConfig;
import com.clever.common.util.MyArrayUtils;
import com.clever.common.util.MyStringUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlidayuSmsClient {

	private final static Logger logger = Logger.getLogger(AlidayuSmsClient.class);

	public static final int SEND_SUCCESS = 0;//成功
	public static final int PARA_ERROR = 1;//参数错误
	public static final int SEND_FAIL = 2;//发送失败
	public static final int SEND_EXCEPTION = 3;//发送出现异常

	private AlidayuSmsClient() {
	}

	/**
	 * 内部点点笔异步发短消息
	 * @param tb
	 * @return
	 */
	public static Runnable sendSMS(final String ipAddress,
							  final TablePhone ip,
							  final Table tb,
							  final SendRecordManageService sendRecordManageService,
							  final String templateNum,
							  final String sign,
							  final CommConfig commConfig,
							  final long st) {
		return new Runnable() {
			@Override
			public void run() {
				List list = new ArrayList();
				if(!MyStringUtils.isBlank(ip.getPhone())){
					list.add(ip.getPhone());
				}
				if(!MyStringUtils.isBlank(ip.getPhone1())){
					list.add(ip.getPhone1());
				}
				if(!MyStringUtils.isBlank(ip.getPhone2())){
					list.add(ip.getPhone2());
				}
				String[] smsNum = (String[])list.toArray(new String[list.size()]);
				final long et = System.currentTimeMillis();
				final Date dt = new Date();
//				int i = send(smsNum, tb.getName(), TemplateSMSType.getName(in.getTemplateID()));
				int i = send(smsNum, ip.getTableName(), templateNum,sign,commConfig);
				//往数据库记录发送结果
				SendRecord record = new SendRecord();
				record.setOrgId(tb.getOrgId());
				record.setClientId(tb.getClientId());
				record.setTableId(tb.getTableId());
				record.setFlag(i);
				record.setPartner(0);//设置为0，非第三方发送
				record.setTemplateNum(templateNum);
				record.setDescription(MyArrayUtils.merge(smsNum, ",") + ",ip=" + ipAddress  + ",tableId=" + ip.getTableId() + ",templateID=" + templateNum + ",st=" + st + ",et=" + et);
				record.setCreatedBy(0);
				record.setCreated(dt);
				sendRecordManageService.addEntityBySeq(record);
			}
		};
//		return 0;
	}

	/**
	 * 第三方合作方异步发短消息
	 * @param smsNum, 号码数组
	 * @param argName, 动态参数
	 * @param templateNum, 模板
	 * @param sign, 签名
	 * @param st, 开始时间
	 * @return
	 */
	public static Runnable sendSMS(final String ip,
							  final SendRecordManageService sendRecordManageService,
							  final String[] smsNum,
							  final String argName,
							  final String templateNum,
							  final String sign,
							  final CommConfig commConfig,
							  final long st) {
		return new Runnable() {
			@Override
			public void run() {
				final long et = System.currentTimeMillis();
				final Date dt = new Date();
				int i = send(smsNum, argName, templateNum, sign, commConfig);
				//往数据库记录发送结果
				SendRecord record = new SendRecord();
				record.setOrgId(0L);
				record.setClientId(0L);
				record.setTableId(0L);
				record.setFlag(i);
				record.setPartner(1);//设置为1，第三方发送
				record.setTemplateNum(templateNum);
				record.setDescription(MyArrayUtils.merge(smsNum, ",") + ",ip=" + ip + ",templateID=" + templateNum + ",st=" + st + ",et=" + et);
				record.setCreatedBy(0);
				record.setCreated(dt);
				sendRecordManageService.addEntityBySeq(record);
			}
		};
//		return 0;
	}

	/**
	 * 如果发送失败则尝试重发，一共尝试3次
	 * @param smsNum
	 * @param argName
	 * @param templateNum
	 * @return
	 */
	public static int send(String[] smsNum, String argName, String templateNum, String sign, CommConfig commConfig){
		int retResult = 4;
		for(int i = 0; i < 3; i++){
			int result = AlidayuSmsClient.sendHttpSMS(smsNum, argName, templateNum, sign, commConfig);
			if(result == 0){
				retResult = result;
				break;
			}else{
				retResult = result;
			}
		}
		return retResult;
	}

	/**
	 * 发送短信
	 * @param mobiles 电话号码数组,逗号分割
	 * @param argName 参数名称
	 * @param templateNum 短信模板
	 * @param sign 短信签名
	 * @return
	 */
	public static int sendHttpSMS(String[] mobiles, String argName, String templateNum, String sign, CommConfig commConfig){
		if (mobiles == null || templateNum == null){
			return PARA_ERROR;
		}
		String mobileStr = MyArrayUtils.merge(mobiles, ",");
		TaobaoClient client = new DefaultTaobaoClient(commConfig.getAlidayuHttpApiUrl(), commConfig.getAlidayuApiAppkey(), commConfig.getAlidayuApiSecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setSmsType(commConfig.getAlidayuSmsType());
		req.setSmsFreeSignName(commConfig.getAlidayuSignNameActionPen());
		if(!MyStringUtils.isBlank(sign)){
			req.setSmsFreeSignName(sign);
		}
		if(!MyStringUtils.isBlank(argName)){
			req.setSmsParamString("{"+commConfig.getAlidayuSendParam()+":'"+argName+"'}");
		}
		req.setRecNum(mobileStr);
		req.setSmsTemplateCode(templateNum);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			logger.info("第三方发送短信,rspBody:" + rsp.getBody());
			if(rsp != null && rsp.isSuccess() == true){
				return SEND_SUCCESS;
			}else{
				return SEND_FAIL;
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("发送短信异常", e);
			return SEND_EXCEPTION;
		}

	}

}
