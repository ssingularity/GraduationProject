package com.sjtu.project.userservice.controller;

import com.sjtu.project.common.response.Result;
import com.sjtu.project.common.util.ResultUtil;
import com.sjtu.project.common.util.UserUtil;
import com.sjtu.project.userservice.dao.SubscriptionRepository;
import com.sjtu.project.userservice.domain.Subscription;
import com.sjtu.project.userservice.domain.SubscriptionStatus;
import com.sjtu.project.userservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ssingualrity
 */
@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @RequestMapping(value = "/subscription", method = RequestMethod.POST)
    public Result<Subscription> subscribe(@RequestBody Subscription subscription) {

        subscription.setUsername(UserUtil.getUsername());
        return ResultUtil.success(subscriptionService.subscribeResource(subscription));
    }

    @RequestMapping(value = "/subscription/{subscriptionId}/status", method = RequestMethod.PUT)
    public Result<String> approveSubscription(@PathVariable String subscriptionId,
                                              @RequestParam("status") SubscriptionStatus targetStatus) {
        subscriptionService.updateSubscription(subscriptionId, targetStatus);
        return ResultUtil.success();
    }


    @RequestMapping(value = "/subscription", method = RequestMethod.GET)
    public Result<List<Subscription>> getUserSubscription() {
        return ResultUtil.success(subscriptionRepository.findAllByUsername(UserUtil.getUsername()));
    }
}
