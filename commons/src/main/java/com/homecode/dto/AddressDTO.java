package com.homecode.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private String address1;


    private String address2;

    private String city;


    private String postcode;


    private String country;
}
