package io.metersphere.platform.domain;

import io.metersphere.platform.domain.response.rest.ZentaoRestUserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetIssueResponse extends ZentaoResponse {
    @Getter
    @Setter
    public static class Issue {
        private String id;
        private String title;
        private String steps;
        private String status;
        private ZentaoRestUserResponse.User openedBy;
        private String openedDate;
        private String deleted;
        private String product;
        private String openedBuild;
        private ZentaoRestUserResponse.User assignedTo;
    }
}
