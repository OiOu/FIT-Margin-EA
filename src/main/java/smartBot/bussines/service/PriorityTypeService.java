package smartBot.bussines.service;

import smartBot.bean.PriorityType;

public interface PriorityTypeService extends Service<PriorityType>{

    PriorityType getType(Integer type);
}
