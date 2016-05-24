package api.handlers.cottages;

import api.business.entities.Cottage;
import api.business.services.interfaces.ICottageService;
import api.contracts.base.ErrorCodes;
import api.contracts.base.ErrorDto;
import api.contracts.cottages.UpdateCottageRequest;
import api.contracts.cottages.UpdateCottageResponse;
import api.contracts.dto.CottageDto;
import api.handlers.base.BaseHandler;
import api.helpers.Validator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class UpdateCottageHandler extends BaseHandler<UpdateCottageRequest, UpdateCottageResponse> {
    @Inject
    private ICottageService cottageService;

    @Override
    public ArrayList<ErrorDto> validate(UpdateCottageRequest request) {
        Subject currentUser = SecurityUtils.getSubject();

        ArrayList<ErrorDto> errors = Validator.checkAllNotNull(request);
        errors.addAll(Validator.checkAllNotNull(request.cottage));

        if (!errors.isEmpty()) {
            return errors;
        }

        if (!currentUser.isAuthenticated()) {
            errors.add(new ErrorDto("Not authenticated.", ErrorCodes.AUTHENTICATION_ERROR));
        }

        if (!currentUser.hasRole("administrator")) {
            errors.add(new ErrorDto("Insufficient permissions.", ErrorCodes.AUTHENTICATION_ERROR));
        }

        if (cottageService.get(request.cottage.id) == null) {
            errors.add(new ErrorDto("cottage not found", ErrorCodes.NOT_FOUND));
        }

        return errors;
    }

    @Override
    public UpdateCottageResponse handleBase(UpdateCottageRequest request) {
        UpdateCottageResponse response = createResponse();

        Cottage cottage = cottageService.get(request.cottage.id);

        cottage.setTitle(request.cottage.title);
        cottage.setBedcount(request.cottage.beds);
        cottage.setImageurl(request.cottage.image);

        response.cottage = new CottageDto(cottage);

        return response;
    }

    @Override
    public UpdateCottageResponse createResponse() {
        return new UpdateCottageResponse();
    }
}
