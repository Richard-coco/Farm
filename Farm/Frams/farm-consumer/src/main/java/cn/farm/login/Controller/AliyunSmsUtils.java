package cn.farm.login.Controller;

import cn.farm.login.dto.Code;
import cn.farm.login.dto.Result;
import cn.farm.login.enums.CodeStateEnum;
import cn.farm.login.exception.CodeException;
import cn.farm.login.exception.DefaultException;
import cn.farm.login.exception.LikeException;
import cn.farm.login.exception.PhoneException;
import cn.farm.login.mapper.UserMappers;
import cn.farm.login.pojo.User;
import cn.farm.login.service.PhoneService;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.farm.login.dto.Code.getNewcode;
import static cn.farm.login.dto.Code.setNewcode;

/**
 * 阿里云短信服务：
 * 注意：需要 签名名称、模版CODE 以及 RAM访问控制中的 AccessKeyID 和 AccessKeySecret  
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/farm/common")
public class AliyunSmsUtils {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private UserMappers userMappers;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIvgKgSZWzi5BA";  // TODO 修改成自己的
    static final String accessKeySecret = "HCL6rHrYQYKvkln6jo55PKbDQIZFvA";   // TODO 修改成自己的

    public SendSmsResponse sendSms(String telephone, String code) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(telephone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("作物胁迫诊断");    // TODO 修改成自己的
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_171117127");    // TODO 修改成自己的
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        request.setSmsUpExtendCode(code);
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);


        //是否需要使用，看后续
      /*  if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功！");
        } else {
            throw new CodeException("短信发送失败！");
        }*/


        return sendSmsResponse;
    }


    /*  不删 留着 以后可能有用
    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {
          //可自助调整超时时间
          System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
          System.setProperty("sun.net.client.defaultReadTimeout", "10000");
          //初始化acsClient,暂不支持region化
          IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
          DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
          IAcsClient acsClient = new DefaultAcsClient(profile);
          //组装请求对象
          QuerySendDetailsRequest request = new QuerySendDetailsRequest();
          //必填-号码
          request.setPhoneNumber("15000000000");
          //可选-流水号
          request.setBizId(bizId);
          //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
          SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
          request.setSendDate(ft.format(new Date()));
          //必填-页大小
          request.setPageSize(10L);
          //必填-当前页码从1开始计数
          request.setCurrentPage(1L);
          //hint 此处可能会抛出异常，注意catch
          QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
          return querySendDetailsResponse;
      }
  */
    @RequestMapping("/regist/sendcode")
    public Result<Code> registSendCode(
            @RequestParam("phone") String phone
    ) throws ClientException, InterruptedException {
        Result result;
        try {
            //===========判断电话是否被使用过====================
            phoneService.foundPhone(phone);
            //=================结束===============================
            setNewcode();
            String code = Integer.toString(getNewcode());
            request.getSession().setAttribute("code", code);
            System.out.println("发送的验证码为：" + code);
            //发短信
            SendSmsResponse response = sendSms(phone, code); // TODO 填写你需要测试的手机号码
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());

            result = new Result(CodeStateEnum.SUCCESS.getState(), CodeStateEnum.SUCCESS.getStateInfo());
            return result;
        } catch (CodeException e) {
            result = new Result(CodeStateEnum.SEND_NULL.getState(), CodeStateEnum.SEND_NULL.getStateInfo());
            return result;
        }catch (PhoneException p){
            result = new Result(CodeStateEnum.USED_PHONE.getState(), CodeStateEnum.USED_PHONE.getStateInfo());
            return result;
        }catch (Exception e){
            result = new Result(CodeStateEnum.INNER_ERROR.getState(), CodeStateEnum.INNER_ERROR.getStateInfo());
            return result;
        }

       /* 不删 留着 以后可能有用
        System.out.println("  ==============================================  ");
        Thread.sleep(3000L);
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }*/

    }

    @RequestMapping("login/sendcode")
    public Result<Code> loginSendCode(
            @RequestParam("phone") String phone
    ) throws ClientException, InterruptedException {
        Result result;
        try {
            //===========判断电话是否在数据库中有数据====================
            phoneService.foundUsedPhone(phone);
            //=================结束===============================

            setNewcode();
            String code = Integer.toString(getNewcode());
            request.getSession().setAttribute("code", code);
            System.out.println("发送的验证码为：" + code);
            //发短信
            SendSmsResponse response = sendSms(phone, code); // TODO 填写你需要测试的手机号码
            System.out.println("Code=" + response.getCode());
            System.out.println("短信接口返回的数据----------------");

            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("Message=" + response.getMessage());

            System.out.println("BizId=" + response.getBizId());

            result = new Result(CodeStateEnum.SUCCESS.getState(), CodeStateEnum.SUCCESS.getStateInfo());
            return result;
        } catch (CodeException e) {
            result = new Result(CodeStateEnum.SEND_NULL.getState(), CodeStateEnum.SEND_NULL.getStateInfo());
            return result;
        }catch (PhoneException p){
            result = new Result(CodeStateEnum.NO_PHONE.getState(), CodeStateEnum.NO_PHONE.getStateInfo());
            return result;
        }catch (Exception e){
            result = new Result(CodeStateEnum.INNER_ERROR.getState(), CodeStateEnum.INNER_ERROR.getStateInfo());
            return result;
        }
    }

    //登陆验证通过
    @RequestMapping("/code/login")
    public Result CodeLogin(
            @RequestParam(value = "phone")String phone,
            @RequestParam(value = "code")String code
    ){
        Result result;
        try {
            String sessionCode = (String) request.getSession().getAttribute("code");
            if(sessionCode.equals(code)){
                //session中验证码和用户验证码对应，需要把用户信息全都存入session中，并且将user返回给前端
                List<User> users = userMappers.foundPhone(phone);
                User user = users.get(0);
                user.setPassword("");
                result = new Result(CodeStateEnum.SUCCESS.getState(),user, CodeStateEnum.SUCCESS.getStateInfo());
            }else{
                throw new DefaultException("验证失败");
            }
        }catch (DefaultException d){
            result = new Result(CodeStateEnum.FAILURE.getState(), CodeStateEnum.FAILURE.getStateInfo());
        }
        List<User> users = userMappers.selectByPhone(phone);
        User user = users.get(0);
        request.getSession().setAttribute("user", user);
        return result;
    }

    //修改绑定电话
    @RequestMapping("/changephone/sendcode")
    public Result<Code> changePhooneSendCode(
            @RequestParam("phone") String phone
    ) throws ClientException, InterruptedException {
        Result result;
        try {
            setNewcode();
            String code = Integer.toString(getNewcode());
            request.getSession().setAttribute("code", code);
            System.out.println("发送的验证码为：" + code);
            //发短信
            SendSmsResponse response = sendSms(phone, code); // TODO 填写你需要测试的手机号码
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());

            result = new Result(CodeStateEnum.SUCCESS.getState(), CodeStateEnum.SUCCESS.getStateInfo());
            return result;
        } catch (CodeException e) {
            result = new Result(CodeStateEnum.SEND_NULL.getState(), CodeStateEnum.SEND_NULL.getStateInfo());
            return result;
        } catch (Exception e) {
            result = new Result(CodeStateEnum.INNER_ERROR.getState(), CodeStateEnum.INNER_ERROR.getStateInfo());
            return result;
        }
    }

    @RequestMapping("/phone/verification")
    public Result verification(
            @RequestParam("code")String code
    ){
        Result result;
        String SessionCode = (String) request.getSession().getAttribute("code");
        try {
            if(!SessionCode.equals(code)){
                throw new DefaultException("验证失败");
            }
            result = new Result(CodeStateEnum.VICTORY.getState(), CodeStateEnum.VICTORY.getStateInfo());
            return result;
        }catch (DefaultException d){
            result = new Result(CodeStateEnum.FAILURE.getState(), CodeStateEnum.FAILURE.getStateInfo());
            return result;
        }
    }
    @RequestMapping("/modifyphone/sendcode")
    public Result<Code> modifyPhooneSendCode(
            @RequestParam("phone") String phone
    ) throws ClientException, InterruptedException {
        Result result;
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user.getPhone()!=""&&user.getPhone()==phone) {
                //前后电话一致
                throw new LikeException("电话前后一致");
            }
            //===========判断电话是否被使用过====================
            phoneService.foundPhone(phone);
            //=================结束===============================

            setNewcode();
            String code = Integer.toString(getNewcode());
            request.getSession().setAttribute("code", code);
            System.out.println("发送的验证码为：" + code);
            //发短信
            SendSmsResponse response = sendSms(phone, code); // TODO 填写你需要测试的手机号码
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());

            result = new Result(CodeStateEnum.SUCCESS.getState(), CodeStateEnum.SUCCESS.getStateInfo());
            return result;
        }catch (LikeException l){
            result = new Result(CodeStateEnum.EQUAL_PHONE.getState(), CodeStateEnum.EQUAL_PHONE.getStateInfo());
            return result;
        } catch (CodeException e) {
            result = new Result(CodeStateEnum.SEND_NULL.getState(), CodeStateEnum.SEND_NULL.getStateInfo());
            return result;
        } catch (PhoneException p) {
            result = new Result(CodeStateEnum.USED_PHONE.getState(), CodeStateEnum.USED_PHONE.getStateInfo());
            return result;
        } catch (Exception e) {
            result = new Result(CodeStateEnum.INNER_ERROR.getState(), CodeStateEnum.INNER_ERROR.getStateInfo());
            return result;
        }
    }
}