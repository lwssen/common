package com.sss.common.service.impl;

import com.sss.common.entity.SssUserRole;
import com.sss.common.dao.ISssUserRoleDao;
import com.sss.common.service.ISssUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色绑定表 服务实现类
 * </p>
 *
 * @author sss
 * @since 2019-09-06
 */
@Service
public class SssUserRoleServiceImpl extends ServiceImpl<ISssUserRoleDao, SssUserRole> implements ISssUserRoleService {

}
