package ant.dubbo.service.impl;

import ant.dubbo.api.UserService;
import ant.dubbo.dao.TradeDetailDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    TradeDetailDao tradeDetailDao ;
    @Override
    public String getName(String name) {
        logger.info("入参"+name);
        return tradeDetailDao.queryById(Long.valueOf(name)).toString();
    }
}
