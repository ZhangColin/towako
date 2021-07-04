package com.towako.traffic.channel;

import com.cartisan.utils.QrCodeUtil;
import com.towako.security.CurrentUser;
import com.towako.traffic.channel.request.RegisterChannelCommand;
import com.towako.traffic.channel.response.ChannelDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "渠道管理：我的渠道信息")
@RestController
@RequestMapping("/traffic/mychannel")
@Validated
@Slf4j
public class MyChannelController {
    private final ChannelAppService service;
    private final CurrentUser currentUser;

    public MyChannelController(ChannelAppService service, CurrentUser currentUser) {
        this.service = service;
        this.currentUser = currentUser;
    }

    @ApiOperation(value = "我的渠道信息")
    @GetMapping("")
    public ResponseEntity<ChannelDto> myChannel() {
        return success(service.getChannelByUserId(currentUser.getUserId()));
    }

    @ApiOperation(value = "我的推广海报")
    @GetMapping("/recommendPoster")
    public void myRecommendPoster(@RequestParam Long channelId, HttpServletResponse response) {
        final ChannelDto channel = service.getByChannelId(channelId);

        try {
            String ticket = channel.getTicket();
            String posterPath;
            int x, y, textX, textY, width, height;

            if (channel.getType().equals(ChannelType.DOCTOR) || channel.getType().equals(ChannelType.TOWAKO_DOCTOR)) {
                posterPath = "/images/doctor_channel.png";
                x = 95;
                y = 720;
                textX = 125;
                textY = 960;
                width = 220;
                height = 220;
            } else {
                posterPath = "/images/other_channel.png";
                x = 100;
                y = 580;
                textX = 105;
                textY = 760;
                width = 160;
                height = 160;
            }


            final BufferedImage posterImage = buildChannelPoster(channelId, ticket, posterPath, x, y, textX, textY, width, height);
            ImageIO.write(posterImage, "PNG", response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private BufferedImage buildChannelPoster(Long channelId, String ticket, String posterPath, int x, int y, int textX, int textY, int width, int height) throws IOException {
        final BufferedImage posterImage = ImageIO.read(getClass().getResource(posterPath));
        final String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        log.info(url);
        final BufferedImage qrCodeImage = ImageIO.read(new URL(url));

        final Image targetQrCodeImage = qrCodeImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        final BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        final Graphics g = tag.getGraphics();
        g.drawImage(targetQrCodeImage, 0, 0, null);
        g.dispose();

        final Graphics2D graphics = posterImage.createGraphics();
        graphics.drawImage(targetQrCodeImage, x, y, width, height, null);
        final RoundRectangle2D.Float shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graphics.setStroke(new BasicStroke(3f));
        graphics.draw(shape);
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawString(channelId.toString(), textX, textY);
        graphics.dispose();
        return posterImage;
    }

    @ApiOperation(value = "我的渠道二维码")
    @GetMapping("/qrcode")
    public void myChannelQrCode(@RequestParam Long channelId, HttpServletResponse response) throws Exception {
        QrCodeUtil.encode("http://channel-h5.lanmedical.com/#/register?pId=" + channelId, response.getOutputStream());
    }

    @ApiOperation(value = "注册渠道")
    @PostMapping("/register")
    public ResponseEntity<?> registerChannel(
            @ApiParam(value = "注册信息", required = true) @Validated @RequestBody RegisterChannelCommand command) {
        service.registerChannel(command);

        return success();
    }

}
