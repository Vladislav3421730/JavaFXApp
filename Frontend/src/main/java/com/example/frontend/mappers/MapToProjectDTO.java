package com.example.frontend.mappers;

import com.example.frontend.dto.ProjectDTO;
import com.example.frontend.models.entities.Project;
import javafx.scene.control.Button;

public class MapToProjectDTO {
    public static ProjectDTO mapToProjectDTO(Project project){

        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setBudget(project.getBudget());
        projectDTO.setEnd_date(project.getEnd_date());
        projectDTO.setStart_date(project.getStart_date());
        projectDTO.setEmployees(project.getEmployees());
        projectDTO.setEquipmentList(project.getEquipmentList());
        projectDTO.setPatents(project.getPatents());
        projectDTO.setStatus(project.getStatus());

        Button buttonInfo =new Button();
        buttonInfo.setText("Информация");
        projectDTO.setInfo(buttonInfo);
        return projectDTO;

    }
}
