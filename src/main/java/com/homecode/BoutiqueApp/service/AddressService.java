package com.homecode.BoutiqueApp.service;


import com.homecode.BoutiqueApp.model.Address;
import com.homecode.BoutiqueApp.model.dto.AddressDTO;

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
