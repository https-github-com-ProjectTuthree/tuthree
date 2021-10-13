package project.tuthree.mapper;

import org.mapstruct.Mapper;
import project.tuthree.domain.BookMark;
import project.tuthree.dto.BookmarkDTO;

@Mapper(componentModel = "spring")
public interface BookMarkMapper extends GenericMapper<BookmarkDTO, BookMark> {
    BookmarkDTO toDto(BookMark bookMark);

    BookMark toEntity(BookmarkDTO bookmarkDTO);
}
