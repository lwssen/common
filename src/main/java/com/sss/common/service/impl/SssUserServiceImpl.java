package com.sss.common.service.impl;

import com.sss.common.entity.SssUser;
import com.sss.common.dao.ISssUserDao;
import com.sss.common.service.ISssUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sss
 * @since 2019-09-06
 */
@Service
public class SssUserServiceImpl extends ServiceImpl<ISssUserDao, SssUser> implements ISssUserService {

}
