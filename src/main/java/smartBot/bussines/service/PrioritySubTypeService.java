package smartBot.bussines.service;

import smartBot.bean.PrioritySubType;

public interface PrioritySubTypeService extends Service<PrioritySubType>{

    PrioritySubType getSubtype(Integer subtype);
}
