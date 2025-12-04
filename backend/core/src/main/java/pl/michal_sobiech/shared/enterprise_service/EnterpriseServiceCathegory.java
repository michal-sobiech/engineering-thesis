package pl.michal_sobiech.engineering_thesis.enterprise_service;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public enum EnterpriseServiceCathegory {
    BEAUTY_AND_WELLNESS,
    HAIRDRESSER,
    BARBER,

    FAMILY_MEDICINE,
    INTERNIST,
    PEDIATRICS,
    PSYCHIATRY,
    STOMATOGLOGY,
    GYNECOLOGY,

    HANDYMAN,
    PLUMBER,
    ELECTRICICAN;

    public static final BiMap<EnterpriseServiceCathegory, String> enterpriseServiceCathegoryToString = HashBiMap
            .create(Map.ofEntries(
                    Map.entry(EnterpriseServiceCathegory.BEAUTY_AND_WELLNESS, "BEAUTY_AND_WELLNESS"),
                    Map.entry(EnterpriseServiceCathegory.HAIRDRESSER, "HAIRDRESSER"),
                    Map.entry(EnterpriseServiceCathegory.BARBER, "BARBER"),
                    Map.entry(EnterpriseServiceCathegory.FAMILY_MEDICINE, "FAMILY_MEDICINE"),
                    Map.entry(EnterpriseServiceCathegory.INTERNIST, "INTERNIST"),
                    Map.entry(EnterpriseServiceCathegory.PEDIATRICS, "PEDIATRICS"),
                    Map.entry(EnterpriseServiceCathegory.PSYCHIATRY, "PSYCHIATRY"),
                    Map.entry(EnterpriseServiceCathegory.STOMATOGLOGY, "STOMATOLOGY"),
                    Map.entry(EnterpriseServiceCathegory.GYNECOLOGY, "GYNECOLOGY"),
                    Map.entry(EnterpriseServiceCathegory.HANDYMAN, "HANDYMAN"),
                    Map.entry(EnterpriseServiceCathegory.PLUMBER, "PLUMBER"),
                    Map.entry(EnterpriseServiceCathegory.ELECTRICICAN, "ELECTRICIAN")));
};
