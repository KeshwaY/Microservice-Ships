package com.github.ships.ships;

import java.util.Collection;

public interface GenericCRUDService<PostDto, GetDto, PutDto, ResponseDto> {
    ResponseDto create(PostDto postDto);
    GetDto get(String id);
    Collection<GetDto> getAll();
    GetDto update(String id, PutDto putDto);
    ResponseDto delete(String id);
}
