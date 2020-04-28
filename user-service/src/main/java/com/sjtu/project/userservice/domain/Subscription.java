package com.sjtu.project.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sjtu.project.common.exception.ServiceException;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.userservice.dao.SubscriptionRepository;
import com.sjtu.project.userservice.service.DataSourceClient;
import com.sjtu.project.userservice.service.PermissionInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import static com.sjtu.project.common.response.ResultCode.REPLICATE_SUBSCRIPTION;
import static com.sjtu.project.common.response.ResultCode.SUBSCTIPTION_STATUS_ERROR;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscription")
public class Subscription {
    @Id
    protected String id;

    protected String username;

    @NotBlank
    protected String resourceId;

    @NotBlank
    protected String resourceOwnerId;

    private SubscriptionStatus status = SubscriptionStatus.PENDING;

    void doReject(){

    }

    void doApprove() {
        ContextUtil.ctx.getBean(PermissionInfoService.class).addDataSourcePermission(username, resourceId);
        ContextUtil.ctx.getBean(DataSourceClient.class).authorize(resourceId, username);
    }

    public void update(SubscriptionStatus targetStatus) {
        if (status != SubscriptionStatus.PENDING) {
            throw new ServiceException(SUBSCTIPTION_STATUS_ERROR);
        }
        if (targetStatus == SubscriptionStatus.APPROVED) {
            doApprove();
        } else if (targetStatus == SubscriptionStatus.REJECTED) {
            doReject();
        }
        this.status = targetStatus;
    }

    public void verifySelf() {
        //除非之前已经拒绝了该用户的请求，不然无法新增新的订阅信息
        ContextUtil.ctx.getBean(SubscriptionRepository.class)
                .findAllByResourceIdAndUsername(resourceId, username)
                .stream()
                .filter(Subscription::isPendingOrApproved)
                .findAny()
                .ifPresent(subscription -> {
                    throw new ServiceException(REPLICATE_SUBSCRIPTION);
                });
    }

    @JsonIgnore
    private boolean isPendingOrApproved() {
        return status == SubscriptionStatus.PENDING ||
                status == SubscriptionStatus.APPROVED;
    }
}
