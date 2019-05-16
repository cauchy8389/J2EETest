package testspringcloud.trace.saleservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("pay-service")
public interface PayService {

	@RequestMapping(method = RequestMethod.GET, value = "/pay")
	void doPay(@RequestParam("money") BigDecimal money);
}
