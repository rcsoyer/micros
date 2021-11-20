package org.acme.micros.webapp.domain;

import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import static java.util.Objects.nonNull;

/**
 * In order to support insert and update in batch with MySQL this custom ID generator is needed
 */
@SuppressWarnings("unused")
public class AssignedIdentityGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(final SharedSessionContractImplementor sessionContractImplementor,
                                 final Object obj) {
        if (obj instanceof AbstractEntity entity && nonNull(entity.getId())) {
            return entity.getId();
        }

        return super.generate(sessionContractImplementor, obj);
    }
}
