package project.tuthree.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import project.tuthree.domain.post.PostFaq;
import project.tuthree.domain.user.Admin;
import project.tuthree.dto.AdminDTO;
import project.tuthree.dto.PostfaqDTO;

public interface AdminMapper extends GenericMapper<AdminDTO, Admin> {

    AdminDTO toDto(Admin admin);

    Admin toEntity(AdminDTO adminDTO);

}
