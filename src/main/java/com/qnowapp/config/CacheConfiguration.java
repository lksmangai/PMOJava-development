package com.qnowapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.qnowapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.qnowapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.qnowapp.domain.User.class.getName());
            createCache(cm, com.qnowapp.domain.Authority.class.getName());
            createCache(cm, com.qnowapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.qnowapp.domain.QnowUser.class.getName());
            createCache(cm, com.qnowapp.domain.UserContact.class.getName());
            createCache(cm, com.qnowapp.domain.UserContact.class.getName() + ".groupMembers");
            createCache(cm, com.qnowapp.domain.UserContact.class.getName() + ".imEmployees");
            createCache(cm, com.qnowapp.domain.City.class.getName());
            createCache(cm, com.qnowapp.domain.StateMaster.class.getName());
            createCache(cm, com.qnowapp.domain.Country.class.getName());
            createCache(cm, com.qnowapp.domain.ImEmployee.class.getName());
            createCache(cm, com.qnowapp.domain.ImEmployee.class.getName() + ".userContacts");
            createCache(cm, com.qnowapp.domain.Department.class.getName());
            createCache(cm, com.qnowapp.domain.Skills.class.getName());
            createCache(cm, com.qnowapp.domain.EmployeeStatus.class.getName());
            createCache(cm, com.qnowapp.domain.SkillCategory.class.getName());
            createCache(cm, com.qnowapp.domain.ImProjects.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectTypeId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectStatusId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectBoardId.class.getName());
            createCache(cm, com.qnowapp.domain.Company.class.getName());
            createCache(cm, com.qnowapp.domain.Company.class.getName() + ".projectCostCenterIds");
            createCache(cm, com.qnowapp.domain.ProjectCostCenterId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectCostCenterId.class.getName() + ".companies");
            createCache(cm, com.qnowapp.domain.ProjectBucketId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectMaingoalId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectSubgoalId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectBusinessgoalId.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectInitiativeId.class.getName());
            createCache(cm, com.qnowapp.domain.GroupMembers.class.getName());
            createCache(cm, com.qnowapp.domain.GroupMembers.class.getName() + ".roles");
            createCache(cm, com.qnowapp.domain.GroupMembers.class.getName() + ".userContacts");
            createCache(cm, com.qnowapp.domain.Roles.class.getName());
            createCache(cm, com.qnowapp.domain.Roles.class.getName() + ".groupMembers");
            createCache(cm, com.qnowapp.domain.ProjectAllocation.class.getName());
            createCache(cm, com.qnowapp.domain.SkillExpertise.class.getName());
            createCache(cm, com.qnowapp.domain.SkillTable.class.getName());
            createCache(cm, com.qnowapp.domain.ImTimesheet.class.getName());
            createCache(cm, com.qnowapp.domain.OpportunityPriorityId.class.getName());
            createCache(cm, com.qnowapp.domain.BacklogPractice.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectTheme.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectClass.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectVertical.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectRoles.class.getName());
            createCache(cm, com.qnowapp.domain.FileStorage.class.getName());
            createCache(cm, com.qnowapp.domain.ProjectTag.class.getName());
            createCache(cm, com.qnowapp.domain.TagType.class.getName());
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
