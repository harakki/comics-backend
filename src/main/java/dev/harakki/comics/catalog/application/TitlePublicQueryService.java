package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.catalog.infrastructure.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TitlePublicQueryService implements TitlePublicQueryApi {

    private final TitleRepository titleRepository;

    @Override
    public List<TitleShortInfo> getTitleShortInfoByIds(Collection<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        return titleRepository.findAllById(ids).stream()
                .map(title -> new TitleShortInfo(title.getId(), title.getName(), title.getSlug()))
                .toList();
    }

}
