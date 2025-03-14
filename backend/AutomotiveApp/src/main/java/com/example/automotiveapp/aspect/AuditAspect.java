package com.example.automotiveapp.aspect;

import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import com.example.automotiveapp.service.utils.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// L8 - first aspect usage
@Component
@Aspect
class AuditAspect {
    private static final Logger logger = LoggerFactory.getInstance();

    @After("execution(* com.example.automotiveapp.service.FriendshipService.addFriend(..)) || " +
            "execution(* com.example.automotiveapp.service.InvitationService.sendInvitation(..)) || " +
            "execution(* com.example.automotiveapp.service.InvitationService.acceptInvitation(..)) || " +
            "execution(* com.example.automotiveapp.service.InvitationService.rejectInvitation(..)) || " +
            "execution(* com.example.automotiveapp.service.UserService.updateUser(..)) || " +
            "execution(* com.example.automotiveapp.service.UserService.deleteAccount(..))")
    public void auditUserActions(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        String userEmail = SecurityUtils.getCurrentUserEmail();
        logger.log("[AUDIT] User " + userEmail + ": " + methodName);
    }
}
