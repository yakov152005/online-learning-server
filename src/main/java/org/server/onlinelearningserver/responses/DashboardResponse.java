package org.server.onlinelearningserver.responses;
import lombok.Getter;
import lombok.Setter;
import org.server.onlinelearningserver.dtos.DashboardDto;

@Getter
@Setter
public class DashboardResponse extends BasicResponse {
    private DashboardDto dashboardDto;


    public DashboardResponse(boolean success, String error, DashboardDto dashboardDto) {
        super(success, error);
        this.dashboardDto = dashboardDto;
    }

    public DashboardResponse(boolean success, String error) {
        super(success, error);
    }
}
