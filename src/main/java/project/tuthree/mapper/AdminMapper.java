package project.tuthree.mapper;

import project.tuthree.domain.user.Admin;
import project.tuthree.dto.user.AdminDTO;

public interface AdminMapper extends GenericMapper<AdminDTO, Admin> {

    AdminDTO toDto(Admin admin);

    Admin toEntity(AdminDTO adminDTO);

}
