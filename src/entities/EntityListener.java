package entities;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import entities.BaseEntity;

/**
 * Basic entity listener that populates necessary fields of {@link BaseEntity}.
 * 
 * @author himanshu
 *
 */
public class EntityListener
{

    public String getUser()
    {
        return "PLATFORM";
    }

    @PrePersist
    public void prePersist(final BaseEntity entity)
    {
        if (entity == null)
            return;

        if (entity.getCreatedBy() == null)
        {
            entity.setCreatedBy(getUser());
        }

        if (entity.getCreatedTime() == null)
        {
            entity.setCreatedTime(new Date());
        }
    }

    @PreUpdate
    public void preUpate(final BaseEntity entity)
    {
        if (entity == null)
            return;

        if (entity.getUpdatedBy() == null)
        {
            entity.setUpdatedBy(getUser());
        }

        if (entity.getUpdatedTime() == null)
        {
            entity.setUpdatedTime(new Date());
        }
    }
}
