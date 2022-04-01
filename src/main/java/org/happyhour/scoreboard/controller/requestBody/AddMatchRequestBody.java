package org.happyhour.scoreboard.controller.requestBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.happyhour.scoreboard.model.AddMatchRequestModel;

import java.util.Date;
import java.util.List;

@Data
public class AddMatchRequestBody {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    List<AddMatchRequestModel> addMatchRequestModels;
}
