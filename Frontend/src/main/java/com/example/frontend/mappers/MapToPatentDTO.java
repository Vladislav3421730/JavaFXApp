package com.example.frontend.mappers;

import com.example.frontend.dto.PatentDTO;
import com.example.frontend.models.entities.Patent;
import javafx.scene.control.Button;

public class MapToPatentDTO {
    public static PatentDTO mapToPatentDTO(Patent patent){
        PatentDTO patentDTO=new PatentDTO();
        patentDTO.setId(patent.getId());
        patentDTO.setName(patent.getName());
        patentDTO.setCost(patent.getCost());
        patentDTO.setCompany(patent.getCompany());
        patentDTO.setPurchase_date(patent.getPurchase_date());
        patentDTO.setValidity_period(patent.getValidity_period());

        Button buttonChange =new Button();
        buttonChange.setText("Редактировать");
        buttonChange.setId(String.valueOf(patent.getId()));
        patentDTO.setChange(buttonChange);

        Button buttonDelete=new Button();
        buttonDelete.setText("Удалить");
        buttonDelete.setId(String.valueOf(patent.getId()));
        patentDTO.setDelete(buttonDelete);

        return patentDTO;

    }
}
