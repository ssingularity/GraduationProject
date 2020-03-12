package com.sjtu.project.userservice.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserAuthorityTest {

    @Test
    public void testGetAuthority() {
        UserAuthority userAuthority = new UserAuthority();
        Assert.assertEquals("USER", userAuthority.getAuthority());
    }
}