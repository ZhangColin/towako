package com.towako.assistedreproduction.medicalmemberpicture;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
@AllArgsConstructor
public class PictureGroupDto {
    private String group;
    private List<MedicalMemberPictureDto> data;
}
