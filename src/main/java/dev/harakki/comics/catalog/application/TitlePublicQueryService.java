package dev.harakki.comics.catalog.application;

import dev.harakki.comics.catalog.api.TitlePublicQueryApi;
import dev.harakki.comics.catalog.api.TitleShortInfo;
import dev.harakki.comics.catalog.domain.Tag;
import dev.harakki.comics.catalog.domain.Title;
import dev.harakki.comics.catalog.infrastructure.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
                .map(title -> new TitleShortInfo(title.getId(), title.getName(), title.getMainCoverMediaId(), title.getSlug()))
                .toList();
    }

    @Override
    public Map<UUID, Set<UUID>> getTitleTagIdsByIds(Collection<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }

        return titleRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(
                        Title::getId,
                        title -> title.getTags().stream()
                                .map(Tag::getId)
                                .collect(Collectors.toSet())
                ));
    }

}
