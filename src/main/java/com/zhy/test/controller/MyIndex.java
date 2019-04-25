package com.zhy.test.controller;

import com.google.code.kaptcha.Producer;
import com.zhy.test.entity.CifCode;
import com.zhy.test.entity.Person;
import com.zhy.test.entity.Pet;
import com.zhy.test.service.CifCodeService;
import org.apache.commons.lang3.ObjectUtils;
import org.nutz.img.Images;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyIndex {

    private Producer kaptchaProducer=null;

    @Autowired
    public void setCaptchaProducer(Producer kaptchaProducer) {
        this.kaptchaProducer = kaptchaProducer;
    }

    @Autowired
    public CifCodeService cifcodeService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req) {
        ModelAndView view = new ModelAndView("mytest");
        //total 是模板的全局变量，可以直接访问
        view.addObject("total","mytest1");

        Pet pet = new Pet();
        pet.setName("dogdog");
        pet.setAge(10);
        String petString = Json.toJson(pet);

        Pet pet2 = ObjectUtils.clone(pet);
        view.addObject("mypet",petString);

        view.addObject("myobj1",pet);
        view.addObject("myobj2",pet2);

        Person p1 = new Person();
        p1.setName("zhy");
        p1.setAge(10);
        p1.setPet(pet);
        //Person p2 = ObjectUtils.clone(p1);
        Person p2 = null;
        try {
            p2 = p1.deepClone();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        p2.setName("zzh");
        p2.getPet().setName("dog2");
        String p1Str = Json.toJson(p1);
        String p2Str = Json.toJson(p2);
        view.addObject("p1",p1Str);
        view.addObject("p2",p2Str);

        List<Integer> arrInt = new ArrayList<Integer>();
        arrInt.add(123);
        arrInt.add(456);
        arrInt.add(789);
        view.addObject("arr",arrInt);

        //org.springframework.beans.factory.xml.
        CifCode cifCode = cifcodeService.testSelect(1);
        view.addObject("cifCodeType",cifCode.getTYPENAME());
        view.addObject("cifCode",Json.toJson(cifCode));


        return view;
    }

    @RequestMapping(value = "/myimg", method = {RequestMethod.GET, RequestMethod.POST})
    public void myImg(HttpServletRequest req, HttpServletResponse resp){
        //BufferedImage bimg =Images.createCaptcha(R.captchaNumber(6));

        BufferedImage bimg =Images.createCaptcha("胖五是好人", 0, 0, null, "#FFF", "微软雅黑");
        try {
            ImageIO.write(bimg,"jpeg",resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/myimg2", method = {RequestMethod.GET, RequestMethod.POST})
    public void myImg2(HttpServletRequest req, HttpServletResponse resp){
        String capText = kaptchaProducer.createText();
        //request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = null;

        try {
            ImageIO.write(bi, "jpg", resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
