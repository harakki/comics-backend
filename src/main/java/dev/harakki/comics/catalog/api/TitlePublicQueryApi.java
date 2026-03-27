package dev.harakki.comics.catalog.api;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TitlePublicQueryApi {

    /**
     * Get short info for a collection of titles by their ids
     * @param ids Collection of title unique identifiers
     * @return List of {@link TitleShortInfo}
     */
    List<TitleShortInfo> getTitleShortInfoByIds(Collection<UUID> ids);

}
