package testspringcloud.cloud.poi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/poi")
public class PoiController {
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
    public ResponseEntity<byte[]> endUserTemplateExport() {
        // 获取登录用户信息
        //SupplierShiroUser shiroUser  = (SupplierShiroUser) SecurityUtils.getSubject().getPrincipal();
//        BaseResponseVo<EndUserTemplateBo> templateResult = guestFeignClient.endUserTemplateExport();
//        EndUserTemplateBo templateBo = templateResult.getData();
        //导出
        byte[] bytes = ExcelRepBuilder.GuestTemplateBytes("sheet1");
        //response.getOutputStream().write();
        return ExcelRepBuilder.entity("Eu客户导入模板",bytes);
    }
}
