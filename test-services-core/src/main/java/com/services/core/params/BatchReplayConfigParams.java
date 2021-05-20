package com.services.core.params;

import com.services.core.entity.Record;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
public class BatchReplayConfigParams extends BaseParams{

    private int selectedHost;

    private List<String> httpEntrancePatterns;

    private Set<Record> recordList;

    private Boolean enableMock;

    private List<Date> date;
}
