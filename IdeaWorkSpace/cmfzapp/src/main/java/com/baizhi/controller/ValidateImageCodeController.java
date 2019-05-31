package com.baizhi.controller;

import com.baizhi.util.ValidateImageCodeUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RequestMapping("/code")
@RestController()
public class ValidateImageCodeController {

    @RequestMapping("/getcode")
    public void getCode(HttpSession session, HttpServletResponse response) {
        //获得文字码
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        session.setAttribute("securityCode", securityCode);

        //生成图片
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        //3.写出  1.图片  2.图片的格式  3.输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

