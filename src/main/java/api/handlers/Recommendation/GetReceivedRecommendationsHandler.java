package api.handlers.Recommendation;

import api.business.services.interfaces.IRecommendationService;
import api.contracts.recommendations.GetRecommendationsRequest;
import api.contracts.recommendations.GetRecommendationsResponse;
import api.contracts.base.ErrorDto;
import api.handlers.base.BaseHandler;
import api.helpers.Validator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

@Stateless
public class GetReceivedRecommendationsHandler extends BaseHandler<GetRecommendationsRequest, GetRecommendationsResponse> {
    @Inject
    private IRecommendationService recommendationService;

    @Override
    public ArrayList<ErrorDto> validate(GetRecommendationsRequest request) {

        return Validator.checkAllNotNullAndIsAuthenticated(request);
    }

    @Override
    public GetRecommendationsResponse handleBase(GetRecommendationsRequest request) {
        GetRecommendationsResponse response = createResponse();

        response.requests = recommendationService.getReceivedRecommendationRequests();
        return response;
    }

    @Override
    public GetRecommendationsResponse createResponse() {
        return new GetRecommendationsResponse();
    }
}
