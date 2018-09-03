package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.ZoneLevel;
import smartBot.bean.jpa.ZoneLevelEntity;
import smartBot.bussines.service.ZoneLevelService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.ZoneLevelServiceMapper;
import smartBot.data.repository.jpa.ZoneLevelJpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class ZoneLevelServiceImpl implements ZoneLevelService {
    private static final Log logger = LogFactory.getLog(ZoneLevelServiceImpl.class);

    @Resource
    private ZoneLevelJpaRepository zoneLevelJpaRepository;

    @Resource
    private ZoneLevelServiceMapper zoneLevelServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Override
    public ZoneLevel findById(Integer id) {
        // Try to get marginRate from cache to avoid additional DB query
        ZoneLevel zoneLevel = serverCache.getZoneLevelFromCache(id);
        if (zoneLevel == null) {
            ZoneLevelEntity zoneLevelEntity = zoneLevelJpaRepository.getById(id);
            if (zoneLevelEntity != null) {
                zoneLevel = zoneLevelServiceMapper.mapEntityToBean(zoneLevelEntity);
                serverCache.setZoneLevelToCache(zoneLevel);
            }
        }
        return zoneLevel;
    }

    @Override
    public List<ZoneLevel> findAll() {
        List<ZoneLevel> zoneLevel = serverCache.getZoneLevelFromCache();
        if (zoneLevel == null || zoneLevel.isEmpty()) {
            List<ZoneLevelEntity> zoneLevelEntities = (List<ZoneLevelEntity>) zoneLevelJpaRepository.findAllEnabled();
            if (zoneLevelEntities != null) {
                zoneLevel = zoneLevelServiceMapper.mapEntitiesToBeans(zoneLevelEntities);
                serverCache.setZoneLevelToCache(zoneLevel);
            }
        }
        return zoneLevel;
    }

    @Override
    public ZoneLevel save(ZoneLevel bean) {
        return null;
    }

    @Override
    public void delete(ZoneLevel bean) {

    }

    @Override
    public void delete(Integer id) {

    }
}
