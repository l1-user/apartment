package com.wanhe.apartment;

import com.wanhe.apartment.entity.SysRole;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.mapper.SysRoleMapper;
import com.wanhe.apartment.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class SpringbootProjectApplicationTests {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    void testSelect() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        sysUsers.forEach(System.out::println);
    }

    @Test
    void testSelectById(){
        SysUser sysUser = sysUserMapper.selectById(1);
        System.out.println(sysUser);
    }

    @Test
    void testInsert(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername("admin");
        sysUser.setPassword("123456");
        sysUser.setRealName("管理员");
        sysUser.setPhone("13888888888");
        sysUser.setEmail("2993070897@163.com");
        sysUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        sysUser.setStatus((byte) 1);
        sysUserMapper.insert(sysUser);
    }

    @Test
    void testUpdate(){
        SysUser sysUser = new SysUser();
        sysUser.setId(4L);
        sysUser.setUsername("admin");
        sysUser.setPassword("2023112101");
        sysUser.setRealName("管理员");
        sysUser.setPhone("13888888888");
        sysUser.setEmail("2993070897@163.com");
        sysUser.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        sysUserMapper.updateById(sysUser);
    }

    @Test
    void testDelete(){
        sysUserMapper.deleteById(4L);
    }

    @Test
    void testInsertRole(){
        SysRole sysRole = new SysRole();
        sysRole.setId(8L);
        sysRole.setRoleCode("admin");
        sysRole.setRoleName("管理员");
        sysRole.setRoleDesc("系统管理员");
        sysRole.setDataScope((byte) 1);
        sysRole.setSortOrder(1);
        sysRole.setStatus((byte) 1);
        sysRole.setRemark("管理员角色");
        sysRole.setCreatedBy("admin");
        sysRole.setUpdatedBy("admin");
        sysRoleMapper.insert(sysRole);
    }

}
