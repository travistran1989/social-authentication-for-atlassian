package com.pawelniewiadomski.jira.openid.authentication.services;

import com.atlassian.fugue.Either;
import com.atlassian.sal.api.message.I18nResolver;
import com.pawelniewiadomski.jira.openid.authentication.activeobjects.OpenIdDao;
import com.pawelniewiadomski.jira.openid.authentication.providers.Errors;
import com.pawelniewiadomski.jira.openid.authentication.activeobjects.OpenIdProvider;
import com.pawelniewiadomski.jira.openid.authentication.providers.ProviderType;
import com.pawelniewiadomski.jira.openid.authentication.rest.responses.ProviderBean;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

@Service
public class ProviderValidator {

    @Autowired protected OpenIdDao openIdDao;

    @Autowired protected I18nResolver i18nResolver;

    @Autowired protected OpenIdDiscoveryDocumentProvider discoveryDocumentProvider;

    @Autowired
    protected ProviderTypeFactory providerTypeFactory;

    @Nonnull
    public Either<Errors, OpenIdProvider> validateAndCreate(ProviderBean providerBean) {
        final ProviderType providerType = providerTypeFactory.getProviderTypeById(providerBean.getProviderType());

        return providerType.createOrUpdate(null, providerBean);
    }

    @Nonnull
    public Either<Errors, OpenIdProvider> validateAndUpdate(@Nullable OpenIdProvider provider, @Nonnull ProviderBean providerBean) throws InvocationTargetException, IllegalAccessException {
        final ProviderType providerType = providerTypeFactory.getProviderTypeById(providerBean.getProviderType());

        return providerType.createOrUpdate(provider, providerBean);
    }
}
