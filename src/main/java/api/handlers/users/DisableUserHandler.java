package api.handlers.users;

import api.business.entities.User;
import api.business.services.interfaces.IUserService;
import api.contracts.base.BaseResponse;
import api.contracts.base.ErrorCodes;
import api.contracts.base.ErrorDto;
import api.contracts.users.DisableUserRequest;
import api.handlers.base.BaseHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DisableUserHandler extends BaseHandler<DisableUserRequest, BaseResponse> {
    @Inject
    private IUserService userService;

    @Override
    public ArrayList<ErrorDto> validate(DisableUserRequest request) {
        ArrayList<ErrorDto> errors = new ArrayList<>();

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            errors.add(new ErrorDto("Not authenticated.", ErrorCodes.AUTHENTICATION_ERROR));
            return errors;
        }

        if (!SecurityUtils.getSubject().hasRole("administrator")) {
            errors.add(new ErrorDto("Permission denied.", ErrorCodes.AUTHENTICATION_ERROR));
            return errors;
        }

        User user = userService.get(request.id);
        if (user == null) {
            errors.add(new ErrorDto("User does not found", ErrorCodes.NOT_FOUND));
        }
        return errors;
    }

    @Override
    public BaseResponse handleBase(DisableUserRequest request) {

        User user = userService.get(request.id);
        userService.disableUser(request.id);
        userService.logoutUser(user.getId());
        return createResponse();
    }

    @Override
    public BaseResponse createResponse() {
        return new BaseResponse();
    }
}