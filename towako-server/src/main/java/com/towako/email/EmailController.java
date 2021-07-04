package com.towako.email;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "邮件")
@RestController
@RequestMapping("/email")
@Validated
@Slf4j
public class EmailController {
    private final JavaMailSender javaMailSender;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @ApiOperation(value = "发送邮件")
    @GetMapping("/send")
    public ResponseEntity<?> sendEmail() throws Exception {

        final MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("service@lanmedical.com", "我的测试邮件_发件人昵称", "UTF-8"));
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress("colin.zhang@lanmedical.com", "我的测试邮件_收件人昵称", "UTF-8"));
        message.setSubject("TEST邮件主题（文本+图片+附件）", "UTF-8");

        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<table cellpadding=\"0\" align=\"center\" style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\"><tbody><tr><th valign=\"middle\" style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">注册成功! （优生慧）</font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </th></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"padding:25px 35px 40px; background-color:#fff;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">亲爱的 XXX</font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </h2>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>感谢您加入优生慧推广渠道！</p><p>下面是您的账号信息：<br><br>登录地址：<a href=\"http://channel-h5.lanmedical.com/\" target=\"_blank\">http://channel-h5.lanmedical.com/</a></p><p>账号：<b>111111</b><br>密码：<b>123456</b><br>邮箱：<b>123@**.com</b><br><br></p><p>附件为优生慧推广渠道操作指引，<b>请按照指引进行推广操作</b>，祝您一切顺利，日进斗金！</p><p><br></p><p>当您在使用本网站时，遵守当地法律法规。<br>如果您有什么疑问可以联系管理员，Email: <a href=\"mailto:z@lanmedical.com\" target=\"_blank\">z@lanmedical.com</a></p><p>或直接扫码添加专属客服：</p><p><img src=\"/cgi-bin/viewfile?f=CBA8815A901695F875F28798521BEA8EE20AC74B8C9F82259E3B84E69726A978F06DE48D864A1654302E7DE183AAE7CD12AAC073E439DAFCEC090653C191E728A5F48C1FD00E1F4BC63E240EAA143667DF25D7476D6F7C790410871C559207BF&amp;usewmcache=1&amp;sid=Sfrk07kBX7D8HDBQ,7\" border=\"0\" width=\"111\" height=\"114\"></p><p><br></p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p align=\"right\">优生慧</p>&nbsp;<br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"width:700px;margin:0 auto;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>此为系统邮件，请勿直接回复！<br>请保管好您的邮箱地址，避免账号被他人盗用&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>优生慧©2021</p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div></td></tr></tbody></table>", "text/html;charset=UTF-8");

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        message.setContent(mm);

        message.saveChanges();

        javaMailSender.send(message);


        return success();
    }

    private Session initSession(){
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        props.setProperty("mail.smtp.ssl.enable", "true");
//        props.setProperty("mail.smtp.enable", "true");

//        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
//        mailSSLSocketFactory.setTrustAllHosts(true);
//        props.setProperty("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("service@lanmedical.com", "Y7HFWrfzfNnEyNnH");
            }
        });
        session.setDebug(true);

        return session;
    }



    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
//    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
//        // 1. 创建一封邮件
//        MimeMessage message = new MimeMessage(session);
//
//        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
//        message.setFrom(new InternetAddress(sendMail, "某宝网", "UTF-8"));
//
//        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
//        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
//
//        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
//        message.setSubject("打折钜惠", "UTF-8");
//
//        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
//        message.setContent("XX用户你好, 今天全场5折, 快来抢购, 错过今天再等一年。。。", "text/html;charset=UTF-8");
//
//        // 6. 设置发件时间
//        message.setSentDate(new Date());
//
//        // 7. 保存设置
//        message.saveChanges();
//
//        return message;
//    }

    /**
     * 创建一封复杂邮件（文本+图片+附件）
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "我的测试邮件_发件人昵称", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "我的测试邮件_收件人昵称", "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject("TEST邮件主题（文本+图片+附件）", "UTF-8");

        /*
         * 下面是邮件内容的创建:
         */

        // 5. 创建图片“节点”
//        MimeBodyPart image = new MimeBodyPart();
//        DataHandler dh = new DataHandler(new FileDataSource("FairyTail.jpg")); // 读取本地文件
//        image.setDataHandler(dh);                   // 将图片数据添加到“节点”
//        image.setContentID("image_fairy_tail");     // 为“节点”设置一个唯一编号（在文本“节点”将引用该ID）

        // 6. 创建文本“节点”
        MimeBodyPart text = new MimeBodyPart();
        //    这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("<table cellpadding=\"0\" align=\"center\" style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\"><tbody><tr><th valign=\"middle\" style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #42a3d3; background-color: #49bcff; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">注册成功! （优生慧）</font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </th></tr><tr><td>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"padding:25px 35px 40px; background-color:#fff;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">亲爱的 XXX</font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </font>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </h2>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>感谢您加入优生慧推广渠道！</p><p>下面是您的账号信息：<br><br>登录地址：<a href=\"http://channel-h5.lanmedical.com/\" target=\"_blank\">http://channel-h5.lanmedical.com/</a></p><p>账号：<b>111111</b><br>密码：<b>123456</b><br>邮箱：<b>123@**.com</b><br><br></p><p>附件为优生慧推广渠道操作指引，<b>请按照指引进行推广操作</b>，祝您一切顺利，日进斗金！</p><p><br></p><p>当您在使用本网站时，遵守当地法律法规。<br>如果您有什么疑问可以联系管理员，Email: <a href=\"mailto:z@lanmedical.com\" target=\"_blank\">z@lanmedical.com</a></p><p>或直接扫码添加专属客服：</p><p><img src=\"/cgi-bin/viewfile?f=CBA8815A901695F875F28798521BEA8EE20AC74B8C9F82259E3B84E69726A978F06DE48D864A1654302E7DE183AAE7CD12AAC073E439DAFCEC090653C191E728A5F48C1FD00E1F4BC63E240EAA143667DF25D7476D6F7C790410871C559207BF&amp;usewmcache=1&amp;sid=Sfrk07kBX7D8HDBQ,7\" border=\"0\" width=\"111\" height=\"114\"></p><p><br></p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p align=\"right\">优生慧</p>&nbsp;<br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"width:700px;margin:0 auto;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>此为系统邮件，请勿直接回复！<br>请保管好您的邮箱地址，避免账号被他人盗用&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <p>优生慧©2021</p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div></td></tr></tbody></table>", "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片 “节点”的关系（将 文本 和 图片 “节点”合成一个混合“节点”）
//        MimeMultipart mm_text_image = new MimeMultipart();
//        mm_text_image.addBodyPart(text);
//        mm_text_image.addBodyPart(image);
//        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合“节点”封装成一个普通“节点”
        //    最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        //    上面的 mm_text_image 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
//        MimeBodyPart text_image = new MimeBodyPart();
//        text_image.setContent(mm_text_image);

        // 9. 创建附件“节点”
//        MimeBodyPart attachment = new MimeBodyPart();
//        DataHandler dh2 = new DataHandler(new FileDataSource("妖精的尾巴目录.doc"));  // 读取本地文件
//        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
//        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // 设置附件的文件名（需要编码）

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
//        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
//        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合“节点”作为邮件的内容添加到邮件对象）
        message.setContent(mm);
//        Multipart mp = new MimeMultipart();
//        message.setContent(mp);
//
//        // 12. 设置发件时间
//        message.setSentDate(new Date());

        // 13. 保存上面的所有设置
        message.saveChanges();

        return message;
    }

}
