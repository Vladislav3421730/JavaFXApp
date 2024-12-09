package com.example.frontend.mappers;

import com.example.frontend.dto.EquipmentDTO;
import com.example.frontend.models.entities.Equipment;
import javafx.scene.control.Button;

public class MapToEquipmentDTO {
    public static EquipmentDTO mapToEquipmentDTO(Equipment equipment){
        EquipmentDTO equipmentDTO=new EquipmentDTO();
        equipmentDTO.setId(equipment.getId());
        equipmentDTO.setCost(equipment.getCost());
        equipmentDTO.setName(equipment.getName());
        equipmentDTO.setDescription(equipment.getDescription());
        equipmentDTO.setPurchase_date(equipment.getPurchase_date());

        Button buttonChange =new Button();
        buttonChange.setText("Редактировать");
        buttonChange.setId(String.valueOf(equipment.getId()));
        equipmentDTO.setChange(buttonChange);

        Button buttonDelete=new Button();
        buttonDelete.setText("Удалить");
        buttonDelete.setId(String.valueOf(equipment.getId()));
        equipmentDTO.setDelete(buttonDelete);
        return equipmentDTO;
    }

}
