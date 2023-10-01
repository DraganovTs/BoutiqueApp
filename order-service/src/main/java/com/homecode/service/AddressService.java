package com.homecode.service;


import com.homecode.commons.dto.AddressDTO;
import com.homecode.model.Address;

public class AddressService {
    public static AddressDTO mapToDTO(Address shipmentAddress) {
        if (shipmentAddress != null) {
            return new AddressDTO(
                    shipmentAddress.getAddress1(),
                    shipmentAddress.getAddress2(),
                    shipmentAddress.getCity(),
                    shipmentAddress.getPostcode(),
                    shipmentAddress.getCountry()
            );
        }
        return null;
    }
}
